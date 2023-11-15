package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represent visual Board application
public class VisualBoard extends JFrame {
    private JFrame frame;
    private JPanel bottomPanel;

    private JButton playerViewButton;
    private JButton teamViewButton;
    private JButton addTeamButton;
    private JButton addPlayerButton;
    private JButton addMedalButton;

    private DefaultTableModel playerModel;
    private DefaultTableModel teamModel;
    private JTable playerTable;
    private JTable teamTable;
    private JScrollPane playerScrollPane;
    private JScrollPane teamScrollPane;

    private static final String JSON_PATH = "./data/medalboard.json";
    private MedalBoard medalBoard;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs an empty visual board
    public VisualBoard() {
        medalBoard = new MedalBoard("Visual");
        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);

        initializeFrame();
        initializeMenuBar();
        initializeViewsButtons();
        initializeBottomPanel();

        initializePlayerPanel();
        initializeTeamPanel();

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize the frame for the visual board
    private void initializeFrame() {
        frame = new JFrame();
        frame.setTitle("Medal Board");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        frame.setSize(500, 500);

        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: initialize a menu bar with a menu and two menu items for save and load the files,
    //          add the menu bar to the frame
    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");

        saveMenuItem.addActionListener(e -> saveBoards());

        loadMenuItem.addActionListener(e -> loadBoards());

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: initialize a button panel with two buttons for change between player view and team view,
    //          and add the panel to the frame
    private void initializeViewsButtons() {
        playerViewButton = new JButton("Player View");
        teamViewButton = new JButton("Team View");

        playerViewButton.addActionListener(e -> playerView());

        teamViewButton.addActionListener(e -> teamView());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        buttonPanel.add(playerViewButton);
        buttonPanel.add(teamViewButton);

        playerViewButton.setEnabled(false);

        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: initialize a bottom panel with two buttons for add a new player and a new team set to invisible,
    //          add the bottom panel to the bottom of the frame
    private void initializeBottomPanel() {
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));

        addPlayerButton = new JButton("Add Player");
        addTeamButton = new JButton("Add Team");
        addMedalButton = new JButton("Add Medal");

        addPlayerButton.addActionListener(e -> addPlayer());

        addTeamButton.addActionListener(e -> addTeam());

        addMedalButton.addActionListener(e -> addMedal());

        bottomPanel.add(addPlayerButton);
        bottomPanel.add(addTeamButton);
        bottomPanel.add(addMedalButton);

        addTeamButton.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: initialize a table to contain data of players, represents a player list
    private void initializePlayerPanel() {
        String[] columnNames = {"Name", "Team", "Gold", "Silver", "Bronze", "Total"};

        playerModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        playerModel.setColumnIdentifiers(columnNames);

        playerTable = new JTable(playerModel);
        playerScrollPane = new JScrollPane(playerTable);

        frame.add(playerScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: initialize a table to contain data of teams, represents a team list
    private void initializeTeamPanel() {
        String[] columnNames = {"Team", "Gold", "Silver", "Bronze", "Total"};

        teamModel = new DefaultTableModel(0, columnNames.length) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        teamModel.setColumnIdentifiers(columnNames);

        teamTable = new JTable(teamModel);
        teamScrollPane = new JScrollPane(teamTable);
    }

    // MODIFIES: this
    // EFFECTS: change to players view, enable teamViewButton, disable playerViewButton, set addPlayer button visible,
    //          set the addTeam button invisible, remove the teamScrollPane, add the playerScroll Pane
    private void playerView() {
        teamViewButton.setEnabled(true);
        frame.remove(teamScrollPane);
        addPlayerButton.setVisible(true);
        addMedalButton.setVisible(true);

        playerViewButton.setEnabled(false);
        frame.add(playerScrollPane);
        addTeamButton.setVisible(false);

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: change to team view, disable teamViewButton, enable playerViewButton, set addPlayer button disable,
    //          set the addTeam button visible, add the teamScrollPane, remove the playerScrollPane
    private void teamView() {
        playerViewButton.setEnabled(true);
        frame.remove(playerScrollPane);
        addPlayerButton.setVisible(false);
        addMedalButton.setVisible(false);

        teamViewButton.setEnabled(false);
        showTeamView();
        teamTable = new JTable(teamModel);
        teamScrollPane.add(teamTable);
        frame.add(teamScrollPane);
        addTeamButton.setVisible(true);

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: pop a window with two labels, two text fields, an add button and a cancel button
    //          Set the add button to Add inputted name and team name to the table, board player list and team list,
    //          then update the table.
    //          set the cancel button as close the window
    private void addPlayer() {
        AddPlayerPanel addPlayerPane = new AddPlayerPanel();

        addPlayerPane.getButtonAdd().addActionListener(e -> {
            String name = addPlayerPane.getNameTextField();
            String teamName = addPlayerPane.getTeamTextField();

            int indexOfTeam = medalBoard.getTeamBoard().indexOfTeam(teamName);

            addPlayerToList(name,teamName,indexOfTeam);

            playerModel.addRow(new Object[]{name, teamName, 0, 0, 0, 0});
            playerTable.setModel(playerModel);
            showTeamView();
            addPlayerPane.getFrame().dispose();
        });

        addPlayerPane.getButtonCancel().addActionListener(e -> addPlayerPane.getFrame().dispose());

        addPlayerPane.getFrame().setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: pop a window with one label, one text field, an add button and a cancel button
    //          Set the add button to Add inputted team name to the table, and team list, then update the table.
    //          set the cancel button as close the window
    private void addTeam() {
        AddTeamPanel addTeamPanel = new AddTeamPanel();

        addTeamPanel.getButtonAdd().addActionListener(e -> {
            String teamName = addTeamPanel.getTeamNameTextField();

            if (medalBoard.getTeamBoard().indexOfTeam(teamName) == -1) {
                medalBoard.getTeamBoard().addTeamToList(new Team(teamName));
                teamModel.addRow(new Object[]{teamName, 0, 0, 0, 0});
            } else {
                JOptionPane.showMessageDialog(null, "There is already a team with that Name");
            }
            addTeamPanel.getFrame().dispose();
        });
        addTeamPanel.getButtonCancel().addActionListener(e -> addTeamPanel.getFrame().dispose());
    }

    // MODIFIES: this
    // EFFECTS: pop a window with a table, and four buttons to add different medals
    private void addMedal() {
        AddMedalPanel addMedalWindow = new AddMedalPanel();
        addMedalWindow.getFrame().add(playerScrollPane);

        addMedalWindow.getButtonAddGold().addActionListener(e -> {
            addAMedal("Gold");
            addMedalWindow.getFrame().setVisible(true);
            addMedalWindow.getFrame().dispose();
        });

        addMedalWindow.getButtonAddSilver().addActionListener(e -> {
            addAMedal("Silver");
            addMedalWindow.getFrame().setVisible(true);
            addMedalWindow.getFrame().dispose();
        });

        addMedalWindow.getButtonAddBronze().addActionListener(e -> {
            addAMedal("Bronze");
            addMedalWindow.getFrame().setVisible(true);
            addMedalWindow.getFrame().dispose();
        });

    }

    // MODIFIES: this
    // EFFECTS: initialize a new player panel and update the table to all the data contained in the board player list
    private void showPlayerView() {
        initializePlayerPanel();
        for (int i = 0; i < medalBoard.getPlayerBoard().getSize(); i++) {
            Player player = medalBoard.getPlayerBoard().getPlayer(i);
            playerModel.addRow(new Object[]{player.getName(),
                    player.getPlayerTeam().getTeamName(),
                    player.getGold(),
                    player.getSilver(),
                    player.getBronze(),
                    player.sumPlayerMedals()});
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize a new team panel and update the table to all the data contained in the board team list
    private void showTeamView() {
        initializeTeamPanel();
        for (int i = 0; i < medalBoard.getTeamBoard().getSize(); i++) {
            Team team = medalBoard.getTeamBoard().getTeam(i);
            teamModel.addRow(new Object[]{team.getTeamName(),
                    team.getTeamGold(),
                    team.getTeamSilver(),
                    team.getTeamBronze(),
                    team.sumTeamMedals()});
        }
    }

    // EFFECTS: save the data inputted into the board to JSON_PATH
    private void saveBoards() {
        String name = JOptionPane.showInputDialog("Please type the name for the board:");
        medalBoard.setName(name);
        try {
            jsonWriter.open();
            jsonWriter.write(medalBoard);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,"Saved " + name + " to " + JSON_PATH);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Sorry, we are unable to write to file: " + JSON_PATH + " at this time.");
        }
    }

    // MODIFIES: this
    // EFFECTS: load the data from JSON_PATH, update the player table from loaded data
    private void loadBoards() {
        try {
            medalBoard = jsonReader.read();
            JOptionPane.showMessageDialog(null,"Loaded " + medalBoard.getName() + " from " + JSON_PATH);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Sorry, we are unable to write to file: " + JSON_PATH + " at this time.");
        }

        showPlayerView();
        playerTable = new JTable(playerModel);
        playerScrollPane = new JScrollPane(playerTable);

        frame.add(playerScrollPane);

        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: check if there is already a team named team name, if yes, add the player to that team;
    //          else, create a new name with teamName and add the player to that team
    private void addPlayerToList(String name, String teamName, int indexOfTeam) {
        Player player = new Player(name);

        if (indexOfTeam != -1) {
            Team playerTeam = medalBoard.getTeamBoard().getTeam(indexOfTeam);
            medalBoard.getPlayerBoard().addPlayerToList(player);
            playerTeam.addPlayerToTeam(player, playerTeam);
        } else {
            Team newTeam = new Team(teamName);
            medalBoard.getPlayerBoard().addPlayerToList(player);
            newTeam.addPlayerToTeam(player, newTeam);
            medalBoard.getTeamBoard().addTeamToList(newTeam);
        }
    }

    // MODIFIES: this
    // EFFECTS: add the selected type of medal to selected player, and update the board
    private void addAMedal(String type) {
        if (playerTable.getSelectedRow() != -1) {
            int selected = playerTable.getSelectedRow();
            int indexOfPlayer = medalBoard.getPlayerBoard()
                    .indexOfPlayer(playerTable.getValueAt(selected, 0).toString());
            switch (type) {
                case "Gold":
                    medalBoard.getPlayerBoard().getPlayer(indexOfPlayer).addGold();
                    break;
                case "Silver":
                    medalBoard.getPlayerBoard().getPlayer(indexOfPlayer).addSilver();
                    break;
                case "Bronze":
                    medalBoard.getPlayerBoard().getPlayer(indexOfPlayer).addBronze();
                    break;
            }
            showPlayerView();
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,"Please select a Player");
        }
    }
}
