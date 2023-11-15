package ui;

import model.*;
import model.exceptions.EmptyListException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

// Medal board application
public class TextMedalBoard {
    private static final String JSON_PATH = "./data/medalboard.json";
    private MedalBoard medalBoard;
    private Scanner input = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: run runBoard()
    public TextMedalBoard() throws FileNotFoundException {
        runBoard();
    }

    // EFFECTS: run the board and let it process user's inputs
    private void runBoard() {
        medalBoard = new MedalBoard("textBoard");
        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);
        String choice;
        boolean keepLooping = true;


        while (keepLooping) {
            mainMenu();

            choice = input.next();

            if (choice.equals("0")) {
                System.out.println("Do you want to save current board?");
                System.out.println("\tS. to save the board");
                System.out.println("\tOther characters to quit");
                String option = input.next();
                if (option.toLowerCase(Locale.ROOT).equals("s")) {
                    saveMedalBoard();
                }
                keepLooping = false;
            } else {
                processMainCommand(choice);
            }
        }

    }

    // EFFECTS: list all the options
    private void mainMenu() {
        System.out.println("\nSelect your option:");
        System.out.println("\t1. Add a Player");
        System.out.println("\t2. Create a Team");
        System.out.println("\t3. Add a medal to a Player");
        System.out.println("\t4. Show the medal counts for a Player");
        System.out.println("\t5. Show the medal counts for a Team");
        System.out.println("\t6. Show board with Player view");
        System.out.println("\t7. Show board with Team view");
        System.out.println("\t0. Quit the application");
        System.out.println("\ts. Save current boards to file");
        System.out.println("\tl. Load current boards from file");
    }

    // EFFECTS: process user's input and call methods according to user's input
    private void processMainCommand(String choice) {
        if ("1".equals(choice)) {
            addPlayer();
        } else if ("2".equals(choice)) {
            createTeam();
        } else if ("3".equals(choice)) {
            addMedalToPlayer();
        } else if ("4".equals(choice)) {
            showPlayerMedals();
        } else if ("5".equals(choice)) {
            showTeamMedals();
        } else if ("6".equals(choice)) {
            showPlayerView();
        } else if ("7".equals(choice)) {
            showTeamView();
        } else if ("s".equals(choice)) {
            saveMedalBoard();
        } else if ("l".equals(choice)) {
            loadMedalBoard();
        }
    }



    // EFFECTS: display the options for user to choose to add medal to player
    private void medalMenu() {
        System.out.println("Select the medal you want to add:");
        System.out.println("\t1.Add a Gold Medal");
        System.out.println("\t2.Add a Silver Medal");
        System.out.println("\t3.Add a Bronze Medal");
        System.out.println("\t0.Return to the Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: Ask the user to type a name and a team name that the user belongs,
    //          if there is no team named the same as user's input, create a team and add the player in it,
    //          else, set the newly created player's team to the one user specified
    private void addPlayer() {
        System.out.println("Please type the Player's name");
        String name = input.next();
        Player player = new Player(name);

        System.out.println("Please type the Player's team");
        String teamName = input.next();

        int indexOfTeam = medalBoard.getTeamBoard().indexOfTeam(teamName);

        if (indexOfTeam != -1) {
            Team playerTeam = medalBoard.getTeamBoard().getTeam(indexOfTeam);
            medalBoard.getPlayerBoard().addPlayerToList(player);
            playerTeam.addPlayerToTeam(player,playerTeam);
        } else {
            Team newTeam = new Team(teamName);
            medalBoard.getPlayerBoard().addPlayerToList(player);
            newTeam.addPlayerToTeam(player, newTeam);
            medalBoard.getTeamBoard().addTeamToList(newTeam);
        }
        System.out.println("Successfully added!");
    }

    // MODIFIES: teamList
    // EFFECTS: check whether the teamList have the team that user types,
    //          if no, then add the team to the teamList
    private void createTeam() {
        System.out.println("Please type the name of the Team you want to create:");
        String teamName = input.next();
        if (medalBoard.getTeamBoard().indexOfTeam(teamName) == -1) {
            medalBoard.getTeamBoard().addTeamToList(new Team(teamName));
            System.out.println("Successfully added team: " + teamName);
        } else {
            System.out.println("Sorry, there is already a team with that name, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: add user choose medal to user chose player
    private void addMedalToPlayer() {
        System.out.println("Please type the player's name:");
        String name = input.next();
        int positionOfPlayer = medalBoard.getPlayerBoard().indexOfPlayer(name);
        if (positionOfPlayer != -1) {
            Player selectedPlayer = medalBoard.getPlayerBoard().getPlayer(positionOfPlayer);
            medalMenu();
            System.out.println("Please type the number of your choice:");
            processAddMedalCommand(selectedPlayer,name);
        } else {
            System.out.println("There is no player with that name, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: process user's input for addMedalToPlayer
    private void processAddMedalCommand(Player p, String name) {
        String medalChoice = input.next();
        switch (medalChoice) {
            case "1":
                p.addGold();
                System.out.println("Successfully added a Gold Medal to " + name + "!");
                break;
            case "2":
                p.addSilver();
                System.out.println("Successfully added a Silver Medal to " + name + "!");
                break;
            case "3":
                p.addBronze();
                System.out.println("Successfully added a Bronze Medal to " + name + "!");
                break;
        }
    }

    // EFFECTS: let user select a player and output the player's medal counts
    private void showPlayerMedals() {
        System.out.println("Please type the name of the player:");
        String name = input.next();
        int positionOfPlayer = medalBoard.getPlayerBoard().indexOfPlayer(name);
        if (positionOfPlayer == -1) {
            System.out.println("There is no player with that name, please try again.");
            return;
        }
        Player selectedPlayer = medalBoard.getPlayerBoard().getPlayer(positionOfPlayer);
        System.out.println(selectedPlayer.getName() + ":" + "\nGold:" + selectedPlayer.getGold()
                + "\nSilver:" + selectedPlayer.getSilver()
                + "\nBronze:" + selectedPlayer.getBronze()
                + "\nIn total:" + selectedPlayer.sumPlayerMedals());
    }

    // EFFECTS: let user select a team and output the team's medal counts
    private void showTeamMedals() {
        System.out.println("Please type the name of the team:");
        String name = input.next();
        int indexOfTeam = medalBoard.getTeamBoard().indexOfTeam(name);
        if (indexOfTeam == -1) {
            System.out.println("Sorry there is no such name, please try again.");
            return;
        }
        Team t = medalBoard.getTeamBoard().getTeam(indexOfTeam);
        System.out.println(t.getTeamName() + ":" + "\nGold:" + t.getTeamGold()
                + "\nSilver:" + t.getTeamSilver()
                + "\nBronze:" + t.getTeamBronze()
                + "\nIn total:" + t.sumTeamMedals());
    }

    // EFFECTS: show all the medals of all the players, if the player list is empty, throw and catch the exception
    private void showPlayerView() {
        try {
            for (Player p : medalBoard.getPlayerBoard().getPlayerList()) {
                System.out.println(p.getName() + ":" + " Gold:" + p.getGold()
                        + " Silver:" + p.getSilver()
                        + " Bronze:" + p.getBronze()
                        + " In total:" + p.sumPlayerMedals());
            }
        } catch (EmptyListException e) {
            System.out.println("Sorry, there is no any player.");
        }
    }

    // EFFECTS: show all the medals of all the teams, if the team list is empty, throw and catch the exception
    private void showTeamView() {
        try {
            for (Team t : medalBoard.getTeamBoard().getTeamList()) {
                System.out.println(t.getTeamName() + ":" + " Gold:" + t.getTeamGold()
                        + " Silver:" + t.getTeamSilver()
                        + " Bronze:" + t.getTeamBronze()
                        + " In total:" + t.sumTeamMedals());
            }
        } catch (EmptyListException e) {
            System.out.println("Sorry, there is no any team.");
        }
    }

    // EFFECTS: save medal board to JSON_PATH
    private void saveMedalBoard() {
        System.out.println("Please type the name for the Medal Board:");
        String name = input.next();
        medalBoard.setName(name);
        medalBoard.setPlayerBoard(medalBoard.getPlayerBoard());
        medalBoard.setTeamBoard(medalBoard.getTeamBoard());
        try {
            jsonWriter.open();
            jsonWriter.write(medalBoard);
            jsonWriter.close();
            System.out.println("Saved " + name + " to " + JSON_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, we are unable to write to file: " + JSON_PATH + " at this time.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads medal board from JSON_PATH
    private void loadMedalBoard() {
        try {
            medalBoard = jsonReader.read();
            System.out.println("Loaded " + medalBoard.getName() + " from " + JSON_PATH);
        } catch (IOException e) {
            System.out.println("Sorry, we are unable to write to file: " + JSON_PATH + " at this time.");
        }
    }
}
