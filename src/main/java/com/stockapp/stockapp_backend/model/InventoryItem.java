/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model;

import com.stockapp.stockapp_backend.enumeration.MeasureUnit;
import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.stockapp.stockapp_backend.security.TokenUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Enumerated
    @ToString.Include
    private MeasureUnit measureUnit;

    private String manufacturer;

    private String reference;

    private String description;

    private Double purchasePrice;

    private Double quantity;

    /* Twelve digits barcode */
    private String universalProductCode;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
}
