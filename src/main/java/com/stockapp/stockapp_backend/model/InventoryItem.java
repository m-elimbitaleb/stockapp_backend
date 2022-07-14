/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    private Boolean crossDock;

    @PrePersist
    public void prePersist() {
        if (crossDock == null) crossDock = Boolean.FALSE;
        if (this.getId() == null) this.createdAt = LocalDateTime.now();
    }
}
