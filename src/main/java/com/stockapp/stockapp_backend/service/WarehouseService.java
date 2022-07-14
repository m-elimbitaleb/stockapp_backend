/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.service;

import com.stockapp.stockapp_backend.mapper.WarehouseMapper;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.WarehouseDTO;
import com.stockapp.stockapp_backend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository repository;
    @Autowired
    private WarehouseMapper mapper;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<Warehouse> getAll() {
        return repository.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Warehouse save(Warehouse warehouse) {
        return repository.save(warehouse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void update(WarehouseDTO warehouse) {
        Optional<Warehouse> optionalExistingWarehouse = repository.findById(warehouse.getId());
        if (optionalExistingWarehouse.isEmpty()) {
            throw new IllegalArgumentException("Given warehouse doesn't exist");
        }
        Warehouse existingWarehouse = optionalExistingWarehouse.get();

        mapper.patch(warehouse, existingWarehouse);

        repository.save(existingWarehouse);
    }

}
