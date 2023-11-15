package model;

import model.exceptions.EmptyListException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Represents a list contains all the teams
public class TeamList implements Writable {
    private LinkedList<Team> teamList;

    // EFFECTS: Initialize a teamList with no team in it
    public TeamList() {
        teamList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a team to the TeamList
    public boolean addTeamToList(Team t) {
        if (!teamList.contains(t)) {
            teamList.add(t);
            EventLog.getInstance().logEvent(new Event("Added team " + t.getTeamName() + " to the team list."));
            return true;
        }
        return false;
    }

    // EFFECTS: return the index of team that has inputted team name, else return -1
    public int indexOfTeam(String teamName) {
        int temp = 0;
        for (Team t : teamList) {
            if (t.getTeamName().equals(teamName)) {
                return temp;
            }
            temp += 1;
        }
        return -1;
    }

    // EFFECTS: sum the total number of Silver medals of all the team
    public int sumGold() {
        int g = 0;  //the number of gold of all the team
        for (Team t : teamList) {
            g += t.getTeamGold();
        }
        return g;
    }

    // EFFECTS: sum the total number of Silver medals of all the team
    public int sumSilver() {
        int s = 0;  //the number of silver of all the team
        for (Team t : teamList) {
            s += t.getTeamSilver();
        }
        return s;
    }

    // EFFECTS: sum the total number of Silver medals of all the team
    public int sumBronze() {
        int b = 0;  //the number of bronze of all the team
        for (Team t : teamList) {
            b += t.getTeamBronze();
        }
        return b;
    }

    // getters
    public Team getTeam(int i) {
        return teamList.get(i);
    }

    public int getSize() {
        return teamList.size();
    }

    // EFFECTS: return the team list, throws exception if list is empty
    public LinkedList<Team> getTeamList() throws EmptyListException {
        if (teamList.isEmpty()) {
            throw new EmptyListException();
        }
        return teamList;
    }

    @Override
    // EFFECTS: return TeamList as a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("TeamList", teamListToJson());
        return json;
    }

    // EFFECTS: returns teams in this teamList as a JSON array
    private JSONArray teamListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teamList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
