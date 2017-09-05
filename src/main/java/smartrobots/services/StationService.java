package smartrobots.services;

import java.util.LinkedList;
import java.util.List;

import smartrobots.model.entities.Station;
import smartrobots.model.entities.Station.StationState;
import smartrobots.model.entities.Station.StationType;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class StationService {

	private ContainerController containerController;
	private List<Station> stations;
	
	public StationService(ContainerController containerController) {
		this.containerController = containerController;
		this.stations = new LinkedList<>();
	}
	
	public List<Station> getStations() {
		return stations;
	}
	
	
	public Station createStation(String name, int x, int y, StationType type, StationState state) {
		Station station = new Station(name, x, y, type, state);
		AgentController agentController;
		try {
			agentController = containerController.createNewAgent(name, "smartrobots.agents.StationAgent", new Object[] { station });
			agentController.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		stations.add(station);
		return station;
	}
}
