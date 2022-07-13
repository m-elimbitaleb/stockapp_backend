--liquibase formatted sql

-- Copyright (c) 2022. stockapp.
-- Proprietary source code; any copy or modification is prohibited.
--
-- @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
--

--changeset init-data:1

--comment insert default warehouse
INSERT INTO warehouse (id, location, name)
VALUES (1, '0,0', 'Default');

--comment insert default admin

INSERT INTO app_user (ID,
                      username,
                      PASSWORD,
                      email,
                      role,
                      first_name,
                      last_name,
                      active_user,
                      created_at,
                      language, warehouse_id)
VALUES (1, 'admin',
        '$2a$10$eUTsk9wSH1IoAEK8p6s/ROc93kekBsJeD9a96udAeH0gDUm..SeoK',
        'admin@stockapp.com',
        1,
        'Admin',
        'stockapp',
        TRUE,
        NOW(),
        'en', 1);

--comment insert default user

INSERT INTO app_user (ID,
                      username,
                      PASSWORD,
                      email,
                      role,
                      first_name,
                      last_name,
                      active_user,
                      created_at,
                      language, warehouse_id)
VALUES (2, 'user',
        '$2a$10$eUTsk9wSH1IoAEK8p6s/ROc93kekBsJeD9a96udAeH0gDUm..SeoK',
        'user@stockapp.com',
        0,
        'User',
        'stockapp',
        TRUE,
        NOW(),
        'en', 1);

