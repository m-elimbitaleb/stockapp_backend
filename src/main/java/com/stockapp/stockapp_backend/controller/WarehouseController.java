/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.controller;

import com.stockapp.stockapp_backend.mapper.WarehouseMapper;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.Ack;
import com.stockapp.stockapp_backend.model.dto.WarehouseDTO;
import com.stockapp.stockapp_backend.security.TokenUser;
import com.stockapp.stockapp_backend.service.UserService;
import com.stockapp.stockapp_backend.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService service;
    @Autowired
    private WarehouseMapper mapper;

    @PostMapping
    public Ack add(@RequestBody WarehouseDTO warehouse) {
        service.save(mapper.toModel(warehouse));
        return new Ack();
    }

    @PutMapping
    public Ack update(@RequestBody WarehouseDTO warehouse) {
        service.update(warehouse);
        return new Ack();
    }


    @GetMapping
    public List<WarehouseDTO> getAll() {
        return service.getAll().stream().map(it -> mapper.toDTO(it)).collect(Collectors.toList());
    }


}
