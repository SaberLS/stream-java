package com.example;

import java.util.Arrays;
import java.util.List;

public class zad_1 {
  public static void main(String[] args) {
    zad_1.example();
  }

  public static void example() {
    System.out.println(
        zad_1.solution(
            Arrays.asList(15, 8, 23, 4, 42, 16, 11, 30, 7, 19, 2, 35, 28, 13, 6)));
  }

  public static List<Integer> solution(List<Integer> numbers) {
    return numbers.stream()
        .filter(num -> num % 2 == 0)
        .sorted()
        .toList();
  }
}

/*
 * ## Zadanie 1 - BARDZO ŁATWE (1.5 punkta)
 * ### Filtrowanie liczb parzystych
 **
 * Opis:**
 * Otrzymujesz listę liczb całkowitych. Używając Stream API, wyfiltruj tylko
 * liczby parzyste, posortuj je rosnąco i zwróć jako listę.
 **
 * Dane wejściowe:**
 * ```java
 * List<Integer> numbers = Arrays.asList(15, 8, 23, 4, 42, 16, 11, 30, 7, 19, 2,
 * 35, 28, 13, 6);
 * ```
 **
 * Oczekiwany wynik:**
 * ```java
 * [2, 4, 6, 8, 16, 28, 30, 42]
 * ```
 **
 * Wymagania:**
 * - Użyj Stream API
 * - Zastosuj operację `filter()`
 * - Użyj `sorted()`
 * - Zwróć wynik jako `List<Integer>`
 */
