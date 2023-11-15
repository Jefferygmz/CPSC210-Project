package ui;

import javax.swing.*;
import java.awt.*;

// Represents a window to let user choose a player and add a selected medal
public class AddMedalPanel {
    private JFrame frame;
    private JPanel bottomPanel;

    private JButton goldButton;
    private JButton silverButton;
    private JButton bronzeButton;
    private JButton buttonCancel;
    private ImageIcon goldImageIcon;
    private ImageIcon silverImageIcon;
    private ImageIcon bronzeImageIcon;

    // constructs a frame with a bottom panel with three buttons
    public AddMedalPanel() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450,250);
        frame.setLocationRelativeTo(null);

        initializeBottomPanel();

        buttonCancel.addActionListener(e -> frame.dispose());

        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initialize icons and set the icons to the three buttons and add them to the bottom panel
    public void initializeBottomPanel() {
        initializeIcons();
        goldButton = new JButton("Add Gold");
        silverButton = new JButton("Add Silver");
        bronzeButton = new JButton("Add Bronze");
        buttonCancel = new JButton("Cancel");

        goldButton.setIcon(goldImageIcon);
        silverButton.setIcon(silverImageIcon);
        bronzeButton.setIcon(bronzeImageIcon);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 5));
        bottomPanel.add(goldButton);
        bottomPanel.add(silverButton);
        bottomPanel.add(bronzeButton);
        bottomPanel.add(buttonCancel);
    }

    // MODIFIES: this
    // EFFECTS: initialize icons and scale them to fit the button
    public void initializeIcons() {
        ImageIcon goldIcon = new ImageIcon("./data/Gold Medal.png");
        ImageIcon silverIcon = new ImageIcon("./data/Silver Medal.png");
        ImageIcon bronzeIcon = new ImageIcon("./data/Bronze Medal.png");

        Image scaledGoldImage = goldIcon.getImage().getScaledInstance(10, 13,
                java.awt.Image.SCALE_SMOOTH);
        Image scaledSilverImage = silverIcon.getImage().getScaledInstance(10, 13,
                java.awt.Image.SCALE_SMOOTH);
        Image scaledBronzeImage = bronzeIcon.getImage().getScaledInstance(10, 13,
                java.awt.Image.SCALE_SMOOTH);

        goldImageIcon = new ImageIcon(scaledGoldImage);
        silverImageIcon = new ImageIcon(scaledSilverImage);
        bronzeImageIcon = new ImageIcon(scaledBronzeImage);
    }

    // getters
    public JFrame getFrame() {
        return frame;
    }

    public JButton getButtonAddGold() {
        return goldButton;
    }

    public JButton getButtonAddSilver() {
        return silverButton;
    }

    public JButton getButtonAddBronze() {
        return bronzeButton;
    }
}
