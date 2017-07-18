package io.claudio.movieapis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.claudio.movieapis.domain.Actor;

public interface ActorRepository extends CrudRepository<Actor, Long> {

	Actor findById(Long id);

	List<Actor> findAll();

	List<Actor> findByFirstName(String firstName);

	List<Actor> findByLastName(String lastName);

	List<Actor> findByShownName(String shownName);

	List<Actor> findByBirthYear(Integer birthYear);

}
