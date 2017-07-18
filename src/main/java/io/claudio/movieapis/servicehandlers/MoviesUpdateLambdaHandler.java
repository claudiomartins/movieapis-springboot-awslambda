package io.claudio.movieapis.servicehandlers;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.claudio.movieapis.domain.Movie;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class MoviesUpdateLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		if (StringUtils.isEmpty(request.body) || request.pathParameters == null
				|| !request.pathParameters.containsKey("id")
				|| !StringUtils.isNumeric(request.pathParameters.get("id"))) {
			return new Response(400, "Invalid request");
		}

		Movie currentMovie = getMovieRepository().findById(Long.valueOf(request.pathParameters.get("id")));

		if (currentMovie == null) {
			return new Response(404, "Not found");
		}

		try {

			Movie newMovieData = new Gson().fromJson(request.body, Movie.class);

			if (StringUtils.isEmpty(newMovieData.title) || newMovieData.releaseYear == 0) {
				return new Response(400, "Invalid request");
			}

			currentMovie.title = newMovieData.title;
			currentMovie.releaseYear = newMovieData.releaseYear;

			currentMovie = getMovieRepository().save(currentMovie);

		} catch (JsonSyntaxException e) {
			return new Response(400, "Invalid request");
		}

		Response response = new Response(200, new Gson().toJson(currentMovie));
		addDefaultHeaders(response);

		return response;

	}

}
