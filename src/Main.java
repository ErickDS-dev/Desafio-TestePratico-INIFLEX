import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Employee> employees = new ArrayList<>(List.of(
                new Employee("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"), "Operador"),
                new Employee("João", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"), "Operador"),
                new Employee("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"), "Coordenador"),
                new Employee("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"), "Diretor"),
                new Employee("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.68"), "Recepcionista"),
                new Employee("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"), "Operador"),
                new Employee("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"), "Contador"),
                new Employee("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"), "Gerente"),
                new Employee("Heloísa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"), "Eletricista"),
                new Employee("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"), "Gerente")
        ));

        removeEmployeeByName(employees);

        printEmployeesFormatted(employees);

        increaseSalaries(employees);

        Map<String, List<Employee>> groupedByPosition = groupByPosition(employees);

        printGroupedByPosition(groupedByPosition);

        printEmployeesBirthday(employees, Month.OCTOBER, Month.DECEMBER);

        printOldestEmployee(employees);

        printEmployeesAlphabeticalOrder(employees);

        System.out.println("\nTotal dos salários dos funcionários: " + getTotalSalaries(employees));

        System.out.println("\nQuantidade de salários mínimos ganhos por cada funcionário:");
        printSalariesInMinimuns(employees, new BigDecimal("1212.00"));






    }


    private static void removeEmployeeByName(List<Employee> employees) {
        employees.removeIf(employee -> employee.getName().equals("João"));
    }

    private static void printEmployeesFormatted(List<Employee> employees) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        for (Employee employee : employees) {
            String formattedDate = employee.getBirthDate().format(outputFormatter);
            String formattedSalary = decimalFormat.format(employee.getSalary());
            System.out.println(employee.getName() + " - " + formattedDate + " - " +
                    formattedSalary + " - " + employee.getPosition());
        }
    }

    private static void increaseSalaries(List<Employee> employees) {
        for (Employee employee : employees) {
            BigDecimal currentSalary = employee.getSalary();
            employee.setSalary(currentSalary.multiply(BigDecimal.valueOf(1.1)));
        }
    }

    private static Map<String, List<Employee>> groupByPosition(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    private static void printGroupedByPosition(Map<String, List<Employee>> groupedByPosition) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        groupedByPosition.forEach((position, employees) -> {
            System.out.println("Função: " + position);
            for (Employee employee : employees) {
                String formattedDate = employee.getBirthDate().format(outputFormatter);
                String formattedSalary = decimalFormat.format(employee.getSalary());
                System.out.println("  " + employee.getName() + " - " + formattedDate + " - " +
                        formattedSalary + " - " + employee.getPosition());
            }
            System.out.println();
        });
    }

    private static void printEmployeesBirthday(List<Employee> employees, Month... months) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        Set<Month> targetMonths = new HashSet<>(Arrays.asList(months));

        employees.stream()
                .filter(employee -> targetMonths.contains(employee.getBirthDate().getMonth()))
                .forEach(employee -> {
                    String formattedDate = employee.getBirthDate().format(outputFormatter);
                    String formattedSalary = decimalFormat.format(employee.getSalary());
                    System.out.println(employee.getName() + " - " + formattedDate + " - " +
                            formattedSalary + " - " + employee.getPosition());
                });
    }

    private static void printOldestEmployee(List<Employee> employees) {
        Employee oldestEmployee = employees.stream()
                .max(Comparator.comparing(employee -> Period.between(employee.getBirthDate(), LocalDate.now()).getYears()))
                .orElse(null);

        if (oldestEmployee != null) {
            int age = Period.between(oldestEmployee.getBirthDate(), LocalDate.now()).getYears();
            System.out.println("Nome: " + oldestEmployee.getName() + " - Idade: " + age);
        } else {
            System.out.println("Nenhum funcionário encontrado.");
        }
    }

    private static void printEmployeesAlphabeticalOrder(List<Employee> employees) {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .forEach(employee -> {
                    String formattedDate = employee.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    System.out.println(employee.getName() + " - " + formattedDate + " - " +
                            employee.getSalary() + " - " + employee.getPosition());
                });
    }

    private static BigDecimal getTotalSalaries(List<Employee> employees) {
        return employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void printSalariesInMinimuns(List<Employee> employees, BigDecimal minimumSalary) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        employees.forEach(employee -> {
            BigDecimal salariesInMinimuns = employee.getSalary().divide(minimumSalary, 2, BigDecimal.ROUND_DOWN);
            String formattedDate = employee.getBirthDate().format(outputFormatter);
            String formattedSalary = decimalFormat.format(employee.getSalary());
            System.out.println(employee.getName() + " - " + formattedDate + " - " +
                    formattedSalary + " - " + employee.getPosition() +
                    " - Salários mínimos: " + salariesInMinimuns);
        });
    }
}
