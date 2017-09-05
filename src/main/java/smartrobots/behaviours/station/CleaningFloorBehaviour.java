package smartrobots.behaviours.station;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import org.pmw.tinylog.Logger;

import smartrobots.behaviours.robot.CalculatePathBehaviour;
import smartrobots.behaviours.robot.DropBlockBehaviour;
import smartrobots.behaviours.robot.FindBlockProviderBehaviour;
import smartrobots.behaviours.robot.MoverBehaviour;
import smartrobots.behaviours.robot.PickBlockBehaviour;
import smartrobots.model.actors.Robot;
import smartrobots.model.entities.Block;
import smartrobots.model.entities.Station;
import smartrobots.model.grid.Position;

public class CleaningFloorBehaviour extends CyclicBehaviour {

	private Station station;
	private DFAgentDescription template;
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
