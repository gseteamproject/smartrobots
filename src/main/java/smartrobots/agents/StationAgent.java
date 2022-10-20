package smartrobots.agents;

import org.pmw.tinylog.Logger;

import smartrobots.behaviours.station.DoWorkBehaviour;
import smartrobots.behaviours.station.FindWorkerBehaviour;
import smartrobots.behaviours.station.RequestBlockBehaviour;
import smartrobots.behaviours.station.RequestBlockPickUp;
import smartrobots.model.entities.Station;
import smartrobots.ontology.BlockProcessingOntology;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;

public class StationAgent extends Agent {

	private static final long serialVersionUID = 3425411531816594492L;
	private Station station;

	@SuppressWarnings("unused")
	private Codec codec = new SLCodec();

	@SuppressWarnings("unused")
	private Ontology ontology = BlockProcessingOntology.getInstance();

	@SuppressWarnings("unused")
	private AID[] robots;
	
	@Override
	public void setup() {
		this.station = (Station) this.getArguments()[0];
		// startFloorBehaviour();
		// addBehaviour(new UpdateRobotsBehaviour());
		switch (station.getState()) {
		case NO_WORKER:
			Logger.info("Firing up FINDWORKERBEHAVIOUR");
			FindWorkerBehaviour fwb = new FindWorkerBehaviour();
			fwb.getDataStore().put("station", station);
			addBehaviour(fwb);
			break;
		case NO_BLOCK:
			addBehaviour(new RequestBlockBehaviour());
			break;
		case WORKING:
			addBehaviour(new DoWorkBehaviour());
			break;
		case DONE:
			addBehaviour(new RequestBlockPickUp());
			break;
		}
	}
	
	/*private void startFloorBehaviour() {
		switch (station.getType()) {
		case CLEANING:
			addBehaviour(new CleaningFloorBehaviour());
			break;
		case PAINTING:
			addBehaviour(new PaintinFloorBehaviour());
			break;
		}
	}*/
	
	/*private void ontologies() {
		// this.getContentManager().registerLanguage(codec);
		// this.getContentManager().registerOntology(ontology);
	}*/
	
	
	public void action() {
	}
}
