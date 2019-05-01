

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author finkelmanj7070
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


public class JohnBreakout extends Pong implements KeyListener, Runnable {

    private Ball ball;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private boolean[] keys;
    private BufferedImage back;
    private int leftScore;
    private int rightScore;
    private Block[][] bricks;
    private int score;

    
    
    public JohnBreakout() {
        //set up all variables related to the game
        super();
       bricks = new Block[4][4];
       int xpos = 300;
       int ypos = 100;
       
       for (Block[] row: bricks){
          for (Block ind: row) {
               ind.setHeight(8);
               ind.setWidth(10);
               ind.setPos(xpos, ypos);
               xpos+= 20;
           
           }
          ypos+= 30;
       }
       
        
       score = 0;
       
       System.out.println("John Finkelman, Period 1, April 29, Computer#25");

        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);		//starts the key thread to log key strokes
    }
    
    @Override
    public void update(Graphics window) {
        paint(window);
    }
    
    @Override
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
        
         for (Block[] row: bricks){
          for (Block ind: row) {
        graphToBack.fillRect(ind.getxPos(), ind.getyPos(), ind.getWidth(), ind.getHeight());
          }
       }
    
        

        //see if ball hits left wall or right wall
        if (!(ball.getxPos() >= 10 && ball.getxPos() <= 780)) {
            ball.setXSpeed(0);
            ball.setYSpeed(0);

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

        graphToBack.drawString("rightScore = " + rightScore, 400, 540);
        graphToBack.drawString("leftScore = " + leftScore, 400, 560);

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
        
         while (score < 16) {
            for (Block[] row: bricks){
          for (Block ind: row) {
              if(ball.didCollideRight(ind)) {
                  score++;
                  ind.setWidth(0);
              }
                  
          }
        }
        }

        twoDGraph.drawImage(back, null, 0, 0);
    }
    
    @Override
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
    
        
    @Override
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
    
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    
    @Override
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

    
   
    



/*package Assessment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author finkelmanj7070
 */

/*
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

public class Breakout extends Ball implements Collidable { 
     private Block[][] bricks;
     private int score;
     
    public Breakout(){
       bricks = new Block[4][4];
       int xpos = 300;
       int ypos = 100;
       
       for (Block[] row: bricks){
          for (Block ind: row) {
               ind.setHeight(10);
               ind.setWidth(10);
               ind.setPos(xpos, ypos);
               xpos+= 20;
           
           }
          ypos+= 30;
       }
       
        
       score = 0;
       
       System.out.println("John Finkelman, Period 1, April 29, Computer#25");
       
}
    
    public void update(Graphics window) {
        paint(window);
    }
    
    public void paint(Graphics window) {
        
        
    }
    
    public void draw(Graphics window) {
        
       for (Block[] row: bricks){
          for (Block ind: row) {
        window.fillRect(ind.getxPos(), ind.getyPos(), ind.getWidth(), ind.getHeight());
          }
       }
    }
    
      public boolean didCollideLeft(Object obj) {
        Block other = (Block) obj;
        return super.getxPos() <= other.getxPos() + other.getWidth() + Math.abs(getXSpeed());
    }

    public boolean didCollideRight(Object obj) {
        Block other = (Block) obj;
        return getxPos() + getWidth() >= other.getxPos() - Math.abs(getXSpeed());
    }

    public boolean didCollideTop(Object obj) {
        Block other = (Block) obj;
        return getyPos() >= other.getyPos() && getyPos() <= other.getyPos() + getHeight();
    }

    public boolean didCollideBottom(Object obj) {
        Block other = (Block) obj;
        return getyPos() + getHeight() >= other.getyPos() && getyPos() + getHeight() <= other.getyPos() + other.getHeight();
    }

    
   
    
}
*/

