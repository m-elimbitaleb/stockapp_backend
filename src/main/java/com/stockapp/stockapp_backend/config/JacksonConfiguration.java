/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {
    public static ObjectMapper staticObjectMapper;

    static {
        staticObjectMapper = new ObjectMapper();
        staticObjectMapper.registerModule(new JavaTimeModule());
        staticObjectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        staticObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        staticObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        staticObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        staticObjectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    @Primary
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(javaTimeModule());
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        return mapper;
    }

}
