package smartrobots.model.entities;

import jade.content.Concept;

import java.io.Serializable;

public class Block implements Serializable, Concept {

	private static final long serialVersionUID = 3842057226163466137L;
	private boolean cleaned;
	private boolean painted;
	
	public Block(boolean cleaned, boolean painted) {
		this.cleaned = cleaned;
		this.painted = painted;
	}
	
	public void setCleaned(boolean cleaned) {
		this.cleaned = cleaned;
	}
	
	public boolean isCleaned() {
		return cleaned;
	}
	
	public void setPainted(boolean painted) {
		this.painted = painted;
	}
	
	public boolean isPainted() {
		return painted;
	}
}
