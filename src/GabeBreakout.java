
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import static java.lang.Character.toUpperCase;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ceribellig8098
 */
public class GabeBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private ArrayList<Paddle> blocks;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;

    public GabeBreakout() {
        //set up all variables related to the game
        blocks = new ArrayList<Paddle>();
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 60, Color.orange, 2);
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n < 4; n++) {
                blocks.add(new Paddle(760 - n * 15, 10 + i * 95, 10, 90, Color.GREEN, 0));
            }
        }
        keys = new boolean[2];
        leftScore = 0;

        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);		//starts the key thread to log key strokes
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
        graphToBack.drawString("Gabriel Ceribelli Per. 1 4/29/2019 PC: 19", 0, 560);

        graphToBack.setColor(Color.red);

        ball.moveAndDraw(graphToBack);
        leftPaddle.draw(graphToBack);
        for (Paddle i : blocks) {
            i.draw(graphToBack);
        }

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            if (ball.getxPos() <= 40) {
                ball.setXSpeed(0);
                ball.setYSpeed(0);
                leftScore = 0;
                reset();
            

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
            if(ball.getxPos() >= 720){
                ball.setXSpeed(-2);
                ball.setYSpeed(1);
            }
            
        }

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
        for (int i = 0; i < blocks.size(); i++) {
            if (ball.didCollideRight(blocks.get(i))
                    && (ball.didCollideTop(blocks.get(i)) || ball.didCollideBottom(blocks.get(i)))) {
                if (ball.getxPos() + ball.getWidth() >= blocks.get(i).getxPos() + Math.abs(ball.getXSpeed())) {
                    ball.setYSpeed(-ball.getYSpeed());
                } else {
                    ball.setXSpeed(-ball.getXSpeed());
                }
                clear(i, graphToBack);
                blocks.remove(i);
                leftScore++;
            }
        }
        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);

        graphToBack.drawString("leftScore = " + leftScore, 400, 560);
        //see if the paddles need to be moved
        if (keys[0] == true) {
            leftPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            leftPaddle.moveDownAndDraw(graphToBack);
        }

        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void update(Graphics window) {
        paint(window);
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

    public void clear(int i, Graphics window) {
        window.setColor(Color.WHITE);
        window.fillRect(blocks.get(i).getxPos(), blocks.get(i).getyPos(), blocks.get(i).getWidth(), blocks.get(i).getHeight());
    }

    public void reset() {
        int size = blocks.size();
        for (int i = 0; i < size; i++) {
            blocks.remove(0);
        }
        for (int i = 0; i < 5; i++) {
            for (int n = 0; n < 4; n++) {
                blocks.add(new Paddle(760 - n * 15, 10 + i * 95, 10, 90, Color.GREEN, 0));
            }
        }
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
