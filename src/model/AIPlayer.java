/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

import java.util.Random;

public class AIPlayer {

    private Controller controller;
    private GameManager model;

    private final int[] randomLocation;     //stores a randomly created location
    private final boolean[][] locationTried;    //keeps track of locations already tried

    public AIPlayer(Controller controller,GameManager model, int rows, int columns) {

        this.controller = controller;
        this.model = model;
        randomLocation = new int[2];
        locationTried = new boolean[rows][columns];
    }
    //creates a random location, and checks that it's not been used already
    public void createRandomLocation(){

        boolean validLocation = false;
        do {
            int x = new Random().nextInt(locationTried.length);
            int y = new Random().nextInt(locationTried.length);
            if(!locationTried[x][y]) {
                validLocation = true;
                locationTried[x][y] = true;
                randomLocation[0] = x;
                randomLocation[1] = y;
            }
        } while (!validLocation);
    }
    //looks for hits, otherwise makes a guess based on a random location
    public void guessLocation(){

        if(model.hitsExist()){
            int[] hit = model.getHit();
            guessBasedOnHit(hit[0], hit[1]);
        }
        else {
            createRandomLocation();
            controller.pressButton(randomLocation[0], randomLocation[1]);
        }
    }
    //tries the squares surrounding a hit, as long as they have not been tried before
    private void guessBasedOnHit(int x, int y) {
        if((x+1 < locationTried.length)&&(!locationTried[x + 1][y])) {
            x++;
            locationTried[x][y] = true;
            controller.pressButton(x, y);
        }
        else if((x-1 >= 0)&&(!locationTried[x - 1][y])) {
            x--;
            locationTried[x][y] = true;
            controller.pressButton(x, y);
        }
        else if((y+1 < locationTried.length)&&(!locationTried[x][y + 1])) {
            y++;
            locationTried[x][y] = true;
            controller.pressButton(x, y);
        }
        else if((y-1 >= 0)&&(!locationTried[x][y - 1])) {
            y--;
            locationTried[x][y] = true;
            controller.pressButton(x, y);
        }
        else{
            model.setHitSurrounded(x, y);
            guessLocation();
        }
    }
    //once a square has been used, prevents the AI from trying to use it again
    public void setLocationTried(int x, int y) {
        locationTried[x][y] = true;
    }

}
