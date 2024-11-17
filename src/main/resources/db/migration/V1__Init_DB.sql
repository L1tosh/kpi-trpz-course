create table roles
(
    id   serial primary key,
    name varchar(64) not null unique
);

create table projects
(
    id          bigserial primary key,
    name        varchar(255) not null,
    description text,
    owner_id    bigint       not null
);

create table sprints
(
    id         bigserial primary key,
    start_time timestamp not null,
    end_time   timestamp not null,
    project_id bigint    not null references projects (id)
);

create table events
(
    id          bigserial primary key,
    title       varchar(255) not null,
    description text,

    start_time  timestamp,
    end_time    timestamp,
    create_time timestamp    not null default now(),

    project_id  int references projects (id)
);

create table tasks
(
    id          bigserial primary key,
    title       varchar(100) not null,
    description text,
    complexity  int,

    start_time  timestamp,
    end_time    timestamp,
    create_time timestamp    not null default now(),

    author_id   bigint       not null,
    executor_id bigint,

    sprint_id   int references sprints (id),
    project_id  int references projects (id),

    task_status   varchar(64) not null ,
    task_type   varchar(64) not null

);

create table comments
(
    id        bigserial primary key,
    author_id bigint    not null,
    text      text      not null,
    time      timestamp not null default now()
);

create table files_storage
(
    id          bigserial primary key,
    file_name   varchar(255) not null,
    file_type   varchar(255) not null,
    file_size   bigint       not null,
    data        bytea        not null,
    upload_time timestamp    not null
);

create table item_comments
(
    item_id    bigint references tasks (id),
    comment_id bigint references comments (id),
    primary key (item_id, comment_id)
);

create table item_files
(
    item_id bigint references tasks (id),
    file_id bigint references files_storage (id),
    primary key (item_id, file_id)
);

create table user_project_role
(
    user_id    bigint not null,
    project_id bigint not null references projects (id),
    role_id    int    not null references roles (id),

    primary key (user_id, role_id, project_id)
);


create table user_sprint
(
    sprint_id bigint not null references sprints (id),
    user_id   bigint not null,

    primary key (user_id, sprint_id)
);
