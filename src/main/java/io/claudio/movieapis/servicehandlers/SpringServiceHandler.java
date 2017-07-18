package io.claudio.movieapis.servicehandlers;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import io.claudio.movieapis.MovieApisApplication;
import io.claudio.movieapis.lambda.Request;
import io.claudio.movieapis.lambda.Response;
import io.claudio.movieapis.repository.ActorRepository;
import io.claudio.movieapis.repository.MovieRepository;

@Component
public abstract class SpringServiceHandler implements RequestHandler<Request, Response> {

	private MovieRepository _movieRepository;

	private ActorRepository _actorRepository;

	protected final Log LOGGER = LogFactory.getLog(this.getClass());

	public MovieRepository getMovieRepository() {
		initializeRepositories();
		return _movieRepository;
	}

	public ActorRepository getActorRepository() {
		initializeRepositories();
		return _actorRepository;
	}

	private static final ApplicationContext context = SpringApplication.run(MovieApisApplication.class);

	public ApplicationContext getApplicationContext() {
		return context;
	}

	@PostConstruct
	public void initializeRepositories() {

		LOGGER.trace("initializeRepositories");

		if (_movieRepository == null) {
			LOGGER.trace("_movieRepository is not initialized yet");
			_movieRepository = getApplicationContext().getBean(MovieRepository.class);
		}

		if (_actorRepository == null) {
			LOGGER.trace("_actorRepository is not initialized yet");
			_actorRepository = getApplicationContext().getBean(ActorRepository.class);
		}

	}

	protected void addDefaultHeaders(Response response) {
		response.addHeader("Content-Type", "application/json");
	}

}
