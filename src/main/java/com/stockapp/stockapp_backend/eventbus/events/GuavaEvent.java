/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.eventbus.events;

import com.stockapp.stockapp_backend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public abstract class GuavaEvent<T> {
    protected String eventName = getClass().getName();
    protected User connectedUser;
    protected T data;

    public GuavaEvent(User connectedUser, T data) {
        this.connectedUser = connectedUser;
        this.data = data;
    }
}
