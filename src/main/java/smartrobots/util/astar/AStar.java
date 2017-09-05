package smartrobots.util.astar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class AStar {

	public static void main(String[] args) {
		AStarNode[][] map = new AStarNode[10][10];
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				map[x][y] = new AStarNode(x, y);
				if ((x == 7 && y >= 0 && y <= 7)
						|| (y == 7 && x >= 3 && x <= 7)) {
					map[x][y].setPassable(false);
				}
			}
		}
		
		long startTime = System.currentTimeMillis();
		List<AStarNode> path = new AStar().findPath(map, 0, 0, 9, 9);
		long stopTime = System.currentTimeMillis();
		System.out.println(String.format("It took %sms to find the path!", stopTime - startTime));
		System.out.println(resultToString(map, path));
	}

	public static String resultToString(AStarNode[][] map, List<AStarNode> path) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (path != null && path.contains(map[x][y])) {
					sb.append("P");
				} else {
					sb.append(map[x][y].isPassable() ? "_" : "W");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}
	
	
	public List<AStarNode> findPath(AStarNode[][] nodes, int startX,
			int startY, int goalX, int goalY) {
		PriorityQueue<AStarNode> open = new PriorityQueue<>(
				new AStarNodeComparator(nodes[goalX][goalY]));
		LinkedList<AStarNode> closed = new LinkedList<>();

		open.add(nodes[startX][startY]); // add starting node to open list

		AStarNode current = open.peek();
		// System.out.println(current);
		while (!open.isEmpty()) {
			current = open.poll();

			if (current.equals(nodes[goalX][goalY])) {
				break;
			}

			open.remove(current);
			closed.add(current);

			for (AStarNode neighbor : getAdjacent(nodes, current)) {
				if (!neighbor.isPassable()) {
					continue;
				}
				/*
				 * cost = g(current) + movementcost(current, neighbor) if
				 * neighbor in OPEN and cost less than g(neighbor): remove
				 * neighbor from OPEN, because new path is better if neighbor in
				 * CLOSED and cost less than g(neighbor): ⁽²⁾ remove neighbor
				 * from CLOSED if neighbor not in OPEN and neighbor not in
				 * CLOSED: set g(neighbor) to cost add neighbor to OPEN set
				 * priority queue rank to g(neighbor) + h(neighbor) set
				 * neighbor's parent to current
				 */

				int cost = current.getG();
				if (open.contains(neighbor) && cost < neighbor.getG()) {
					open.remove(neighbor);
				}
				
			    if (closed.contains(neighbor) && cost < neighbor.getG()) {
			    	closed.remove(neighbor);
			    }

				if (!open.contains(neighbor) && !closed.contains(neighbor)) {
					neighbor.setPredecessor(current);
					open.add(neighbor);
				}
			}
		}

		List<AStarNode> result = tracePath(current);
		return result;
	}
	
	private List<AStarNode> tracePath(AStarNode current) {
		LinkedList<AStarNode> path = new LinkedList<>();
		AStarNode tmp = current;

		while (tmp.getPredecessor() != null) {
			path.add(tmp);
			tmp = tmp.getPredecessor();
		}

		Collections.reverse(path);
		return path;
	}
	

	private List<AStarNode> getAdjacent(AStarNode[][] nodes, AStarNode node) {
		LinkedList<AStarNode> adjacentNodes = new LinkedList<AStarNode>();
		int x = node.getX();
		int y = node.getY();

		if (x > 0 && y > 0) {
			// adjacentNodes.add(nodes[x - 1][y - 1]);
			adjacentNodes.add(nodes[x - 1][y]);
			adjacentNodes.add(nodes[x][y - 1]);
		}

		if (x < nodes.length - 1 && y < nodes[0].length - 1) {
			adjacentNodes.add(nodes[x][y + 1]);
			adjacentNodes.add(nodes[x + 1][y]);
			//adjacentNodes.add(nodes[x + 1][y + 1]);
		}

		//if (x > 0 && y < nodes[0].length - 1) {
		//	adjacentNodes.add(nodes[x - 1][y + 1]);
		//}

		//if (x < nodes.length - 1 && y > 0) {
		//	adjacentNodes.add(nodes[x + 1][y - 1]);
		//}

		// System.out.println("adjacent nodes=" + adjacentNodes.toString());

		adjacentNodes.stream().filter(AStarNode::isPassable)
				.collect(Collectors.toList());

		return adjacentNodes;
	}
}
