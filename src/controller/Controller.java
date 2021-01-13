/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package controller;

import model.AIPlayer;
import model.GameManager;
import view.MainFrame;

public class Controller {

    private final GameManager model;
    private final MainFrame view;
    private AIPlayer aiPlayer;
    private final int rows;
    private final int columns;

    public Controller(){

        int size = MainFrame.getBoardSize();    //asks the user for a board size, uses this size to create components
        this.rows = size;
        this.columns = size;
        model = new GameManager(this, rows, columns);
        view = new MainFrame(this, rows, columns);
        aiPlayer = new AIPlayer(this, model, rows, columns);
        updateHighScores();
    }
    //checks if a ship exists, and it's status. Updates view based on result.(Hit/Miss/Sunk) Finally checks if the game is over.
    public void buttonPressed(int x, int y) {

        int shipExists = model.doesShipExist(x, y);

        if (shipExists == 1) {  //ship exists, has enough lives left to survive a hit
            view.shipHit(model.getShip(x, y).getName());
            view.changeButtonImageToHit(x, y);
        }
        else if(shipExists == 2) {  //ship exists, and is sunk by the current hit
            view.shipSunk(model.getShip(x, y).getName());
            int [][] startOfShip = model.getShip(x, y).getLocation();
            for(int i = 0; i < model.getShip(x, y).getLength(); i++){   //gets all the squares a particular ship exists on
                view.changeButtonImageToSunk(startOfShip[i][0], startOfShip[i][1], model.getShipIcon(startOfShip[i][0], startOfShip[i][1]));
                model.removeHits(startOfShip[i][0], startOfShip[i][1]);
            }
        }
        else {  //no ship found
            view.shotMissed();
            view.changeButtonImageToMiss(x, y);
        }
        model.increaseActions();
        view.updateActions();
        aiPlayer.setLocationTried(x, y);

        if(model.isGameOver()){ //if all ships destroyed, triggers game over
            view.gameOver();
            model.gameIsOver();
        }
    }
    //relays a button press from the AI player
    public void pressButton(int x, int y){

        view.pressButton(x,y);
    }
    //checks that the game is not over, then tells the AI player to choose a button to press. Double checks AI button is disabled if game over.
    public void getAIToGuessLocation(){

        if (model.getActions() < (rows * columns)) {
            aiPlayer.guessLocation();
        } else {
            view.disableAIButton();
        }
    }
    public void getAIToFinish(){

        while(!model.isGameOver()) {
        getAIToGuessLocation();
        }
    }
    //gets a list of the remaining ships from game manager
    public int[] getTotalShips() {

        return model.getTotalShips();
    }
    //relays a request from model to update the remaining number of ships in view.
    public void updateRemainingShips() {

        view.updateRemainingShips();
    }
    //gets the running total number of guesses performed
    public int getActions() {

       return model.getActions();
    }
    //if game over, checks for a new high score
    public void gameOver(boolean newHighScore) {

        view.disableAIButton(); //disables the AI button
        if(newHighScore){
            view.getHighScoreName();
        }
        else
            view.betterLuck();
        updateHighScores();
    }
    //relays a name to the high score list and updates the list in view
    public void setHSName(String name) {
        model.setNewHighScore(name);
        updateHighScores();
    }
    //gets the list of current high scores
    public void updateHighScores(){

        view.updateHighScores(model.getHighScores());
    }
    //resets the board and AI
    public void resetGame() {

        model.resetBoard();
        aiPlayer = new AIPlayer(this, model, rows, rows);
    }
}
