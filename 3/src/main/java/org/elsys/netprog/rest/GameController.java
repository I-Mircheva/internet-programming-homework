package org.elsys.netprog.rest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.elsys.netprog.model.Game;
import org.elsys.netprog.model.GameBank;
import org.elsys.netprog.model.RandomGenerator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameController {

	private static GameBank allGames = new GameBank();

	// curl -X POST http://localhost:8080/jersey-rest-homework/game/startGame
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException {
		Game game = new Game();
		allGames.addGame(game);
		return Response.created(new URI("/games")).status(201).entity(game.getId()).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws JSONException {

		Game game = allGames.getGameById(gameId);

		if (!guess.matches("\\d+")) {
			return Response.status(400).build();
		}
		Integer guessInt = Integer.parseInt(guess);

		if(game == null) {
			return Response.status(404).build();
		}

		if(!RandomGenerator.hasDistinctDigits(guessInt) || !( guessInt > 1022 && guessInt < 9877 )) {
			return Response.status(400).build();
		}

		Integer gameTurns = game.getTurnsCount() + 1;
		game.setTurnsCount(gameTurns);

		return Response.ok().entity(generateJSONResponse(game, game.checkBullsAndCows(Integer.parseInt(guess))).toString()).status(200).build();

	}

	public JSONObject generateJSONResponse(Game game, ArrayList<Integer> bullsAndCows) throws JSONException {

		JSONObject obj = new JSONObject();

		obj.put("gameId", game.getId());
		obj.put("cowsNumber", bullsAndCows.get(1));
		obj.put("bullsNumber", bullsAndCows.get(0));
		obj.put("turnsCount", game.getTurnsCount());
		obj.put("success", game.getSuccess());

		return obj;
	}

//	{
//		"gameId":"{gameId}",
//		"cowsNumber":{cows},
//		"bullsNumber":{bulls},
//		"turnsCount":{counter},
//		"success":{true|false}
//	}


	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() throws JSONException {
		return Response.status(200).entity(generateJSONArray(allGames)).build();
	}

	public String generateJSONArray(GameBank allGames) throws JSONException {

		JSONArray allGamesJSON = new JSONArray();

		for (Game game : allGames.getAllGames()) {
			JSONObject obj = new JSONObject();

			obj.put("gameId", game.getId());
			obj.put("turnsCount", game.getTurnsCount());
			if(!game.getSuccess()) {
				obj.put("secret", "****");
			} else {
				obj.put("secret", game.getSecret().toString());
			}
			obj.put("success", game.getSuccess());

			allGamesJSON.put(obj);
		}

		return allGamesJSON.toString();
	}

//	[{	"gameId":"{gameId}",
//		"turnsCount":{turnsCount},
//		"secret":"{secret}",
//		"success":{true|false}},
//		....]

}
