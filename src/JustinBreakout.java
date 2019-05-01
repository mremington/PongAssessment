

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
/**
 *
 * @author justinkaufman
 */
public class JustinBreakout extends Canvas implements Runnable, KeyListener{
    private Ball ball;
    private Paddle leftPaddle;
    
    private boolean[] keys;
    private BufferedImage back;
    private int score;
    private Block[][] block;
    private ArrayList<Block> gone;
    
    public JustinBreakout(){
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        leftPaddle = new Paddle(20, 200, 10, 40, Color.orange, 2);
        keys = new boolean[2];
        block = new Block[4][5];
        gone = new ArrayList<Block>();
        score = 0;
        int x =740;
        int y=0;
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                block[i][j] = new Block(x, y, 10, 100, Color.GREEN);
                x+=15;
            }
            x=740;
            y+=120;
        }
        /*int x1 =740;
        int y1=100;
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                block[i][j] = new Block(x1, y1, 10, 100, Color.GREEN);
                x1+=15;
            }
            
        }
        int x2 =740;
        int y2=100;
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                block[i][j] = new Block(x2, y2, 10, 100, Color.GREEN);
                x2+=15;
            }
            
        }
        int x3 =740;
        int y3=100;
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                block[i][j] = new Block(x3, y3, 10, 100, Color.GREEN);
                x3+=15;
            }
            
        }
        int x4 =740;
        int y4=100;
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                block[i][j] = new Block(x4, y4, 10, 100, Color.GREEN);
                x4+=15;
            }
            
        }*/
        
        
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
        //take a snap shop of the current Frame and same it as an image
        //that is the exact same width and height as the current Frame
        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        //create a graphics reference to the back ground image
        //we will draw all changes on the background image
        Graphics graphToBack = back.createGraphics();
        graphToBack.drawString("Justin Kaufman - Period 1 - April 29, 2019 - Computer 35", 0, 50);

        graphToBack.setColor(Color.red);

        ball.moveAndDraw(graphToBack);
        leftPaddle.draw(graphToBack);
       
        
        for (int i=0; i< block.length; i++){
            for (int j =0; j<block.length; j++){
                if (!gone.contains(block[i][j])){
                    block[i][j].draw(graphToBack);
                }
            }
        }
        
        for (int i =0; i<block.length; i++){
            for (int j=0; j<block.length; j++){
                if (ball.didCollideRight(block[i][j])){
                    if (!gone.contains(block[i][j])){
                        block[i][j].draw(graphToBack, Color.WHITE);
                        gone.add(block[i][j]);
                        score ++;
                    }
                }
            }
        }

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);


            /*if (ball.getxPos() >= 720) {
                score++;
            }*/

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

        graphToBack.drawString("Score = " + score, 400, 560);

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
