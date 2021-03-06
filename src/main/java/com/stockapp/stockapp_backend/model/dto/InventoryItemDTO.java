/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDTO {

    private Long id;

    private String name;

    private String manufacturer;

    private String reference;

    private String description;

    private Double purchasePrice;

    /* Twelve digits barcode */
    private String universalProductCode;

    private LocalDateTime createdAt;

    private LocalDateTime storageDate;

    private String warehouseName;

    private String creatorName;

    private Boolean crossDock;
}
