
create table if not exists orders
(
    id       bigint not null auto_increment,
    status   varchar(255) not null,
    table_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table if not exists products
(
    id   bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table if not exists products_in_order
(
    id        bigint not null auto_increment,
    amount     integer,
    order_id   bigint not null,
    product_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table if not exists cafe_tables
(
    id      bigint not null auto_increment,
    name    varchar(255),
    user_id bigint,
    primary key (id)
) engine = InnoDB;

create table if not exists users
(
    id         bigint       not null auto_increment,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role       varchar(255) not null,
    username   varchar(255),
    primary key (id)
) engine = InnoDB;