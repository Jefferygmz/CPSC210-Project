package ui;

import javax.swing.*;
import java.awt.*;

// Represents a window for adding a new team
public class AddTeamPanel {
    private JFrame addTeamFrame;
    private JPanel addTeamPanel;
    private JPanel bottomPanel;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private JTextField textFieldTeamName;


    // construct a frame with one label, one text fields, and two buttons,
    public AddTeamPanel() {
        addTeamFrame = new JFrame();
        addTeamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addTeamFrame.setSize(180,130);
        addTeamFrame.setLocationRelativeTo(null);

        addTeamPanel = new JPanel();
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));
        buttonAdd = new JButton("Add");
        buttonCancel = new JButton("Cancel");
        textFieldTeamName = new JTextField(10);

        addTeamPanel.add(new JLabel("Team Name"));
        addTeamPanel.add(textFieldTeamName);

        bottomPanel.add(buttonAdd);
        bottomPanel.add(buttonCancel);
        addTeamFrame.add(addTeamPanel,BorderLayout.CENTER);
        addTeamFrame.add(bottomPanel,BorderLayout.SOUTH);
        addTeamFrame.setVisible(true);
    }

    // getters
    public JFrame getFrame() {
        return addTeamFrame;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public String getTeamNameTextField() {
        return textFieldTeamName.getText();
    }
}
