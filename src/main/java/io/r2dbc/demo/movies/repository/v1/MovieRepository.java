package io.r2dbc.demo.movies.repository.v1;

import io.r2dbc.demo.movies.model.v1.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {

}
