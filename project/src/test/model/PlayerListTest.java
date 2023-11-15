package model;

import model.exceptions.EmptyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerListTest {
    private PlayerList testPlayerList;

    @BeforeEach
    void runBefore() {
        testPlayerList = new PlayerList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testPlayerList.getSize());
        try {
            testPlayerList.getPlayerList();
            fail("Exception should be thrown");
        } catch (EmptyListException e) {
            //expected
        }
    }


    @Test
    void testAddPlayerToListWithNoMedals() {
        Player testPlayer = new Player("Cici");

        assertTrue(testPlayerList.addPlayerToList(testPlayer));

        try {
            assertEquals(Arrays.asList(testPlayer), testPlayerList.getPlayerList());
            //expected
        } catch (EmptyListException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    void testAddPlayerToListsWIthMultipleMedals() {
        Player testPlayer = new Player("John");
        testPlayer.addGold();
        testPlayer.addGold();
        testPlayer.addBronze();

        try {
            assertTrue(testPlayerList.addPlayerToList(testPlayer));
            assertEquals(Arrays.asList(testPlayer), testPlayerList.getPlayerList());
        } catch (EmptyListException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    void testAddPlayerToListsWIthSamePlayer() {
        Player testPlayer = new Player("Jack");
        testPlayer.addGold();
        testPlayer.addSilver();
        testPlayer.addBronze();

        try {
            assertTrue(testPlayerList.addPlayerToList(testPlayer));
            assertFalse(testPlayerList.addPlayerToList(testPlayer));
            assertEquals(Arrays.asList(testPlayer), testPlayerList.getPlayerList());
        } catch (EmptyListException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    void testAddMultiplePlayerToLists() {
        Player testPlayer1 = new Player("Johnathan");
        Player testPlayer2 = new Player("Frank");

        testPlayer1.addBronze();
        testPlayer1.addSilver();

        try {
            assertTrue(testPlayerList.addPlayerToList(testPlayer1));
            assertTrue(testPlayerList.addPlayerToList(testPlayer2));
            assertEquals(Arrays.asList(testPlayer1, testPlayer2), testPlayerList.getPlayerList());
        } catch (EmptyListException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    void testSelectPlayerInList() {
        Player testPlayer1 = new Player("Johnathan");
        Player testPlayer2 = new Player("Frank");

        assertTrue(testPlayerList.addPlayerToList(testPlayer1));
        assertTrue(testPlayerList.addPlayerToList(testPlayer2));

        assertEquals(0, testPlayerList.indexOfPlayer("Johnathan"));
        assertEquals(1, testPlayerList.indexOfPlayer("Frank"));
    }

    @Test
    void testSelectPlayerNotInList() {
        Player testPlayer1 = new Player("Johnathan");
        Player testPlayer2 = new Player("Jack");

        assertTrue(testPlayerList.addPlayerToList(testPlayer1));

        assertEquals(-1, testPlayerList.indexOfPlayer("Jack"));

        assertTrue(testPlayerList.addPlayerToList(testPlayer2));
        assertEquals(1, testPlayerList.indexOfPlayer("Jack"));
    }

    @Test
    void testGetPlayer() {
        Player testPlayer1 = new Player("Johnathan");
        Player testPlayer2 = new Player("Frank");

        assertTrue(testPlayerList.addPlayerToList(testPlayer1));
        assertTrue(testPlayerList.addPlayerToList(testPlayer2));

        assertEquals("Johnathan", testPlayerList.getPlayer(0).getName());
        assertEquals("Frank", testPlayerList.getPlayer(1).getName());
    }
}