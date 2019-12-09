package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;

public class Menu extends JPanel implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 1000, HEIGHT = 1000;

    private Thread thread;

    private boolean running;

    private boolean right = true, left = false, up = false, down = false;

    private Bodypart b;
    private ArrayList<Bodypart> snake;

    private Apple apple;
    private ArrayList<Apple> apples;

    private Random r;

    private int xCoor = 10, yCoor = 10, size = 15;
    private int ticks = 0;
    int Score, HighScore, Time;

    private Stopwatch stopwatch;

    public static enum STATE {
        MENU,
        GAME
    }

    public static STATE State = STATE.MENU;

    public Menu() {
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        addMouseListener(this);
        snake = new ArrayList<Bodypart>();
        apples = new ArrayList<Apple>();

        r = new Random();

        start();
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
        if (Score > HighScore)
            HighScore = Score;
        Score = 0;
        Time = 0;
        stopwatch = new Stopwatch();

    }

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
                    Time++;
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
        g.clearRect(0, 0, WIDTH, HEIGHT);
        String s = format("HighScore %d  Score %d  Time %.2f", HighScore, Score, stopwatch.elapsedTime());
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

        if (State == STATE.GAME) {

            g.setColor(Color.black);
            g.fillRect(0, 0, 1000, 1000);
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).draw(g);
            }
            for (int i = 0; i < apples.size(); i++) {
                apples.get(i).draw(g);

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
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_UP && !down) {
            up = true;
            left = false;
            right = false;
        }
        if (key == KeyEvent.VK_DOWN && !up) {
            down = true;
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int mx = e.getX();
        int my = e.getY();
        if (mx >= 0 && mx <= 1000) {
            if (my >= 0 && my <= 1000) {
                Menu.State = Menu.STATE.GAME;
                System.out.println("START");

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
