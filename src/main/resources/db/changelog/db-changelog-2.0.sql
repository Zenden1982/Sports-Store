--liquibase formatted sql


--changelog nikita:2

-- Вставка данных в таблицу категорий
INSERT INTO categories (category_name, category_description) VALUES
('Электроника', 'Устройства и гаджеты, включая телефоны, ноутбуки и аксессуары.'),
('Книги', 'Разные жанры книг, включая художественную, научную и образовательную литературу.'),
('Одежда', 'Одежда для мужчин и женщин, включая рубашки, брюки и платья.'),
('Дом и кухня', 'Бытовая техника, мебель и кухонная утварь для улучшения дома.');


-- Вставка данных в таблицу пользователей
INSERT INTO users (username, password, email, first_name, last_name, address, phone_number, registration_token, enabled) VALUES
('ivangor', 'пароль123', 'ivanov@example.com', 'Иван', 'Иванов', 'Улица Ленина, д. 1', '555-1111', 'токен123', TRUE),
('mariasm', 'пароль456', 'mariasmith@example.com', 'Мария', 'Смит', 'Проспект Мира, д. 45', '555-2222', 'токен456', TRUE),
('alekseybl', 'пароль789', 'alekseyblue@example.com', 'Алексей', 'Синий', 'Улица Пушкина, д. 12', '555-3333', 'токен789', TRUE);


-- Вставка данных в таблицу ролей
INSERT INTO roles (name) VALUES
('ROLE__USER'), ('ROLE__MODERATOR');


-- Вставка данных в таблицу заказов
INSERT INTO orders (user_id, total_price, status) VALUES
(1, 2999.99, 'PENDING'),
(2, 199.99, 'DELIVERED'),
(3, 1299.49, 'CANCELED');


-- Вставка данных в таблицу продуктов
INSERT INTO products (product_name, product_description, price, stock, category_id) VALUES
('Смартфон', 'Современный смартфон с большим экраном и хорошей камерой.', 19999.99, 50, 1),
('Книга по программированию', 'Книга о программировании на Java.', 999.99, 100, 2),
('Джинсы', 'Удобные джинсы для повседневной носки.', 2999.99, 75, 3),
('Микроволновая печь', 'Микроволновка для быстрого разогрева пищи.', 4999.99, 30, 4);


-- Вставка данных в таблицу связи между пользователями и ролями
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3);


-- Вставка данных в таблицу отзывов
INSERT INTO reviews (user_id, product_id, comment, rating) VALUES
(1, 1, 'Отличный смартфон! Очень доволен покупкой.', 5),
(2, 2, 'Полезная книга, рекомендую.', 4),
(3, 3, 'Хорошие джинсы, но немного большие.', 3);


-- Вставка данных в таблицу отзывов
INSERT INTO reviews (user_id, product_id, comment, rating) VALUES
(1, 1, 'Отличный смартфон! Очень доволен покупкой.', 5),
(2, 2, 'Полезная книга, рекомендую.', 4),
(3, 3, 'Хорошие джинсы, но немного большие.', 3);


-- Вставка данных в таблицу изображений
INSERT INTO images (image, product_id) VALUES
('image1.jpg', 1),
('image2.jpg', 2),
('image3.jpg', 3);


-- Вставка данных в таблицу скидок
INSERT INTO discounts (code, percentage, expiry_date, description, product_id) VALUES
('SUMMER21', 10, '2024-09-30 23:59:59', 'Летняя скидка 10% на все товары', 1),
('BOOKS15', 15, '2024-08-31 23:59:59', 'Скидка 15% на книги', 2),
('JEANS20', 20, '2024-12-31 23:59:59', 'Скидка 20% на джинсы', 3);
