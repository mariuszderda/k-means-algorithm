# Klasteryzacja K-Średnich z wykorzystaniem Korutyn w Kotlinie

Projekt ten implementuje algorytm klasteryzacji K-średnich w języku Kotlin, wykorzystując korutyny do równoległego przetwarzania w celu zwiększenia wydajności. Został stworzony, aby zademonstrować, jak efektywnie używać współbieżności w Kotlinie do zadań związanych z przetwarzaniem danych.

## Cechy

-   **Równoległy algorytm K-średnich**: Wykorzystuje korutyny (`kotlinx.coroutines`) do zrównoleglenia procesu przypisywania punktów do centroidów oraz ponownego obliczania ich położeń, co czyni go wydajnym dla dużych zbiorów danych.
-   **Przejrzyste struktury danych**: Używa prostej klasy danych `Point` do reprezentowania punktów 2D.
-   **Konfigurowalność**: Umożliwia ustawienie liczby klastrów (k) oraz maksymalnej liczby iteracji.

## Jak uruchomić

Do uruchomienia projektu potrzebne jest środowisko programistyczne kompatybilne z Kotliem (np. IntelliJ IDEA) lub skonfigurowany kompilator Kotlina.

1.  **Sklonuj repozytorium (jeśli dotyczy):**
    ```bash
    git clone https://github.com/twoja-nazwa-uzytkownika/k_means_kotlin.git
    cd k_means_kotlin/k_means
    ```
    *(Uwaga: Zastąp `twoja-nazwa-uzytkownika` i `k_means_kotlin` rzeczywistymi wartościami, jeśli projekt jest hostowany na GitHubie.)*

2.  **Otwórz w IntelliJ IDEA:**
    *   Otwórz IntelliJ IDEA.
    *   Wybierz `File` -> `Open` i przejdź do katalogu `k_means`.
    *   IntelliJ IDEA powinien automatycznie skonfigurować projekt Kotlin.

3.  **Zbuduj i uruchom:**
    *   Znajdź plik `src/Main.kt`.
    *   Kliknij prawym przyciskiem myszy na `Main.kt` i wybierz `Run 'MainKt'`.

    Alternatywnie, możesz zbudować i uruchomić projekt z wiersza poleceń:

    ```bash
    # Zakładając, że masz zainstalowany kompilator kotlinc
    kotlinc src/Main.kt -include-runtime -d k_means.jar
    java -jar k_means.jar
    ```

## Struktura kodu

-   `Main.kt`:
    -   `data class Point(val x: Double, val y: Double)`: Reprezentuje punkt w przestrzeni 2D.
    -   `fun Point.distanceTo(other: Point): Double`: Funkcja rozszerzająca, która oblicza odległość euklidesową między dwoma punktami.
    -   `suspend fun kMeansParallel(...)`: Główna funkcja implementująca algorytm K-średnich z przetwarzaniem równoległym.
    -   `fun main()`: Punkt wejściowy aplikacji, odpowiedzialny za generowanie losowych punktów, wywołanie `kMeansParallel` i wyświetlenie wyników klasteryzacji.

## Przykładowe wyjście

Program wyświetli podsumowanie klasteryzacji, w tym położenie centroidów i liczbę punktów w każdym klastrze.

```
Podsumowanie klastrowania (K=3):
========================================
Klaster #1
Środek (Centroid): [x=XX.XX, y=YY.YY]
Liczba punktów: N
Punkty: (X.X; Y.Y), (X.X; Y.Y), ...

Klaster #2
Środek (Centroid): [x=XX.XX, y=YY.YY]
Liczba punktów: N
Punkty: (X.X; Y.Y), (X.X; Y.Y), ...

Klaster #3
Środek (Centroid): [x=XX.XX, y=YY.YY]
Liczba punktów: N
Punkty: (X.X; Y.Y), (X.X; Y.Y), ...
```

*(Uwaga: Dokładne współrzędne centroidów i przypisania punktów będą się różnić przy każdym uruchomieniu z powodu losowej inicjalizacji i generowania punktów.)*
