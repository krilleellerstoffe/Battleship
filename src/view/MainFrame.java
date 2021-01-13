/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final Controller controller;
    private MainPanel panel;

    private final int width = 800;
    private final int height = 600;

    private final int rows;
    private final int columns;

    public MainFrame (Controller controller, int rows, int columns){

        this.controller = controller;
        this.rows = rows;
        this.columns = columns;
        setupFrame();
    }
    private void setupFrame () {

        int offsetX = width/4;
        int offsetY = width/4;

        setSize(width, height);
        setLocation(offsetX, offsetY);
        setTitle("Battleships");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MainPanel(this, width, height, rows, columns);
        setContentPane(panel);
        setResizable(false);
        setVisible(true);
        pack();
    }
    //ask the player how big the board should be (min 10x10, max 20x20)
    public static int getBoardSize() {

        int choice = 10;
        try {
            choice = Integer.parseInt(JOptionPane.showInputDialog("How many squares wide should the board be? (Min 10, Max 20)"));
        } catch (Exception e) {

        }
        if (choice > 20) {
            choice = 20;
        } else if (choice < 10) {
            choice = 10;
        }
        return choice;
    }
    public void newRules(){

        dispose();
        new Controller();
    }

    public void CenterButtonPressed(int x, int y){

        controller.buttonPressed(x, y);
    }

    public void changeButtonImageToHit(int x, int y) {
        panel.getPnlCenter().changeButtonImageToHit(x,y);
    }
    public void changeButtonImageToMiss(int x, int y) {
        panel.getPnlCenter().changeButtonImageToMiss(x, y);
    }
    public void changeButtonImageToSunk(int x, int y, ImageIcon icon) {
        panel.getPnlCenter().changeButtonImageToSunk(x, y, icon);
    }

    public int[] getAllShips() {

        return controller.getTotalShips();
    }
    public void updateRemainingShips() {

        panel.getPnlEast().updateRemainingShips();
    }

    public int getRows() {

        return rows;
    }
    public int getActions() {

        return controller.getActions();
    }
    public void updateActions() {

        panel.getPnlWest().updateActions();
    }

    public void pressButton(int x, int y) {

        panel.getPnlCenter().buttonPressed(x, y);
    }
    public void letAIPressButton() {
        controller.getAIToGuessLocation();
    }
    public void letAIFinish(){
        controller.getAIToFinish();
    }
    public void disableAIButton() {
        panel.getPnlWest().disableAIButton();
    }
    //commiserates the player
    public void betterLuck() {

        JOptionPane.showMessageDialog(null, "No high score, better luck next time!");
    }
    //opens dialog to get player's name when high score made
    public void getHighScoreName() {

        new HighScoreDialog(this);
    }
    public void setHSName(String name) {

        controller.setHSName(name);
    }
    public void updateHighScores(String[] highScores) {
        panel.getPnlWest().updateHighScores(highScores);
    }
    //information on previous guess
    public void shipHit(String name) {

        panel.getPnlEast().updateLastActionInfo("You hit a "+name+"!");
    }
    public void shipSunk(String name) {

        panel.getPnlEast().updateLastActionInfo("You sunk a "+name+"!");
    }
    public void shotMissed() {
        panel.getPnlEast().updateLastActionInfo("You missed!");
    }

    public void resetGame() {

        controller.resetGame();
        panel.getPnlCenter().resetGrid();
        panel.getPnlEast().updateRemainingShips();
        panel.getPnlWest().enableAIButton();
        panel.getPnlWest().updateActions();
    }
    public void gameOver() {
        panel.getPnlCenter().disableButtons();
        panel.getPnlEast().updateLastActionInfo("All ships sunk! Game Over");
    }
}
