INSERT INTO item_types (name)
VALUES ('Epic'),
       ('Task'),
       ('Subtask'),
       ('Bug'),
       ('Story');

INSERT INTO statuses (name, item_type_id)
VALUES ('To Do', 1),       -- Статус для Эпика
       ('In Progress', 1), -- Статус для Эпика
       ('Done', 1),        -- Статус для Эпика
       ('To Do', 2),       -- Статус для Задачи
       ('In Progress', 2), -- Статус для Задачи
       ('Done', 2),        -- Статус для Задачи
       ('Done', 3),        -- Статус для Подзадачи
       ('Blocked', 4),     -- Статус для Бага
       ('Ready for Review', 5); -- Статус для Истории

INSERT INTO items (title, description, complexity, start_time, end_time, create_time, author_id, executor_id, sprint_id,
                   project_id, status_id, item_type)
VALUES ('Create User Authentication Module', 'Develop authentication and authorization features for the system.', 5,'2024-11-01 09:00:00', '2024-11-10 17:00:00', now(), 11, null, null, 1, 2, 1), -- Эпик
       ('Design Login Page', 'Create a responsive login page UI/UX.', 3, '2024-11-02 09:00:00', '2024-11-04 17:00:00', now(), 11, null, null, 1, 2, 2),                                               -- Задача
       ('Implement JWT Token Handling', 'Handle token generation, validation, and refresh.', 4, '2024-11-04 09:00:00', '2024-11-07 17:00:00', now(), 11, null, null, 1, 2, 2),                        -- Задача
       ('Fix Login Bug on Mobile', 'Resolve login issues on mobile devices.', 2, '2024-11-05 09:00:00', '2024-11-06 17:00:00', now(), 13, null, null, 1, 4, 4),                        -- Баг
       ('Add Validation for User Input', 'Implement frontend and backend validation for login inputs.', 3, '2024-11-07 09:00:00', '2024-11-09 17:00:00', now(), 13, null, null, 1, 5, 3); -- Подзадача


