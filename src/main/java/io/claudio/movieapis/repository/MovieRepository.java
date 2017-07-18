package io.claudio.movieapis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.claudio.movieapis.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

	List<Movie> findAll();

	Movie findById(Long id);

	List<Movie> findByTitle(String title);

	List<Movie> findByActorsShownName(String shownName);

	List<Movie> findByTitleAndActorsShownName(String title, String shownName);

}
