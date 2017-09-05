package smartrobots.services;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import smartrobots.model.entities.Pallet;

public class PalletService {

	private ContainerController containerController;
	private List<Pallet> pallets;
	
	public PalletService(ContainerController containerController) {
		this.containerController = containerController;
		pallets = new LinkedList<>();
	}
	
	public Pallet createPallet(String name, int x, int y, boolean goal, int initialBlocks) {
		Pallet pallet = new Pallet(name, x, y, goal, initialBlocks);
		AgentController agentController;
		try {
			agentController = containerController.createNewAgent(name, "smartrobots.agents.PalletAgent", new Object[] { pallet });
			agentController.start();
		} catch (StaleProxyException e) {
			Logger.error(e);
		}
		pallets.add(pallet);
		return pallet;
	}
	
	public List<Pallet> getPallets() {
		return pallets;
	}
}
