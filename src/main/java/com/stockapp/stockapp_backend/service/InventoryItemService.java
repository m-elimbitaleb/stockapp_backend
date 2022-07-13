/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.service;

import com.stockapp.stockapp_backend.mapper.InventoryItemMapper;
import com.stockapp.stockapp_backend.model.InventoryItem;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.InventoryItemDTO;
import com.stockapp.stockapp_backend.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {

    @Autowired
    private InventoryItemRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private InventoryItemMapper mapper;

    @PreAuthorize("hasAnyAuthority('USER')")
    public List<InventoryItem> getAll() {
        Optional<Long> warehouseId = userService.getConnectedUserWarehouseId();
        if(warehouseId.isEmpty()) {
            throw new IllegalStateException("User doesn' belong to a warehouse");
        }
        return repository.findByWarehouseId(warehouseId.get());
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public InventoryItem save(InventoryItem inventoryItem) {
        setWarehouse(inventoryItem);
        setCreator(inventoryItem);
        return repository.save(inventoryItem);
    }

    private void setCreator(InventoryItem item) {
        item.setCreatedBy(userService.getConnectedUser());
    }

    private void setWarehouse(InventoryItem item) {

        Optional<Long> connectedUserWarehouseId = userService.getConnectedUserWarehouseId();
        if(connectedUserWarehouseId.isEmpty()) {
            throw new IllegalStateException("Connected user is not connected to a warehouse");
        }
        item.setWarehouse(Warehouse.builder().id(connectedUserWarehouseId.get()).build());
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public void update(InventoryItemDTO inventoryItem) {
        Optional<InventoryItem> optionalExistingInventoryItem = repository.findById(inventoryItem.getId());
        if (optionalExistingInventoryItem.isEmpty()) {
            throw new IllegalArgumentException("Given inventory item doesn't exist");
        }

        assertUserIsOperatorOfInventoryItemWarehouse(inventoryItem.getId());

        InventoryItem existingInventoryItem = optionalExistingInventoryItem.get();

        mapper.patch(inventoryItem, existingInventoryItem);

        repository.save(existingInventoryItem);
    }

    private void assertUserIsOperatorOfInventoryItemWarehouse(Long inventoryItemId) {
        Optional<Long> inventoryItemWarehouseId = repository.getWarehouseIdByInventoryItemId(inventoryItemId);

        if (inventoryItemWarehouseId.isEmpty()) {
            throw new IllegalStateException("Inventory item doesn't belong to a warehouse");
        }
        Optional<Long> connectedUserWarehouseId = userService.getConnectedUserWarehouseId();
        if (connectedUserWarehouseId.isEmpty() ||
                !connectedUserWarehouseId.get().equals(inventoryItemWarehouseId.get())) {
            throw new IllegalStateException("Connected user is not this warehouse operator");
        }
    }
}
