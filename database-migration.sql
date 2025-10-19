-- Добавляем поля quantity, selected и liked в таблицу cart_advertisements
ALTER TABLE cart_advertisements
ADD COLUMN IF NOT EXISTS quantity INT NOT NULL DEFAULT 1,
ADD COLUMN IF NOT EXISTS selected BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN IF NOT EXISTS liked BOOLEAN NOT NULL DEFAULT false;

-- Добавляем поле rating в таблицу users (если его еще нет)
ALTER TABLE users
ADD COLUMN IF NOT EXISTS rating REAL NOT NULL DEFAULT 0.0;

-- Добавляем поле image_url в таблицу advertisements
ALTER TABLE advertisements
ADD COLUMN IF NOT EXISTS image_url VARCHAR(2048);

-- Добавляем поля badge и delivery_time в таблицу shipping_methods
ALTER TABLE shipping_methods
ADD COLUMN IF NOT EXISTS badge VARCHAR(255),
ADD COLUMN IF NOT EXISTS delivery_time VARCHAR(255);

-- Добавляем поле description в таблицу payment_methods
ALTER TABLE payment_methods
ADD COLUMN IF NOT EXISTS description VARCHAR(500);

-- Создаем таблицу products
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    total_screens INT NOT NULL,
    total_components INT NOT NULL,
    workflow JSONB,
    workflow_id VARCHAR(255) NOT NULL
);
