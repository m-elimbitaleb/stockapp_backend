/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.repository;

import com.stockapp.stockapp_backend.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("select s.warehouse.id from Shipment s where s.id = ?1")
    Optional<Long> getWarehouseIdByShipmentId(Long shipmentId);

    @Query("select s from Shipment s where s.warehouse.id = ?1")
    List<Shipment> findByWarehouseId(Long warehouseId);

    @Query("select s.items from Shipment s where s.warehouse.id = ?1")
    List<List<Long>> getItemsInShipmentsForWarehouse(Long warehouseId);
}
