package io.r2dbc.demo.movies.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.demo.movies.model.v1.Cast;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ReadingConverter
public class CastReadingConverter implements CustomConverter<String, Cast> {

    private final ObjectMapper objectMapper;

    @Override
    public Cast convert(String source) {
        try {
            return objectMapper.readValue(source, Cast.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
