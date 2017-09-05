package smartrobots.services;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import smartrobots.model.grid.Grid;
import smartrobots.model.grid.Grid.Tile;

public class GridService {

	private ContainerController containerController;
	private List<Grid> grids;
	
	public GridService(ContainerController containerController) {
		this.containerController = containerController;
		this.grids = new LinkedList<>();
	}
	
	public List<Grid> getGrids() {
		return grids;
	}
	
	public void addGrid(Grid grid) {
		startGridAgent(grid);
		grids.add(grid);
	}
	
	public void addGrid(String name, Tile[][] tiles, List<Object> gridObjects) {
		addGrid(new Grid(name, tiles, gridObjects));
	}
	
	private void startGridAgent(Grid grid) {
		AgentController agentController;
		try {
			agentController = containerController.createNewAgent(grid.getName(), "smartrobots.agents.GridAgent", new Object[] { grid });
			agentController.start();
		} catch (StaleProxyException e) {
			Logger.error(e);
		}
	}
	
	public Grid getGrid(String name) {
		return grids.stream().filter(grid -> grid.getName().equals(name)).findAny().get();
	}
}
