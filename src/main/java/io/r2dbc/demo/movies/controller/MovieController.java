package io.r2dbc.demo.movies.controller;

import io.r2dbc.demo.movies.model.v2.Movie;
import io.r2dbc.demo.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public Flux<Movie> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Mono<Movie> insert(@RequestBody Movie movie) {
        return service.insert(movie);
    }
}
