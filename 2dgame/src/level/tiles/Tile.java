package level.tiles;

import gfx.Colours;
import gfx.Screen;
import level.Level;

public abstract class Tile {
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1, 333, -1, -1), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00);
	public static final Tile WATER = new AnimatedTile(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colours.get(-1, 004, 115, -1), 0xFF0000FF, 1000);
	public static final Tile SNOW = new BasicTile(4, 3, 0, Colours.get(555, -1, -1, -1), 0xFFFFFFFF);
	public static final Tile BRICK = new BasicSolidTile(5, 4, 0, Colours.get(-1, 333, 400, -1), 0xFFFF0000);
	public static final Tile FIRE = new AnimatedTile(6, new int[][] {{0, 4}, {1, 4}, {2, 4}, {1, 4}}, Colours.get(000, 400, 550, -1), 0xFF00FF18, 1000);
	public static final Tile SPEED = new BasicTile(7, 5, 0, Colours.get(051, -1, 035, -1), 0xFF2BCBC5);
	public static final Tile BUSH_MIDDLE = new BasicTile(8, 6, 0, Colours.get(000, 141, 141, -1), 0xFFF20000);
	public static final Tile BUSH_BOTTOM = new BasicTile(9, 7, 0, Colours.get(000, 141, 131, -1), 0xFFF2F200);
	public static final Tile BUSH_LEFT = new BasicTile(10, 8, 0, Colours.get(000, 141, 131, -1), 0xFFF3F300);
	public static final Tile BUSH_RIGHT = new BasicTile(11, 9, 0, Colours.get(000, 141, 131, -1), 0xFFF30000);
	public static final Tile BUSH_TOP = new BasicTile(12, 10, 0, Colours.get(000, 141, 131, 131), 0xFFF5F5F5);
	public static final Tile BUSH_CORNER_BR = new BasicTile(13, 11, 0, Colours.get(000, 141, 131, -1), 0xFFF3F3F3);
	public static final Tile BUSH_CORNER_BL = new BasicTile(14, 12, 0, Colours.get(000, 141, 131, -1), 0xFFF2F2F2);
	public static final Tile BUSH_CORNER_TR = new BasicTile(15, 13, 0, Colours.get(000, 141, 131, -1), 0xFF625F5F);
	public static final Tile BUSH_CORNER_TL = new BasicTile(16, 14, 0, Colours.get(000, 141, 131, -1), 0xFF5B0E0E);
	public static final Tile BUSH_WINNER = new BasicTile(17, 15, 0, Colours.get(000, 141, -1, -1), 0xFF001500);
	public static final Tile HEALTH = new BasicTile(18, 16, 0, Colours.get(000, 400, -1, -1), 0xFF0022F2);
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	protected int levelColour;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int colour) {
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplicate tile id on" + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColour = colour;
		tiles[id] = this;
	}

	public byte getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	public int getLevelColour() {
		return levelColour;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);
}
