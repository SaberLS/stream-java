package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class zad_4 {
  public static void main(String[] args) {
  }

  public static Map<String, List<ProductStats>> solution(List<Order> orders) {
    return zad_4.orderCountByCategoryAndProduct(orders).entrySet()
        .stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey, // category
            e -> zad_4.topProducts(e.getValue(), 3)));
  }

  public static Map<String, Map<String, Long>> orderCountByCategoryAndProduct(List<Order> orders) {
    return zad_4.completedOrders(orders.stream())
        .flatMap(zad_4::uniqueProductsPerOrder)
        .collect(zad_4.countProductsByCategory());

    // zad_4.orderCountByCategoryAndProduct(orders).forEach((category, products) ->
    // {
    // System.out.println(category + ":");
    // products.forEach((name, occurences) -> System.out.println("\t" + name + " = "
    // + occurences));
    // });
    // OUT:
    /*
     * Dekoracje:
     * * Dywan = 2
     * * Oświetlenie:
     * * Lampa LED = 3
     * Elektronika:
     * * Mysz Logitech = 3
     * * Laptop Dell = 3
     * * Klawiatura Razer = 3
     * * Monitor Samsung = 3
     * Meble:
     * * Biurko Ikea = 3
     * * Krzesło Herman Miller = 3
     */
  }

  public static Stream<Order> completedOrders(Stream<Order> orders) {
    return orders
        .filter(o -> "ZREALIZOWANE".equals(o.getStatus()));
  }

  public static Stream<Map.Entry<String, String>> uniqueProductsPerOrder(Order order) {
    return order.getItems().stream()
        .map(item -> // OrderItem
        Map.entry(item.getCategory(), item.getProductName()))
        .distinct(); // jeden produkt = jedno zamówienie
  }

  public static Collector<Map.Entry<String, String>, ? // dowolny typ
      , Map<String, Map<String, Long>>> countProductsByCategory() {
    return Collectors.groupingBy(
        Map.Entry::getKey, // category
        Collectors.groupingBy( // ile razy się pojawił produkt
            Map.Entry::getValue, // product
            Collectors.counting()));
  }

  public static List<ProductStats> topProducts(
      Map<String, Long> productCounts, int limit) {

    return productCounts.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .limit(limit)
        .map(product -> new ProductStats(product.getKey(), product.getValue()))
        .toList();
  }

  public static void example() {
    List<Order> orders = Arrays.asList(
        new Order("ORD001", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
            new OrderItem("Mysz Logitech", "Elektronika", 2, 150),
            new OrderItem("Biurko Ikea", "Meble", 1, 800))),
        new Order("ORD002", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
            new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
            new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500))),
        new Order("ORD003", "W TRAKCIE", Arrays.asList(
            new OrderItem("Smartfon iPhone", "Elektronika", 1, 4500))),
        new Order("ORD004", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Monitor Samsung", "Elektronika", 2, 1200),
            new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
            new OrderItem("Lampa LED", "Oświetlenie", 3, 120))),
        new Order("ORD005", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
            new OrderItem("Biurko Ikea", "Meble", 1, 800),
            new OrderItem("Lampa LED", "Oświetlenie", 2, 120))),
        new Order("ORD006", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Mysz Logitech", "Elektronika", 1, 150),
            new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
            new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500))),
        new Order("ORD007", "ANULOWANE", Arrays.asList(
            new OrderItem("Tablet Samsung", "Elektronika", 1, 2200))),
        new Order("ORD008", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
            new OrderItem("Lampa LED", "Oświetlenie", 1, 120),
            new OrderItem("Dywan", "Dekoracje", 1, 350))),
        new Order("ORD009", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Mysz Logitech", "Elektronika", 3, 150),
            new OrderItem("Biurko Ikea", "Meble", 1, 800))),
        new Order("ORD010", "ZREALIZOWANE", Arrays.asList(
            new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
            new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500),
            new OrderItem("Dywan", "Dekoracje", 1, 350))));

    zad_4.printSolution(zad_4.solution(orders));
  }

  public static void printSolution(Map<String, List<ProductStats>> data) {
    data.forEach((category, products) -> {
      System.out.println(category + ":");
      products.forEach(p -> System.out.println("\t" + p));
    });
  }
}

@Setter
@Getter
@AllArgsConstructor
class ProductStats {
  private String productName;
  private long orderCount; // liczba zamówień zawierających ten produkt

  // konstruktor, gettery, settery

  @Override
  public String toString() {
    return this.getProductName() + ": " + this.getOrderCount();
  }
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class OrderItem {
  private String productName;
  private String category;
  private int quantity;
  private double price;

  // konstruktor, gettery, settery
}

@Getter
@Setter
@AllArgsConstructor
@ToString
class Order {
  private String orderId;
  private String status; // "ZREALIZOWANE", "W TRAKCIE", "ANULOWANE"
  private List<OrderItem> items;

