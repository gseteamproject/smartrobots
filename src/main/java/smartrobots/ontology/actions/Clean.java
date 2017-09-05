package smartrobots.ontology.actions;

import smartrobots.model.entities.Block;
import jade.content.AgentAction;

public class Clean implements AgentAction {

	private static final long serialVersionUID = 2667207149383162959L;
	private Block block;
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
}
