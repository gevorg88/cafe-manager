create table cafe_table (
                            id bigint not null auto_increment,
                            identificator varchar(255),
                            primary key (id)
) engine=InnoDB;

create table hibernate_sequence (
    next_val bigint
) engine=InnoDB;

insert into hibernate_sequence values ( 1 );

insert into hibernate_sequence values ( 1 );

create table user_role (
                           user_id bigint not null,
                           role varchar(255)
) engine=InnoDB;

create table users (
                       id bigint not null auto_increment,
                       email varchar(255),
                       first_name varchar(255),
                       last_name varchar(255),
                       password varchar(255),
                       primary key (id)
) engine=InnoDB;

alter table user_role
    add constraint FK_user_to_user_role
        foreign key (user_id)
            references users (id);

use cafe_manager;
INSERT INTO users (email, first_name, last_name, password) VALUES ('admin@admin.mail', 'admin', 'admin','admin');
INSERT INTO user_role (user_id, role) VALUES (1, 'MANAGER');