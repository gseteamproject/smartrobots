package smartrobots.model.grid;

import jade.content.Concept;
import jade.content.Predicate;

import java.io.Serializable;

public class Position<T extends Number> implements Serializable, Predicate {
	
	private static final long serialVersionUID = -5836200928348936189L;
	private T x;
	private T y;
	private Concept concept;
	
	public Position(T x, T y, Concept c) {
		this.x = x;
		this.y = y;
		this.concept = c;
	}
	
	public Position(T x, T y) {
		this(x, y, null);
	}
	
	public void setX(T x) {
		this.x = x;
	}
	
	public T getX() {
		return x;
	}
	
	
	public void setY(T y) {
		this.y = y;
	}
	
	public T getY() {
		return y;
	}
	
	public Concept getConcept() {
		return concept;
	}
	
	@Override
	public String toString() {
		return String.format("[x=%s; y=%s]", x, y);
	}
}
