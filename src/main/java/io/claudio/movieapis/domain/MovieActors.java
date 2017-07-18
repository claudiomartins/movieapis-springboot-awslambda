package io.claudio.movieapis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class MovieActors {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	public Movie movie;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actor_id")
	public Actor actor;

	@Column(name = "character_name")
	public String characterName;

	@Column(name = "created_at")
	public Timestamp createAt;

	@Column(name = "modified_at")
	public Timestamp modifiedAt;

	public MovieActors() {
		super();
	}

	public MovieActors(Movie movie, Actor actor, String characterName) {
		super();
		this.movie = movie;
		this.actor = actor;
		this.characterName = characterName;
	}

}
