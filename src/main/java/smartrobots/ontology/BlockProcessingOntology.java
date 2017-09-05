package smartrobots.ontology;

import smartrobots.model.entities.Block;
import smartrobots.model.grid.Position;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.PredicateSchema;

public class BlockProcessingOntology extends Ontology implements BlockProcessingVocabulary {

	private static final long serialVersionUID = 2983319966391398084L;	
	private static Ontology instance = new BlockProcessingOntology();
	public static final String ONTOLOGY_NAME = "block-processing-ontology";
	
	public static Ontology getInstance() {
		return instance;
	}

	private BlockProcessingOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			add(new ConceptSchema(BLOCK), Block.class);
			add(new PredicateSchema(POSITION), Position.class);
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}
	}
	
}
