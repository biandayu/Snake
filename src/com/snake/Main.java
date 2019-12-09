package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    GamePanel panel;
    JMenuBar menu;
    JMenu controls;
    JMenuItem play;
    JMenuItem restart;
    JMenuItem changeColor;
    JMenuItem instructions;

    public Main() {
        this.setResizable(false);

        menu = new JMenuBar();
        controls = new JMenu("Controls");
        play = new JMenuItem("Play");
        restart = new JMenuItem("Restart");
        changeColor = new JMenuItem("Change Color");
        instructions = new JMenuItem("Instructions");

        restart.setMnemonic(KeyEvent.VK_R);
        restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        restart.setMnemonic(KeyEvent.VK_I);
        instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        play.setMnemonic(KeyEvent.VK_P);
        play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        controls.add(play);
        controls.add(restart);
        controls.add(changeColor);
        controls.add(instructions);
        menu.add(controls);
        this.setJMenuBar(menu);

        panel = new GamePanel();
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(1000, 1000));
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent eee) {
                play();
            }
        });

        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ee) {
                restart();
            }
        });

        changeColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ee) {
                changeColor();
            }
        });

        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ee) {
                instruct();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                panel.keyPress(e);
            }
        });

        this.setVisible(true);
        this.pack();
    }

    //function to start thread for starting game
    public void play() {
        panel.State = STATE.GAME;
        panel.running = true;
        panel.Score = 0;
        panel.Time = 0;
        panel.stopwatch = new Stopwatch();
        panel.thread.start();
        play.setEnabled(false);
    }

    //function for restarting game, it will resize the snake to its initial position and initial length
    public void restart() {
        // TODO
    }

    public void changeColor() {
        if (panel.appleColor == Color.RED) {
            panel.appleColor = Color.ORANGE;
        } else {
            panel.appleColor = Color.RED;
        }
    }

    //function to be called on the click of instructions
    public void instruct() {
        JOptionPane.showMessageDialog(this, "1. Ctrl+R - Restart \n2. S - Increase Snake speed \n3. D - Decrease Snake speed \n4. LEFT - Turn left \n5. RIGHT - Turn right \n6. UP - go up \n7. DOWN - go down \n8. SPACE - to pause/resume the game \n0. Ctrl+I - to view Instructions", "Instructions", 1);
    }

    public static void main(String[] args) {
        new Main();
    }
}
