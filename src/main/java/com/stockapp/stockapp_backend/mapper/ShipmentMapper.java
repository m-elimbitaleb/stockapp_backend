package com.stockapp.stockapp_backend.mapper;

import com.stockapp.stockapp_backend.model.Shipment;
import com.stockapp.stockapp_backend.model.dto.ShipmentDTO;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentMapper {
    Shipment toModel(ShipmentDTO dto);

    @Mapping(target = "creatorName", source = "createdBy.fullName")
    ShipmentDTO toDTO(Shipment model);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(ShipmentDTO dto, @MappingTarget Shipment model);
}
