package smartrobots.behaviours.robot;

import java.util.List;

import org.pmw.tinylog.Logger;

import smartrobots.model.actors.Robot;
import smartrobots.model.grid.Grid.Tile;
import smartrobots.model.grid.Position;
import smartrobots.util.SampleGrid;
import smartrobots.util.astar.AStar;
import smartrobots.util.astar.AStarNode;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class CalculatePathBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 2334237770230847964L;
	
	public CalculatePathBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void action() {
		AStar astar = new AStar();
		Robot robot = (Robot) this.getParent().getDataStore().get("robot");
		Position<?> goal = (Position<?>) this.getParent().getDataStore().get("nextGoal");
		Logger.info(goal.getX() + " " + goal.getY());
		List<AStarNode> path = astar.findPath(convertToAStarNodes(), (int) robot.getX(), (int) robot.getY(), (int) goal.getX(), (int) goal.getY());
		Logger.info(path);
		this.getParent().getDataStore().put("currentPath", path);
	}
	
	@Override
	public int onEnd() {
		return 1;
	}
	
	@Override
	public void reset() {
		super.reset();
	}
	
	private AStarNode[][] convertToAStarNodes() {
		AStarNode[][] result = new AStarNode[SampleGrid.SMART_ROBOTS.length][SampleGrid.SMART_ROBOTS[0].length];
		for (int x = 0; x < SampleGrid.SMART_ROBOTS.length; x++) {
			for (int y = 0; y < SampleGrid.SMART_ROBOTS[0].length; y++) {
				result[x][y] = new AStarNode(x, y);
				result[x][y]
						.setPassable(!(SampleGrid.SMART_ROBOTS[x][y] == Tile.NO_TRACK));
			}
		}
		return result;
	}
	
}
