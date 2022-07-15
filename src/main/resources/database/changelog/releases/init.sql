--liquibase formatted sql

-- Copyright (c) 2022. stockapp.
-- Proprietary source code; any copy or modification is prohibited.
--
-- @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
--

--changeset init:1
CREATE SEQUENCE hibernate_sequence
    START WITH 100;

create table app_user
(
    id           bigint not null,
    active_user  boolean,
    birth_date   timestamp,
    created_at   timestamp,
    email        varchar(255),
    first_name   varchar(255),
    language     varchar(2),
    last_name    varchar(255),
    password     varchar(510),
    phone        varchar(24),
    reset_token  varchar(510),
    role         integer,
    username     varchar(255),
    warehouse_id bigint,
    primary key (id)
);

create table inventory_item
(
    id                     bigint not null,
    created_at             timestamp,
    cross_dock             boolean,
    storage_date           timestamp,
    manufacturer           varchar(255),
    name                   varchar(255),
    purchase_price         float,
    reference              varchar(255),
    description            varchar(510),
    universal_product_code varchar(12),
    warehouse_id           bigint,
    created_by_id          bigint,
    primary key (id)
);

create table shipment
(
    id            bigint not null,
    created_at    timestamp,
    cross_dock    boolean,
    reference     varchar(255),
    shipper       varchar(255),
    items         TEXT,
    created_by_id bigint,
    warehouse_id  bigint,
    primary key (id)
);

create table warehouse
(
    id         bigint not null,
    created_at timestamp,
    location   varchar(255),
    name       varchar(255),
    primary key (id)
);

alter table app_user
    add constraint fk_user_warehouse_id
        foreign key (warehouse_id)
            references warehouse;

alter table inventory_item
    add constraint fk_inventory_item_warehouse_id
        foreign key (warehouse_id)
            references warehouse;

alter table shipment
    add constraint fk_shipment_warehouse_id
        foreign key (warehouse_id)
            references warehouse;

alter table inventory_item
    add constraint fk_inventory_item_creator_id
        foreign key (created_by_id)
            references app_user;

alter table shipment
    add constraint fk_shipment_creator_id
        foreign key (created_by_id)
            references app_user;

