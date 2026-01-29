package com.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class zad_2 {
  public static void main(String[] args) {
    zad_2.example();
  }

  public static List<String> solution(List<Product> products) {
    return products.stream()
        .filter(product -> product.getCategory().equals("Elektronika")
            && product.getPrice() > 1000)
        .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
        .map(Product::getName)
        .toList();
  }

  public static void example() {
    List<Product> products = Arrays.asList(
        new Product("Laptop Dell", "Elektronika", 3500.00),
        new Product("Mysz Logitech", "Elektronika", 150.00),
        new Product("Monitor Samsung", "Elektronika", 1200.00),
        new Product("Klawiatura Razer", "Elektronika", 450.00),
        new Product("Smartfon iPhone", "Elektronika", 4500.00),
        new Product("Krzesło biurowe", "Meble", 800.00),
        new Product("Słuchawki Sony", "Elektronika", 350.00),
        new Product("Tablet Samsung", "Elektronika", 2200.00),
        new Product("Biurko", "Meble", 1500.00),
        new Product("Drukarka HP", "Elektronika", 900.00));

    System.out.println(zad_2.solution(products));
  }
}

@Getter
@Setter
@AllArgsConstructor
class Product {
  private String name;
  private String category;
  private double price;

  // konstruktor, gettery, settery
}

/*
 * ## Zadanie 2 - ŁATWE (2 punkty)
 * ### Przetwarzanie listy produktów
 **
 * Opis:**
 * Masz listę produktów. Używając Stream API, znajdź wszystkie produkty z
 * kategorii "Elektronika", które kosztują więcej niż 1000 zł, posortuj je
 * według ceny malejąco i zwróć listę ich nazw.
 **
 * Dane wejściowe:**
 * ```java
 * class Product {
 * private String name;
 * private String category;
 * private double price;
 *
 * // konstruktor, gettery, settery
 * }
 *
 * List<Product> products = Arrays.asList(
 * new Product("Laptop Dell", "Elektronika", 3500.00),
 * new Product("Mysz Logitech", "Elektronika", 150.00),
 * new Product("Monitor Samsung", "Elektronika", 1200.00),
 * new Product("Klawiatura Razer", "Elektronika", 450.00),
 * new Product("Smartfon iPhone", "Elektronika", 4500.00),
 * new Product("Krzesło biurowe", "Meble", 800.00),
 * new Product("Słuchawki Sony", "Elektronika", 350.00),
 * new Product("Tablet Samsung", "Elektronika", 2200.00),
 * new Product("Biurko", "Meble", 1500.00),
 * new Product("Drukarka HP", "Elektronika", 900.00)
 * );
 * ```
 **
 * Oczekiwany wynik:**
 * ```java
 * ["Smartfon iPhone", "Laptop Dell", "Tablet Samsung", "Monitor Samsung"]
 * ```
 **
 * Wymagania:**
 * - Użyj Stream API
 * - Filtruj po kategorii "Elektronika"
 * - Filtruj produkty droższe niż 1000 zł
 * - Sortuj według ceny malejąco
 * - Zwróć listę nazw produktów
 */
