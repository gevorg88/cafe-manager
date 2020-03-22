
drop table if exists orders;

drop table if exists products;

drop table if exists products_in_order;

drop table if exists cafe_tables;

drop table if exists users;

create table orders
(
    id       bigint not null auto_increment,
    status   varchar(255) not null,
    table_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table products
(
    id   bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table products_in_order
(
    id        bigint not null auto_increment,
    amount     integer,
    order_id   bigint not null,
    product_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table cafe_tables
(
    id      bigint not null auto_increment,
    name    varchar(255),
    user_id bigint,
    primary key (id)
) engine = InnoDB;

create table users
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

alter table orders
    add constraint UK_8xru7i0vwsvpgl6069t7x8vpa unique (table_id);

alter table products
    add constraint UK_o61fmio5yukmmiqgnxf8pnavn unique (name);

alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

alter table users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

alter table orders
    add constraint orders_to_tables_fk
        foreign key (table_id)
            references tables (id);

alter table products_in_order
    add constraint products_in_order_to_orders_fk
        foreign key (order_id)
            references orders (id);

alter table products_in_order
    add constraint products_in_order_to_products_fk
        foreign key (product_id)
            references products (id);

alter table tables
    add constraint tables_to_users_fk
        foreign key (user_id)
            references users (id);