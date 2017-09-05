package smartrobots;

import static spark.Spark.before;
import static spark.Spark.options;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;

import org.pmw.tinylog.Logger;

import smartrobots.model.entities.Station.StationState;
import smartrobots.model.entities.Station.StationType;
import smartrobots.model.grid.Grid;
import smartrobots.util.SampleGrid;
import smartrobots.resources.GridResource;
import smartrobots.resources.RobotResource;
import smartrobots.resources.PalletResource;
import smartrobots.resources.StationResource;
import smartrobots.services.GridService;
import smartrobots.services.PalletService;
import smartrobots.services.RobotService;
import smartrobots.services.StationService;


public class Main {
	
	private static Runtime rt;
	private static ContainerController cc;

	public static void main(String[] args) {
		java.lang.Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
		rt = Runtime.instance();
		cc = rt.createMainContainer(createProfile("localhost", 3456));
		enableCORS("*", "*", "*");
		
		GridService gridService = new GridService(cc);
		gridService.addGrid(new Grid("smartrobots", SampleGrid.SMART_ROBOTS, new LinkedList<Object>()));
		new GridResource(gridService);
		
		/*PalletService palletService = new PalletService(cc);
		palletService.createPallet("source-1", 0, 2, false, 100);
		palletService.createPallet("source-2", 0, 4, false, 100);
		palletService.createPallet("source-3", 0, 6, false, 100);
		palletService.createPallet("goal-1", 15, 2, true, 0);
		palletService.createPallet("goal-2", 15, 2, true, 0);
		palletService.createPallet("goal-3", 15, 2, true, 0);
		new PalletResource(palletService);*/
		
		StationService stationService = new StationService(cc);
		stationService.createStation("cleaning-station", 0, 0, StationType.CLEANING, StationState.NO_WORKER);
		stationService.createStation("painting-station", 0, 0, StationType.PAINTING, StationState.NO_WORKER);
		new StationResource(stationService);
		
		RobotService robotService = new RobotService(cc);
	    robotService.createRobot("bob", 7, 8, 1.0);
		robotService.createRobot("joe", 8, 8, 2.0);
		new RobotResource(robotService);
	}
	
	
	private static Profile createProfile(String host, int port) {
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, host);
		profile.setParameter(Profile.MAIN_PORT, "" + port);
		return profile;
	}
	
	private static void shutdown() {
		try {
			cc.kill();
			Logger.info("MainContainer killed");
		} catch (StaleProxyException e) {
			Logger.error(e.getMessage());
		}
	}
	
	// Enables CORS on requests. This method is an initialization method and should be called once.
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        // Note: this may or may not be necessary in your particular application
	        response.type("application/json");
	    });
	}
}
