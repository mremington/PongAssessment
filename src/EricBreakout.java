


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

public class EricBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int score;
    private Block block11;
    private Block block12;
    private Block block13;
    private Block block14;
    private Block block21;
    private Block block22;
    private Block block23;
    private Block block24;
    private Block block31;
    private Block block32;
    private Block block33;
    private Block block34;
    private Block block41;
    private Block block42;
    private Block block43;
    private Block block44;
    private Block block51;
    private Block block52;
    private Block block53;
    private Block block54;

    public EricBreakout() {
        System.out.println("Erik Beer || Period 1 || 4/29/19 || CA-SU-F106-40");
        //set up all variables related to the game
        ball = new Ball(160, 100, 10, 10, Color.blue, 2, 1);
        rightPaddle = new Paddle(760, 200, 10, 75, Color.orange, 2);
        block11 = new Block(20, 10, 10, 80, Color.green);
        block12 = new Block(40, 10, 10, 80, Color.green);
        block13 = new Block(60, 10, 10, 80, Color.green);
        block14 = new Block(80, 10, 10, 80, Color.green);
        block21 = new Block(20, 100, 10, 80, Color.green);
        block22 = new Block(40, 100, 10, 80, Color.green);
        block23 = new Block(60, 100, 10, 80, Color.green);
        block24 = new Block(80, 100, 10, 80, Color.green);
        block31 = new Block(20, 190, 10, 80, Color.green);
        block32 = new Block(40, 190, 10, 80, Color.green);
        block33 = new Block(60, 190, 10, 80, Color.green);
        block34 = new Block(80, 190, 10, 80, Color.green);
        block41 = new Block(20, 280, 10, 80, Color.green);
        block42 = new Block(40, 280, 10, 80, Color.green);
        block43 = new Block(60, 280, 10, 80, Color.green);
        block44 = new Block(80, 280, 10, 80, Color.green);
        block51 = new Block(20, 370, 10, 80, Color.green);
        block52 = new Block(40, 370, 10, 80, Color.green);
        block53 = new Block(60, 370, 10, 80, Color.green);
        block54 = new Block(80, 370, 10, 80, Color.green);
        keys = new boolean[4];
        score = 0;

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

        rightPaddle.draw(graphToBack);
        block11.draw(graphToBack);
        block12.draw(graphToBack);
        block13.draw(graphToBack);
        block14.draw(graphToBack);
        block21.draw(graphToBack);
        block22.draw(graphToBack);
        block23.draw(graphToBack);
        block24.draw(graphToBack);
        block31.draw(graphToBack);
        block32.draw(graphToBack);
        block33.draw(graphToBack);
        block34.draw(graphToBack);
        block41.draw(graphToBack);
        block42.draw(graphToBack);
        block43.draw(graphToBack);
        block44.draw(graphToBack);
        block51.draw(graphToBack);
        block52.draw(graphToBack);
        block53.draw(graphToBack);
        block54.draw(graphToBack);
        ball.moveAndDraw(graphToBack);

        

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

            if (ball.getxPos() <= 40) {
                //score++;
            }
            if (ball.getxPos() >= 720) {
                score = 0;
                
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

        graphToBack.drawString("Score = " + score, 400, 540);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 450)) {
            ball.setYSpeed(-ball.getYSpeed());
        }
        //see if ball hits the left wall
        if (!(ball.getxPos() >= 10)){
            ball.setXSpeed(-ball.getXSpeed());
        } 

        if (ball.didCollideRight(rightPaddle)
                && (ball.didCollideTop(rightPaddle) || ball.didCollideBottom(rightPaddle))) {
            if (ball.getxPos() + ball.getWidth() >= rightPaddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }
        if (ball.didCollideLeft(block11)
                && (ball.didCollideTop(block11) || ball.didCollideBottom(block11))){
            if (!(block11.getColor().equals(Color.white))){
                block11.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block12)
                && (ball.didCollideTop(block12) || ball.didCollideBottom(block12))){
            if (!(block12.getColor().equals(Color.white))){
                block12.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block13)
                && (ball.didCollideTop(block13) || ball.didCollideBottom(block13))){
            if (!(block13.getColor().equals(Color.white))){
                block13.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block14)
                && (ball.didCollideTop(block14) || ball.didCollideBottom(block14))){
            if (!(block14.getColor().equals(Color.white))){
                block14.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block21)
                && (ball.didCollideTop(block21) || ball.didCollideBottom(block21))){
            if (!(block21.getColor().equals(Color.white))){
                block21.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block22)
                && (ball.didCollideTop(block22) || ball.didCollideBottom(block22))){
            if (!(block22.getColor().equals(Color.white))){
                block22.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block23)
                && (ball.didCollideTop(block23) || ball.didCollideBottom(block23))){
            if (!(block23.getColor().equals(Color.white))){
                block23.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block24)
                && (ball.didCollideTop(block24) || ball.didCollideBottom(block24))){
            if (!(block24.getColor().equals(Color.white))){
                block24.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block31)
                && (ball.didCollideTop(block31) || ball.didCollideBottom(block31))){
            if (!(block31.getColor().equals(Color.white))){
                block31.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block32)
                && (ball.didCollideTop(block32) || ball.didCollideBottom(block32))){
            if (!(block32.getColor().equals(Color.white))){
                block32.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block33)
                && (ball.didCollideTop(block33) || ball.didCollideBottom(block33))){
            if (!(block33.getColor().equals(Color.white))){
                block33.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block34)
                && (ball.didCollideTop(block34) || ball.didCollideBottom(block34))){
            if (!(block34.getColor().equals(Color.white))){
                block34.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block41)
                && (ball.didCollideTop(block41) || ball.didCollideBottom(block41))){
            if (!(block41.getColor().equals(Color.white))){
                block41.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block42)
                && (ball.didCollideTop(block42) || ball.didCollideBottom(block42))){
            if (!(block42.getColor().equals(Color.white))){
                block42.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block43)
                && (ball.didCollideTop(block43) || ball.didCollideBottom(block43))){
            if (!(block43.getColor().equals(Color.white))){
                block43.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block44)
                && (ball.didCollideTop(block44) || ball.didCollideBottom(block44))){
            if (!(block44.getColor().equals(Color.white))){
                block44.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block51)
                && (ball.didCollideTop(block51) || ball.didCollideBottom(block51))){
            if (!(block51.getColor().equals(Color.white))){
                block51.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block52)
                && (ball.didCollideTop(block52) || ball.didCollideBottom(block52))){
            if (!(block52.getColor().equals(Color.white))){
                block52.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block53)
                && (ball.didCollideTop(block53) || ball.didCollideBottom(block53))){
            if (!(block53.getColor().equals(Color.white))){
                block53.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }
        if (ball.didCollideLeft(block54)
                && (ball.didCollideTop(block54) || ball.didCollideBottom(block54))){
            if (!(block54.getColor().equals(Color.white))){
                block54.setColor(Color.white);
                score++;
                ball.setXSpeed(-ball.getXSpeed());
            }  
        }


        //see if the paddles need to be moved
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
