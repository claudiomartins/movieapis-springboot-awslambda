INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (1, 'Spider-Man: Homecoming', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (2, 'Despicable Me 3', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (3, 'Baby Driver', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (4, 'Wonder Woman', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (5, 'Transformers: The Last Knight', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (6, 'Cars 3', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (7, 'The House', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie (id, title, release_year, created_at, modified_at) VALUES (8, 'The Big Sick', 2017, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO actor (id, first_name, last_name, shown_name, birth_year, created_at, modified_at) VALUES (1, 'Tom', 'Holland', 'Tom Holland', 1989, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO actor (id, first_name, last_name, shown_name, birth_year, created_at, modified_at) VALUES (2, 'Michael', 'Keaton', 'Michael Keaton', 1989, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie_actors (id, movie_id, actor_id, created_at, modified_at) VALUES (1, 1, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie_actors (id, movie_id, actor_id, created_at, modified_at) VALUES (2, 1, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO actor (id, first_name, last_name, shown_name, birth_year, created_at, modified_at) VALUES (3, 'Steve', 'Carell', 'Steve Carell', 1989, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO actor (id, first_name, last_name, shown_name, birth_year, created_at, modified_at) VALUES (4, 'Kristen', 'Wiig', 'Kristen Wiig', 1989, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie_actors (id, movie_id, actor_id, created_at, modified_at) VALUES (3, 2, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO movie_actors (id, movie_id, actor_id, created_at, modified_at) VALUES (4, 2, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());