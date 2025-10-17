-- Индексы для оптимизации производительности базы данных PostgreSQL

-- Индексы для таблицы advertisements
CREATE INDEX IF NOT EXISTS idx_advertisements_owner_id ON advertisements(owner_id);
CREATE INDEX IF NOT EXISTS idx_advertisements_name ON advertisements(name);
CREATE INDEX IF NOT EXISTS idx_advertisements_name_lower ON advertisements(LOWER(name));
CREATE INDEX IF NOT EXISTS idx_advertisements_description_lower ON advertisements(LOWER(description));

-- Индексы для таблицы users
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- Индексы для таблицы carts
CREATE INDEX IF NOT EXISTS idx_carts_user_id ON carts(user_id);

-- Индексы для таблицы cart_advertisements
CREATE INDEX IF NOT EXISTS idx_cart_advertisements_cart_id ON cart_advertisements(cart_id);
CREATE INDEX IF NOT EXISTS idx_cart_advertisements_advertisement_id ON cart_advertisements(advertisement_id);

-- Индексы для таблицы ships
CREATE INDEX IF NOT EXISTS idx_ships_receiver_id ON ships(receiver_id);
CREATE INDEX IF NOT EXISTS idx_ships_owner_id ON ships(owner_id);
CREATE INDEX IF NOT EXISTS idx_ships_shipping_method_id ON ships(shipping_method_id);
CREATE INDEX IF NOT EXISTS idx_ships_payment_method_id ON ships(payment_method_id);

-- Анализируем таблицы для обновления статистики
ANALYZE advertisements;
ANALYZE users;
ANALYZE carts;
ANALYZE cart_advertisements;
ANALYZE ships;
ANALYZE payment_methods;
ANALYZE shipping_methods;

