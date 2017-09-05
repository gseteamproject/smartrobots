package smartrobots.util.astar;

public class Heuristic {

	public static int getManhattenDistance(int xStart, int yStart, int xGoal, int yGoal) {
		// |6-9|+|8-9| = 4
		// |6-9|+|9-9| = 3
		return Math.abs(xStart - xGoal) + Math.abs(yStart - yGoal);
	}
}
