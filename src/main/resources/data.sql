INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Teremok'),
       ('KFC'),
       ('Kebab house'),
       ('Newly opened');

INSERT INTO MEAL (lunch_date, description, price, restaurant_id)
VALUES (CURRENT_DATE, 'Pancake', 32500, 1),
       (CURRENT_DATE, 'Kvas', 15000, 1),
       (CURRENT_DATE, 'Chicken', 65000, 2),
       (CURRENT_DATE, 'French fries', 7900, 2),
       (CURRENT_DATE, 'Coke', 11000, 2),
--        yesterday
       (CURRENT_DATE - 1, 'Yesterday Pancake', 22500, 1),
       (CURRENT_DATE - 1, 'Yesterday Kvas', 5000, 1),
       (CURRENT_DATE - 1, 'Yesterday Soup', 12000, 1),

       --        tomorrow
       (CURRENT_DATE + 1, 'Tomorrow Pancake', 42500, 1),
       (CURRENT_DATE + 1, 'Tomorrow Kvas', 20000, 1),
       (CURRENT_DATE + 1, 'Tomorrow Chicken', 75000, 2),
       (CURRENT_DATE + 1, 'Tomorrow French fries', 9900, 2),
       (CURRENT_DATE + 1, 'Tomorrow Coke', 12000, 2),
       (CURRENT_DATE + 1, 'Tomorrow Kebab', 21000, 3),
       (CURRENT_DATE + 1, 'Tomorrow Plov', 69800, 3),
       (CURRENT_DATE + 1, 'Tomorrow Airan', 8900, 3),
       (CURRENT_DATE + 1, 'Tomorrow Nuts and fruits', 25300, 3)
