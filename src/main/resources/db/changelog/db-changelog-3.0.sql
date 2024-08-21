--liquibase formatted sql

--changeset nikita:2

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS reviews;

-- Таблица категорий
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    category_description VARCHAR(100) NOT NULL,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);

-- Таблица пользователей
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    registration_token VARCHAR(255),
    enabled BOOLEAN,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);

-- Таблица ролей
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Таблица заказов
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    total_price NUMERIC(10, 2),
    status VARCHAR(50) NOT NULL,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Таблица продуктов
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL UNIQUE,
    product_description VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2),
    stock INT NOT NULL,
    category_id BIGINT,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Таблица связи между пользователями и ролями
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Таблица отзывов
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    product_id BIGINT,
    comment VARCHAR(100),
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Таблица товаров в заказе
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Таблица изображений
CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    image VARCHAR(255),
    product_id BIGINT,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Таблица скидок
CREATE TABLE discounts (
    id SERIAL PRIMARY KEY,
    code VARCHAR(15) NOT NULL UNIQUE,
    percentage INT NOT NULL CHECK (percentage >= 1 AND percentage <= 100),
    expiry_date timestamp with time zone NOT NULL,
    description VARCHAR(100),
    product_id BIGINT,
    created_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    last_modified_date timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
);
