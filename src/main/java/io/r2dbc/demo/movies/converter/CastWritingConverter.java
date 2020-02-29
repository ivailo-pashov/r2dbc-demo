package io.r2dbc.demo.movies.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.demo.movies.model.v1.Cast;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@WritingConverter
public class CastWritingConverter implements CustomConverter<Cast, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convert(Cast source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
