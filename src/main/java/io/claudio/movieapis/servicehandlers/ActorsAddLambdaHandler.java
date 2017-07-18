package io.claudio.movieapis.servicehandlers;

import org.springframework.util.StringUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.claudio.movieapis.domain.Actor;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;

public class ActorsAddLambdaHandler extends SpringServiceHandler {

	@Override
	public Response handleRequest(Request request, Context context) {

		if (StringUtils.isEmpty(request.body)) {
			return new Response(400, "Invalid request");
		}

		Actor newActor = null;

		try {
			newActor = new Gson().fromJson(request.body, Actor.class);
		} catch (JsonSyntaxException e) {
			return new Response(400, "Invalid request");
		}

		if (newActor == null || StringUtils.isEmpty(newActor.firstName) || StringUtils.isEmpty(newActor.lastName)
				|| StringUtils.isEmpty(newActor.shownName) || newActor.birthYear <= 0) {
			return new Response(400, "Invalid request");
		}

		newActor = getActorRepository().save(newActor);

		Actor testActor = getActorRepository().findById(newActor.id);

		Response response = new Response(200, new Gson().toJson(newActor));
		addDefaultHeaders(response);

		return response;

	}

}
