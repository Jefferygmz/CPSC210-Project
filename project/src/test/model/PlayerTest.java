package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("John");
    }

    @Test
    void testConstructor() {
        assertTrue(testPlayer.getId()>0);
        assertEquals("John", testPlayer.getName());
        assertEquals(0, testPlayer.getGold());
        assertEquals(0, testPlayer.getSilver());
        assertEquals(0, testPlayer.getBronze());
        assertNull(testPlayer.getPlayerTeam());
    }

    @Test
    void testAddGold() {
        testPlayer.addGold();
        assertEquals(1, testPlayer.getGold());

        testPlayer.addGold();
        assertEquals(2, testPlayer.getGold());
    }

    @Test
    void testAddSilver() {
        testPlayer.addSilver();
        assertEquals(1, testPlayer.getSilver());

        testPlayer.addSilver();
        assertEquals(2, testPlayer.getSilver());
    }

    @Test
    void testAddBronze() {
        testPlayer.addBronze();
        assertEquals(1, testPlayer.getBronze());

        testPlayer.addBronze();
        assertEquals(2, testPlayer.getBronze());
    }

    @Test
    void testSumZeroMedal() {
        assertEquals(0, testPlayer.sumPlayerMedals());
    }

    @Test
    void testSumMedals() {
        testPlayer.addGold();
        testPlayer.addGold();
        testPlayer.addSilver();
        testPlayer.addBronze();

        assertEquals(4, testPlayer.sumPlayerMedals());
    }
}
