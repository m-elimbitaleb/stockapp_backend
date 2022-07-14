/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.model;

import com.stockapp.stockapp_backend.model.converter.LongListConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reference;

    private String shipper;

    private Boolean crossDock;

    @Convert(converter = LongListConverter.class)
    private List<Long> items;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @PrePersist
    public void prePersist() {
        if (crossDock == null) crossDock = Boolean.FALSE;
        if (this.getId() == null) this.createdAt = LocalDateTime.now();
    }
}
