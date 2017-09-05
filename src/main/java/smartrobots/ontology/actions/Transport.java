package smartrobots.ontology.actions;

import jade.content.AgentAction;
import smartrobots.model.entities.Block;

public class Transport implements AgentAction {

	private static final long serialVersionUID = 764942799471286950L;
	private Block block;
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
}
