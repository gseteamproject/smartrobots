package smartrobots.behaviours.robot;

import org.pmw.tinylog.Logger;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class FindBlockReceiptBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 3176181629089160032L;
	private DFAgentDescription template;
	private int state;
	
	public FindBlockReceiptBehaviour(Agent agent) {
		super(agent);
	}
	
	@Override
	public void onStart() {
		state = 0;
		template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("block-receipt");
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
					state = 1;
				}
			} catch (FIPAException e) {
				Logger.error(e);
			}
		} else if (state == 1) {
			ACLMessage reply = myAgent.receive();
			if (reply != null) {
				try {
					getParent().getDataStore().put("nextGoal", reply.getContentObject());
					getParent().getDataStore().put("goalAgentAID", reply.getSender());
					state = 2;
				} catch (UnreadableException e) {
					Logger.error(e);
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
