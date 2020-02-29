package io.r2dbc.demo.movies.repository.v2;

import io.r2dbc.demo.movies.model.v2.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieRepository {

    //there is a bug when the repository method names overlap with ReactiveCrudRepository methods
    Flux<Movie> findAllMovies();

    Flux<Movie> saveAllMovies(Iterable<Movie> movies);

    Mono<Movie> save(Movie movie);
}
