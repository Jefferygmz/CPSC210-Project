package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represent a Team with a team name, gold, silver, and bronze medal counts for the team, and all the team members
public class Team implements Writable {
    private static int initialMedal = 0;    // initial a newly created team medal number to 0

    private String name;
    private int teamGold;
    private int teamSilver;
    private int teamBronze;
    private ArrayList<Player> teamMembers;

    // EFFECT: Initialize a team with team name as name, all 0 medals, and empty teamMembers.
    public Team(String teamName) {
        this.name = teamName;
        this.teamGold = initialMedal;
        this.teamSilver = initialMedal;
        this.teamBronze = initialMedal;
        this.teamMembers = new ArrayList<>();
        EventLog.getInstance().logEvent(new
                Event("Created a new team with name " + teamName + "."));
    }

    // MODIFIES: this
    // EFFECTS: add selected player p to the team, add his/her medal counts to each category of team's medal counts,
    //          set the player's team to the team
    public boolean addPlayerToTeam(Player p, Team t) {
        if (!teamMembers.contains(p)) {
            p.setTeam(t);
            teamMembers.add(p);
            teamGold += p.getGold();
            teamSilver += p.getSilver();
            teamBronze += p.getBronze();
            EventLog.getInstance().logEvent(new Event("Added player " + p.getName() + " to team "
                    + t.getTeamName() + "."));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: get the number of Gold medals of all the players in a team
    public int getTeamGold() {
        int gold = 0;
        for (Player p : teamMembers) {
            gold += p.getGold();
        }
        setTeamGold(gold);
        return gold;
    }

    // MODIFIES: this
    // EFFECTS: get the number of Gold medals of all the players in a team
    public int getTeamSilver() {
        int silver = 0;
        for (Player p : teamMembers) {
            silver += p.getSilver();
        }
        setTeamSilver(silver);
        return silver;
    }

    // MODIFIES: this
    // EFFECTS: get the number of Gold medals of all the players in a team
    public int getTeamBronze() {
        int bronze = 0;
        for (Player p : teamMembers) {
            bronze += p.getBronze();
        }
        setTeamBronze(bronze);
        return bronze;
    }

    // EFFECTS: sum the number of all medals of a team
    public int sumTeamMedals() {
        return getTeamGold() + getTeamSilver() + getTeamBronze();
    }

    //getters
    public ArrayList<Player> getTeamMembers() {
        return teamMembers;
    }

    public String getTeamName() {
        return name;
    }

    //setters
    public void setTeamGold(int g) {
        teamGold = g;
    }

    public void setTeamSilver(int s) {
        teamGold = s;
    }

    public void setTeamBronze(int b) {
        teamGold = b;
    }

    @Override
    // EFFECTS: return Team as a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("TeamName", name);
        json.put("TeamGoldCount", teamGold);
        json.put("TeamSilverCount", teamSilver);
        json.put("TeamBronzeCount", teamBronze);
        json.put("TeamMembers", teamMembersToJson());
        return json;
    }

    // EFFECTS: returns players in this team as a JSON array
    private JSONArray teamMembersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : teamMembers) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
