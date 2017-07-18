package io.claudio.movieapis.servicehandlers;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.claudio.movieapis.domain.Actor;
import io.claudio.movieapis.domain.Movie;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class MoviesAddActorLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		LOGGER.trace("MoviesAddActorLambdaHandler handleRequest - new - " + request.body);

		if (StringUtils.isEmpty(request.body) || request.pathParameters == null
				|| !request.pathParameters.containsKey("id")
				|| !StringUtils.isNumeric(request.pathParameters.get("id"))) {
			LOGGER.trace("MoviesAddActorLambdaHandler handleRequest - invalid request - " + request.body);
			return new Response(400, "Invalid request");
		}

		Movie currentMovie = getMovieRepository().findById(Long.valueOf(request.pathParameters.get("id")));

		if (currentMovie == null) {
			return new Response(404, "Not found");
		}

		try {

			Type type = new TypeToken<Map<String, String>>() {
			}.getType();
			Map<String, String> bodyMap = new Gson().fromJson(request.body, type);

			if (bodyMap.containsKey("actorId") && StringUtils.isNumeric(bodyMap.get("actorId"))) {

				Actor actor = getActorRepository().findById(Long.valueOf(bodyMap.get("actorId")));

				if (actor != null) {
					currentMovie.actors.add(actor);
					currentMovie = getMovieRepository().save(currentMovie);
				} else {
					return new Response(400, "Invalid request");
				}

			} else {
				return new Response(400, "Invalid request");
			}

		} catch (JsonSyntaxException e) {
			LOGGER.trace("MoviesAddActorLambdaHandler handleRequest - JsonSyntaxException - " + e.getMessage());
			return new Response(400, "Invalid request");
		}

		Response response = new Response(200, new Gson().toJson(currentMovie));
		addDefaultHeaders(response);

		return response;

	}

}
