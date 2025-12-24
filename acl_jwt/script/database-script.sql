#
drop table users;
#
# drop table users_seq;
# #
# drop table acl_sid;
# #
# drop table acl_class;
# #
# drop table acl_entry;
# #
# drop table acl_object_identity;
/*
------------------------------ MySQL script ------------------------------
 */
drop table users;
drop table user_role;
drop table blogs;
drop table grants;
drop table policies;
drop table resources;

create table users
(
    id           bigint primary key auto_increment,
    username     varchar(50) unique,
    password     varchar(255),
    created_time datetime,
    created_user varchar(255),
    updated_time datetime,
    updated_user varchar(255)
);

create table roles
(
    id   bigint primary key auto_increment,
    code varchar(100) not null,
    name varchar(100) not null
);

create table user_role
(
    id           bigint primary key auto_increment,
    role_id      bigint not null,
    user_id      bigint not null,
    created_time datetime,
    created_user varchar(255),
    updated_time datetime,
    updated_user varchar(255),
    constraint fk_ur_u foreign key (user_id) references users (id),
    constraint fk_ur_r foreign key (role_id) references roles (id)
);

create table blogs
(
    id           bigint primary key auto_increment,
    title        varchar(255),
    content      text,
    owner_id     bigint,
    status       VARCHAR(100),
    type         VARCHAR(100), -- NORMAL, PREMIUM
    created_time datetime,
    created_user varchar(255),
    updated_time datetime,
    updated_user varchar(255)
);

CREATE TABLE resources
(
    id           BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    type         VARCHAR(100), -- ARTICLE, COMMENT, FILE
    owner_id     BIGINT,
    visibility   VARCHAR(100), -- PRIVATE, INTERNAL, PUBLIC
    created_time datetime,
    created_user varchar(255),
    updated_time datetime,
    updated_user varchar(255)
);

CREATE TABLE policies
(
    id                   BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    resource_type        VARCHAR(100),
    action               VARCHAR(20),
    condition_expression TEXT,
    created_time         datetime,
    created_user         varchar(255),
    updated_time         datetime,
    updated_user         varchar(255)
);

CREATE TABLE grants
(
    id              BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    resource_id     BIGINT,
    user_granted_id BIGINT,
    action          VARCHAR(20),
    expires_at      DATETIME,
    created_time    datetime,
    created_user    varchar(255),
    updated_time    datetime,
    updated_user    varchar(255)
);

select *
from roles;