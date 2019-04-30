

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class LynneBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private int rightScore;
    private String name;
    private Block block;
    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;
    private Block block5;
    private Block block6;
    private Block block7;
    private Block block8;
    private Block block9;
    private Block block10;
    private Block block11;
    private Block block12;
    private Block block13;
    private Block block14;
    private Block block15;
    
    public LynneBreakout() {
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 40, Color.orange, 2);
        rightPaddle = new Paddle(760, 200, 10, 40, Color.orange, 2);
        keys = new boolean[4];
        leftScore = 0;
        rightScore = 0;
        name = "Lynne Dillman";
        block = new Block(10,30,10,100, Color.GREEN);
        block1 = new Block(10,150,10,100, Color.GREEN);
        block2 = new Block(10,270,10,100, Color.GREEN);
        block3 = new Block(10,390,10,100, Color.GREEN);
        block4 = new Block(30,30,10,100, Color.GREEN);
        block5 = new Block(30,150,10,100, Color.GREEN);
        block6 = new Block(30,270,10,100, Color.GREEN);
        block7 = new Block(30,390,10,100, Color.GREEN);
        block8 = new Block(50,30,10,100, Color.GREEN);
        block9 = new Block(50,150,10,100, Color.GREEN);
        block10 = new Block(50,270,10,100, Color.GREEN);
        block11 = new Block(50,390,10,100, Color.GREEN);
        block12 = new Block(70,30,10,100, Color.GREEN);
        block13 = new Block(70,150,10,100, Color.GREEN);
        block14 = new Block(70,270,10,100, Color.GREEN);
        block15 = new Block(70,390,10,100, Color.GREEN);
        
        

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
        rightPaddle.draw(graphToBack);
        block.draw(graphToBack);
        block1.draw(graphToBack);
        block2.draw(graphToBack);
        block3.draw(graphToBack);
        block4.draw(graphToBack);
        block5.draw(graphToBack);
        block6.draw(graphToBack);
        block7.draw(graphToBack);
        block8.draw(graphToBack);
        block9.draw(graphToBack);
        block10.draw(graphToBack);
        block11.draw(graphToBack);
        block12.draw(graphToBack);
        block13.draw(graphToBack);
        block14.draw(graphToBack);
        block15.draw(graphToBack);

        //see if ball hits left wall or right wall
        if ((ball.getyPos() <= 120 && ball.getyPos() >= 30) && ball.getxPos() == 70) {
            block12.setColor(Color.white);
            
            rightScore++;
            }
        if ((ball.getyPos() <= 230 && ball.getyPos() >= 150) && ball.getxPos() == 70) {
            block13.setColor(Color.white);
            
            rightScore++;
            }
        if ((ball.getyPos() <= 370 && ball.getyPos() >= 270) && ball.getxPos() == 70) {
            block14.setColor(Color.white);
            
            rightScore++;
            }
        if ((ball.getyPos() <= 450 && ball.getyPos() >= 390) && ball.getxPos() == 70) {
            block15.setColor(Color.white);   
          
            rightScore++;
            }
        
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);
            
        
            if (ball.getxPos() <= 40) {
                rightScore = 0;
                leftScore = 0;
            }
            if (ball.getxPos() >= 720) {
                leftScore = 0;
                rightScore = 0;
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

        graphToBack.drawString("rightScore = " + rightScore, 400, 540);
        graphToBack.drawString("leftScore = " + leftScore, 400, 560);
        graphToBack.drawString("period 1, 4/29/19, Computer #30 " + name, 400, 50);

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
