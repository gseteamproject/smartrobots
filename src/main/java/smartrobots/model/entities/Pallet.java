package smartrobots.model.entities;

import jade.content.Concept;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Pallet implements Serializable, Concept {
	
	private static final long serialVersionUID = 4334790097320297015L;
	private final String name;
	private int x;
	private int y;
	private boolean goal;
	private final List<Block> blocks;
	
	public Pallet(String name, int x, int y, boolean goal) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.goal = goal;
		blocks = new LinkedList<Block>();
	}

	public Pallet(String name, int x, int y, boolean goal, int initialBlocks) {
		this(name, x, y, goal);
		for (int i = 0; i < initialBlocks; i++) {
			blocks.add(new Block(false, false));
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}

	public boolean isGoal() {
		return goal;
	}
}
