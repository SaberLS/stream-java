package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class zad_3 {
  public static void main(String[] args) {
    List<Employee> employees = Arrays.asList(
        new Employee("Anna Kowalska", "IT", 8000.00),
        new Employee("Jan Nowak", "IT", 7500.00),
        new Employee("Maria Wiśniewska", "HR", 4500.00),
        new Employee("Piotr Zieliński", "IT", 9000.00),
        new Employee("Katarzyna Lewandowska", "HR", 4800.00),
        new Employee("Tomasz Kamiński", "Sprzedaż", 5500.00),
        new Employee("Agnieszka Wójcik", "Sprzedaż", 6000.00),
        new Employee("Michał Kowalczyk", "IT", 8500.00),
        new Employee("Ewa Szymańska", "HR", 5000.00),
        new Employee("Paweł Dąbrowski", "Sprzedaż", 5800.00),
        new Employee("Magdalena Król", "Marketing", 6500.00),
        new Employee("Krzysztof Piotrowski", "Marketing", 7000.00),
        new Employee("Joanna Grabowska", "HR", 4200.00),
        new Employee("Adam Pawlak", "Sprzedaż", 6200.00));

    zad_3.solution(employees).forEach((dept, avg) -> System.out.println(dept + " = " + avg));
  }

  static Map<String, Double> solution(List<Employee> employees) {
    return employees.stream()
        .collect(Collectors.groupingBy(
            Employee::getDepartment,
            Collectors.collectingAndThen(
                Collectors.averagingDouble(Employee::getSalary),
                avg -> Math.round(avg * 100.0) / 100.0)))
        .entrySet().stream()
        .filter(avg -> avg.getValue() > 5000)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}

@Getter
@Setter
@AllArgsConstructor
class Employee {
  private String name;
  private String department;
  private double salary;

  // konstruktor, gettery, settery
}

/*
 * ## Zadanie 3 - ŚREDNIE (3 punkty)
 * ### Analiza danych pracowników
 **
 * Opis:**
 * Masz listę pracowników. Używając Stream API, wykonaj następujące operacje:
 * 1. Pogrupuj pracowników według działu
 * 2. Dla każdego działu oblicz średnią pensję
 * 3. Zwróć mapę, gdzie kluczem jest nazwa działu, a wartością średnia pensja w
 * tym dziale (zaokrąglona do 2 miejsc po przecinku)
 * 4. Uwzględnij tylko działy, gdzie średnia pensja przekracza 5000 zł
 **
 * Dane wejściowe:**
 * ```java
 * class Employee {
 * private String name;
 * private String department;
 * private double salary;
 *
 * // konstruktor, gettery, settery
 * }
 *
 * List<Employee> employees = Arrays.asList(
 * new Employee("Anna Kowalska", "IT", 8000.00),
 * new Employee("Jan Nowak", "IT", 7500.00),
 * new Employee("Maria Wiśniewska", "HR", 4500.00),
 * new Employee("Piotr Zieliński", "IT", 9000.00),
 * new Employee("Katarzyna Lewandowska", "HR", 4800.00),
 * new Employee("Tomasz Kamiński", "Sprzedaż", 5500.00),
 * new Employee("Agnieszka Wójcik", "Sprzedaż", 6000.00),
 * new Employee("Michał Kowalczyk", "IT", 8500.00),
 * new Employee("Ewa Szymańska", "HR", 5000.00),
 * new Employee("Paweł Dąbrowski", "Sprzedaż", 5800.00),
 * new Employee("Magdalena Król", "Marketing", 6500.00),
 * new Employee("Krzysztof Piotrowski", "Marketing", 7000.00),
 * new Employee("Joanna Grabowska", "HR", 4200.00),
 * new Employee("Adam Pawlak", "Sprzedaż", 6200.00)
 * );
 * ```
 **
 * Oczekiwany wynik:**
 * ```java
 * {
 * "IT" = 8250.00,
 * "Sprzedaż" = 5875.00,
 * "Marketing" = 6750.00
 * }
 * ```
 * (Dział HR ma średnią 4625.00, więc nie powinien być uwzględniony)
 **
 * Wymagania:**
 * - Użyj Stream API
 * - Użyj `Collectors.groupingBy()`
 * - Użyj `Collectors.averagingDouble()`
 * - Zaokrąglij wyniki do 2 miejsc po przecinku
 * - Filtruj działy ze średnią pensją > 5000
 * - Zwróć `Map<String, Double>`
 */
