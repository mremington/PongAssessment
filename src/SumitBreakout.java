
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.*;
import static java.lang.Character.toUpperCase;
import java.util.*;

public class SumitBreakout extends Canvas implements KeyListener, Runnable{
    
    private Ball ball;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private int rightScore;
    
    private int score;
    
    private Paddle breakOut1;
    private Paddle breakOut2;
    private Paddle breakOut3;
    private Paddle breakOut4;
    private Paddle breakOut5;
    
    private Paddle breakOut6;
    private Paddle breakOut7;
    private Paddle breakOut8;
    private Paddle breakOut9;
    private Paddle breakOut10;
    
    private Paddle breakOut11;
    private Paddle breakOut12;
    private Paddle breakOut13;
    private Paddle breakOut14;
    private Paddle breakOut15;
    
    private Paddle breakOut16;
    private Paddle breakOut17;
    private Paddle breakOut18;
    private Paddle breakOut19;
    private Paddle breakOut20;

    public SumitBreakout() {
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 40, Color.orange, 2);
        
        rightPaddle = new Paddle(760, 200, 10, 40, Color.orange, 0);
        
        breakOut1 = new Paddle(680, 30, 10, 80, Color.green, 0);
        breakOut2 = new Paddle(680, 120, 10, 80, Color.green, 0);
        breakOut3 = new Paddle(680, 220, 10, 80, Color.green, 0);
        breakOut4 = new Paddle(680, 320, 10, 80, Color.green, 0);
        breakOut5 = new Paddle(680, 420, 10, 80, Color.green, 0);
        
        breakOut6 = new Paddle(700, 30, 10, 80, Color.green, 0);
        breakOut7 = new Paddle(700, 120, 10, 80, Color.green, 0);
        breakOut8 = new Paddle(700, 220, 10, 80, Color.green, 0);
        breakOut9 = new Paddle(700, 320, 10, 80, Color.green, 0);
        breakOut10 = new Paddle(700, 420, 10, 80, Color.green, 0);
        
        breakOut11 = new Paddle(720, 30, 10, 80, Color.green, 0);
        breakOut12 = new Paddle(720, 120, 10, 80, Color.green, 0);
        breakOut13 = new Paddle(720, 220, 10, 80, Color.green, 0);
        breakOut14 = new Paddle(720, 320, 10, 80, Color.green, 0);
        breakOut15 = new Paddle(720, 420, 10, 80, Color.green, 0);
                
        breakOut16 = new Paddle(740, 30, 10, 80, Color.green, 0);
        breakOut17 = new Paddle(740, 120, 10, 80, Color.green, 0);
        breakOut18 = new Paddle(740, 220, 10, 80, Color.green, 0);
        breakOut19 = new Paddle(740, 320, 10, 80, Color.green, 0);
        breakOut20 = new Paddle(740, 420, 10, 80, Color.green, 0);
        
        keys = new boolean[4];
        leftScore = 0;
        rightScore = 0;
        
        score = 0;
        
        System.out.println("Sumit Gupta; P.1; 4, 29, 2019");
        

        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
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
        //rightPaddle.draw(graphToBack);
        
        
        breakOut1.draw(graphToBack);
        breakOut2.draw(graphToBack);
        breakOut3.draw(graphToBack);
        breakOut4.draw(graphToBack);
        breakOut5.draw(graphToBack);
        
        breakOut6.draw(graphToBack);
        breakOut7.draw(graphToBack);
        breakOut8.draw(graphToBack);
        breakOut9.draw(graphToBack);
        breakOut10.draw(graphToBack);
        
        breakOut11.draw(graphToBack);
        breakOut12.draw(graphToBack);
        breakOut13.draw(graphToBack);
        breakOut14.draw(graphToBack);
        breakOut15.draw(graphToBack);
        
        breakOut16.draw(graphToBack);
        breakOut17.draw(graphToBack);
        breakOut18.draw(graphToBack);
        breakOut19.draw(graphToBack);
        breakOut20.draw(graphToBack);

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);
            
            score = 0;

            if (ball.getxPos() <= 40) {
                rightScore++;
            }
            if (ball.getxPos() >= 720) {
                leftScore++;
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

        //graphToBack.drawString("rightScore = " + rightScore, 400, 540);
        //graphToBack.drawString("leftScore = " + leftScore, 400, 560);
        
        graphToBack.drawString("Score = " + score, 400, 500);
        
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

        if (ball.didCollideRight(rightPaddle)
                && (ball.didCollideTop(rightPaddle) || ball.didCollideBottom(rightPaddle))) {
            if (ball.getxPos() + ball.getWidth() >= rightPaddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        /*   
        if (ball.didCollideRight(breakOut1)
                && (ball.didCollideTop(breakOut1) || ball.didCollideBottom(breakOut1))) {
            if (ball.getxPos() + ball.getWidth() >= breakOut1.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
                score++;
            }
            
        if (ball.didCollideRight(breakOut2)
                && (ball.didCollideTop(breakOut2) || ball.didCollideBottom(breakOut2))) {
            if (ball.getxPos() + ball.getWidth() >= breakOut2.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
                score++;
            }
            
        if (ball.didCollideRight(breakOut3)
                && (ball.didCollideTop(breakOut3) || ball.didCollideBottom(breakOut3))) {
            if (ball.getxPos() + ball.getWidth() >= breakOut3.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
                score++;
            }
            
        if (ball.didCollideRight(breakOut4)
                && (ball.didCollideTop(breakOut4) || ball.didCollideBottom(breakOut4))) {
            if (ball.getxPos() + ball.getWidth() >= breakOut4.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
                score++;
            }
        
        if (ball.didCollideRight(breakOut5)
                && (ball.didCollideTop(breakOut5) || ball.didCollideBottom(breakOut5))) {
            if (ball.getxPos() + ball.getWidth() >= breakOut5.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
                score++;
            }
        */
            
        }

        //see if the paddles need to be moved
        if (keys[0] == true) {
            leftPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            leftPaddle.moveDownAndDraw(graphToBack);
        }
        if (keys[2] == true) {
            rightPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[3] == true) {
            rightPaddle.moveDownAndDraw(graphToBack);
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
            case 'I':
                keys[2] = true;
                break;
            case 'K':
                keys[3] = true;
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
            case 'I':
                keys[2] = false;
                break;
            case 'K':
                keys[3] = false;
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
    
}
