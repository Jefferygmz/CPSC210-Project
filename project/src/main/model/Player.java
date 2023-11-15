package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent a player that has a unique id, a name, a team, and medal counts for gold, silver, and silver
public class Player implements Writable {
    private static int nextPlayerId = 1;    // keep track of the id of the next created player
    private static int initialMedal = 0;    // initial a newly created player medal number to 0

    private int id;                         // player's id (in case of two player has the same name)
    private String name;                    // player's name
    private int goldCount;       // goldCount for the player is initialized to 0
    private int silverCount;     // silverCount for the player is initialized to 0
    private int bronzeCount;     // bronzeCount for the player is initialized to 0
    private Team team;


    // EFFECTS: initialize a Player, name of the player is set to Player,
    //          all counts for gold,silver and bronze is initialized to 0,
    //          and no team
    public Player(String playerName) {
        id = nextPlayerId++;
        this.name = playerName;
        this.goldCount = initialMedal;
        this.silverCount = initialMedal;
        this.bronzeCount = initialMedal;
        this.team = null;
    }

    // EFFECTS: sum the number of gold, silver, and bronze medals of a player
    public int sumPlayerMedals() {
        return goldCount + silverCount + bronzeCount;
    }

    // MODIFIES: this
    // EFFECTS: add one Gold medal to a player
    public int addGold() {
        EventLog.getInstance().logEvent(new Event("Added a gold to player " + name + "."));
        return goldCount++;
    }

    // MODIFIES: this
    // EFFECTS: add one Silver medal to a player
    public int addSilver() {
        EventLog.getInstance().logEvent(new Event("Added a silver to player " + name + "."));
        return silverCount++;
    }

    // MODIFIES: this
    // EFFECTS: add one Bronze medal to a player
    public int addBronze() {
        EventLog.getInstance().logEvent(new Event("Added a bronze to player " + name + "."));

        return bronzeCount++;
    }


    // setters
    public void setTeam(Team t) {
        team = t;
    }

    public void setId(int i) {
        id = i;
    }


    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return goldCount;
    }

    public int getSilver() {
        return silverCount;
    }

    public int getBronze() {
        return bronzeCount;
    }

    public Team getPlayerTeam() {
        return team;
    }

    @Override
    // EFFECTS: return Player as a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("goldCount", goldCount);
        json.put("silverCount", silverCount);
        json.put("bronzeCount", bronzeCount);
        json.put("Team", team.getTeamName());
        return json;
    }

}
