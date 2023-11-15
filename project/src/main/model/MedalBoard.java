package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represent a medal board contains a player board and a team board
public class MedalBoard implements Writable {
    private String name;
    private PlayerList playerBoard;
    private TeamList teamBoard;

    // EFFECTS: construct a medal board with inputted name, an empty player list to represent player board
    //          and an empty team list
    public MedalBoard(String name) {
        this.name = name;
        this.playerBoard = new PlayerList();
        this.teamBoard = new TeamList();
    }

    // MODIFIES: this
    // EFFECTS: add a player to the player board
    public void addPlayer(Player p) {
        playerBoard.addPlayerToList(p);
    }

    // MODIFIES: this
    // EFFECTS: add a team to the team board
    public void addTeam(Team t) {
        teamBoard.addTeamToList(t);
    }

    // getters
    public PlayerList getPlayerBoard() {
        return playerBoard;
    }

    public TeamList getTeamBoard() {
        return teamBoard;
    }

    public String getName() {
        return name;
    }

    // setters
    public void setName(String newName) {
        name = newName;
    }

    public void setPlayerBoard(PlayerList pl) {
        playerBoard = pl;
    }

    public void setTeamBoard(TeamList tl) {
        teamBoard = tl;
    }


    @Override
    // EFFECTS: returns medal board as a JSON object contains a name and a JSON Array which is the boards
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Boards", boardsToJson());
        return json;
    }

    // EFFECTS: returns player board and team board as a JSON array
    private JSONArray boardsToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(playerBoard.toJson());
        jsonArray.put(teamBoard.toJson());
        return jsonArray;
    }

}
