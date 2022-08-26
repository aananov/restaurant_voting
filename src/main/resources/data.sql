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
       ('Kebab house');

INSERT INTO MEAL (date, description, price, restaurant_id)
VALUES (CURRENT_DATE, 'Pancake', 32500, 1),
       (CURRENT_DATE, 'Kvas', 15000, 1),
       (CURRENT_DATE, 'Chicken', 65000, 2),
       (CURRENT_DATE, 'French fries', 7900, 2),
       (CURRENT_DATE, 'Coke', 11000, 2),
       (CURRENT_DATE, 'Kebab', 17000, 3),
       (CURRENT_DATE, 'Plov', 49800, 3),
       (CURRENT_DATE, 'Airan', 6900, 3),
--        yesterday
       (CURRENT_DATE - 1, 'Pancake', 22500, 1),
       (CURRENT_DATE - 1, 'Kvas', 5000, 1),
       (CURRENT_DATE - 1, 'Chicken', 32000, 2),
       (CURRENT_DATE - 1, 'French fries', 3600, 2),
       (CURRENT_DATE - 1, 'Coke', 6000, 2),
       (CURRENT_DATE - 1, 'Kebab', 11000, 3),
       (CURRENT_DATE - 1, 'Plov', 35800, 3),
       (CURRENT_DATE - 1, 'Airan', 4900, 3),
       --        tomorrow
       (CURRENT_DATE + 1, 'Pancake', 42500, 1),
       (CURRENT_DATE + 1, 'Kvas', 20000, 1),
       (CURRENT_DATE + 1, 'Chicken', 75000, 2),
       (CURRENT_DATE + 1, 'French fries', 9900, 2),
       (CURRENT_DATE + 1, 'Coke', 12000, 2),
       (CURRENT_DATE + 1, 'Kebab', 21000, 3),
       (CURRENT_DATE + 1, 'Plov', 69800, 3),
       (CURRENT_DATE + 1, 'Airan', 8900, 3)
