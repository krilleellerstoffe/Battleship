/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastPanel extends JPanel {

    private final MainFrame view;
    private GridLayout layout;

    private final int width;
    private final int height;

    private JLabel lblLastAction;
    private JLabel[] lblShips;
    private int[] allShips;
    private JButton btnNewRules; //if user wants to change the size of the board

    public EastPanel(MainFrame view, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;

        setupPanel();
    }
    private void setupPanel() {

        layout = new GridLayout(7, 2);
        setLayout(layout);
        setSize(width, height);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        lblLastAction = new JLabel();
        add(lblLastAction);

        allShips = view.getAllShips();
        lblShips = new JLabel[allShips.length];
        for (int i = 0; i < lblShips.length; i++) {
            lblShips[i] = new JLabel();
            getLabelInfo(i);
            add(lblShips[i]);
        }

        btnNewRules = new JButton("Change board size");
        btnNewRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.newRules();
            }
        });
        add(btnNewRules);
    }
    //update information on remaining ships
    public void updateRemainingShips(){
        allShips = view.getAllShips();
        for (int i = 0; i < lblShips.length; i++) {
            getLabelInfo(i);
        }
    }
    //displays the result of previous guess
    public void updateLastActionInfo(String text){

        lblLastAction.setText(text);
    }
    //gets ship information and count of each to be displayed
    private void getLabelInfo(int shipId) {

        switch (shipId) {
            case 0:
                lblShips[shipId].setText("Submarines x " + allShips[shipId] + " ");
                lblShips[shipId].setIcon(new ImageIcon("resources/sub1.jpg"));
                break;
            case 1:
                lblShips[shipId].setText("Torpedos x " + allShips[shipId] + " ");
                lblShips[shipId].setIcon(new ImageIcon("resources/torpedo.jpg"));
                break;
            case 2:
                lblShips[shipId].setText("Hunters x " + allShips[shipId] + " ");
                lblShips[shipId].setIcon(new ImageIcon("resources/hunter.jpg"));
                break;
            case 3:
                lblShips[shipId].setText("Cruisers x " + allShips[shipId] + " ");
                lblShips[shipId].setIcon(new ImageIcon("resources/cruiser.jpg"));
                break;
            case 4:
                lblShips[shipId].setText("Flagships x " + allShips[shipId] + " ");
                lblShips[shipId].setIcon(new ImageIcon("resources/flagship.jpg"));
                break;
        }
    }

}
