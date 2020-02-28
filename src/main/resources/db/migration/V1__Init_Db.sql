drop table if exists orders;

drop table if exists products;

drop table if exists products_in_order;

drop table if exists tables;

drop table if exists users;

create table orders
(
    id       bigint not null auto_increment,
    status   integer,
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
    id         bigint not null,
    amount     integer,
    status     integer,
    order_id   bigint not null auto_increment,
    product_id bigint not null,
    primary key (order_id, product_id)
) engine = InnoDB;

create table tables
(
    id      bigint not null auto_increment,
    name    varchar(255),
    user_id bigint,
    primary key (id)
) engine = InnoDB;

create table users
(
    id         bigint not null auto_increment,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role       integer,
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
    add constraint FKrkhrp1dape261t3x3spj7l5ny
        foreign key (table_id)
            references tables (id);

alter table products_in_order
    add constraint FK52yge8hl6teqf4n4bkul5k5dm
        foreign key (order_id)
            references orders (id);

alter table products_in_order
    add constraint FK60tyehiqoydncbdl4sy6b3tic
        foreign key (product_id)
            references products (id);

alter table tables
    add constraint FKa46mvfaviexapys9mope0pwyj
        foreign key (user_id)
            references users (id);
INSERT INTO users (email,
                   first_name,
                   last_name,
                   password,
                   role,
                   username)
VALUES ('admin@gmail.com',
        'admin',
        'admin',
        '$2a$12$avo4eJ8mSfn1X.rlzQyzquDMBF7kRVaum5n7ANpCupsY2UyhCvc7q',
        1, 'admin')