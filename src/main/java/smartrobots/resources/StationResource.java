package smartrobots.resources;

import static spark.Spark.*;
import smartrobots.services.StationService;
import com.google.gson.Gson;

public class StationResource {

	
	public StationResource(final StationService stationService) {
		Gson gson = new Gson();
		
		get("/stations", (request, response) -> {
			return stationService.getStations().toArray();
		}, gson::toJson);
	}
}
