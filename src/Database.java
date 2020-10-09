import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Elliot Åberg Fält
 * Date: 2020-10-09
 * Time: 10:24
 * Project: Best Gym Ever
 * Copyright: MIT
 */
public class Database {
    protected boolean test = false;
    protected Scanner scanner;
    List<Person> people = new ArrayList<>();

    public List<String> readFromFile() {
        List<String> readFile = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                readFile.add(line);
            }
            return readFile;
        } catch (IOException e) {
            System.out.println("Kan inte avläsa filen.");
            e.getStackTrace();
            System.exit(0);
            return null;
        }
    }

    public String readInput(String prompt, String testParameter) {

        if (test) {
            scanner = new Scanner(testParameter);
        } else {
            scanner = new Scanner(System.in);
        }

        while (true) {
            System.out.println(prompt);
            String s = scanner.nextLine();
            if (!s.equals("")) {
                return s;
            } else {
                System.out.println("Indatan får inte vara tomt");
            }
        }
    }

    public void createPeopleList(List<String> file) {
        LocalDate date;
        String id;
        String name;
        for (int i = 0; i < file.size(); i++) {
            int indexOfComma = file.get(i).indexOf(',');
            if (file.get(i).contains(",")) {
                name = file.get(i).substring(indexOfComma + 2);
                id = file.get(i).substring(0, indexOfComma);
                date = LocalDate.parse(file.get(i + 1));
                people.add(new Person(name, id, date));
            }
        }
    }

    public Person getPerson(String nameOrId) {
        for (Person person : people) {
            if (person.getName().equals(nameOrId) || person.getId().equals(nameOrId)) {
                return person;
            }
        }
        return null;
    }

    public Person getPerson(LocalDate date) {
        for (Person person : people) {
            if (person.getDate().equals(date)) {
                return person;
            }
        }
        return null;
    }

    // For testing purposes
    protected boolean checkIfMember(Person person) {
        LocalDate date;
        if (test) {
            date = LocalDate.parse("2020-10-09");
        } else {
            date = LocalDate.now();
        }
        if (person != null) {
            return person.checkIfMember(date);
        } else {
            return false;
        }
    }

    public void mainProgram() {
        createPeopleList(readFromFile());
//        for (Person person : people) {
//            System.out.println("Namn: " + person.getName() + "\nID: " + person.getId() + "\nSenast betalat årsavgift: " + person.getDate() +
//                    "\nÄr nuvarande medlem: " + person.checkIfMember() + "\n");
//        }
        String s = readInput("Skriv in personnummer eller namn för den person du söker efter", null);
        Person person = getPerson(s);
        if (person == null) {
            System.out.println("Den personen är inte medlem");
            return;
        }
        if (!person.checkIfMember()) {
            System.out.println("Den personen är en förre detta medlem");
        } else {
            System.out.println("Medlemens namnm: " + person.getName() + "\nPersonnummer: " + person.getId());
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        database.mainProgram();
    }
}
