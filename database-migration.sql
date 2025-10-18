
-- Добавляем поля quantity, selected и liked в таблицу cart_advertisements
ALTER TABLE cart_advertisements
ADD COLUMN IF NOT EXISTS quantity INT NOT NULL DEFAULT 1,
ADD COLUMN IF NOT EXISTS selected BOOLEAN NOT NULL DEFAULT true,
ADD COLUMN IF NOT EXISTS liked BOOLEAN NOT NULL DEFAULT false;

-- Добавляем поле rating в таблицу users (если его еще нет)
ALTER TABLE users
ADD COLUMN IF NOT EXISTS rating REAL NOT NULL DEFAULT 0.0;
