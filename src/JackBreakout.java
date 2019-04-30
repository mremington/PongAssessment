

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
import java.util.ArrayList;
import java.util.List;

public class JackBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private List<Paddle> BB;
    /*private Paddle leftPaddle1;
    private Paddle leftPaddle2;
    private Paddle leftPaddle3;
    private Paddle leftPaddle4;
    private Paddle leftPaddle5;
    private Paddle leftPaddle6; */
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private int rightScore;

    public JackBreakout() {
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        BB = new ArrayList<Paddle>();
        BB.add(new Paddle(20, 20, 10, 75, Color.green, 2));
        BB.add(new Paddle(20, 105, 10, 75, Color.green, 2));
        BB.add(new Paddle(20, 190, 10, 75, Color.green, 2));
        BB.add(new Paddle(20, 275, 10, 75, Color.green, 2));
        BB.add(new Paddle(20, 360, 10, 75, Color.green, 2));
        BB.add(new Paddle(20, 445, 10, 75, Color.green, 2));
        rightPaddle = new Paddle(760, 200, 10, 400, Color.orange, 2);
        keys = new boolean[4];
        leftScore = 0;
        rightScore = 0;

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
        /*leftPaddle1.draw(graphToBack);
        leftPaddle2.draw(graphToBack);
        leftPaddle3.draw(graphToBack);
        leftPaddle4.draw(graphToBack);
        leftPaddle5.draw(graphToBack);
        leftPaddle6.draw(graphToBack);*/
        for(Paddle p : BB){
            p.draw(graphToBack);
        }
        rightPaddle.draw(graphToBack);

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);
            ball.setxPos(400);
            ball.setyPos(300);
            rightScore = 0;
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

        graphToBack.drawString("Score = " + rightScore, 400, 540);
        graphToBack.drawString("Jack Bao; Period 1; 4/29; 04" + leftScore, 400, 560);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 450)) {
            ball.setYSpeed(-ball.getYSpeed());
        }

        //collide with breakouts
        for (Paddle p : BB) {
        if (ball.didCollideLeft(p)
                && (ball.didCollideTop(p) || ball.didCollideBottom(p))) {

            if (ball.getxPos() <= p.getxPos() + p.getWidth() - Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                rightScore++;
                ball.setXSpeed(-ball.getXSpeed());
                p.draw(graphToBack, Color.WHITE);
                BB.remove(p);
                break;
            }
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
        /*if (keys[0] == true) {
            p.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            p.moveDownAndDraw(graphToBack);
        }*/
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

