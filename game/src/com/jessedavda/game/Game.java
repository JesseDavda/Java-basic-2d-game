package com.jessedavda.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.jessedavda.game.graphics.Screen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// setting the width, height and scale
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "game";

	private Thread thread;
	private JFrame frame;

	private boolean running = false;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		frame = new JFrame();
		screen = new Screen(width, height);
	}

	// starting the new thread
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	// stop the game by killing the threads
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// the running method
	public void run() {
		//setting all the timer variables
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		//update and frame variables
		int frames = 0;
		int updates = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
				while(delta >= 1) {
					update();
					updates++;
					delta --;
				}
			render();
			frames++;
			
			//fps and update counter
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | fps: " + frames + " , ups: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}

	public void update() {

	}

	// rendering graphical objects onto the screen
	public void render() {
		// creating the buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render();
		
		//setting the game pixel array equal to the screen pixel array
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	// main method
	public static void main(String[] args) {
		Game game = new Game();

		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
