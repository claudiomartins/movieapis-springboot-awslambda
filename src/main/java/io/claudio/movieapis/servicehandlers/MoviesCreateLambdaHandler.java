package io.claudio.movieapis.servicehandlers;

import org.springframework.util.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.claudio.movieapis.domain.Movie;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class MoviesCreateLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		if (StringUtils.isEmpty(request.body)) {
			return new Response(400, "Invalid request");
		}

		Movie newMovie = null;

		try {
			newMovie = new Gson().fromJson(request.body, Movie.class);
		} catch (JsonSyntaxException e) {
			return new Response(400, "Invalid request");
		}

		if (newMovie == null || StringUtils.isEmpty(newMovie.title) || newMovie.releaseYear == 0) {
			return new Response(400, "Invalid request");
		}

		newMovie = getMovieRepository().save(newMovie);

		Response response = new Response(200, new Gson().toJson(newMovie));
		addDefaultHeaders(response);

		return response;

	}

}
