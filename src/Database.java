import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final List<Person> people = new ArrayList<>();

    /**
     * Skapar en lista av alla personer från inskickad fil.
     * @param file filen som blir avläst.
     */
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

    /**
     * Läser av filen customers.txt och returnerar en lista av alla rader.
     * @return En lista med alla rader.
     */
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

    /**
     * Läser indatan som användaren skriver in och returnerar den som en sträng.
     * @param prompt Prompten för vad man ska skriva för något.
     * @param testParameter Testparameter för tester som byter ut användaren. Är null i huvudprogrammet.
     * @return Indatan som en sträng
     */
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

    /**
     * Tar in namn eller personnummer och returnerar personen som letas efter.
     * @param nameOrId Namnet eller personnummret av personen som letas efter.
     * @return Personen som letas efter.
     */
    public Person getPerson(String nameOrId) {
        for (Person person : people) {
            if (person.getName().equalsIgnoreCase(nameOrId) || person.getId().equals(nameOrId)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Tar in datumet för senaster gången personen betalade sin årsavgift
     * och returnerar personen som letas efter om dehittas.
     * @param date Datumet för senaste gången personen betalade sin årsavgift.
     * @return Personen som letas efter.
     */
    public Person getPerson(LocalDate date) {
        for (Person person : people) {
            if (person.getDate().equals(date)) {
                return person;
            }
        }
        return null;
    }

    // For testing purposes
    //Kollar om personen är en medlem, används bara för tester då koden själv använder Person-klassens inbyggda
    //metod för att kolla medlemskap.
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

    /**
     * Skriver i en loggbok(textfil) om en person tränat, använder bara medlemmar.
     * @param person Medlemmen som tränar
     * @param optionalTestDate Datum för test som byter nuvarande datum. Är null i huvudprogrammet.
     */
    public void hasTrained(Person person, String optionalTestDate) {
        LocalDate date;
        if(test) {
            date = LocalDate.parse(optionalTestDate);
        } else {
            date = LocalDate.now();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("logbook.txt", true))) {
            bw.append(person.toString()).append("Tränade: ").append(String.valueOf(date)).append("\r\n\r\n");
        } catch (IOException e) {
            System.out.println("Kan inte skriva till filen");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Skapar listan av personer utifrån filen customers.txt och läser sedan indata från användaren.
     * Letar därefter om personen finns i listan och skriver ut om dem inte är medlem eller är en före detta medlem.
     * Om personen är en medlem så skriv att de tränat.
     */
    public void mainProgram() {
        createPeopleList(readFromFile());

        String s = readInput("Skriv in personnummer eller namn för den person du söker efter", null);
        Person person = getPerson(s);
        if (person == null) {
            System.out.println("Den personen är inte medlem");
            return;
        }
        if (!person.checkIfMember()) {
            System.out.println("Den personen är en förre detta medlem");
        } else {
            System.out.println("Personen är en nuvarande medlem");
            hasTrained(person, null);
        }
    }

    public static void main(String[] args) {
        Database database = new Database();
        database.mainProgram();
    }
}
