package model;

import model.exceptions.EmptyListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a player list that contains all the players
public class PlayerList implements Writable {
    private ArrayList<Player> playerList;

    // EFFECTS: initialize a playerList with no player in it
    public PlayerList() {
        playerList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a Player to the player list
    public boolean addPlayerToList(Player p) {
        if (!playerList.contains(p)) {
            playerList.add(p);
            EventLog.getInstance().logEvent(new Event("Added player " + p.getName() + " to the player list."));
            return true;
        }
        return false;
    }

    // EFFECTS: return the index of the player with inputted name, return -1 if there is no such player
    public int indexOfPlayer(String playerName) {
        int temp = 0;
        for (Player p : playerList) {
            if (p.getName().equals(playerName)) {
                return temp;
            }
            temp += 1;
        }
        return -1;
    }

    // getters
    public Player getPlayer(int i) {
        return playerList.get(i);
    }

    public int getSize() {
        return playerList.size();
    }

    // return the player list, throws exception if list is empty
    public ArrayList<Player> getPlayerList() throws EmptyListException {
        if (playerList.isEmpty()) {
            throw new EmptyListException();
        }
        return playerList;
    }

    @Override
    // EFFECTS: returns player list as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("PlayerList", playerListToJson());
        return json;
    }

    // EFFECTS: returns players in this playerList as a JSON array
    private JSONArray playerListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : playerList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
