package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team testTeam;

    @BeforeEach
    void runBefore() {
        testTeam = new Team("CA");
    }

    @Test
    void testConstructor() {
        assertEquals("CA", testTeam.getTeamName());
        assertEquals(0, testTeam.getTeamGold());
        assertEquals(0, testTeam.getTeamSilver());
        assertEquals(0, testTeam.getTeamBronze());
        assertTrue(testTeam.getTeamMembers().isEmpty());
    }

    @Test
    void testAddPlayerToTeamWithNoMedals() {
        Player testPlayer = new Player("Cici");

        assertTrue(testTeam.addPlayerToTeam(testPlayer, testTeam));

        assertEquals("CA",testTeam.getTeamName());
        assertEquals(0, testTeam.getTeamGold());
        assertEquals(0, testTeam.getTeamGold());
        assertEquals(0, testTeam.getTeamGold());
        assertEquals(Arrays.asList(testPlayer), testTeam.getTeamMembers());
    }

    @Test
    void testAddPlayerToTeamsWIthMultipleMedals() {
        Player testPlayer = new Player("John");
        testPlayer.addGold();
        testPlayer.addGold();
        testPlayer.addBronze();

        assertTrue(testTeam.addPlayerToTeam(testPlayer, testTeam));

        assertEquals(2, testTeam.getTeamGold());
        assertEquals(1, testTeam.getTeamBronze());
        assertEquals(3, testTeam.sumTeamMedals());
        assertEquals(Arrays.asList(testPlayer), testTeam.getTeamMembers());
    }

    @Test
    void testAddPlayerToTeamsWIthSamePlayer() {
        Player testPlayer = new Player("Jack");
        testPlayer.addGold();
        testPlayer.addSilver();
        testPlayer.addBronze();

        assertTrue(testTeam.addPlayerToTeam(testPlayer, testTeam));
        assertFalse(testTeam.addPlayerToTeam(testPlayer, testTeam));

        assertEquals(1, testTeam.getTeamGold());
        assertEquals(1, testTeam.getTeamSilver());
        assertEquals(1, testTeam.getTeamBronze());
        assertEquals(3, testTeam.sumTeamMedals());
        assertEquals(Arrays.asList(testPlayer), testTeam.getTeamMembers());
    }
}
