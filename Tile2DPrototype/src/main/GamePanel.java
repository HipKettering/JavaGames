package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;



public class GamePanel extends JPanel  implements Runnable{
	
	//SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16
	final int scale = 3; 
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile 
	final int maxScreenCol = 16 * 2;
	final int maxScreenRow = 12 * 2;  //makes ratio 4:3
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//FPS
	int FPS = 1000;
	
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; // thread can be start/stopped. needs implements Runnable added to class to use thread
	Player player = new Player(this, keyH);


	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.red);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}


	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta > 1) {
				// This Loop will update info such as character position
				update();
				// as well as draw the screen with the updated info
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	
	public void update() {		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		
		g2.dispose();
	}
}
