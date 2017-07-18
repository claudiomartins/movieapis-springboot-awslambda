package io.claudio.movieapis.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Actor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column(name = "first_name")
	public String firstName;

	@Column(name = "last_name")
	public String lastName;

	@Column(name = "shown_name")
	public String shownName;

	@Column(name = "birth_year")
	public Integer birthYear;

	@Column(name = "created_at")
	public Timestamp createdAt;

	@Column(name = "modified_at")
	public Timestamp modifiedAt;

	public Actor() {
		super();
	}

	public Actor(String firstName, String lastName, String shownName, Integer birthYear) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.shownName = shownName;
		this.birthYear = birthYear;
	}

}
