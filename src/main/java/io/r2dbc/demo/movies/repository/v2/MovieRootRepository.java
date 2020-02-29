package io.r2dbc.demo.movies.repository.v2;

import io.r2dbc.demo.movies.model.v2.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRootRepository extends ReactiveCrudRepository<Movie, Long> {

}
