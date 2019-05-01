
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class JohnJBreakout extends Pong {

    private Ball ball;
    private Paddle rightPaddle;
    private ArrayList<Paddle> blocks;
    private boolean[] keys;
    private BufferedImage back;
    private int score;

    public JohnJBreakout() {
        System.out.println("John Gharib, Period 1, April 29, 2019, Computer # 22");
        //set up all variables related to the game
        ball = new Ball(600, 300, 10, 10, Color.blue, 1, 1);
        rightPaddle = new Paddle(760, 200, 10, 40, Color.orange, 2);
        keys = new boolean[4];
        score = 0;
        blocks = new ArrayList<Paddle>(24);
        int space1 = 0;
        int space2 = 0;
        for (int i = 0; i < 24; i++) {
            if (i % 6 == 0) {
                space1 += 50;
                space2 = 30;
            }
            blocks.add(new Paddle(space1, space2, 20, 50, Color.GREEN, 0));
            space2 += 80;
        }
        setBackground(Color.WHITE);
        setVisible(true);
        new Thread(this).start();
        addKeyListener(this);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Graphics2D twoDGraph = (Graphics2D) window;
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }
        Graphics graphToBack = back.createGraphics();
        graphToBack.setColor(Color.red);
        ball.moveAndDraw(graphToBack);
        rightPaddle.draw(graphToBack);
        hitBlock(graphToBack);

        //see if ball hits left wall or right wall
        if ((ball.getxPos() < 0)) {
            ball.setXSpeed(-ball.getXSpeed());
        }
        if ((ball.getxPos() > 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);
            score = 0;
            try {
                Thread.currentThread().sleep(950);
            } catch (Exception e) {
            }
            ball.draw(graphToBack, Color.WHITE);
            ball.setxPos((int) (Math.random() * 50) + 400);
            ball.setyPos((int) (Math.random() * 50) + 300);
            double pN = Math.random();
            if (pN > 0.75) {
                ball.setXSpeed(-1);
                ball.setYSpeed(-1);
            } else if (pN > 0.5) {
                ball.setXSpeed(1);
                ball.setYSpeed(-1);
            } else if (pN > 0.25) {
                ball.setXSpeed(-1);
                ball.setYSpeed(1);
            } else {
                ball.setXSpeed(1);
                ball.setYSpeed(1);
            }
        }

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);
        graphToBack.drawString("Score = " + score, 400, 540);

        if (!(ball.getyPos() >= 0 && ball.getyPos() <= 450)) {
            ball.setYSpeed(-ball.getYSpeed());
        }

        if (ball.didCollideRight(rightPaddle) && (ball.didCollideTop(rightPaddle) || ball.didCollideBottom(rightPaddle))) {
            if (ball.getxPos() + ball.getWidth() >= rightPaddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }

        if (keys[0] == true) {
            rightPaddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
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

    public void hitBlock(Graphics window) {
        for (int i = 0; i < blocks.size(); i++) {
            Paddle p = blocks.get(i);
            int xS = ball.getXSpeed();
            int yS = ball.getYSpeed();
            if (ball.didCollideRight(p) || ball.didCollideLeft(p) || ball.didCollideTop(p) || ball.didCollideBottom(p)) {
                ball.setXSpeed(0);
                ball.setYSpeed(0);
                if (ball.didCollideRight(p) || ball.didCollideLeft(p)) {
                    ball.setXSpeed(-xS);
                    ball.setYSpeed(yS);
                } else if (ball.didCollideTop(p) || ball.didCollideBottom(p)) {
                    ball.setXSpeed(xS);
                    ball.setYSpeed(-yS);
                }
                score++;
                blocks.remove(i);
            }
            p.draw(window);
        }
    }
}
