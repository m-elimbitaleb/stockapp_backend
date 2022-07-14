/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.controller;

import com.stockapp.stockapp_backend.mapper.ShipmentMapper;
import com.stockapp.stockapp_backend.model.dto.Ack;
import com.stockapp.stockapp_backend.model.dto.ShipmentDTO;
import com.stockapp.stockapp_backend.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService service;
    @Autowired
    private ShipmentMapper mapper;

    @PostMapping
    public Ack add(@RequestBody ShipmentDTO shipment) {
        service.save(mapper.toModel(shipment));
        return new Ack();
    }

    @PutMapping
    public Ack update(@RequestBody ShipmentDTO shipment) {
        service.update(shipment);
        return new Ack();
    }


    @GetMapping
    public List<ShipmentDTO> getAll() {
        return service.getAll().stream().map(it -> mapper.toDTO(it)).collect(Collectors.toList());
    }

    @GetMapping("/item-in-shipments")
    public List<Long> getItemsInShipments() {
        return service.getItemsInShipments();
    }


}
