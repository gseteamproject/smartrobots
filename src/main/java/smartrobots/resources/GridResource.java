package smartrobots.resources;

import static spark.Spark.*;

import com.google.gson.Gson;

import smartrobots.model.grid.Grid;
import smartrobots.services.GridService;

public class GridResource {

	public GridResource(final GridService gridService) {
		Gson gson = new Gson();
		
		get("/grids", (request, response) -> {
			/* list all grids*/
			return gridService.getGrids();
		}, gson::toJson);
		
		post("/grids", (request, response) -> {
			Grid grid = gson.fromJson(request.body(), Grid.class);
			gridService.addGrid(grid);
			return grid;
		}, gson::toJson);
	
		get("/grids/:name", (request, response) -> {
			return gridService.getGrid(request.params(":name"));
		}, gson::toJson);
	}
}
