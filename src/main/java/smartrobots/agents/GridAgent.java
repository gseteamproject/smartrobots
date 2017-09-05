package smartrobots.agents;

import jade.core.Agent;
import smartrobots.model.grid.Grid;

public class GridAgent extends Agent {
	
	private static final long serialVersionUID = -805344314438708356L;
	private Grid grid;
	
	
	@Override
	protected void setup() {
		grid = (Grid) getArguments()[0];
	}
}
