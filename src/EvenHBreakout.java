/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

/**
 *
 * @author hue1430
 */
public class EvenHBreakout extends JFrame implements Runnable, KeyListener{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private Ball ball;
    private Block[] blocks = new Block[20];
    private Paddle leftPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    
    public EvenHBreakout() {
        super("PONG");
        setSize(WIDTH, HEIGHT);

        this.setFocusable(true);

        setVisible(true);
        
        ball = new Ball(0, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 80, Color.orange, 2);
        keys = new boolean[2];
        leftScore = 0;
        for (int i = 0; i<5;i++) {
            blocks[i] = new Block(700, 46+i*100, 5, 93, Color.green);
        }
        for (int i = 5; i<10;i++) {
            blocks[i] = new Block(710, 46+(i-5)*100, 5, 93, Color.green);
        }
        for (int i = 10; i<15;i++) {
            blocks[i] = new Block(720, 46+(i-10)*100, 5, 93, Color.green);
        }
        for (int i = 15; i<20;i++) {
            blocks[i] = new Block(730, 46+(i-15)*100, 5, 93, Color.green);
        }
        setBackground(Color.WHITE);
        new Thread(this).start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);		//starts the key thread to log key strokes
    }
    
    public void update(Graphics window) {
        paint(window);
    }
    
    public void paint(Graphics window) {
        Graphics2D twoDGraph = (Graphics2D) window;

        //take a snap shop of the current Frame and same it as an image
        //that is the exact same width and height as the current Frame
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();

        graphToBack.setColor(Color.red);

        ball.moveAndDraw(graphToBack);
        leftPaddle.draw(graphToBack);
        for (int i = 0; i<20;i++) {
            blocks[i].draw(graphToBack);
        }
        
        
        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

            if (ball.getxPos() >= 720) {
                leftScore = 0;
            }

            try {
                Thread.currentThread().sleep(950);
            } catch (Exception e) {
            }

            ball.draw(graphToBack, Color.WHITE);
            ball.setxPos((int) (Math.random() * 50) + 400);
            ball.setyPos((int) (Math.random() * 50) + 300);
            int whoot = (int) (Math.random() * 2);
            if (whoot == 0) {
                ball.setXSpeed(2);
                ball.setYSpeed(1);
            } else {
                ball.setXSpeed(-2);
                ball.setYSpeed(1);
            }
        }

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);

        graphToBack.drawString("Score = " + leftScore, 400, 560);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 450)) {
            ball.setYSpeed(-ball.getYSpeed());
        }

        if (ball.didCollideLeft(leftPaddle)
                && (ball.didCollideTop(leftPaddle) || ball.didCollideBottom(leftPaddle))) {

            if (ball.getxPos() <= leftPaddle.getxPos() + leftPaddle.getWidth() - Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
        for (int i =0; i < 20; i ++) {
            if (ball.didCollideRight(blocks[i])
                && (ball.didCollideTop(blocks[i]) || ball.didCollideBottom(blocks[i]))) {
                blocks[i].setColor(Color.white);
                
                leftScore++;

            if (ball.getxPos() + ball.getWidth() >= blocks[i].getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
        }
        


        //see if the paddles need to be moved
        if (keys[0] == true) {
            leftPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            leftPaddle.moveDownAndDraw(graphToBack);
        }

        twoDGraph.drawImage(back, null, 0, 0);
    }
    
    public void keyPressed(KeyEvent e) {
        switch (toUpperCase(e.getKeyChar())) {
            case 'W':
                keys[0] = true;
                break;
            case 'S':
                keys[1] = true;
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (toUpperCase(e.getKeyChar())) {
            case 'W':
                keys[0] = false;
                break;
            case 'S':
                keys[1] = false;
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(8);
                repaint();
            }
        } catch (Exception e) {
        }
    }
    
    public static void main(String args[]) {
        System.out.println("Evan Hu APCSA Period 1");
        EvenHBreakout run = new EvenHBreakout();
    }
}
