package smartrobots.agents;

import java.io.IOException;

import org.pmw.tinylog.Logger;

import smartrobots.model.entities.Pallet;
import smartrobots.model.grid.Position;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import smartrobots.model.entities.Block;

public class PalletAgent extends Agent {

	private static final long serialVersionUID = -8601353122313266428L;
	private Pallet pallet;

	@Override
	public void setup() {
		Logger.info("starting a pallet agent");
		this.pallet = (Pallet) getArguments()[0];

		// register source-pallet-service
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		if (!pallet.isGoal()) {
			Logger.info("Registering goal pallet service");
			sd.setType("block-provider");
			sd.setName(getLocalName() + "-block-provider");
			addBehaviour(new BlockProviderBehaviour());
		} else {
			Logger.info("Registering source pallet service");
			sd.setType("block-receipt");
			sd.setName(getLocalName() + "-block-receipt");
			addBehaviour(new BlockReceiptBehaviour());
		}
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			Logger.error(e);
		}

	}

	private class BlockProviderBehaviour extends CyclicBehaviour {

		private static final long serialVersionUID = 7147617214998511055L;

		@Override
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				handleMessage(msg);
			} else {
				block();
			}
		}

		private void handleMessage(ACLMessage msg) {
			switch (msg.getContent()) {
			case "WHERE-R-U":
				Logger.info("replying with position");
				replyWithPosition(msg, "PICKUP-BLOCK");
				break;
			case "GIVE-BLOCK":
				Logger.info("replying with block");
				replyWithBlock(msg);
				break;
			}
		}


		private void replyWithBlock(ACLMessage msg) {
			ACLMessage msgTx = msg.createReply();
			try {
				msgTx.setContentObject(pallet.getBlocks().remove(0));
			} catch (IOException e) {
				Logger.error(e);
			}
			send(msgTx);
		}
	}
	

	private class BlockReceiptBehaviour extends CyclicBehaviour {

		private static final long serialVersionUID = 5631264194530546626L;


		@Override
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				handleMessage(msg);
			} else {
				block();
			}
		}

		private void handleMessage(ACLMessage msg) {
			switch (msg.getContent()) {
			case "WHERE-R-U":
				Logger.info("replying with position");
				replyWithPosition(msg, "DELIVER-BLOCK");
				break;
			case "TAKE-BLOCK":
				Logger.info("taking block");
				takeBlock(msg);
				break;
			}
		}


		private void takeBlock(ACLMessage msg) {
			try {
				pallet.getBlocks().add((Block) msg.getContentObject());
			} catch (UnreadableException e) {
				Logger.error(e);
			}
		}
	}
	
	private void replyWithPosition(ACLMessage msg, String content) {
		ACLMessage msgTx = msg.createReply();
		try {
			msgTx.setContent(content);
			msgTx.setContentObject(new Position<Integer>(pallet.getX(),
					pallet.getY()));
		} catch (IOException e) {
			Logger.error(e);
		}
		send(msgTx);
	}
}