package smartrobots.ontology.actions;

import jade.content.AgentAction;
import smartrobots.model.entities.Block;

public class Paint implements AgentAction {

	private static final long serialVersionUID = -2255038243863957664L;
	private Block block;
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
}
