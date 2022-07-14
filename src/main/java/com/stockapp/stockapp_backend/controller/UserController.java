/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.controller;

import com.stockapp.stockapp_backend.mapper.UserMapper;
import com.stockapp.stockapp_backend.model.dto.Ack;
import com.stockapp.stockapp_backend.model.dto.UserDTO;
import com.stockapp.stockapp_backend.security.TokenUser;
import com.stockapp.stockapp_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserMapper mapper;

    @PostMapping
    public Ack add(@RequestBody UserDTO user) {
        service.save(mapper.toModel(user));
        return new Ack();
    }


    @PutMapping("/activate/{id}")
    public Ack activate(@PathVariable(value = "id") Long id) {
        service.enableUser(id);
        return new Ack();
    }

    @PutMapping("/update-info")
    public Ack update(@RequestBody UserDTO user) {
        service.updateUserInfo(user);
        return new Ack();
    }


    @DeleteMapping("/{id}")
    public Ack disableUser(@PathVariable(value = "id") Long id) {
        service.disableUser(id);
        return new Ack();
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getNonAdminUsers()
                .stream().map(it -> mapper.toDTO(it)).collect(Collectors.toList());
    }


}
