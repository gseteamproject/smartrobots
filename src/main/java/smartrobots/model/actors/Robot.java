package smartrobots.model.actors;

import jade.content.Concept;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import smartrobots.model.entities.Block;

public class Robot implements Serializable, Concept {

	private static final long serialVersionUID = 8050375149740530052L;
	private String name;
	private double x;
	private double y;
	private Block block;
	private boolean hasBlock;
	private Movement movement;
	private double speed;
	
	public Robot(String name, double x, double y, double speed, boolean hasBlock, Movement movement) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.hasBlock = hasBlock;
		this.movement = movement;
		this.speed = speed;
		block = null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return round(x, 2);
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return round(y, 2);
	}
	
	public int getGridX() {
		return (int) x;
	}
	
	public int getGridY() {
		return (int) y;
	}
	
	public Movement getMovement() {
		return movement;
	}
	
	public boolean hasBlock() {
		return hasBlock;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public void setBlock(Block block) {
		this.block = block;
		hasBlock = this.block != null;
	}
	
	
	public void move(Movement movement, double units) {
		switch (movement) {
			case UP:
				y -= units;
				break;
			case RIGHT:
				x += units;
				break;
			case DOWN:
				y += units;
				break;
			case LEFT:
				x -= units;
			case NONE:
				// yeah do nothing man
		}
	}
	
	public enum Movement {
		NONE,
		UP,
		RIGHT,
		DOWN,
		LEFT
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public double getSpeed() {
		return speed;
	}
}
