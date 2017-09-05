package smartrobots.util;

import smartrobots.model.grid.Grid.Tile;

public final class SampleGrid {

	
	public static final Tile[][] SMART_ROBOTS;
	
	static {
		int[][] map = new int[][] {
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 1, 0, 0, 1, 4, 1, 0, 0, 1, 5, 1, 0, 0, 1, 0 },
			{ 2, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 3 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }
		};
		
		System.out.println(String.format("%s %s", map[0].length, map.length));
		
		Tile[][] tmp = new Tile[map[0].length][map.length];
		for (int x = 0; x < map[0].length; x++) {
			for (int y = 0; y < map.length; y++) {
				tmp[x][y] = Tile.values()[map[y][x]];
			}
		}
		SMART_ROBOTS = tmp;
	}
}
