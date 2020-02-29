package io.r2dbc.demo.movies.converter;

import org.springframework.core.convert.converter.Converter;

public interface CustomConverter<T, V> extends Converter<T, V> {

}
