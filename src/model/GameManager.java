/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

import javax.swing.*;

public class GameManager {

    private final Controller controller;
    private final HighScoreKeeper highScoreKeeper;
    private final GameBoard board;

    private final int rows;
    private final int columns;

    private final int[] totalShips = new int[5];    //list of ships on board and how many of each

    private int numberOfActions;    //number of guesses made by player
    private boolean gameOver;
    private final boolean[][] hits; //keeps track of any hits which have not yet sunk a ship
    private final int[] hitFound = new int[2];   //saves the coordinates of the first hit found in above array

    public GameManager(Controller controller, int rows, int columns){

        this.controller = controller;
        highScoreKeeper = new HighScoreKeeper(rows);
        board = new GameBoard(this, rows, columns);
        this.rows = rows;
        this.columns = columns;
        hits = new boolean[rows][columns];
        populateBoard();
    }
    //adds a different number of ships to the board, depending on it's size
    private void populateBoard(){

        board.addFlagship();    //only ever one flagship
        for(int i = 0; i <= rows; i+=8){
            board.addCruiser();
        }
        for(int i = 0; i <= rows; i+=6){
            board.addHunter();
        }
        for(int i = 0; i <= rows; i+=4){
            board.addTorpedo();
        }
        for(int i = 0; i <= rows; i+=4){
            board.addSubmarine();
        }
    }
    //returns the list o f remaining ships
    public int[] getTotalShips(){
        return totalShips;
    }
    //returns a sum of all the ships remaining
    public int countTotalShips(){

        int count = 0;
        for(int i = 0; i<totalShips.length; i++){
            count+=totalShips[i];
        }
        return count;
    }
    //increases the count of a particular type of ship in the remaining ships list
    public void addShipToTotal(int shipId) {

        totalShips[shipId]++;
    }
    //increase the action count (number of guesses made by player)
    public void increaseActions() {

        numberOfActions++;
    }
    //returns number of guesses made
    public int getActions() {
        return numberOfActions;
    }
    //checks if any previous guesses were a hit that hasn't yet sunk a ship
    public boolean hitsExist() {

        boolean hitsExist = false;
        for(int i = 0; i < hits.length; i++){
            for(int j = 0; j < hits.length; j++){
                if(hits[i][j]){
                    hitFound[0] = i;
                    hitFound[1] = j;
                    hitsExist = true;
                }
            }
        }
        return hitsExist;
    }
    //gets the location of such a hit
    public int[] getHit() {

        return hitFound;
    }
    //if a hit sinks a boat, removes these from the hits list(as no longer relevant)
    public void removeHits(int x, int y) {
        hits[x][y] = false;
    }
    //updates high score list with name and score
    public void setNewHighScore(String name) {

        highScoreKeeper.setNewHighScore(name, numberOfActions);
    }
    //gets a list of high scores
    public String[] getHighScores(){

        return highScoreKeeper.getHighScores();
    }
    //checks if ship exists on a square, and whether it is alive/sunk after a hit
    public int doesShipExist(int x, int y) {

        int shipExists = 0;
        if (!(board.getSquare(x, y)==null)){
            hits[x][y] = true;
            shipExists = 1;
            if (takeLifeFromShip(x, y))
                shipExists = 2;
        }
        else{
            hits[x][y] = false;
        }
        return shipExists;
    }
    //returns the ship on a particular square
    public Ship getShip(int x, int y){
        return board.getSquare(x,y).getShip();
    }
    //gets the relevant image for when a ship is sunk
    public ImageIcon getShipIcon(int x, int y) {

        return board.getSquare(x, y).getSunkIcon();
    }
    //reduces lives by one, 'sinks' ship if there was only one life left
    public boolean takeLifeFromShip(int x, int y){

        boolean sunk = false;
        int shipID = board.getShip(x, y).getID();

        board.getShip(x, y).reduceLivesByOne();
        hits[x][y] = true;

        if(board.getShip(x, y).isSunk()) {
            sunk = true;
            totalShips[shipID]--;
            controller.updateRemainingShips();
        }
        return sunk;
    }
    //sets game as over when all ships sunk
    public boolean isGameOver() {

        if(countTotalShips()==0){
            gameOver = true;
        }
        return gameOver;
    }
    //checks if new high score has been made, and ends the game
    public void gameIsOver() {

        boolean newHighScore = highScoreKeeper.checkHighScore(numberOfActions);
        controller.gameOver(newHighScore);
    }
    //resets the board and repopulates it with a new array of ships
    public void resetBoard(){

        numberOfActions = 0;
        board.resetBoard();
        for(int i = 0;i<totalShips.length; i++){
            totalShips[i] = 0;
        }
        gameOver = false;
        populateBoard();
    }

    public void setHitSurrounded(int x, int y) {
        hits[x][y] = false;
    }
}
