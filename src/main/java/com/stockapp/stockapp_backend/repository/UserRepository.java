/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.repository;

import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.stockapp.stockapp_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u where u.activeUser = true and u.role = ?1 and " +
            "(u.busy is null or u.busy = false)")
    List<User> findByRoleAndNotBusy(UserRole role);

    @Transactional
    @Modifying
    @Query("update User u set u.busy = ?2 where u.id = ?1")
    void updateCouriserBusyStatus(Long id, boolean busy);

    @Transactional
    @Modifying
    @Query("update User u set u.activeUser = false where u.id = ?1")
    void disableUser(Long id);

    List<User> findAllByRoleNot(UserRole role);

    @Query("select u.id from User u where u.username = ?1 or u.email = ?2 or u.phone = ?3")
    Optional<Long> getUserIdIfExists(String username, String email, String phone);

    Optional<User> findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("update User u set u.activeUser = true where u.id = ?1")
    void enableUser(Long id);
}
