package smartrobots.behaviours.robot;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import org.pmw.tinylog.Logger;

import smartrobots.model.actors.Robot;

public class DropBlockBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 8794475122069851467L;
	private int state;

	public DropBlockBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void onStart() {
		state = 0;
	}

	@Override
	public void action() {
		if (state == 0) {
			Robot robot = (Robot) getParent().getDataStore().get("robot");
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.addReceiver((AID) getParent().getDataStore().get("goalAgentAID"));
			request.setContent("TAKE-BLOCK");
			try {
				request.setContentObject(robot.getBlock());
				robot.setBlock(null);
			} catch (IOException e) {
				Logger.error(e);
			}
			myAgent.send(request);
			state = 1;
		}
	}

	@Override
	public boolean done() {
		return state == 1;
	}

	@Override
	public int onEnd() {
		return 1;
	}

	@Override
	public void reset() {
		onStart();
		super.reset();
	}
}
