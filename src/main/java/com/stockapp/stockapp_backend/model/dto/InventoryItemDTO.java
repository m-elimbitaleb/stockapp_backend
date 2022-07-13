/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model.dto;

import com.stockapp.stockapp_backend.enumeration.MeasureUnit;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDTO {

    private Long id;

    private String name;

    private MeasureUnit measureUnit;

    private String manufacturer;

    private String reference;

    private String description;

    private Double purchasePrice;

    private Double quantity;

    /* Twelve digits barcode */
    private String universalProductCode;

    private LocalDateTime createdAt;

    private String warehouseName;

    private String creatorName;
}
