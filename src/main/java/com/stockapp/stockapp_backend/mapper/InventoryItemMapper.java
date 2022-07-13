package com.stockapp.stockapp_backend.mapper;

import com.stockapp.stockapp_backend.model.InventoryItem;
import com.stockapp.stockapp_backend.model.dto.InventoryItemDTO;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryItemMapper {
    InventoryItem toModel(InventoryItemDTO dto);

    @Mapping(target = "warehouseName", source = "warehouse.name")
    @Mapping(target = "creatorName", source = "createdBy.fullName")
    InventoryItemDTO toDTO(InventoryItem model);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(InventoryItemDTO dto, @MappingTarget InventoryItem model);
}
