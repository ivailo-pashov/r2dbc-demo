package io.r2dbc.demo.movies.config;

import io.r2dbc.demo.movies.converter.CustomConverter;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

@Configuration
public class CustomR2dbcDataAutoConfiguration extends R2dbcDataAutoConfiguration {

    private final ConnectionFactory factory;
    private final List<CustomConverter<?,?>> customConverters;

    public CustomR2dbcDataAutoConfiguration(ConnectionFactory connectionFactory,
        List<CustomConverter<?, ?>> customConverters) {
        super(connectionFactory);
        this.factory = connectionFactory;
        this.customConverters = customConverters;
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        R2dbcDialect dialect = DialectResolver.getDialect(this.factory);
        List<Object> converters = new ArrayList<>(dialect.getConverters());
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS);
        return new R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder(), converters),
            customConverters);
    }
}
