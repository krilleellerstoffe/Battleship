/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import javax.swing.*;
import java.util.Random;

public class GameBoard {

    private GameManager model;

    private final int rows; //width of game board in squares
    private final int columns;  //height of game board

    private final Square[][] squares; //an array of squares on the board
    private final boolean[][] occupiedSquares;  //keeps track of the squares that contain something

    private final int[] randomLocation = new int[2];    //a randomly created (start)location for placing of ships
    private int[][] newShipLocation;    //location of all parts of a newly placed ship(affected by length)
    private int direction;  //horizontal or vertical placement(created randomly)

    public GameBoard(GameManager model, int rows, int columns) {

        this.model = model;
        this.rows = rows;
        this.columns = columns;
        squares = new Square[rows][columns];
        occupiedSquares = new boolean[rows][columns];
    }
    //places new ships of given type on the board
    public void addSubmarine() {

        int length = 1;
        if(lookForValidLocation(length)) {
            Ship sub = new Submarine(newShipLocation);
            squares[newShipLocation[0][0]][newShipLocation[0][1]] = new Square(sub, new ImageIcon("resources/sub"+direction+".jpg"));
            model.addShipToTotal(0);
        }
    }
    public void addTorpedo(){

        int length = 2;
        if(lookForValidLocation(length)) {
            Ship torpedo = new Torpedo(newShipLocation);
            for (int i = 0; i < length; i++) {
                squares[newShipLocation[i][0]][newShipLocation[i][1]] = new Square(torpedo, new ImageIcon("resources/torpedo"+i+ direction +".jpg"));
            }
            model.addShipToTotal(1);
        }
    }
    public void addHunter(){

        int length = 3;
        if(lookForValidLocation(length)) {
            Ship hunter = new Hunter(newShipLocation);
            for (int i = 0; i < length; i++) {
                squares[newShipLocation[i][0]][newShipLocation[i][1]] = new Square(hunter, new ImageIcon("resources/hunter" + i + direction + ".jpg"));
            }
            model.addShipToTotal(2);
        }
    }
    public void addCruiser(){

        int length = 4;
        if(lookForValidLocation(length)) {
            Ship cruiser = new Cruiser(newShipLocation);
            for (int i = 0; i < length; i++) {
                squares[newShipLocation[i][0]][newShipLocation[i][1]] = new Square(cruiser, new ImageIcon("resources/cruiser"+i+ direction +".jpg"));
            }
            model.addShipToTotal(3);
        }
    }
    public void addFlagship(){

        int length = 5;
        if(lookForValidLocation(length)) {
            Ship flagship = new Flagship(newShipLocation);
            for (int i = 0; i < length; i++) {
                squares[newShipLocation[i][0]][newShipLocation[i][1]] = new Square(flagship, new ImageIcon("resources/flagship"+i+direction+".jpg"));
            }
            model.addShipToTotal(4);
        }
    }
    //creates a random location for placing of ships, and the direction to be placed
    private int[] createRandomLocation(){

        boolean validLocation = false;
        do {
            int x = new Random().nextInt(rows);
            int y = new Random().nextInt(columns);
            if(!occupiedSquares[x][y]) {
                validLocation = true;
                randomLocation[0] = x;
                randomLocation[1] = y;
            }
        } while (!validLocation);
        return randomLocation;
    }
    private int createRandomDirection(){
        direction = new Random().nextInt(2);
        return direction;
    }
    //checks that the free location can accommodate a ship of the given length(either vertically/horizontally)
    private boolean lookForValidLocation(int length) {

        boolean validDirection = false;
        int count = 0;
        while (!validDirection) {
            count++;
            if(count>100){
                validDirection = false;
                break;
            }
            createRandomLocation();
            createRandomDirection();
            switch (direction) {
                case 0:
                    if (checkForValidHorizontal(length)) {
                        newShipLocation = addShipToHorizontalLocation(length);
                        validDirection = true;
                    }
                    break;
                case 1:
                    if (checkForValidVertical(length)) {
                        newShipLocation = addShipToVerticalLocation(length);
                        validDirection = true;
                    }
                    break;
            }
        }
        return validDirection;
    }
    private boolean checkForValidHorizontal(int length) {

        boolean validDirection = false;
        int validSquares = 0;
        for (int i = 0; (i < length) && (randomLocation[0]+i < columns); i++) {
            if (!occupiedSquares[randomLocation[0]+i][randomLocation[1]]) { //checks horizontal
                validSquares++;
            }
        }
        if(validSquares>=length){
            validDirection = true;
        }
        return validDirection;
    }
    private boolean checkForValidVertical(int length) {

        boolean validDirection = false;
        int validSquares = 0;
        for(int i = 0; (i < length)&&(randomLocation[1]+i < rows); i++) {
            if (!occupiedSquares[randomLocation[0]][randomLocation[1]+i]) { //checks vertical
                validSquares++;
            }
        }
        if (validSquares>=length){
            validDirection = true;
        }
        return validDirection;
    }
    //once as suitable location is found, adds the new ship
    private int[][] addShipToHorizontalLocation(int length){
        int[][] location = new int[length][2];
        for(int i = 0; i<length; i++){
            location[i][0] = randomLocation[0]+i;
            location[i][1] = randomLocation[1];
            occupiedSquares[randomLocation[0]+i][randomLocation[1]] = true;
        }
        return location;
    }
    private int[][] addShipToVerticalLocation(int length){
        int[][] location = new int[length][2];
        for(int i = 0; i<location.length; i++){
            location[i][0] = randomLocation[0];
            location[i][1] = randomLocation[1]+i;
            occupiedSquares[randomLocation[0]][randomLocation[1]+i] = true;
        }
        return location;
    }
    //returns square and ship objects, and/or their attributes
    public Square getSquare(int x,int y) {
        return squares[x][y];
    }
    public Ship getShip(int x, int y) {
        return squares[x][y].getShip();
    }
    //clears squares of all ships
    public void resetBoard(){

        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
                squares[i][j] = null;
                occupiedSquares[i][j] = false;
            }
        }
    }
}
