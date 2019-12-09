package com.snake;

import javax.swing.*;

public class Main {

    public Main() {
        JFrame frame = new JFrame();
        Menu menu = new Menu();

        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("SNAKECODING");

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Main();
    }
}
