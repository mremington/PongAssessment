
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
import javax.swing.JFrame;

/**
 *
 * @author chenm7302
 */
public class MichaelCBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private Block rightWall;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private List<Block> bl;

    public MichaelCBreakout() {
        //set up all variables related to the game
        System.out.print("Michael Chen P1 4/29/2019 C15");
        ball = new Ball(35, 200, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 400, Color.orange, 2);
        rightWall = new Block(800, 0, 10, 600, Color.GRAY);
        keys = new boolean[3];
        leftScore = 0;
        bl = new ArrayList<Block>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Block temp = new Block(600 + 50 * i, 120 * j, 45, 100, Color.ORANGE);
                bl.add(temp);
            }
        }
        setBackground(Color.WHITE);
        setVisible(true);
        new Thread(this).start();
        addKeyListener(this);		//starts the key thread to log key strokes
    }

    public boolean isBet(int x, int y, int z) {
        return x >= y && x <= z;
    }

    public boolean isCol(Ball ball, Block b) {
        int ay = ball.getyPos() + ball.getYSpeed();
        int ax = ball.getxPos() + ball.getXSpeed();
        if (isBet(ay, b.getyPos(), b.getyPos() + b.getHeight()) || isBet(ay + ball.getHeight(), b.getyPos(), b.getyPos() + b.getHeight())) {
            if (isBet(ax, b.getxPos(), b.getxPos() + b.getWidth()) && isBet(ax + ball.getWidth(), b.getxPos(), b.getxPos() + b.getWidth())) {
                ball.setYSpeed(-ball.getYSpeed());
                return true;
            }
            if (isBet(ax, b.getxPos(), b.getxPos() + b.getWidth()) || isBet(ax + ball.getWidth(), b.getxPos(), b.getxPos() + b.getWidth())) {

                ball.setXSpeed(-ball.getXSpeed());
                return true;
            }
        }
        return false;
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

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 800)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

            try {
                Thread.currentThread().sleep(950);
            } catch (Exception e) {
            }
            leftScore = 0;
            ball.draw(graphToBack, Color.WHITE);
            ball.setxPos(35);
            ball.setyPos(200);
        }
        if (keys[2]) {
            ball.setXSpeed(2);
            ball.setYSpeed(1);
        }
        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);

        graphToBack.drawString("Score = " + leftScore, 400, 560);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 600)) {
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

        if (ball.didCollideRight(rightWall)
                && (ball.didCollideTop(rightWall) || ball.didCollideBottom(rightWall))) {
            if (ball.getxPos() + ball.getWidth() >= rightWall.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }

        for (int i = 0; i < bl.size(); i++) {
            Block b = bl.get(i);
            if (isCol(ball, b)) {

                bl.get(i).draw(graphToBack, Color.WHITE);
                bl.remove(i);
                i--;
                leftScore++;
            } else {
                bl.get(i).draw(graphToBack);
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
            case ' ':
                keys[2] = true;
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
            case ' ':
                keys[2] = false;
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