  // konstruktor, gettery, settery
}

/*
 * ## Zadanie 4 - TRUDNE (3.5 punkta)
 * ### Analiza zamówień e-commerce
 **
 * Opis:**
 * Masz listę zamówień z systemu e-commerce. Każde zamówienie zawiera listę
 * produktów. Używając Stream API, wykonaj złożoną analizę:
 *
 * 1. Znajdź wszystkie zamówienia ze statusem "ZREALIZOWANE"
 * 2. Z tych zamówień wyciągnij wszystkie produkty
 * 3. Pogrupuj produkty według kategorii
 * 4. Dla każdej kategorii znajdź 3 najczęściej zamawianych produktów (według
 * liczby wystąpień)
 * 5. Zwróć mapę, gdzie:
 * - Klucz: nazwa kategorii
 * - Wartość: lista obiektów zawierających nazwę produktu i liczbę zamówień
 **
 * Dane wejściowe:**
 * ```java
 * class OrderItem {
 * private String productName;
 * private String category;
 * private int quantity;
 * private double price;
 *
 * // konstruktor, gettery, settery
 * }
 *
 * class Order {
 * private String orderId;
 * private String status; // "ZREALIZOWANE", "W TRAKCIE", "ANULOWANE"
 * private List<OrderItem> items;
 *
 * // konstruktor, gettery, settery
 * }
 *
 * List<Order> orders = Arrays.asList(
 * new Order("ORD001", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
 * new OrderItem("Mysz Logitech", "Elektronika", 2, 150),
 * new OrderItem("Biurko Ikea", "Meble", 1, 800)
 * )),
 * new Order("ORD002", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
 * new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
 * new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500)
 * )),
 * new Order("ORD003", "W TRAKCIE", Arrays.asList(
 * new OrderItem("Smartfon iPhone", "Elektronika", 1, 4500)
 * )),
 * new Order("ORD004", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Monitor Samsung", "Elektronika", 2, 1200),
 * new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
 * new OrderItem("Lampa LED", "Oświetlenie", 3, 120)
 * )),
 * new Order("ORD005", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
 * new OrderItem("Biurko Ikea", "Meble", 1, 800),
 * new OrderItem("Lampa LED", "Oświetlenie", 2, 120)
 * )),
 * new Order("ORD006", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Mysz Logitech", "Elektronika", 1, 150),
 * new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
 * new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500)
 * )),
 * new Order("ORD007", "ANULOWANE", Arrays.asList(
 * new OrderItem("Tablet Samsung", "Elektronika", 1, 2200)
 * )),
 * new Order("ORD008", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
 * new OrderItem("Lampa LED", "Oświetlenie", 1, 120),
 * new OrderItem("Dywan", "Dekoracje", 1, 350)
 * )),
 * new Order("ORD009", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Mysz Logitech", "Elektronika", 3, 150),
 * new OrderItem("Biurko Ikea", "Meble", 1, 800)
 * )),
 * new Order("ORD010", "ZREALIZOWANE", Arrays.asList(
 * new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
 * new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500),
 * new OrderItem("Dywan", "Dekoracje", 1, 350)
 * ))
 * );
 * ```
 **
 * Oczekiwany wynik:**
 * ```java
 * class ProductStats {
 * private String productName;
 * private long orderCount; // liczba zamówień zawierających ten produkt
 *
 * // konstruktor, gettery, settery
 * }
 *
 * Map<String, List<ProductStats>> result = {
 * "Elektronika" = [
 * ProductStats("Laptop Dell", 3),
 * ProductStats("Monitor Samsung", 3),
 * ProductStats("Mysz Logitech", 3)
 * ],
 * "Meble" = [
 * ProductStats("Biurko Ikea", 3),
 * ProductStats("Krzesło Herman Miller", 3)
 * ],
 * "Oświetlenie" = [
 * ProductStats("Lampa LED", 3)
 * ],
 * "Dekoracje" = [
 * ProductStats("Dywan", 2)
 * ]
 * }
 * ```
 **
 * Wymagania:**
 * - Użyj Stream API (w tym `flatMap()` do spłaszczenia struktury)
 * - Filtruj zamówienia po statusie "ZREALIZOWANE"
 * - Wyciągnij wszystkie produkty ze wszystkich zamówień
 * - Pogrupuj produkty według kategorii
 * - Policz wystąpienia każdego produktu
 * - Dla każdej kategorii wybierz maksymalnie 3 najpopularniejsze produkty
 * - Sortuj produkty w kategorii według liczby zamówień malejąco
 * - Zwróć `Map<String, List<ProductStats>>`
 *
 */
