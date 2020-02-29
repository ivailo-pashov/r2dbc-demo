package io.r2dbc.demo.movies.repository.v2;

import io.r2dbc.demo.movies.model.v2.Cast;
import io.r2dbc.demo.movies.model.v2.Movie;
import io.r2dbc.demo.movies.model.v2.Role;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {

    private final MovieRootRepository rootRepository;
    private final RoleRepository roleRepository;

    @Override
    public Flux<Movie> findAllMovies() {
        return findParallel();
    }

    @Override
    public Mono<Movie> save(Movie movie) {
        return saveAllMovies(Collections.singleton(movie)).last();
    }

    @Override
    public Flux<Movie> saveAllMovies(Iterable<Movie> movies) {
        Set<Long> existingMovieIds = StreamSupport.stream(movies.spliterator(), false)
            .map(Movie::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        return (existingMovieIds.isEmpty() ? deleteRoles(existingMovieIds) : Mono.just(0))
            .thenMany(rootRepository.saveAll(movies))
            .map(movie -> {
                if (movie.getCast() != null && movie.getCast().getRoles() != null) {
                    movie.getCast().getRoles().forEach(role -> {
                        role.setId(null);
                        role.setMovie(movie.getId());
                    });
                }
                return movie;
            })
            .collect(Collectors.toList())
            .flatMap(savedMovies -> roleRepository.saveAll(getAllRoles(savedMovies))
                .collect(Collectors.toList())
                .map(roles -> addCasts(savedMovies, roles))
            )
            .flatMapIterable(Function.identity());
    }

    private Set<Role> getAllRoles(List<Movie> savedMovies) {
        return savedMovies.stream()
            .filter(movie -> movie.getCast() != null)
            .flatMap(movie -> movie.getCast().getRoles().stream())
            .collect(Collectors.toSet());
    }

    private Flux<Movie> findAllNaive() {
        return rootRepository.findAll()
            .flatMap(movie -> findRoles(Collections.singleton(movie.getId()))
                .collect(Collectors.toSet())
                .map(roles -> {
                    movie.setCast(new Cast(roles));
                    return movie;
                })
            );
    }

    private Flux<Movie> findParallel() {
        return rootRepository.findAll()
            .collect(Collectors.toList())
            .flatMap(movies -> findRoles(
                movies.stream().map(Movie::getId).collect(Collectors.toSet())
            )
                .collect(Collectors.toList())
                .map(roles -> addCasts(movies, roles))).flatMapIterable(Function.identity());
    }

    private List<Movie> addCasts(List<Movie> movies, List<Role> roles) {
        Map<Long, Cast> castsByMovieId = roles.stream()
            .collect(Collectors.groupingBy(Role::getMovie,
                Collectors.collectingAndThen(Collectors.toSet(), Cast::new)));
        movies.forEach(movie -> movie.setCast(castsByMovieId.get(movie.getId())));
        return movies;
    }

    private Flux<Role> findRoles(Set<Long> movieIds) {
        if (movieIds.isEmpty()) {
            return Flux.empty();
        }
        return roleRepository.findByMovieIn(movieIds);
    }

    private Mono<Integer> deleteRoles(Set<Long> movieIds) {
        if (movieIds.isEmpty()) {
            return Mono.just(0);
        }
        return roleRepository.deleteByMovieIn(movieIds);
    }
}
