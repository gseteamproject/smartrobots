package smartrobots.util.astar;

public class AStarNode {

	private int x;
	private int y;
	private AStarNode predecessor;
	private boolean passable;

	public AStarNode(int x, int y, AStarNode predecessor) {
		this.x = x;
		this.y = y;
		this.predecessor = predecessor;
		this.passable = true;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	
	public boolean isPassable() {
		return passable;
	}

	public AStarNode(int x, int y) {
		this(x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getG() {
		int movementCost = 100;
		int f = movementCost;
		AStarNode current = this;
		while ((current = current.getPredecessor()) != null) {
			f += movementCost;
		}
		return f;
	}

	public int getH(AStarNode other) {
		return Heuristic.getManhattenDistance(this.getY(), this.getY(), other.getY(), other.getY());
	}

	public void setPredecessor(AStarNode predecessor) {
		this.predecessor = predecessor;
	}

	public AStarNode getPredecessor() {
		return predecessor;
	}
	
	@Override
	public String toString() {
		return String.format("AStarNode[x=%s, y=%s, g=%s]", getX(), getY(), getG());
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		
	    if (other == null || getClass() != other.getClass()) {
	    	return false;
	    }

	    AStarNode otherNode = (AStarNode) other;
	    if (this.getX() != otherNode.getX() || this.getY() != otherNode.getY()) {
	    	return false;
	    }

	    return true;	
	}
}
