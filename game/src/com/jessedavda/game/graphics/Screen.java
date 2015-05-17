package com.jessedavda.game.graphics;

import java.util.Random;

public class Screen {

	private int width, height;
	public int[] pixels;
	
	public int[] tiles = new int[64 * 64];
	
	//random number generator
	private Random random = new Random();
	
	//the screen constructor
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < 64 * 64; i++) {
			tiles[i] = random.nextInt(0xFFFFFF);
		}
	}
	
	// clear the screen method
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render() {
		//looping through all of the pixels in the window
		for(int y = 0; y < height; y++) {
			int yy = y;
			//if(y < 0 || y >= height) break;
			for(int x = 0; x < width; x++) {
				int xx = x + 20;
			//	if (x < 0 || x >= width) break;
				int tileIndex = ((xx >> 4) & 63) + ((yy >> 4) & 63) * 64;
				pixels[x + y * width] = tiles[tileIndex];			
			}
		}
	}
}
