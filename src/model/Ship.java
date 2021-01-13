/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

public abstract class Ship {

    private final int id;   //identifier to prevent complicated code in other classes
    private final String name;
    private int lives;  //number of hits left that a ship can take before sinking
    private int length; //length of ship
    private boolean sunk;   //is ship alive or not
    private int[][] location;   //location of all ship parts (can be horizontal or vertical)

    public Ship(int id, String name, int lives, int length, int[][] location){

        this.id = id;
        this.name = name;
        this.lives = lives;
        this.length = length;
        this.location = location;
    }

    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }

    public int getLives() {
        return lives;
    }
    public void reduceLivesByOne() {
        lives--;
        if(lives==0)
            sunk = true;
    }

    public boolean isSunk() {
        return sunk;
    }
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public int[][] getLocation() {
        return location;
    }
    public void setLocation(int[][] location) {
        this.location = location;
    }

    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
}
