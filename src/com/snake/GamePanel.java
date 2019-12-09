package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;

public class GamePanel extends JPanel implements Runnable {

    Thread thread;

    boolean running;

    private boolean right = true, left = false, up = false, down = false;

    private Bodypart b;
    private ArrayList<Bodypart> snake;

    private Apple apple;
    private ArrayList<Apple> apples;

    private Random r;

    private int xCoor = 10, yCoor = 10, size = 15;
    private int ticks = 0;
    int Score, HighScore, Time = 70;
    Stopwatch stopwatch;
    boolean gamepause = true;
    Color appleColor = Color.RED;

    STATE State = STATE.MENU;

    public GamePanel() {
        snake = new ArrayList<Bodypart>();
        apples = new ArrayList<Apple>();
        r = new Random();
        thread = new Thread(this);
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                keyPress(e);
            }
        });
        this.setVisible(true);
    }

    /**
     * public void start() {
     * running = true;
     * if (Score > HighScore)
     * HighScore = Score;
     * Score = 0;
     * Time = 0;
     * stopwatch = new Stopwatch();
     * State = STATE.GAME;
     * }
     **/

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void tick() {
        if (State == STATE.GAME) {
            if (snake.size() == 0) {
                b = new Bodypart(xCoor, yCoor, 10);
                snake.add(b);
            }
            ticks++;
            if (ticks > 500000) {
                if (right) xCoor++;
                if (left) xCoor--;
                if (up) yCoor--;
                if (down) yCoor++;

                ticks = 0;

                b = new Bodypart(xCoor, yCoor, 10);
                snake.add(b);

                if (snake.size() > size) {
                    snake.remove(0);
                }
            }
            if (apples.size() == 0) {
                int xCoor = r.nextInt(69);
                int yCoor = r.nextInt(69);

                apple = new Apple(xCoor, yCoor, 10);
                apples.add(apple);
            }

            for (int i = 0; i < apples.size(); i++) {
                if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
                    size++;
                    apples.remove(i);
                    i++;
                    Score++;
                }
            }
        }
        //Collision on snake border body part
        for (int i = 0; i < snake.size(); i++) {
            if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
                if (i != snake.size() - 1) {
                    System.out.println("You Hit Your Self");
                    stop();
                }
            }
        }

        //collision on border
        if (xCoor < 0 || xCoor > 99 || yCoor < 0 || yCoor > 99) {
            System.out.println("You Hit The Wall!");
            stop();
        }

    }

    public void paint(Graphics g) {
        /**
         g.clearRect(0, 0, WIDTH, HEIGHT);
         g.setColor(Color.black);
         g.fillRect(0, 0, WIDTH, HEIGHT);

         for (int i = 0; i < WIDTH / 10; i++) {
         g.drawLine(i * 10, 0, i * 10, HEIGHT);
         }
         for (int i = 0; i < HEIGHT / 10; i++) {
         g.drawLine(0, i * 10, HEIGHT, i * 10);
         }

         g.setColor(Color.black);
         g.fillRect(200, 100, 300, 100);
         g.fillRect(200, 230, 300, 100);
         g.fillRect(200, 360, 300, 100);
         Font fnt0 = new Font("arail", Font.BOLD, 45);
         g.setFont(fnt0);
         g.setColor(Color.yellow);
         g.drawString("Snake Game", WIDTH / 3, 150);
         Font fnt1 = new Font("arail", Font.BOLD, 30);
         g.setFont(fnt1);
         g.setColor(Color.yellow);
         g.drawString("START GAME", (WIDTH / 3) + 10, 280);
         g.drawString("QUIT GAME", (WIDTH / 3) + 20, 410);
         **/
        if (State == STATE.GAME) {
            String s = format("HighScore %d  Score %d  Time %.2f", HighScore, Score, stopwatch.elapsedTime());
            g.setColor(Color.black);
            g.fillRect(0, 0, 1000, 1000);
            for (int i = 0; i < snake.size(); i++) {
                g.setColor(Color.YELLOW);
                snake.get(i).draw(g);
            }
            for (int i = 0; i < apples.size(); i++) {
                g.setColor(appleColor);
                apples.get(i).draw(g);

                g.setColor(Color.CYAN);
                Font fnt2 = new Font("arial", Font.PLAIN, 15);
                g.setFont(fnt2);
                g.drawString(s, 15, 15);

            }
        }
    }

    @Override
    public void run() {
        while (running) {
            tick();
            repaint();

            try {
                Thread.sleep(Time);
            } catch (Exception e) {

            }
        }

    }

    void keyPress(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        } else if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        } else if (key == KeyEvent.VK_UP && !down) {
            up = true;
            left = false;
            right = false;
        } else if (key == KeyEvent.VK_DOWN && !up) {
            down = true;
            left = false;
            right = false;
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (key == KeyEvent.VK_SPACE) {
            if (gamepause) {
                thread.suspend();
                gamepause = false;
            } else {
                thread.resume();
                gamepause = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Time--;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            Time++;
        }

    }
}
