package smartrobots.behaviours.robot;

import org.pmw.tinylog.Logger;

import smartrobots.model.actors.Robot;
import smartrobots.model.entities.Block;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class PickBlockBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 8857166102979572503L;
	private int state;

	public PickBlockBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void onStart() {
		state = 0;
	}

	@Override
	public void action() {
		if (state == 0) {
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setContent("GIVE-BLOCK");
			request.addReceiver((AID) getParent().getDataStore().get(
					"goalAgentAID"));
			myAgent.send(request);
			state = 1;
		} else if (state == 1) {
			ACLMessage reply = myAgent.receive();
			if (reply != null) {
				try {
					Robot robot = (Robot) getParent().getDataStore().get("robot");
					if (reply.getContentObject().getClass() == Block.class) {
						robot.setBlock((Block) reply.getContentObject());
						Logger.info(robot.getBlock());
						state = 2;
					}
				} catch (UnreadableException e) {
					Logger.error(e);
				}
			} else {
				block();
			}
		}
	}

	@Override
	public boolean done() {
		return state == 2;
	}

	@Override
	public int onEnd() {
		return 1;
	}

	@Override
	public void reset() {
		onStart();
	}
}
