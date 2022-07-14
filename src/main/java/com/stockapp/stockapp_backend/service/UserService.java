/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.service;

import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.stockapp.stockapp_backend.model.User;
import com.stockapp.stockapp_backend.model.Warehouse;
import com.stockapp.stockapp_backend.model.dto.Ack;
import com.stockapp.stockapp_backend.model.dto.UserDTO;
import com.stockapp.stockapp_backend.repository.UserRepository;
import com.stockapp.stockapp_backend.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new IllegalStateException("Cannot get authentication from SecurityContext");

        String username = auth.getName();
        if (username == null) throw new IllegalStateException("Cannot get connected user from SecurityContext");

        User user = repository.findByUsername(username);
        if (user == null) throw new IllegalStateException("Connected User cannot be found in database");
        return user;
    }


    public Ack checkAnAdminExists() {
        boolean adminExists = repository.existsByRole(UserRole.ADMIN);
        return new Ack(adminExists);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<User> getNonAdminUsers() {
        return repository.findAllByRoleNot(UserRole.ADMIN);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void save(User user) {
        if (isUpdating(user)) {
            updateExistingUser(user);
        } else {
            createNewUser(user);
        }
    }

    @Transactional
    public void initApp(String password) {
        if (checkAnAdminExists().acknowledged) {
            throw new IllegalStateException("App already initialized");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        Warehouse defaultWarehouse = Warehouse.builder()
                .createdAt(LocalDateTime.now())
                .name("Default")
                .location("0,0")
                .build();
        warehouseRepository.save(defaultWarehouse);

        User adminUser = User.builder()
                .username("admin")
                .firstName("Admin")
                .lastName("APP")
                .role(UserRole.ADMIN)
                .email("admin@local")
                .createdAt(LocalDateTime.now())
                .warehouse(Warehouse.builder().id(defaultWarehouse.getId()).build())
                .language("en")
                .password(passwordEncoder.encode(password))
                .activeUser(Boolean.TRUE)
                .phone("00212600000000").build();

        repository.save(adminUser);
    }

    private void updateExistingUser(User user) {
        Optional<User> savedUserOptional = repository.findById(user.getId());
        if (!savedUserOptional.isPresent()) {
            throw new IllegalArgumentException("The user you are trying to update doesn't exist");
        }
        User savedUser = savedUserOptional.get();
        savedUser.setUsername(user.getUsername());
        savedUser.setEmail(user.getEmail());
        savedUser.setPhone(user.getPhone());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setRole(user.getRole());

        repository.save(savedUser);
    }

    private boolean isUpdating(User user) {
        return user.getId() != null;
    }

    private void createNewUser(User user) {
        if (!isExistingUser(user)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActiveUser(true);
            repository.save(user);
        }
    }

    private boolean isExistingUser(User user) {
        Optional<Long> existingUserIdOptional = repository.getUserIdIfExists(user.getUsername(), user.getEmail(), user.getPhone());
        if (existingUserIdOptional.isPresent()) {
            throw new IllegalArgumentException("User with these credentials already exists");
        }
        return false;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void disableUser(Long userId) {
        Optional<User> user = repository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalStateException("Trying to delete a non-existing user");
        }

        User connectedUser = getConnectedUser();
        if (userId.equals(connectedUser.getId())) {
            throw new IllegalStateException("You can't delete yourself");
        }

        if (UserRole.ADMIN.equals(user.get().getRole())) {
            throw new IllegalStateException("You can't delete another ADMIN");
        }
        repository.disableUser(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void enableUser(Long id) {
        repository.enableUser(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updateUserInfo(UserDTO dto) {
        Optional<User> optionalUser = repository.findById(dto.getId());
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("Trying to update a non-existing user");
        }

        User user = optionalUser.get();

        user.setPhone(dto.getPhone());
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());

        repository.save(user);
    }

    public Optional<Long> getConnectedUserWarehouseId() {
        return repository.getUserWarehouseId(getConnectedUser().getId());
    }

    public Optional<String> getConnectedUserWarehouseName() {
        return repository.getUserWarehouseName(getConnectedUser().getId());
    }
}
