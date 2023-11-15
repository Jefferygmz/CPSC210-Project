package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedalBoardTest {
    private MedalBoard testMedalBoard;
    private PlayerList testPlayerBoard;
    private TeamList testTeamBoard;

    @BeforeEach
    void setUp() {
        testMedalBoard = new MedalBoard("New Board");
        testPlayerBoard = new PlayerList();
        testTeamBoard = new TeamList();
        testMedalBoard.setName("Test Board");
        testMedalBoard.setTeamBoard(testTeamBoard);
        testMedalBoard.setPlayerBoard(testPlayerBoard);
    }

    @Test
    void testConstructor() {
        assertEquals("Test Board", testMedalBoard.getName());
        assertEquals(testPlayerBoard, testMedalBoard.getPlayerBoard());
        assertEquals(testTeamBoard, testMedalBoard.getTeamBoard());
    }

    @Test
    void testAddPlayer() {
        Player testPlayer = new Player("Jack");
        assertEquals(0,testMedalBoard.getPlayerBoard().getSize());

        testMedalBoard.addPlayer(testPlayer);

        assertEquals(1,testMedalBoard.getPlayerBoard().getSize());
        assertEquals("Jack", testMedalBoard.getPlayerBoard().getPlayer(0).getName());
    }

    @Test
    void testAddTeam() {
        Team testTeam = new Team("Green");
        assertEquals(0,testMedalBoard.getTeamBoard().getSize());

        testMedalBoard.addTeam(testTeam);

        assertEquals(1,testMedalBoard.getTeamBoard().getSize());
        assertEquals("Green",testMedalBoard.getTeamBoard().getTeam(0).getTeamName());
    }

}
