import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Elliot Åberg Fält
 * Date: 2020-10-09
 * Time: 10:25
 * Project: Best Gym Ever
 * Copyright: MIT
 */
class DatabaseTest {
    Database database = new Database();
    List<String> file = new ArrayList<>();
    List<Person> people;

    private void createList() {
        file.add("7603021234, Alhambra Aromes");
        file.add("2019-07-01");
        file.add("8104021234, Bear Belle");
        file.add("2018-12-02");
        file.add("8512021234, Chamade Coriola");
        file.add("2017-03-12");
        file.add("7608021234, Diamanda Djedi");
        file.add("2020-01-30");
        file.add("7605021234, Elmer Ekorrsson");
        file.add("2010-04-07");
        file.add("7911061234, Fritjoff Flacon");
        file.add("1999-12-16");
        file.add("7512166544, Greger Ganache");
        file.add("2020-03-23");
        file.add("5711121234, Hilmer Heur");
        file.add("2019-08-18");
        file.add("8906138493, Ida Idylle");
        file.add("2017-03-07");
        file.add("9902149834, Jicky Juul");
        file.add("2018-09-27");
        file.add("4604151234, Kadine Karlsson");
        file.add("2018-01-09");
        file.add("9110261234, Liu Lingren");
        file.add("2018-02-15");
        file.add("7907281234, Mitsuko Mayotte");
        file.add("2018-12-22");
        file.add("7805211234, Nahema Ninsson");
        file.add("2020-08-04");
    }

    @Test
    public final void readFromFileTest() {
        createList();
        assertEquals(database.readFromFile(), file);
        assertNotEquals(database.readFromFile(), null);
    }

    @Test
    public final void readInputTest() {
        database.test = true;

        assertEquals(database.readInput("Test 1", "7603021234"), "7603021234");
        assertNotEquals(database.readInput("Test 2", "null"), "7603021234");
    }

    @Test
    public final void checkIfMemberTest() {
        database.test = true;
        database.createPeopleList(database.readFromFile());
        assertTrue(database.checkIfMember(database.getPerson("7608021234")));
        assertFalse(database.checkIfMember(database.getPerson("Alhambra Aromes")));
        assertFalse(database.checkIfMember(database.getPerson(LocalDate.parse("1999-12-16"))));
        assertFalse(database.checkIfMember(null));
    }

    @Test
    public final void hasTrainedTest() {
        database.test = true;
        database.createPeopleList(database.readFromFile());
        database.hasTrained(database.getPerson("Diamanda Djedi"), "2018-12-14");
        database.hasTrained(database.getPerson("7805211234"), "2020-06-30");
    }
}