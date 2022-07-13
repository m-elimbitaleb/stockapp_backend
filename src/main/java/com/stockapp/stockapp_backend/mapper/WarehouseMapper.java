package com.stockapp.stockapp_backend.mapper;

import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.WarehouseDTO;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WarehouseMapper {
    Warehouse toModel(WarehouseDTO dto);

    WarehouseDTO toDTO(Warehouse model);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(WarehouseDTO dto, @MappingTarget Warehouse model);
}
