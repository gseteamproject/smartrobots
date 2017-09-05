package smartrobots.resources;

import static spark.Spark.*;

import com.google.gson.Gson;

import smartrobots.services.RobotService;

public class RobotResource {

	public RobotResource(final RobotService robotService) {
		Gson gson = new Gson();
		
		get("/robots", (request, response) -> {
			return robotService.getRobots().toArray();
		}, gson::toJson);
		
		post("/robots", (request, response) -> {
			return robotService.createRobot(request.queryParams("name"),
					Integer.valueOf(request.queryParams("x")),
					Integer.valueOf(request.queryParams("y")),
					Double.valueOf(request.queryParams("speed")));
		}, gson::toJson);
		
		get("/robots/:name", (request, response) -> {
			String name = request.params(":name");
			return robotService.getRobot(name);
		}, gson::toJson);
		
		after((request, response) -> {
			response.type("application/json");
		});
	}	
}
