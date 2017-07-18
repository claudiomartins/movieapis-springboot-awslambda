package io.claudio.movieapis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.claudio.movieapis.domain.Actor;
import io.claudio.movieapis.domain.Movie;
import io.claudio.movieapis.repository.ActorRepository;
import io.claudio.movieapis.repository.MovieRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieApisApplicationTests {

	/** This is in no way meant to be a comprehensive list of test cases, just an example of what can be done and how **/

	@Autowired
	private MovieRepository _movieRepository;

	@Autowired
	private ActorRepository _actorRepository;

	@Test
	public void movieTests() {

		List<Movie> allMovies = _movieRepository.findAll();

		assertNotNull(allMovies);

		Movie firstMovie = _movieRepository.findById(1L);

		assertNotNull(firstMovie);
		assertEquals(firstMovie.title, "Spider-Man: Homecoming");
		assertTrue(firstMovie.actors != null);
		assertTrue(firstMovie.actors.size() > 0);

		Movie newMovie = new Movie();
		newMovie.title = "Test new movie";
		newMovie.releaseYear = 2017;

		newMovie = _movieRepository.save(newMovie);

		assertNotNull(newMovie);
		assertNotNull(newMovie.id);

		_movieRepository.delete(newMovie);

	}

	@Test
	public void actorTests() {

		List<Actor> allActors = _actorRepository.findAll();

		assertNotNull(allActors);

		Actor firstActor = _actorRepository.findById(1L);

		assertNotNull(firstActor);
		assertEquals(firstActor.shownName, "Tom Holland");
		assertTrue(firstActor.birthYear != null);

		Actor newActor = new Actor();
		newActor.shownName = "Test new actor";
		newActor.firstName = "Test";
		newActor.lastName = "new actor";
		newActor.birthYear = 2017;

		newActor = _actorRepository.save(newActor);

		assertNotNull(newActor);
		assertNotNull(newActor.id);

		_actorRepository.delete(newActor);

	}

}
