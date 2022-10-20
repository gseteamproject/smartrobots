package smartrobots.behaviours.robot;

import smartrobots.model.actors.Robot;
import smartrobots.model.grid.Position;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class HandleStationRequestBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 8874504551679086958L;
	private Robot robot;
	private int state;
	
	public HandleStationRequestBehaviour() {
		robot = (Robot) getDataStore().get("robot");
		state = 0;
	}
	
	@Override
	public void action() {
		if (state == 0) {
			ACLMessage msg = myAgent.receive();
			if (msg != null && msg.getConversationId().equals("need-worker")) {
				try {
					if (msg.getContentObject().getClass() == Position.class) {
						Position<?> pos = (Position<?>) msg.getContentObject();
						if ((int) robot.getX() != (int) pos.getX() || (int) robot.getY() != (int) pos.getY()) {
							getParent().getDataStore().put("nextGoal",
									msg.getContentObject());
							getParent().getDataStore().put("goalAgentAID",
									msg.getSender());
							state = 1;
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

}
