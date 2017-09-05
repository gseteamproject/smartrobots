package smartrobots.behaviours.robot;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import org.pmw.tinylog.Logger;

import smartrobots.model.actors.Robot;
import smartrobots.model.grid.Position;

public class FindBlockProviderBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 4826508187532931388L;
	private DFAgentDescription template;
	private int state;
	private Robot robot;

	public FindBlockProviderBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void onStart() {
		state = 0;
		robot = (Robot) getParent().getDataStore().get("robot");
		template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		if (!robot.hasBlock()) {
			sd.setType("block-provider");
			((SequentialBehaviour) getParent())
					.addSubBehaviour(new PickBlockBehaviour(myAgent));
		} else {
			sd.setType("block-receipt");
			((SequentialBehaviour) getParent())
					.addSubBehaviour(new DropBlockBehaviour(myAgent));
		}
		template.addServices(sd);
	}

	@Override
	public void action() {
		if (state == 0) {
			try {
				DFAgentDescription[] result = DFService.search(this.myAgent,
						template);
				for (int i = 0; i < result.length; i++) {
					ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
					request.setContent("WHERE-R-U");
					request.addReceiver(result[i].getName());
					myAgent.send(request);
					Logger.info("request sent");
					state = 1;
				}
			} catch (FIPAException e) {
				Logger.error(e);
			}
		} else if (state == 1) {
			ACLMessage reply = myAgent.receive();
			if (reply != null) {
				try {
					if (reply.getContentObject().getClass() == Position.class) {
						Position<?> pos = (Position<?>) reply
								.getContentObject();
						if ((int) robot.getX() != (int) pos.getX() || (int) robot.getY() != (int) pos.getY()) {
							getParent().getDataStore().put("nextGoal",
									reply.getContentObject());
							getParent().getDataStore().put("goalAgentAID",
									reply.getSender());
							state = 2;
						}
					}
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}

	/*
	 * @Override public void action() { ACLMessage reply = myAgent.receive(); if
	 * (reply != null) { try { getParent().getDataStore().put("nextGoal",
	 * reply.getContentObject()); } catch (UnreadableException e) {
	 * Logger.error(e); } done = true; } else { block(); } }
	 */

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
		super.reset();
	}
}
