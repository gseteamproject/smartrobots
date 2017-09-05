package smartrobots.agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import smartrobots.behaviours.robot.CalculatePathBehaviour;
import smartrobots.behaviours.robot.DropBlockBehaviour;
import smartrobots.behaviours.robot.FindBlockProviderBehaviour;
import smartrobots.behaviours.robot.FindBlockReceiptBehaviour;
import smartrobots.behaviours.robot.MoverBehaviour;
import smartrobots.behaviours.robot.PickBlockBehaviour;
import smartrobots.model.actors.Robot;
import smartrobots.ontology.BlockProcessingOntology;
public class RobotAgent extends Agent {

	private static final long serialVersionUID = -212551849313545445L;
	private Codec codec = new SLCodec();
	private Ontology ontology = BlockProcessingOntology.getInstance();
	private Robot robot;
	
	@Override
	protected void setup() {
		this.robot = (Robot) this.getArguments()[0];
		this.getContentManager().registerLanguage(codec);
		this.getContentManager().registerOntology(ontology);
		this.addBehaviour(picker());
	}
	
	
	private SequentialBehaviour picker() {
		SequentialBehaviour picker = new SequentialBehaviour(this) {
			private static final long serialVersionUID = 5308540359375783446L;
			public int onEnd() {
				myAgent.addBehaviour(picker());
				return super.onEnd();
			}
		};
		picker.getDataStore().put("robot", robot);
		picker.addSubBehaviour(new FindBlockProviderBehaviour(this));
		picker.addSubBehaviour(new CalculatePathBehaviour(this));
		picker.addSubBehaviour(new MoverBehaviour(this, 10));
		return picker;
	}
	
	private SequentialBehaviour transporter() {
		ParallelBehaviour base = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL) {
			private static final long serialVersionUID = 8909624426147018773L;

			public int onEnd() {
				myAgent.addBehaviour(picker());
				return super.onEnd();
			}
		};
		
		SequentialBehaviour transporter = new SequentialBehaviour(this);
		transporter.getDataStore().put("robot", robot);
		transporter.addSubBehaviour(new FindBlockReceiptBehaviour(this));
		transporter.addSubBehaviour(new CalculatePathBehaviour(this));
		transporter.addSubBehaviour(new MoverBehaviour(this, 100));
		transporter.addSubBehaviour(new DropBlockBehaviour(this));
		return transporter;
	}
	
	
	private SequentialBehaviour worker() {
		SequentialBehaviour worker = new SequentialBehaviour(this) {
			public int onEnd() {
				reset();
				myAgent.addBehaviour(this);
				return super.onEnd();
			}
		};
		worker.getDataStore().put("robot", robot);
		worker.addSubBehaviour(new FindBlockProviderBehaviour(this));
		worker.addSubBehaviour(new CalculatePathBehaviour(this));
		worker.addSubBehaviour(new MoverBehaviour(this, 100));
		worker.addSubBehaviour(new PickBlockBehaviour(this));
		return worker;
	}
	
	
	@Override
	protected void takeDown() {
		super.takeDown();
	}
}
