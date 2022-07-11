/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.eventbus.events;

import com.stockapp.stockapp_backend.model.User;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TestEvent extends GuavaEvent<User> {
    public TestEvent(User connectedUser, User test) {
        super(connectedUser, test);
    }
}
