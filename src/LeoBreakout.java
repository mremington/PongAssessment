
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

public class LeoBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private List<Block> hitbox;

    public LeoBreakout() {
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 60, Color.orange, 2);
        keys = new boolean[4];
        leftScore = 0;
        hitbox = new ArrayList<Block>();
        int x = 0;
        int y = 0;
        for (int j = 0; j < 5; j++) {
            x = 0;
            for (int i = 0; i < 3; i++) {
                hitbox.add(new Block(700 + x, 0 + y, 5, 100, Color.GREEN));
                x += 10;
            }
            y += 110;
        }
        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);		
        //starts the key thread to log key strokes
        System.out.print("Leo Lu, Period 1, CA-SU-F106-03, 4/29/2019 9:00 AM");
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
        for (Block x : hitbox) {
            x.draw(graphToBack);
        }
        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

            if (ball.getxPos() >= 720) {
                hitbox.clear();
                int x = 0;
                int y = 0;
                for (int j = 0; j < 5; j++) {
                    x = 0;
                    for (int i = 0; i < 3; i++) {
                        hitbox.add(new Block(700 + x, 0 + y, 5, 100, Color.GREEN));
                        x += 10;
                    }
                    y += 110;
                }
                leftScore = 0;
            }
            if (ball.getxPos() <= 20) {
                hitbox.clear();
                int x = 0;
                int y = 0;
                for (int j = 0; j < 5; j++) {
                    x = 0;
                    for (int i = 0; i < 3; i++) {
                        hitbox.add(new Block(700 + x, 0 + y, 5, 100, Color.GREEN));
                        x += 10;
                    }
                    y += 110;
                }
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

        graphToBack.drawString("leftScore = " + leftScore, 400, 560);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 550)) {
            ball.setYSpeed(-ball.getYSpeed());
        }
        if (leftPaddle.getyPos()<=20 ){
            leftPaddle.draw(graphToBack, Color.WHITE);
            leftPaddle.setPos(leftPaddle.getxPos(), 21);
        }
        if (leftPaddle.getyPos()>=550){
            leftPaddle.draw(graphToBack, Color.WHITE);
            leftPaddle.setPos(leftPaddle.getxPos(), 549);
        }
        
        if (ball.didCollideLeft(leftPaddle)
                && (ball.didCollideTop(leftPaddle) || ball.didCollideBottom(leftPaddle))) {

            if (ball.getxPos() <= leftPaddle.getxPos() + leftPaddle.getWidth() - Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
        for (Block x : hitbox) {
            if (ball.didCollideRight(x) && (ball.didCollideTop(x) || ball.didCollideBottom(x))) {
                x.draw(graphToBack, Color.WHITE);
                hitbox.remove(x);

                ball.setXSpeed(-ball.getXSpeed());

                leftScore += 1;
                break;
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
}
