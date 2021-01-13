/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import javax.swing.*;

public class Square {

    private Ship ship;
    private ImageIcon sunkIcon;

    public Square(Ship ship, ImageIcon sunkIcon) {
        this.ship = ship;
        this.sunkIcon = sunkIcon;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public ImageIcon getSunkIcon() {
        return sunkIcon;
    }

    public void setSunkIcon(ImageIcon sunkIcon) {
        this.sunkIcon = sunkIcon;
    }
}
