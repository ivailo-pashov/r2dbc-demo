package io.r2dbc.demo.movies.service;

import io.r2dbc.demo.movies.model.v2.Movie;
import io.r2dbc.demo.movies.repository.v2.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Flux<Movie> getAll() {
        return repository.findAllMovies();
    }

    private final TransactionalOperator transactionalOperator;

    public Mono<Movie> insert(Movie movie) {
        return repository.save(movie)
            .as(transactionalOperator::transactional);
    }
}
