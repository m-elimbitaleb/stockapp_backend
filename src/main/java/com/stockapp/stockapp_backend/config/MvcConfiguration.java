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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${stockapp.upload-directory}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")

                .addResourceLocations("file:" + uploadDirectory);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper mapper = jsonMessageConverter.getObjectMapper();
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapper.registerModule(new JavaTimeModule());
                mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
                break;
            }
        }
    }
}
