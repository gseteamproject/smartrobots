package smartrobots.behaviours.station;

import java.io.IOException;

import org.pmw.tinylog.Logger;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import smartrobots.model.entities.Station;
import smartrobots.model.grid.Position;

public class FindWorkerBehaviour extends SimpleBehaviour {
	
	private static final long serialVersionUID = -944676346534814765L;
	private static final int UPDATE_ACTIVE_ROBOTS = 0; 
	private static final int SEND_CFP_TO_ROBOTS = 1;
	private static final int WAIT_FOR_ANSWER = 2;
	private static final int WAIT_FOR_ANSWERS = 3;
	private Station station;
	private AID currentWorker;
	private AID[] robots;

	@SuppressWarnings("unused")
	private int repliesCount;

	@SuppressWarnings("unused")
	private MessageTemplate template;

	private int step;

	@Override
	public void onStart() {
		station = (Station) getDataStore().get("station");
		step = UPDATE_ACTIVE_ROBOTS;
	}

	@Override
	public void action() {
		switch (step) {
			case UPDATE_ACTIVE_ROBOTS:
				Logger.info("UPDATING ACTIVE ROBOTS");
				getRobots();
				break;
			case SEND_CFP_TO_ROBOTS:
				Logger.info("SENDING CFP TO ROBOTS");
				sendCFP();
				break;
			case WAIT_FOR_ANSWERS:
				Logger.info("WAITING FOR ANSWERS");
				waitForAnswers();
				break;
		}
		
	}
	
	private void getRobots() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("worker");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			robots = new AID[result.length];
			for (int i = 0; i < result.length; i++) {
				robots[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		step = SEND_CFP_TO_ROBOTS;
	}
	
	private void sendCFP() {
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		for (int i = 0; i < robots.length; ++i) {
			cfp.addReceiver(robots[i]);
		}
		try {
			Position<Integer> p = new Position<>(station.getX(), station.getY());
			cfp.setContentObject(p);
			cfp.setConversationId("need-worker");
			cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique
			myAgent.send(cfp);
			// Prepare the template to get proposals
			template = MessageTemplate.and(
			MessageTemplate.MatchConversationId("need-worker"),
			MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
		step = WAIT_FOR_ANSWER;
		} catch(IOException e) {
			Logger.error(e);
		}
	}
	
	private void waitForAnswers() {
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			@SuppressWarnings("unused")
			String title = msg.getContent();

			@SuppressWarnings("unused")
			ACLMessage reply = msg.createReply();
		}
	}

	@Override
	public boolean done() {
		return currentWorker != null;
	}
}
