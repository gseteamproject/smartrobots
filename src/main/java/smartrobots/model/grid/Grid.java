package smartrobots.model.grid;

import java.util.List;

public class Grid {

	private final String name;
	private final Tile[][] tiles;
	private final List<Object> gridObjects;
	
	public Grid(String name, Tile[][] tiles, List<Object> gridObjects) {
		this.name = name;
		this.tiles = tiles;
		this.gridObjects = gridObjects;
	}
	
	public String getName() {
		return name;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public List<Object> getGridObjects() {
		return gridObjects;
	}
	
	public int getWidth() {
		return tiles[0].length;
	}
	
	public int getHeight() {
		return tiles.length;
	}
	
	public enum Tile {
		NO_TRACK,
		TRACK,
		SOURCE_PALLET,
		GOAL_PALLET,
		CLEANING_STATION,
		PAINTING_STATION
	}
}
