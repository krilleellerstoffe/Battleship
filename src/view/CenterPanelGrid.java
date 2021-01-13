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

public class CenterPanelGrid extends JPanel{

    private final MainFrame view;

    private GridLayout layout;

    private final int height;
    private final int width;

    private final int rows;
    private final int columns;

    private JButton[][] buttonGrid; //array of buttons which represent each square on board
    private final boolean[][] buttonAlreadyUsed;    //keeps track of used/disabled buttons

    public CenterPanelGrid(MainFrame view,int rows, int columns, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        buttonAlreadyUsed = new boolean[rows][columns];
        setupPanel();
        createComponents();
    }

    private void setupPanel() {

        layout = new GridLayout(rows, columns);
        setLayout(layout);
        setSize(width,height);
    }
    private void createComponents() {

        buttonGrid = new JButton[rows][columns];

        for (int row = 0; row < buttonGrid.length; row++) {
            for(int column = 0; column < buttonGrid[row].length; column++){
                buttonGrid[row][column] = new JButton();
                buttonGrid[row][column].setPreferredSize(new Dimension(50, 50));
                buttonGrid[row][column].setIcon(new ImageIcon("resources/wave.png"));
                int x = row;
                int y = column;
                buttonGrid[row][column].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonPressed(x, y);
                    }
                });
                add(buttonGrid[row][column]);
            }
        }
    }
    //if the button hasn't already been pressed, sets a guess in motion
    public void buttonPressed(int x, int y){

        if(!buttonAlreadyUsed[x][y]) {
            buttonAlreadyUsed[x][y] = true;
            view.CenterButtonPressed(x, y);
        }
    }
    //change the image on a button to represent it's current state
    public void changeButtonImageToHit(int x, int y) {

        buttonGrid[x][y].setIcon(new ImageIcon("resources/explosion.gif"));
    }
    public void changeButtonImageToMiss(int x, int y){

        buttonGrid[x][y].setIcon(new ImageIcon("resources/miss.png"));
    }
    public void changeButtonImageToSunk(int x, int y, ImageIcon icon) {

            buttonGrid[x][y].setIcon(icon);
    }
    //resets all the buttons
    public void resetGrid(){

        for (int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                buttonGrid[i][j].setIcon(new ImageIcon("resources/wave.png"));
                buttonAlreadyUsed[i][j] = false;
            }
        }

    }
    //disables all the buttons in case of game over state
    public void disableButtons() {
        for (int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                buttonAlreadyUsed[i][j] = true;
            }
        }
    }
}
