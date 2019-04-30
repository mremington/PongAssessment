
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

public class TylerBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private int rightScore;
    private ArrayList<Block> blocks = new ArrayList<>();

    public TylerBreakout() {
        System.out.println("Tyler Bovenzi, Period 1, 4/29/19, Computer #21");
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 1, 2);
        leftPaddle = new Paddle(20, 200, 10, 40, Color.orange, 2);
        keys = new boolean[4];
        leftScore = 0;
        rightScore = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 5; i++) {
                blocks.add(new Block(700 + j * 15, i * 120, 7, 110, Color.green));
            }
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
        for (Block i : blocks) {
            i.draw(graphToBack);
        }

        ball.moveAndDraw(graphToBack);
        leftPaddle.draw(graphToBack);

        //see if ball hits left wall or right wall
        if (ball.getxPos() >= 780) {
            ball.setXSpeed(-ball.getXSpeed());
        }
        if (ball.getxPos() <= 10) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

            leftScore = 0;

            try {
                Thread.currentThread().sleep(250);
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

        for (int i = 0; i < blocks.size(); i++) {
            Block temp = blocks.get(i);
            if (ball.getxPos() + ball.getWidth() > temp.getxPos() && ball.getxPos() < temp.getxPos() + temp.getWidth() && ball.getyPos() + ball.getHeight() > temp.getyPos() && ball.getyPos() < temp.getyPos() + temp.getHeight()) {
                temp.draw(graphToBack, Color.white);
                blocks.remove(i);
                leftScore++;

                ball.setXSpeed(-ball.getXSpeed());

            }
        }

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() + ball.getHeight() <= 600)) {
            ball.setYSpeed(-ball.getYSpeed());
        }

        if (ball.didCollideLeft(leftPaddle) && (ball.didCollideTop(leftPaddle) || ball.didCollideBottom(leftPaddle))) {

            if (ball.getxPos() <= leftPaddle.getxPos() + leftPaddle.getWidth() - Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
//        for (int i = 0; i < blocks.size(); i++) {
//            Block temp = blocks.get(i);
//            if (ball.didCollideRight(temp)
//                    && (ball.didCollideTop(temp) || ball.didCollideBottom(temp))) {
//                temp.draw(graphToBack, Color.white);
//                blocks.remove(i);
//                leftScore++;
//                if (ball.getxPos() + ball.getWidth() >= temp.getxPos() + Math.abs(ball.getXSpeed())) {
//                    ball.setYSpeed(-ball.getYSpeed());
//                } else {
//                    ball.setXSpeed(-ball.getXSpeed());
//                }
//            }
//        }
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
