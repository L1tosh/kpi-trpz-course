-- Вставка данных в таблицу roles
INSERT INTO roles (name)
VALUES ('Administrator'),
       ('Manager'),
       ('Developer'),
       ('Tester'),
       ('Viewer');

insert into users(name, surname, email, password)
values ('Alice', 'Smith', 'alice.smith@example.com', 'password1234');

-- Вставка данных в таблицу users
INSERT INTO users (name, surname, email, password, sprint_id, project_id)
VALUES ('Bob', 'Johnson', 'bob.johnson@example.com', 'securePass12', NULL, 1),
       ('Charlie', 'Brown', 'charlie.brown@example.com', 'passWord5678', NULL, 1),
       ('Diana', 'Prince', 'diana.prince@example.com', 'dianaSuper98', NULL, 1),
       ('Ethan', 'Hunt', 'ethan.hunt@example.com', 'topSecret21', NULL, 1);

-- Вставка данных в таблицу user_role
INSERT INTO user_role (user_id, role_id)
VALUES (6, 1), -- Alice is an Administrator
       (11, 2), -- Bob is a Manager
       (11, 3), -- Bob is also a Developer
       (12, 4), -- Charlie is a Tester
       (13, 1), -- Diana is an Administrator
       (13, 3), -- Diana is also a Developer
       (14, 5); -- Ethan is a Viewer
