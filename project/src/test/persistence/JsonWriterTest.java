package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    private MedalBoard testBoard;

    @BeforeEach
    void runBefore() {
        testBoard = new MedalBoard("New Board");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBoard() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBoard.json");
            writer.open();
            writer.write(testBoard);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBoard.json");
            testBoard = reader.read();
            assertEquals("New Board", testBoard.getName());
            assertEquals(0, testBoard.getPlayerBoard().getSize());
            assertEquals(0, testBoard.getTeamBoard().getSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBoard() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBoard.json");
            Player testPlayer1 = new Player("Jack");
            Player testPlayer2 = new Player("Frank");
            Player testPlayer3 = new Player("Tim");

            Team testTeam1 = new Team("France");
            Team testTeam2 = new Team("Japan");

            testPlayer1.addGold();
            testPlayer1.addSilver();
            testPlayer2.addSilver();
            testPlayer3.addBronze();

            testTeam1.addPlayerToTeam(testPlayer1,testTeam1);
            testTeam1.addPlayerToTeam(testPlayer2,testTeam1);
            testTeam2.addPlayerToTeam(testPlayer3,testTeam2);

            testBoard.addTeam(testTeam1);
            testBoard.addTeam(testTeam2);
            testBoard.addPlayer(testPlayer1);
            testBoard.addPlayer(testPlayer2);
            testBoard.addPlayer(testPlayer3);

            writer.open();
            writer.write(testBoard);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBoard.json");
            testBoard = reader.read();
            assertEquals("New Board", testBoard.getName());
            assertEquals(3, testBoard.getPlayerBoard().getSize());
            assertEquals(2, testBoard.getTeamBoard().getSize());

            assertEquals("France", testBoard.getTeamBoard().getTeam(0).getTeamName());
            assertEquals(1, testBoard.getTeamBoard().getTeam(0).getTeamGold());
            assertEquals(2, testBoard.getTeamBoard().getTeam(0).getTeamSilver());
            assertEquals(2, testBoard.getTeamBoard().getTeam(0).getTeamMembers().size());

            assertEquals("Japan", testBoard.getTeamBoard().getTeam(1).getTeamName());
            assertEquals(1, testBoard.getTeamBoard().getTeam(1).getTeamBronze());

            assertEquals("Jack", testBoard.getPlayerBoard().getPlayer(0).getName());
            assertEquals(1, testBoard.getPlayerBoard().getPlayer(0).getGold());
            assertEquals(1, testBoard.getPlayerBoard().getPlayer(0).getSilver());
            assertEquals(0, testBoard.getPlayerBoard().getPlayer(0).getBronze());
            assertEquals("France", testBoard.getPlayerBoard().getPlayer(0).getPlayerTeam().getTeamName());
            assertEquals(1, testBoard.getPlayerBoard().getPlayer(0).getPlayerTeam().getTeamGold());


            assertEquals("Frank", testBoard.getPlayerBoard().getPlayer(1).getName());
            assertEquals(0, testBoard.getPlayerBoard().getPlayer(1).getGold());
            assertEquals(1, testBoard.getPlayerBoard().getPlayer(1).getSilver());
            assertEquals(0, testBoard.getPlayerBoard().getPlayer(1).getBronze());
            assertEquals("France", testBoard.getPlayerBoard().getPlayer(1).getPlayerTeam().getTeamName());

            assertEquals("Tim", testBoard.getPlayerBoard().getPlayer(2).getName());
            assertEquals(0, testBoard.getPlayerBoard().getPlayer(2).getGold());
            assertEquals(0, testBoard.getPlayerBoard().getPlayer(2).getSilver());
            assertEquals(1, testBoard.getPlayerBoard().getPlayer(2).getBronze());
            assertEquals("Japan", testBoard.getPlayerBoard().getPlayer(2).getPlayerTeam().getTeamName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
