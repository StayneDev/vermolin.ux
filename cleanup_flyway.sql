-- Limpar histórico do Flyway (DROP E RECREATE)
DROP TABLE IF EXISTS flyway_schema_history CASCADE;

-- Limpar dados incorretos das tables de dados
TRUNCATE TABLE stock_movements CASCADE;
TRUNCATE TABLE sale_items CASCADE;
TRUNCATE TABLE sales CASCADE;
TRUNCATE TABLE products CASCADE;
TRUNCATE TABLE suppliers CASCADE;

-- Reset sequences
ALTER SEQUENCE suppliers_id_seq RESTART WITH 1;
ALTER SEQUENCE products_id_seq RESTART WITH 1;
ALTER SEQUENCE sales_id_seq RESTART WITH 1;
ALTER SEQUENCE sale_items_id_seq RESTART WITH 1;
ALTER SEQUENCE stock_movements_id_seq RESTART WITH 1;
