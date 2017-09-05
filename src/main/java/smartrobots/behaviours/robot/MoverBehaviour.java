package smartrobots.behaviours.robot;

import smartrobots.model.actors.Robot;
import smartrobots.util.astar.AStarNode;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

import java.util.List;

import org.pmw.tinylog.Logger;

public class MoverBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -8726033637197239756L;
	private Robot robot;
	private List<?> path;
	private AStarNode next;	
	private long period;
	private boolean done;
	
	public MoverBehaviour(Agent a, long period) {
		super(a);
		this.period = period;
	}
	
	@Override
	public void onStart() {
		robot = (Robot) getParent().getDataStore().get("robot"); 
	    path  = (List<?>) getParent().getDataStore().get("currentPath");
	    next  = !path.isEmpty() ? (AStarNode) path.remove(0) : null;
	    done  = next == null ? true : false;
	}
	/**
	 * 
	 */
	@Override
	public void action() {
		if (!done) {
			// Logger.info("ticking");
			onTick();
			block(period);
		} else {
			Logger.info("ending");
			onEnd();
		}
	}

	public void onTick() {
		if (next != null && robot.getX() == next.getX() && robot.getY() == next.getY()) {
			if (!path.isEmpty()) {
				next = (AStarNode) path.remove(0);
				Logger.info(String.format("next is %s %s", next.getX(), next.getY()));
			} else {
				done = true;
				Logger.info("nigga, we done");
			}
		}
		
		if (!done) {
			robot.move(decideNextMove(next), 0.01 * robot.getSpeed());
		}
	}
	
	@Override
	public boolean done() {
		return done;
	}
	
	@Override
	public int onEnd() {
		return 1;
	}
	
	@Override
	public void reset() {
		onStart();
		super.reset();
	}
	
	private Robot.Movement decideNextMove(AStarNode goal) {
		double x = robot.getX() - goal.getX();
		double y = robot.getY() - goal.getY();
		// Logger.info(String.format("%s (x=%s, y=%s)", robot.getName(), x, y));
		
		if (x < 0 && y == 0) {
			return Robot.Movement.RIGHT; 
		}
		
		if (x > 0 && y == 0) {
			return Robot.Movement.LEFT;
		}
		
		if (y < 0 && x == 0) {
			return Robot.Movement.DOWN;
		}
		
		return Robot.Movement.UP;
		
	}
}
