-- ============================================
-- V3: Criar tabela de produtos
-- ============================================

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    unit VARCHAR(10) NOT NULL,
    stock_quantity DECIMAL(10,3) NOT NULL DEFAULT 0,
    min_stock DECIMAL(10,3) DEFAULT 0,
    supplier_id BIGINT,
    expiry_date DATE,
    requires_weighing BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE INDEX idx_products_code ON products(code);
CREATE INDEX idx_products_supplier ON products(supplier_id);
CREATE INDEX idx_products_active ON products(active);
CREATE INDEX idx_products_low_stock ON products(stock_quantity, min_stock);

-- Inserir produtos de teste
INSERT INTO products (code, name, description, price, unit, stock_quantity, min_stock, supplier_id, requires_weighing, created_by)
VALUES 
('P001', 'Tomate', 'Tomate fresco', 5.99, 'KG', 50.0, 10.0, 1, true, 1),
('P002', 'Alface', 'Alface americana', 3.50, 'UNIDADE', 100.0, 20.0, 1, false, 1),
('P003', 'Cenoura', 'Cenoura orgânica', 4.20, 'KG', 30.0, 5.0, 2, true, 1),
('P004', 'Banana', 'Banana prata', 6.90, 'KG', 80.0, 15.0, 1, true, 1),
('P005', 'Maçã', 'Maçã gala', 8.50, 'KG', 40.0, 10.0, 2, true, 1);
