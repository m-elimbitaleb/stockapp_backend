/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.repository;

import com.stockapp.stockapp_backend.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    @Query("select i.warehouse.id from InventoryItem i where i.id = ?1")
    Optional<Long> getWarehouseIdByInventoryItemId(Long inventoryItemId);

    @Query("select i from InventoryItem i where i.warehouse.id = ?1")
    List<InventoryItem> findByWarehouseId(Long aLong);
}
