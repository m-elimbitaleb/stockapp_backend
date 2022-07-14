/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {
    private static final String SEPARATOR = "|";

    @Override
    public String convertToDatabaseColumn(List<Long> list) {
        if (CollectionUtils.isEmpty(list)) return "";
        return list.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<Long> convertToEntityAttribute(String str) {
        if (StringUtils.isEmpty(str)) return new ArrayList<>();
        return Arrays.stream(str.split("[" + SEPARATOR + "]")).map(Long::valueOf).collect(Collectors.toList());
    }
}

