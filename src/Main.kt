import kotlinx.coroutines.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Point(val x: Double, val y: Double)

fun Point.distanceTo(other: Point): Double {
    return sqrt((this.x - other.x).pow(2) + (this.y - other.y).pow(2))
}

suspend fun kMeansParallel(
    points: List<Point>, k: Int, maxIterations: Int = 100, chunkSize: Int = 10
): Map<Point, List<Point>> = coroutineScope {
    if (points.isEmpty() || k <= 0) return@coroutineScope emptyMap()

    var centroids = points.shuffled().take(k)
    var assignments = mutableMapOf<Point, MutableList<Point>>()

    repeat(maxIterations) {
        val chunks = points.chunked(chunkSize)

        val deferredResult = chunks.map { chunk ->
            async(Dispatchers.Default) {
                chunk.groupBy { point ->
                    centroids.minByOrNull { it.distanceTo(point) }
                }
            }
        }
        val partialResults = deferredResult.awaitAll()
        val newAssignment = mutableMapOf<Point, MutableList<Point>>()


        for (partialMap in partialResults) {
            for ((centroid, points) in partialMap) {
                newAssignment.getOrPut(centroid as Point) { mutableListOf() }.addAll(points)
            }
        }

        val newCentroid = newAssignment.map { (oldCentroid, clusterPoints) ->
            async(Dispatchers.Default) {
                if (clusterPoints.isEmpty()) {
                    oldCentroid
                } else {
                    val avgX = clusterPoints.map { it.x }.average()
                    val avgY = clusterPoints.map { it.y }.average()
                    Point(avgX, avgY)
                }
            }
        }.awaitAll()

        if (newCentroid == centroids) return@repeat

        centroids = newCentroid
        assignments = newAssignment
    }
    return@coroutineScope assignments
}

fun main() = runBlocking {
    val randomPoints = List(1000000) {
        Point(
            x = Random.nextDouble(0.0, 100.0), y = Random.nextDouble(0.0, 100.0)
        )
    }

    val k = 3
    val clusters = kMeansParallel(randomPoints, k = k)

    println("\nPodsumowanie klastrowania (K=$k):")
    println("=".repeat(40))

    var clusterIndex = 1

    clusters.forEach { centroid, members ->
        println("Klaster #$clusterIndex")
        println("Środek (Centroid): [x=${"%.2f".format(centroid.x)}, y=${"%.2f".format(centroid.y)}]")
        println("Liczba punktów: ${members.size}")
        println("Punkty: ${members.joinToString { "(${"%.1f".format(it.x)}; ${"%.1f".format(it.y)})" }}")
        println()
        clusterIndex++
    }
}