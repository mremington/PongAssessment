/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author liuk6719
 */

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

public class KellyBreakout extends Canvas implements KeyListener, Runnable
{
    
    private Ball ball;
    private List<Paddle> bricks;;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int rightScore;

    public KellyBreakout() 
    {
        //set up all variables related to the game
        ball = new Ball(10, 100, 10, 10, Color.blue, 2, 1);
        bricks = new ArrayList<Paddle>();
        //int x, int y, int w, int h
        
        //first four
        Paddle one = new Paddle(10, 10, 10 ,100, Color.orange, 2);
        bricks.add(one);
        Paddle two = new Paddle(25, 10, 10 ,100, Color.orange, 2);
        bricks.add(two);
        Paddle three = new Paddle(40, 10, 10 ,100, Color.orange, 2);
        bricks.add(three);
        Paddle four = new Paddle(55, 10, 10 ,100, Color.orange, 2);
        bricks.add(four);
        
        //second four
        Paddle five = new Paddle(10, 115, 10 ,100, Color.orange, 2);
        bricks.add(five);
        Paddle six = new Paddle(25, 115, 10 ,100, Color.orange, 2);
        bricks.add(six);
        Paddle seven = new Paddle(40, 115, 10 ,100, Color.orange, 2);
        bricks.add(seven);
        Paddle eight = new Paddle(55, 115, 10 ,100, Color.orange, 2);
        bricks.add(eight);
        
        //third four
        Paddle nine = new Paddle(10, 220, 10 ,100, Color.orange, 2);
        bricks.add(nine);
        Paddle ten = new Paddle(25, 220, 10 ,100, Color.orange, 2);
        bricks.add(ten);
        Paddle eleven = new Paddle(40, 220, 10 ,100, Color.orange, 2);
        bricks.add(eleven);
        Paddle twelve = new Paddle(55, 220, 10 ,100, Color.orange, 2);
        bricks.add(twelve);
        
        //fourth four
        Paddle thirteen = new Paddle(10, 325, 10 ,100, Color.orange, 2);
        bricks.add(thirteen);
        Paddle fourteen = new Paddle(25, 325, 10 ,100, Color.orange, 2);
        bricks.add(fourteen);
        Paddle fifteen = new Paddle(40, 325, 10 ,100, Color.orange, 2);
        bricks.add(fifteen);
        Paddle sixteen = new Paddle(55, 325, 10 ,100, Color.orange, 2);
        bricks.add(sixteen);
        
        Paddle seventeen = new Paddle(10, 430, 10 ,100, Color.orange, 2);
        bricks.add(seventeen);
        Paddle eighteen = new Paddle(25, 430, 10 ,100, Color.orange, 2);
        bricks.add(eighteen);
        Paddle nineteen = new Paddle(40, 430, 10 ,100, Color.orange, 2);
        bricks.add(nineteen);
        Paddle twenty = new Paddle(55, 430, 10 ,100, Color.orange, 2);
        bricks.add(twenty);
        
        // :)
        

        rightPaddle = new Paddle(760, 200, 10, 200, Color.orange, 2);
        keys = new boolean[4];
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
        graphToBack.drawString("Kelly Liu, Period 1, 4/29/19, Comp :20 " , 10, 10);
        ball.moveAndDraw(graphToBack);
        rightPaddle.draw(graphToBack);
        for(int i = 0; i < 20; i ++)
        {
            Paddle blank = bricks.get(i);
             blank.draw(graphToBack);
        }

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);
            int counter = 20;
            for( int i = 0; i < counter; i ++)
            {
                Paddle blank = bricks.get(i);
               //if statement checks if it hits blocks and its running..?
                if (ball.getyPos()+ball.getHeight()>=blank.getyPos() && ball.getyPos() < blank.getyPos()+blank.getHeight() &&  ball.getxPos()+ ball.getWidth()<=blank.getxPos()+blank.getWidth())
                {
                    bricks.remove(blank); // wrong ... ? 
                    System.out.println("das"); // tester
                    rightScore++; 
                    counter = counter - 1;
                    repaint();
                    ball.setXSpeed(-ball.getXSpeed());
                }
            }
            
           
            //if (ball.getxPos() <= 40) {
             //   rightScore++;
            //}
            if (ball.getxPos() >= 720) {
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

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 450)) {
            ball.setYSpeed(-ball.getYSpeed());
        }

       // if (ball.didCollideLeft(leftPaddle)
               // && (ball.didCollideTop(leftPaddle) || ball.didCollideBottom(leftPaddle))) {

            //if (ball.getxPos() <= leftPaddle.getxPos() + leftPaddle.getWidth() - Math.abs(ball.getXSpeed())) {
               // ball.setYSpeed(-ball.getYSpeed());
            //} else {
                //ball.setXSpeed(-ball.getXSpeed());
            //}
        //}

        if (ball.didCollideRight(rightPaddle)
                && (ball.didCollideTop(rightPaddle) || ball.didCollideBottom(rightPaddle))) {
            if (ball.getxPos() + ball.getWidth() >= rightPaddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }

        //see if the paddles need to be moved
       // if (keys[0] == true) {
          //  leftPaddle.moveUpAndDraw(graphToBack);
        //}
        //if (keys[1] == true) {
            //leftPaddle.moveDownAndDraw(graphToBack);
       // }
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

    

