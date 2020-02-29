package io.r2dbc.demo.movies.model.v2;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cast {

    private Set<Role> roles;
}
