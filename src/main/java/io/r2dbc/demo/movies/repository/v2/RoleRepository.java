package io.r2dbc.demo.movies.repository.v2;

import io.r2dbc.demo.movies.model.v2.Role;
import java.util.Set;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository
    extends ReactiveCrudRepository<Role, Long> {

    @Query("SELECT * FROM roles WHERE movie IN (:movieIds)")
    Flux<Role> findByMovieIn(Set<Long> movieIds);

    @Modifying
    @Query("DELETE FROM roles WHERE movie IN :movieIds")
    Mono<Integer> deleteByMovieIn(Set<Long> movieIds);
}
