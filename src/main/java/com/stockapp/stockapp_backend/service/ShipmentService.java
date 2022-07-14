/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.service;

import com.stockapp.stockapp_backend.mapper.ShipmentMapper;
import com.stockapp.stockapp_backend.model.Shipment;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.ShipmentDTO;
import com.stockapp.stockapp_backend.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private ShipmentMapper mapper;

    @PreAuthorize("hasAnyAuthority('USER')")
    public List<Shipment> getAll() {
        Optional<Long> userWarehouseId = userService.getConnectedUserWarehouseId();
        return repository.findByWarehouseId(userWarehouseId.orElse(null));
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public Shipment save(Shipment shipment) {
        shipment.setCreatedBy(userService.getConnectedUser());
        setWarehouse(shipment);
        return repository.save(shipment);
    }

    private void setWarehouse(Shipment item) {

        Optional<Long> connectedUserWarehouseId = userService.getConnectedUserWarehouseId();
        if (connectedUserWarehouseId.isEmpty()) {
            throw new IllegalStateException("Connected user is not connected to a warehouse");
        }
        item.setWarehouse(Warehouse.builder().id(connectedUserWarehouseId.get()).build());
    }


    @PreAuthorize("hasAnyAuthority('USER')")
    public List<Long> getItemsInShipments() {
        Optional<Long> userWarehouseId = userService.getConnectedUserWarehouseId();
        return repository.getItemsInShipmentsForWarehouse(userWarehouseId.orElse(null)).stream()
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public void update(ShipmentDTO shipment) {
        Optional<Shipment> optionalExistingShipment = repository.findById(shipment.getId());
        if (optionalExistingShipment.isEmpty()) {
            throw new IllegalArgumentException("Given shipment doesn't exist");
        }

        Shipment existingShipment = optionalExistingShipment.get();
        assertUserIsOperatorOfShipmentWarehouse(existingShipment.getId());

        mapper.patch(shipment, existingShipment);

        repository.save(existingShipment);
    }

    private void assertUserIsOperatorOfShipmentWarehouse(Long shipmentId) {
        Optional<Long> shipmentIdWarehouseId = repository.getWarehouseIdByShipmentId(shipmentId);

        if (shipmentIdWarehouseId.isEmpty()) {
            throw new IllegalStateException("Shipment doesn't belong to a warehouse");
        }
        Optional<Long> connectedUserWarehouseId = userService.getConnectedUserWarehouseId();
        if (connectedUserWarehouseId.isEmpty() ||
                !connectedUserWarehouseId.get().equals(shipmentIdWarehouseId.get())) {
            throw new IllegalStateException("Connected user is not the shipment warehouse operator");
        }

    }

}
