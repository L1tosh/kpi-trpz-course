create table roles
(
    id   serial primary key,
    name varchar(64) not null unique
);

create table users
(
    id         bigserial primary key,
    name       varchar(64)  not null,
    surname    varchar(64),
    email      varchar(128) not null unique,
    password   varchar(32)  not null check ( length(password) >= 8 ),
    sprint_id  int references sprints (id),
    project_id int
);

create table user_role
(
    user_id bigint not null references users (id),
    role_id int    not null references roles (id),

    primary key (user_id, role_id)
);




