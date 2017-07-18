package io.claudio.movieapis.servicehandlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import io.claudio.movieapis.domain.Movie;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class MoviesQueryLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		List<Movie> foundMovies = null;

		LOGGER.trace("MoviesQueryLambdaHandler handleRequest - new");

		if ((request.queryStringParameters == null || request.queryStringParameters.isEmpty())
				&& (request.pathParameters == null || request.pathParameters.isEmpty())) {

			foundMovies = getMovieRepository().findAll();

			LOGGER.trace("MoviesQueryLambdaHandler handleRequest - find all movies - found: " + foundMovies != null
					? foundMovies.size()
					: 0);

		} else if ((request.queryStringParameters == null || request.queryStringParameters.isEmpty())
				&& request.pathParameters.containsKey("id")) {

			if (StringUtils.isNumeric(request.pathParameters.get("id"))) {

				Movie foundMovie = getMovieRepository().findById(Long.valueOf(request.pathParameters.get("id")));
				foundMovies = new ArrayList<>();

				if (foundMovie != null) {
					foundMovies.add(foundMovie);
				}

				LOGGER.trace("MoviesQueryLambdaHandler handleRequest - find movie by with ID "
						+ request.pathParameters.get("id") + " - found: " + foundMovie != null ? "yes" : "no");

			} else {
				return new Response(400, "Invalid request");
			}

		} else if (request.queryStringParameters != null && request.queryStringParameters.containsKey("title")
				&& request.queryStringParameters.containsKey("actor")) {

			foundMovies = getMovieRepository().findByTitleAndActorsShownName(request.queryStringParameters.get("title"),
					request.queryStringParameters.get("actor"));

			LOGGER.trace("MoviesQueryLambdaHandler handleRequest - find all movies with title '"
					+ request.queryStringParameters.get("title") + "' AND actor name '"
					+ request.queryStringParameters.containsKey("actor") + "' - found: " + foundMovies != null
							? foundMovies.size()
							: 0);

		} else if (request.queryStringParameters != null && !request.queryStringParameters.containsKey("title")
				&& request.queryStringParameters.containsKey("actor")) {

			foundMovies = getMovieRepository().findByActorsShownName(request.queryStringParameters.get("actor"));

			LOGGER.trace("MoviesQueryLambdaHandler handleRequest - find all movies with actor name '"
					+ request.queryStringParameters.containsKey("actor") + "' - found: " + foundMovies != null
							? foundMovies.size()
							: 0);

		} else if (request.queryStringParameters != null && request.queryStringParameters.containsKey("title")) {

			foundMovies = getMovieRepository().findByTitle(request.queryStringParameters.get("title"));

			LOGGER.trace("MoviesQueryLambdaHandler handleRequest - find all movies with title '"
					+ request.queryStringParameters.get("title") + "' - found: " + foundMovies != null
							? foundMovies.size()
							: 0);

		}

		Response response = null;

		if (foundMovies != null && foundMovies.size() > 0) {
			response = new Response(200, new Gson().toJson(foundMovies));
			addDefaultHeaders(response);
		} else {
			response = new Response(404, "Not found");
		}

		return response;

	}

}
