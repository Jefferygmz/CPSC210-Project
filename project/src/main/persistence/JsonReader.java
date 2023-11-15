package persistence;

import model.EventLog;
import model.MedalBoard;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: initialize a json reader
    public JsonReader(String source) {
        this.source = source;
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads MedalBoard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MedalBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseMedalBoard(jsonObject);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MedalBoard from JSON object and returns it
    private MedalBoard parseMedalBoard(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MedalBoard mb = new MedalBoard(name);
        addBoards(mb, jsonObject);
        return mb;
    }

    // MODIFIES: mb
    // EFFECTS: parses playerList and teamList from JSON object and adds them to MedalBoard
    private void addBoards(MedalBoard mb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Boards");

        JSONObject playerList = (JSONObject) jsonArray.get(0);
        JSONObject teamList = (JSONObject) jsonArray.get(1);

        addTeamList(mb, teamList);
        addPlayerList(mb, playerList);

    }

    // MODIFIES: mb
    // EFFECTS: parses PlayerList from JSON object and adds it to MedalBoard
    private void addPlayerList(MedalBoard md, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("PlayerList");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(md, nextPlayer);
        }
    }

    // MODIFIES: mb
    // EFFECTS: parses player from JSON object and adds it to MedalBoard
    private void addPlayer(MedalBoard md, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        int goldCount = jsonObject.getInt("goldCount");
        int silverCount = jsonObject.getInt("silverCount");
        int bronzeCount = jsonObject.getInt("bronzeCount");
        String teamName = jsonObject.getString("Team");
        Player player = new Player(name);
        player.setId(id);
        for (int i = goldCount; i > 0; i--) {
            player.addGold();
        }
        for (int i = silverCount; i > 0; i--) {
            player.addSilver();
        }
        for (int i = bronzeCount; i > 0; i--) {
            player.addBronze();
        }
        player.setTeam(md.getTeamBoard().getTeam(md.getTeamBoard().indexOfTeam(teamName)));

        md.addPlayer(player);
    }

    // MODIFIES: mb
    // EFFECTS: parses TeamList from JSON object and adds it to MedalBoard
    private void addTeamList(MedalBoard md, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("TeamList");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(md, nextTeam);
        }
    }

    // MODIFIES: mb
    // EFFECTS: parses team from JSON object and adds it to MedalBoard
    private void addTeam(MedalBoard md, JSONObject jsonObject) {
        String teamName = jsonObject.getString("TeamName");
        int teamGoldCount = jsonObject.getInt("TeamGoldCount");
        int teamSilverCount = jsonObject.getInt("TeamSilverCount");
        int teamBronzeCount = jsonObject.getInt("TeamBronzeCount");

        Team team = new Team(teamName);
        team.setTeamGold(teamGoldCount);
        team.setTeamSilver(teamSilverCount);
        team.setTeamBronze(teamBronzeCount);

        JSONArray jsonArray = jsonObject.getJSONArray("TeamMembers");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            team.addPlayerToTeam(constructPlayer(team, nextPlayer), team);
        }
        md.addTeam(team);
    }

    // EFFECTS: construct player from the JSON object
    private Player constructPlayer(Team team, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        int goldCount = jsonObject.getInt("goldCount");
        int silverCount = jsonObject.getInt("silverCount");
        int bronzeCount = jsonObject.getInt("bronzeCount");

        Player player = new Player(name);
        player.setTeam(team);
        player.setId(id);
        for (int i = goldCount; i > 0; i--) {
            player.addGold();
        }
        for (int i = silverCount; i > 0; i--) {
            player.addSilver();
        }
        for (int i = bronzeCount; i > 0; i--) {
            player.addBronze();
        }


        return player;
    }
}

