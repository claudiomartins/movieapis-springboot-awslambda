package io.claudio.movieapis.servicehandlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import io.claudio.movieapis.domain.Actor;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class ActorsQueryLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		List<Actor> foundActors = null;

		if ((request.queryStringParameters == null || request.queryStringParameters.isEmpty())
				&& (request.pathParameters == null || request.pathParameters.isEmpty())) {

			foundActors = getActorRepository().findAll();

		} else if ((request.queryStringParameters == null || request.queryStringParameters.isEmpty())
				&& request.pathParameters.containsKey("id")
				&& StringUtils.isNumeric(request.pathParameters.get("id"))) {

			Actor foundActor = getActorRepository().findById(Long.valueOf(request.pathParameters.get("id")));
			foundActors = new ArrayList<>();

			if (foundActor != null) {
				foundActors.add(foundActor);
			}

		} else if (request.queryStringParameters != null && request.queryStringParameters.containsKey("name")) {
			foundActors = getActorRepository().findByShownName(request.queryStringParameters.get("name"));
		} else {
			return new Response(400, "Invalid request");
		}

		Response response = null;

		if (foundActors != null && foundActors.size() > 0) {
			response = new Response(200, new Gson().toJson(foundActors));
			addDefaultHeaders(response);
		} else {
			response = new Response(404, "Not found");
		}

		return response;

	}

}
