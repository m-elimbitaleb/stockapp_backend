package com.stockapp.stockapp_backend.mapper;

import com.stockapp.stockapp_backend.AppContext;
import com.stockapp.stockapp_backend.model.User;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.UserDTO;
import com.stockapp.stockapp_backend.model.dto.WarehouseDTO;
import com.stockapp.stockapp_backend.repository.WarehouseRepository;
import org.mapstruct.*;

import java.util.Optional;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public abstract User toModel(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "warehouseName", source = "warehouse.name")
    public abstract UserDTO toDTO(User model);

    @AfterMapping
    public void setWarehouse(UserDTO dto, @MappingTarget User model) {
        WarehouseRepository repository = AppContext.getBean(WarehouseRepository.class);
        Optional<Warehouse> warehouse = repository.findByName(dto.getWarehouseName());
        warehouse.ifPresent(model::setWarehouse);
    }
}
