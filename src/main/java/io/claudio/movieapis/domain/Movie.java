package io.claudio.movieapis.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column(name = "title")
	public String title;

	@Column(name = "release_year")
	public Integer releaseYear;

	@Column(name = "created_at")
	public Timestamp createdAt;

	@Column(name = "modified_at")
	public Timestamp modifiedAt;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MovieActors", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
	public List<Actor> actors;

	public Movie() {
		super();
	}

	public Movie(String title, Integer releaseYear) {
		super();
		this.title = title;
		this.releaseYear = releaseYear;
	}

}
