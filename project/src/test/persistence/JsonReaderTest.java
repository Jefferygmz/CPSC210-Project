package persistence;

import model.MedalBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:noFileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyBoard.json");
        try {
            MedalBoard mb = reader.read();
            assertEquals("New Board", mb.getName());
            assertEquals(0, mb.getPlayerBoard().getSize());
            assertEquals(0, mb.getTeamBoard().getSize());

        } catch (IOException e) {
            fail("Cannot read from file");
        }
    }

    @Test
    void testWriterGeneralBoard() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralBoard.json");
        try {
            MedalBoard md = reader.read();
            assertEquals("New Board", md.getName());
            assertEquals(3, md.getPlayerBoard().getSize());
            assertEquals(2, md.getTeamBoard().getSize());

            assertEquals("France", md.getTeamBoard().getTeam(0).getTeamName());
            assertEquals(1, md.getTeamBoard().getTeam(0).getTeamGold());
            assertEquals(2, md.getTeamBoard().getTeam(0).getTeamSilver());
            assertEquals(2, md.getTeamBoard().getTeam(0).getTeamMembers().size());

            assertEquals("Japan", md.getTeamBoard().getTeam(1).getTeamName());
            assertEquals(1, md.getTeamBoard().getTeam(1).getTeamBronze());

            assertEquals("Jack", md.getPlayerBoard().getPlayer(0).getName());
            assertEquals(1, md.getPlayerBoard().getPlayer(0).getGold());
            assertEquals(1, md.getPlayerBoard().getPlayer(0).getSilver());
            assertEquals(0, md.getPlayerBoard().getPlayer(0).getBronze());
            assertEquals("France", md.getPlayerBoard().getPlayer(0).getPlayerTeam().getTeamName());
            assertEquals(1, md.getPlayerBoard().getPlayer(0).getPlayerTeam().getTeamGold());


            assertEquals("Frank", md.getPlayerBoard().getPlayer(1).getName());
            assertEquals(0, md.getPlayerBoard().getPlayer(1).getGold());
            assertEquals(1, md.getPlayerBoard().getPlayer(1).getSilver());
            assertEquals(0, md.getPlayerBoard().getPlayer(1).getBronze());
            assertEquals("France", md.getPlayerBoard().getPlayer(1).getPlayerTeam().getTeamName());

            assertEquals("Tim", md.getPlayerBoard().getPlayer(2).getName());
            assertEquals(0, md.getPlayerBoard().getPlayer(2).getGold());
            assertEquals(0, md.getPlayerBoard().getPlayer(2).getSilver());
            assertEquals(1, md.getPlayerBoard().getPlayer(2).getBronze());
            assertEquals("Japan", md.getPlayerBoard().getPlayer(2).getPlayerTeam().getTeamName());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
