package io.r2dbc.demo.movies;

import io.r2dbc.demo.movies.model.v2.Cast;
import io.r2dbc.demo.movies.model.v2.Movie;
import io.r2dbc.demo.movies.model.v2.Role;
import io.r2dbc.demo.movies.repository.v2.MovieRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final io.r2dbc.demo.movies.repository.v1.MovieRepository movie1Repository;
    private final MovieRepository movie2Repository;

    public void run(ApplicationArguments args) {

        updateAndFetchAllMovie1();
        updateAndFetchAllMovie2();
    }

    private void updateAndFetchAllMovie1() {
        io.r2dbc.demo.movies.model.v1.Movie movie = new io.r2dbc.demo.movies.model.v1.Movie();
        movie.setTitle("v1");
        movie.setSummary("summary 1");
        movie.setReleasedAt(LocalDate.now());
        movie.setTags(Collections.singleton("bestever"));
        io.r2dbc.demo.movies.model.v1.Cast cast = new io.r2dbc.demo.movies.model.v1.Cast();
        io.r2dbc.demo.movies.model.v1.Role role = new io.r2dbc.demo.movies.model.v1.Role();
        role.setActor(1);
        role.setName("John");
        cast.setRoles(Collections.singleton(role));
        movie.setCast(cast);
        movie1Repository.save(movie).block();
        System.out.println(movie1Repository.findById(1L).block());
        System.out.println(movie1Repository.findAll().collect(Collectors.toList()).block());
    }

    private void updateAndFetchAllMovie2() {
        Movie movie = new Movie();
        movie.setTitle("1");
        movie.setSummary("summary 1");
        movie.setReleasedAt(LocalDate.now());
        movie.setTags(Collections.singleton("bestever"));
        Cast cast = new Cast();
        Role role = new Role();
        role.setActor(1);
        role.setName("John");
        cast.setRoles(Collections.singleton(role));
        movie.setCast(cast);
        movie2Repository.saveAllMovies(Collections.singleton(movie)).collect(Collectors.toList()).block();
        System.out.println(movie2Repository.findAllMovies().collect(Collectors.toList()).block());
    }
}
