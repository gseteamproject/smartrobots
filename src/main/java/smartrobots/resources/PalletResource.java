package smartrobots.resources;

import static spark.Spark.get;
import static spark.Spark.post;
import smartrobots.services.PalletService;

import com.google.gson.Gson;

public class PalletResource {

	public PalletResource(final PalletService palletService) {
		// TODO implement properly
		
		Gson gson = new Gson();
		
		get("/pallets", (request, response) -> {
			return palletService.getPallets();
		}, gson::toJson);
		
		post("/pallets", (request, response) -> {
			return response;
		}, gson::toJson);
	
		get("/pallets/:name", (request, response) -> {
			return response;
		}, gson::toJson);
	}
}
