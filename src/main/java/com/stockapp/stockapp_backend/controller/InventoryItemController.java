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
import org.springframework.data.repository.query.Param;
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
    public Ack add(@RequestBody InventoryItemDTO item) {
        if (item.getId() != null) {
            throw new IllegalStateException("Item already exists, try update API instead");
        }
        service.save(mapper.toModel(item));
        return new Ack();
    }

    @PostMapping("pick-to-storage")
    public Ack pickToStorage(@RequestBody Long itemId,
                             @RequestParam("remove") Integer removeFromStorage) {
        service.pickToStorage(itemId, removeFromStorage == 1);
        return new Ack();
    }

    @PostMapping("pick-cross-dock")
    public Ack pickForCrossDock(@RequestBody Long itemId,
                                @RequestParam("remove") Integer removeFromCrossDock) {
        service.pickForCrossDock(itemId, removeFromCrossDock == 1);
        return new Ack();
    }

    @PutMapping
    public Ack update(@RequestBody InventoryItemDTO item) {
        service.update(item);
        return new Ack();
    }


    @GetMapping
    public List<InventoryItemDTO> getAll() {
        return service.getAll().stream().map(it -> mapper.toDTO(it)).collect(Collectors.toList());
    }


}
