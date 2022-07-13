/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.controller;

import com.stockapp.stockapp_backend.mapper.InventoryItemMapper;
import com.stockapp.stockapp_backend.model.dto.Ack;
import com.stockapp.stockapp_backend.model.dto.InventoryItemDTO;
import com.stockapp.stockapp_backend.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryItemController {

    @Autowired
    private InventoryItemService service;
    @Autowired
    private InventoryItemMapper mapper;

    @PostMapping
    public Ack add(@RequestBody InventoryItemDTO warehouse) {
        if (warehouse.getId() != null) {
            throw new IllegalStateException("Item already exists, try update API instead");
        }
        service.save(mapper.toModel(warehouse));
        return new Ack();
    }

    @PutMapping
    public Ack update(@RequestBody InventoryItemDTO warehouse) {
        service.update(warehouse);
        return new Ack();
    }


    @GetMapping
    public List<InventoryItemDTO> getAll() {
        return service.getAll().stream().map(it -> mapper.toDTO(it)).collect(Collectors.toList());
    }


}
