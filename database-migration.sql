-- Миграция для добавления новых полей в корзину

-- Добавляем поле price в таблицу advertisements
ALTER TABLE advertisements
ADD COLUMN IF NOT EXISTS price DECIMAL(10, 2) NOT NULL DEFAULT 0.00;

-- Добавляем поля quantity и selected в таблицу cart_advertisements
ALTER TABLE cart_advertisements
ADD COLUMN IF NOT EXISTS quantity INT NOT NULL DEFAULT 1,
ADD COLUMN IF NOT EXISTS selected BOOLEAN NOT NULL DEFAULT true;

-- Создаем индексы для оптимизации запросов
CREATE INDEX IF NOT EXISTS idx_cart_advertisements_cart_id ON cart_advertisements(cart_id);
CREATE INDEX IF NOT EXISTS idx_cart_advertisements_advertisement_id ON cart_advertisements(advertisement_id);
CREATE INDEX IF NOT EXISTS idx_advertisements_owner_id ON advertisements(owner_id);
CREATE INDEX IF NOT EXISTS idx_advertisements_price ON advertisements(price);

