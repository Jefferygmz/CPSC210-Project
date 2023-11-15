package model;

import model.exceptions.EmptyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TeamListTest {
    private TeamList testTeamList;

    @BeforeEach
    void runBefore() {
        testTeamList = new TeamList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testTeamList.getSize());
        try {
            testTeamList.getTeamList();
            fail("Exception should be thrown");
        } catch (EmptyListException e) {
            //expected
        }
    }

    @Test
    void testAddTeamToListWithNoMedals() {
        Team testTeam = new Team("CA");

        assertTrue(testTeamList.addTeamToList(testTeam));

        assertEquals(0, testTeamList.sumGold());
        assertEquals(0, testTeamList.sumSilver());
        assertEquals(0, testTeamList.sumBronze());

        try {
            assertEquals(Arrays.asList(testTeam), testTeamList.getTeamList());
        } catch (EmptyListException e) {
            //expected
        }
    }

    @Test
    void testAddTeamsToListsWIthMedals() {
        Player testPlayer1 = new Player("Jack");
        Player testPlayer2 = new Player("Frank");
        Team testTeam1 = new Team("US");
        Team testTeam2 = new Team("CA");

        testPlayer1.addGold();
        testPlayer1.addGold();
        testPlayer1.addBronze();

        testPlayer2.addGold();
        testPlayer2.addSilver();

        testTeam1.addPlayerToTeam(testPlayer1, testTeam1);
        testTeam2.addPlayerToTeam(testPlayer2, testTeam2);

        assertTrue(testTeamList.addTeamToList(testTeam1));
        assertTrue(testTeamList.addTeamToList(testTeam2));

        assertEquals(3, testTeamList.sumGold());
        assertEquals(1, testTeamList.sumSilver());
        assertEquals(1, testTeamList.sumBronze());

        try {
            assertEquals(Arrays.asList(testTeam1, testTeam2), testTeamList.getTeamList());
        } catch (EmptyListException e) {
            //expected
        }
    }

    @Test
    void testAddSameTeamToList() {
        Team testTeam = new Team("CA");

        assertTrue(testTeamList.addTeamToList(testTeam));
        assertFalse(testTeamList.addTeamToList(testTeam));

        try {
            assertEquals(Arrays.asList(testTeam), testTeamList.getTeamList());
        } catch (EmptyListException e) {
            //expected
        }
    }

    @Test
    void testSelectTeamInList() {
        Team testTeam1 = new Team("CA");
        Team testTeam2 = new Team("CN");

        assertTrue(testTeamList.addTeamToList(testTeam1));
        assertTrue(testTeamList.addTeamToList(testTeam2));

        assertEquals(0, testTeamList.indexOfTeam("CA"));
        assertEquals(1, testTeamList.indexOfTeam("CN"));
    }

    @Test
    void testSelectTeamNotInList() {
        Team testTeam1 = new Team("CA");
        Team testTeam2 = new Team("CN");

        assertTrue(testTeamList.addTeamToList(testTeam1));

        assertEquals(-1, testTeamList.indexOfTeam("CN"));
        assertEquals(0, testTeamList.indexOfTeam("CA"));

        assertTrue(testTeamList.addTeamToList(testTeam2));
        assertEquals(1, testTeamList.indexOfTeam("CN"));
    }

    @Test
    void testGetTeam() {
        Team testTeam1 = new Team("CA");
        Team testTeam2 = new Team("CN");

        assertTrue(testTeamList.addTeamToList(testTeam1));
        assertTrue(testTeamList.addTeamToList(testTeam2));

        assertEquals("CA", testTeamList.getTeam(0).getTeamName());
        assertEquals("CN", testTeamList.getTeam(1).getTeamName());
    }
}
