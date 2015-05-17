package entities;

import java.util.Date;

import gfx.Colours;
import gfx.FontForGame;
import gfx.Screen;
import jesse.game.InputHandler;
import level.Level;

public class Player extends Mob {

	private InputHandler input;
	private int colour = Colours.get(-1, 111, 145, 543);
	private int scale = 1;
	protected boolean isSwimming = false;
	protected boolean isSnow = false;
	protected boolean isSpeed = false;
	protected boolean isBush = false;
	protected boolean isFire = false;
	private int tickCount;
	private String username;
	int healthInt = 100;
	String health = Integer.toString(healthInt);
	protected boolean isHurt = false;
	protected boolean dead = false;
	private int air = 10;
	protected boolean isHealth = false;
	long startTime = System.currentTimeMillis();
	long elapsedTime = 0L;

	
	public Player(Level level, String name, int x, int y, InputHandler input, String username) {
		super(level, "player", x, y, 1);
		this.input = input;
		this.username = username;
	}

	public void tick() {
		int xa = 0;
		int ya = 0;
		
		 if(isSpeed) {
			 if (input.up.isPressed()) ya = ya - 2;
			 if (input.down.isPressed()) ya = ya + 2;
			 if (input.right.isPressed()) xa = xa + 2;
			 if (input.left.isPressed()) xa = xa - 2;
		 } else if(dead){
			 if (input.up.isPressed());
			 if (input.down.isPressed());
			 if (input.right.isPressed());
			 if (input.left.isPressed());
		 } else {
			 if (input.up.isPressed()) ya--;
			 if (input.down.isPressed())ya++;
			 if (input.right.isPressed())xa++;
			 if (input.left.isPressed())xa--;
		 }
		 
		 if(xa != 0 || ya != 0) {
			 move(xa, ya);
			 isMoving = true;
		 }else {
			 isMoving = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == (8) || level.getTile(this.x >> 3, this.y >> 3).getId() == (9) || level.getTile(this.x >> 3, this.y >> 3).getId() == (10) || level.getTile(this.x >> 3, this.y >> 3).getId() == (11) || level.getTile(this.x >> 3, this.y >> 3).getId() == (12) || level.getTile(this.x >> 3, this.y >> 3).getId() == (13) || level.getTile(this.x >> 3, this.y >> 3).getId() == (14) || level.getTile(this.x >> 3, this.y >> 3).getId() == (15) || level.getTile(this.x >> 3, this.y >> 3).getId() == (16) || level.getTile(this.x >> 3, this.y >> 3).getId() == (17)) {
			 isBush = true;
		 } else {
			 isBush = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 18) {
			 isHealth = true;
		 } else {
			 isHealth = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 6) {
			 isFire = true;
			 isHurt = true;
		 } else {
			 isFire = false;
			 isHurt = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 7) {
			 isSpeed = true;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 7) {
			 isSpeed = true;
		 }
		 if(isSpeed && level.getTile(this.x >> 3, this.y >> 3).getId() != 7) {
			 isSpeed = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 4) {
			 isSnow = true;
		 }
		 if(isSnow && level.getTile(this.x >> 3, this.y >> 3).getId() != 4) {
			 isSnow = false;
		 }
		 if(level.getTile(this.x >> 3, this.y >> 3).getId() == 3) {
			 isSwimming = true;
		 }
		 if(isSwimming && level.getTile(this.x >> 3, this.y >> 3).getId() != 3) {
			 isSwimming = false;
		 }
		 tickCount++;
	}
	
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 28;
		int walkingSpeed = 0;
		
		int modifier = 8 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;
		int xNamePositioning = xOffset;
		
		if(isSnow) {
			walkingSpeed = 6;
		} else if(isSpeed) {
			walkingSpeed = 2;
		} else {
			walkingSpeed = 4;
		}
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if(movingDir == 1) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}

		if(isSwimming){
			int waterColour = 0;
			yOffset += 4;
			if(tickCount % 60 < 15) {
				waterColour = Colours.get(-1, -1, 225, -1);
			} else if(15 <= tickCount % 60 && tickCount % 60 < 30) {
				waterColour = Colours.get(-1, 225, 115, -1);
			} else if(30 <= tickCount % 60 && tickCount % 60 < 45) {
				waterColour = Colours.get(-1, 115, -1, 225);
			} else {
				waterColour = Colours.get(-1, 225, 115, -1);
				yOffset -= 1;
			}
			screen.render(xOffset, yOffset + 3, 0 + 27 * 32, waterColour, 0x00, 1);
			screen.render(xOffset + 8, yOffset + 3, 0 + 27 * 32, waterColour, 0x01, 1);
		}
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop, scale);
		
		
		if(!isSwimming && !isBush) {
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom, scale);
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom, scale);
		} 
		
		if(username.length() % 2 == 1){ xNamePositioning = xOffset + 5;}
		
		
		if((username != null) && (isSnow)){
			FontForGame.render(username, screen, xNamePositioning - ((username.length() -1) / 2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 000), 1);
		} else if (isFire){
			FontForGame.render(username, screen, xNamePositioning - ((username.length() -1) / 2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 000), 1);
		} else {
			FontForGame.render(username, screen, xNamePositioning - ((username.length() -1) / 2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 555), 1);
		}
		
		if(isHealth) {
			healthInt = healthInt + 1;
		}
		
		if(healthInt <= 0) {
			healthInt = 0;
		}
		if(healthInt >= 100) {
			healthInt = 100;
		}
		
		if(isHurt) {
			healthInt = healthInt - 1;
		}

		health = Integer.toString(healthInt);
		
		System.out.println(health);
		
		FontForGame.render(health, screen,  xNamePositioning - ((health.length() -1) / 2 * 8), yOffset - 20, Colours.get(-1, -1, -1, 000), 1);
	
		if(healthInt <= 0) {
			dead = true;
			FontForGame.render("You have died", screen, xOffset - 40, yOffset, Colours.get(-1,  -1, -1, 000), 1);
			System.exit(0);
		} else if(dead) {
			FontForGame.render("You have died", screen, xOffset - 40, yOffset, Colours.get(-1,  -1, -1, 000), 1);
			System.exit(0);
		}
		
		if(isSwimming) {
			for (int i = 0; i < air;) {
				air--;
			}
			FontForGame.render("Air:" + air, screen, xNamePositioning - ((health.length() -1) / 2 * 8), yOffset - 30, Colours.get(-1, -1, -1, 000), 1);
		}
	}	
	
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		for(int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for(int x = xMin; x < xMax; x++) {
			if(isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for(int y = yMin; y < yMax; y++) {
			if(isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername() {
		return username;
	}

}
