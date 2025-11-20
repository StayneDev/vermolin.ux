-- =====================================================
-- V13: Atualizar schema de stock_movements para auditoria
-- =====================================================

-- Renomear coluna de usuário para created_by mantendo rastreio
ALTER TABLE stock_movements
    RENAME COLUMN user_id TO created_by;

-- Adicionar campos faltantes utilizados pela aplicação
ALTER TABLE stock_movements
    ADD COLUMN IF NOT EXISTS previous_quantity DECIMAL(10,3),
    ADD COLUMN IF NOT EXISTS new_quantity DECIMAL(10,3),
    ADD COLUMN IF NOT EXISTS notes TEXT,
    ADD COLUMN IF NOT EXISTS supplier_id BIGINT,
    ADD COLUMN IF NOT EXISTS expiry_date DATE,
    ADD COLUMN IF NOT EXISTS sale_id BIGINT;

-- Garantir relacionamentos
ALTER TABLE stock_movements
    DROP CONSTRAINT IF EXISTS fk_stock_movements_supplier,
    DROP CONSTRAINT IF EXISTS fk_stock_movements_sale;

ALTER TABLE stock_movements
    ADD CONSTRAINT fk_stock_movements_supplier
        FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    ADD CONSTRAINT fk_stock_movements_sale
        FOREIGN KEY (sale_id) REFERENCES sales(id);

-- Atualizar índices para novos campos
DROP INDEX IF EXISTS idx_stock_movements_user;
CREATE INDEX IF NOT EXISTS idx_stock_movements_created_by ON stock_movements(created_by);
CREATE INDEX IF NOT EXISTS idx_stock_movements_supplier ON stock_movements(supplier_id);
CREATE INDEX IF NOT EXISTS idx_stock_movements_sale ON stock_movements(sale_id);
