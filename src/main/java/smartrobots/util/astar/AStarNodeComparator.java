package smartrobots.util.astar;

import java.util.Comparator;

public class AStarNodeComparator implements Comparator<AStarNode> {
	
	private AStarNode goal;
	
	public AStarNodeComparator(AStarNode goal) {
		this.goal = goal;
	}

	@Override
	public int compare(AStarNode node, AStarNode otherNode) {	
		if (node.getG() + node.getH(goal) < otherNode.getG() + otherNode.getH(goal)) {
			return -1;
		} else if (node.getG() + node.getH(goal) > otherNode.getG() + otherNode.getH(goal)) {
			return 1;
		}
		return 0;
	}	
}
