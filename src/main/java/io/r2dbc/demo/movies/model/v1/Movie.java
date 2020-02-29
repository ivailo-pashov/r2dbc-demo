package io.r2dbc.demo.movies.model.v1;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("movies_1")
public class Movie {

    @Id
    private Long id;
    private String title;
    private String summary;
    private LocalDate releasedAt;
    @Column("roles")
    private Cast cast;
    private Set<String> tags;
}
