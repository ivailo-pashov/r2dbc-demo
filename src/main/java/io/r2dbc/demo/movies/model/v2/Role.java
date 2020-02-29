package io.r2dbc.demo.movies.model.v2;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("roles")
public class Role {

    @Id
    private Long id;
    private long movie;
    private long actor;
    private String name;
}
