import java.time.LocalDate;

/**
 * Created by Elliot Åberg Fält
 * Date: 2020-10-09
 * Time: 12:45
 * Project: Best Gym Ever
 * Copyright: MIT
 */
public class Person {
    private String name;
    private String id;
    private LocalDate date;

    public Person (String name, String id, LocalDate date) {
        this.name = name;
        this.id = id;
        this.date = date;
    }

    public boolean checkIfMember(LocalDate today) {
        return today.isBefore(date.plusYears(1));
    }

    public boolean checkIfMember() {
        return LocalDate.now().isBefore(date.plusYears(1));
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }
}
