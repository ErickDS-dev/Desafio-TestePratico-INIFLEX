import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Employee extends Person {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
    private BigDecimal salary;
    private String position;

    public Employee(String name, LocalDate birthDate,BigDecimal salary, String jobFunction) {
        super(name, birthDate);
        this.salary = salary;
        this.position = jobFunction;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

}
