package ui;

import javax.swing.*;
import java.awt.*;

// Represents a window for adding a new player
public class AddPlayerPanel {
    private JFrame frame;
    private JPanel addPlayerPanel;
    private JPanel bottomPanel;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldTeam;

    // construct a frame with two labels, two text fields, and two buttons,
    public AddPlayerPanel() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(180,180);
        frame.setLocationRelativeTo(null);

        addPlayerPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));
        buttonAdd = new JButton("Add");
        buttonCancel = new JButton("Cancel");
        textFieldName = new JTextField(10);
        textFieldTeam = new JTextField(10);

        addPlayerPanel.add(new JLabel("Name"));
        addPlayerPanel.add(textFieldName);
        addPlayerPanel.add(new JLabel("Team"));
        addPlayerPanel.add(textFieldTeam);

        bottomPanel.add(buttonAdd);
        bottomPanel.add(buttonCancel);
        frame.add(addPlayerPanel,BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // getters
    public JFrame getFrame() {
        return frame;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public String getNameTextField() {
        return textFieldName.getText();
    }

    public String getTeamTextField() {
        return textFieldTeam.getText();
    }
}

