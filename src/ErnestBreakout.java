/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class ErnestBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    //private Paddle leftPaddle;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int score;
    private List<Block> blocks;

    public ErnestBreakout() {
        System.out.println("Ernest Lin\nPeriod 1\nApril 29th\nComputer number: CA-SU-F106-12");
        //set up all variables related to the game
        ball = new Ball(100, 100, 10, 10, Color.blue, 2, 1);
        //leftPaddle = new Paddle(20, 200, 10, 40, Color.orange, 2);
        rightPaddle = new Paddle(760, 200, 10, 40, Color.orange, 4);
        keys = new boolean[4];
        score = 0;
        blocks = new ArrayList<Block>();
        
        int tempX = 20;
        int tempY = 20;
        for (int i = 0; i < 5; i++){
            blocks.add(new Block(tempX, tempY, 10, 100, Color.GREEN));
            blocks.add(new Block(tempX + 20, tempY, 10, 100, Color.GREEN));
            blocks.add(new Block(tempX + 40, tempY, 10, 100, Color.GREEN));
            blocks.add(new Block(tempX + 60, tempY, 10, 100, Color.GREEN));
            tempY += 110;
        }
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
        if (score == 20){
            System.out.println("You won!");
            System.exit(0);
        }
        ball.moveAndDraw(graphToBack);
        //leftPaddle.draw(graphToBack);
        rightPaddle.draw(graphToBack);

        for (Block b: blocks){
            b.draw(graphToBack);
 
        }
        //see if ball hits left wall or right wall
        if (!(ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

              score = 0;
            for (Block b: blocks){
                b.draw(graphToBack, Color.WHITE);
                
            }
            blocks = new ArrayList<Block>();
        
            int tempX = 20;
            int tempY = 20;
            for (int i = 0; i < 5; i++){
                blocks.add(new Block(tempX, tempY, 10, 100, Color.GREEN));
                blocks.add(new Block(tempX + 20, tempY, 10, 100, Color.GREEN));
                blocks.add(new Block(tempX + 40, tempY, 10, 100, Color.GREEN));
                blocks.add(new Block(tempX + 60, tempY, 10, 100, Color.GREEN));
                tempY += 110;
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
                ball.setYSpeed(-1);
            } else {
                ball.setXSpeed(2);
                ball.setYSpeed(1);
            }
        }

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);

        graphToBack.drawString("Score = " + score, 400, 540);
        //graphToBack.drawString("leftScore = " + leftScore, 400, 560);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 550 )) {
            ball.setYSpeed(-ball.getYSpeed());
        }
        if(!(ball.getxPos() >= 20)){
            ball.setXSpeed(-ball.getXSpeed());
        }

        /*if (ball.didCollideLeft(leftPaddle)
                && (ball.didCollideTop(leftPaddle) || ball.didCollideBottom(leftPaddle))) {

            if (ball.getxPos() <= leftPaddle.getxPos() + leftPaddle.getWidth() - Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
*/
        if (ball.didCollideRight(rightPaddle)
                && (ball.didCollideTop(rightPaddle) || ball.didCollideBottom(rightPaddle))) {
            if (ball.getxPos() + ball.getWidth() >= rightPaddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
        for (Block b: blocks){
            if (ball.didCollideLeft(b) && (ball.didCollideTop(b) || ball.didCollideBottom(b))){
                score++;
                b.draw(graphToBack, Color.WHITE);
                blocks.remove(b);
                if (ball.getxPos() + ball.getWidth() >= b.getxPos() + Math.abs(ball.getXSpeed())) {
                    ball.setYSpeed(-ball.getYSpeed());
                } else {
                    ball.setXSpeed(-ball.getXSpeed());
                }
                int whoot = (int) (Math.random() * 2);
                if (whoot == 0) {
                    ball.setXSpeed(2);
                    ball.setYSpeed(-1*((int)Math.random()*20 +1));
                } else {
                    ball.setXSpeed(2);
                    ball.setYSpeed((int)Math.random()*20 + 1);
                }
            }
        }
        //see if the paddles need to be moved
        /*if (keys[0] == true) {
            leftPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            leftPaddle.moveDownAndDraw(graphToBack);
        }
                */
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
