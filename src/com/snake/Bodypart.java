package com.snake;

import java.awt.*;

public class Bodypart {

    private int xCoor, yCoor, width, height;

    public Bodypart(int xCoor, int yCoor, int tileSize) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.width = tileSize;
        this.height = tileSize;
    }

    public void draw(Graphics g) {
        g.fillRect(xCoor * width, yCoor * height, width, height);
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

}
