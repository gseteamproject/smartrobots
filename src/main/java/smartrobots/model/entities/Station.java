package smartrobots.model.entities;

import jade.content.Concept;

import java.io.Serializable;

import smartrobots.model.actors.Robot;

public class Station implements Serializable, Concept {
	
	private static final long serialVersionUID = 9212616253061745033L;
	private String name;
	private int x;
	private int y;
	private StationType type;
	private StationState state;
	private Robot currentWorker;
	private Block currentBlock;
	
	public Station(String name, int x, int y, StationType type, StationState state, Robot currentWorker, Block currentBlock) {
		this.name  = name;
		this.x     = x;
		this.y     = y;
		this.type  = type;
		this.state = state;
		this.currentWorker = currentWorker;
		this.currentBlock  = currentBlock;
	}
	
	public Station(String name, int x, int y, StationType type, StationState state) {
		this(name, x, y, type, state, null, null);
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
	
	public StationType getType() {
		return type;
	}	
	
	public StationState getState() {
		return state;
	}
	
	public void setState(StationState state) {
		this.state = state;
	}
	
	public void setCurrentWorker(Robot currentWorker) {
		this.currentWorker = currentWorker;
	}
	
	public Robot getCurrentWorker() {
		return currentWorker;
	}
	
	public void setCurrentBlock(Block currentBlock) {
		this.currentBlock = currentBlock;
	}
	
	public Block getCurrentBlock() {
		return currentBlock;
	}
	
	public enum StationType {
		CLEANING,
		PAINTING
	}
	
	public enum StationState {
		NO_WORKER,
		NO_BLOCK,
		WORKING,
		DONE
	}

	public boolean hasWorker() {
		return currentWorker != null;
	}
}
