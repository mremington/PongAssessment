
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;

public class IssacBreakout extends Canvas implements KeyListener, Runnable {

    private Ball ball;
    private Paddle paddle;
    private List<Block> blocks;
    private List<Block> toRemove;
    private boolean[] keys;
    private BufferedImage back;
    private int score;

    public IssacBreakout() {
        //set up all variables related to the game
        ball = new Ball(500, 200, 10, 10, Color.blue, 2, 1);
        paddle = new Paddle(760, 200, 10, 40, Color.PINK, 2);
        blocks = new ArrayList<Block>();
        //initialize blocks
        for(int i = 0; i < 5; i++) { // 5 rows of blocks
        	int x = 10 + 20*i;
        	int wid = 10;
        	int len = 80;
        	for(int j = 0; j < 6; j++) { // 6 blocks each row
        		int y = 30 + 85*j;
        		blocks.add(new Block(x,y,wid,len,Color.GREEN));
        	}
        }
        toRemove = new ArrayList<Block>();
        keys = new boolean[2];
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

        ball.moveAndDraw(graphToBack);
        paddle.draw(graphToBack);
        
        //draw blocks
        for(Block b : blocks) {
        	b.draw(graphToBack);
        }

        //see if ball hits right wall
        if (!(ball.getxPos() <= 780)) {
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
            int whoot = (int) (Math.random() * 2);
            if (whoot == 0) {
                ball.setXSpeed(2);
                ball.setYSpeed(1);
            } else {
                ball.setXSpeed(-2);
                ball.setYSpeed(1);
            }
        }
        
        //see if ball hits left wall
        if(ball.getxPos() <= 10) {
        	ball.setXSpeed(-ball.getXSpeed());
        }

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        graphToBack.setColor(Color.red);

        graphToBack.drawString("score :: " + score, 400, 540);
        
        graphToBack.drawString("use the up and down arrows to move the paddle!", 270, 560);
        
        graphToBack.drawString("isaac hoffman | period 1 | april 29 2019 | computer #10", 260, 20);

        //see if ball hits top wall or bottom wall
        if (!(ball.getyPos() >= 20 && ball.getyPos() <= 540)) {
            ball.setYSpeed(-ball.getYSpeed());
        }
        
        toRemove.clear();
        //see if ball hit any blocks
        for(Block b : blocks) {
	        if (ball.didCollideLeft(b)
	                && (ball.didCollideTop(b) || ball.didCollideBottom(b))) {
	        	if (ball.getxPos() + ball.getWidth() <= b.getxPos() + Math.abs(ball.getXSpeed())) {
	                ball.setYSpeed(-ball.getYSpeed());
	            } else {
	                ball.setXSpeed(-ball.getXSpeed());
	            }
	        	toRemove.add(b);
	        	score++;
	        }
        }
        blocks.removeAll(toRemove);
        
        //draw over removed blocks
        for(Block b : toRemove) {
        	graphToBack.clearRect(b.getxPos(), b.getyPos(), b.getWidth(), b.getHeight());
        }
      
        
        //see if ball hit paddle
        if (ball.didCollideRight(paddle)
                && (ball.didCollideTop(paddle) || ball.didCollideBottom(paddle))) {
            if (ball.getxPos() + ball.getWidth() >= paddle.getxPos() + Math.abs(ball.getXSpeed())) {
                ball.setYSpeed(-ball.getYSpeed());
            } else {
                ball.setXSpeed(-ball.getXSpeed());
            }
        }

        //see if the paddles need to be moved
        if (keys[0] == true) {
            paddle.moveUpAndDraw(graphToBack);
        }
        if (keys[1] == true) {
            paddle.moveDownAndDraw(graphToBack);
        }
        
        //check if all blocks have been broken
        if(blocks.isEmpty()) {
        	
        	try {
                Thread.currentThread().sleep(950);
            } catch (Exception e) {
            }
        	
        	//reset game
        	graphToBack.clearRect(ball.getxPos(), ball.getyPos(), ball.getWidth(), ball.getHeight());
        	ball.setxPos(500);
        	ball.setyPos(200);
            
            for(int i = 0; i < 4; i++) {
            	int x = 10 + 20*i;
            	int wid = 10;
            	int len = 80;
            	for(int j = 0; j < 6; j++) {
            		int y = 30 + 85*j;
            		blocks.add(new Block(x,y,wid,len,Color.GREEN));
            	}
            }
            score = 0;
        }

        twoDGraph.drawImage(back, null, 0, 0);
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
        	keys[0] = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
        	keys[1] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
    	if(e.getKeyCode() == KeyEvent.VK_UP) {
        	keys[0] = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
        	keys[1] = false;
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
