-- ============================================
-- V7: Inserir dados iniciais (Fornecedores, Produtos, Movimentações de Estoque e Vendas)
-- ============================================

-- ============================================
-- 1. Inserir Fornecedores
-- ============================================
INSERT INTO suppliers (name, cnpj, contact_name, phone, email, address, active, created_by)
VALUES 
('Produções Agrícolas Silva', '12345678000191', 'José Silva', '(11) 98765-4321', 'jose@silvaagro.com', 'Rua Rural 100, São Paulo, SP', true, 1),
('Exportadora Hortaliças Brasil', '98765432000181', 'Maria Santos', '(11) 3456-7890', 'maria@hortibrasil.com', 'Av. Industrial 200, São Paulo, SP', true, 1);

-- ============================================
-- 2. Inserir Produtos
-- ============================================
-- Hortaliças (Folhosas)
INSERT INTO products (code, name, description, price, unit, stock_quantity, min_stock, supplier_id, requires_weighing, active, created_by)
VALUES 
('P-HC-001', 'Alface Crespa', 'Alface crespa fresca, ideal para saladas', 5.99, 'UNIDADE', 50, 10, 1, false, true, 1),
('P-HC-002', 'Espinafre Orgânico', 'Espinafre 100% orgânico sem agrotóxicos', 8.50, 'KG', 25, 5, 2, true, true, 1),
('P-HC-003', 'Rúcula Especial', 'Rúcula de sabor intenso, recém colhida', 6.75, 'KG', 20, 5, 1, true, true, 1);

-- Frutas
INSERT INTO products (code, name, description, price, unit, stock_quantity, min_stock, supplier_id, requires_weighing, active, created_by)
VALUES 
('P-FR-001', 'Maçã Fuji', 'Maçã Fuji vermelha importada', 12.50, 'KG', 100, 20, 2, true, true, 1),
('P-FR-002', 'Banana Prata', 'Banana prata madura ideal para consumo', 3.50, 'KG', 150, 30, 1, true, true, 1),
('P-FR-003', 'Laranja Pêra', 'Laranja pêra suculenta, suco natural', 7.20, 'KG', 80, 15, 2, true, true, 1),
('P-FR-004', 'Morango Fresco', 'Morango vermelho fresco importado', 18.99, 'UNIDADE', 40, 5, 1, false, true, 1);

-- Raízes e Tubérculos
INSERT INTO products (code, name, description, price, unit, stock_quantity, min_stock, supplier_id, requires_weighing, active, created_by)
VALUES 
('P-RT-001', 'Batata Inglesa', 'Batata inglesa tipo extra, selecionada', 8.90, 'KG', 200, 40, 1, true, true, 1),
('P-RT-002', 'Cenoura Fresca', 'Cenoura fresca, tamanho médio', 4.50, 'KG', 120, 25, 2, true, true, 1),
('P-RT-003', 'Batata Doce', 'Batata doce roxa, rica em nutrientes', 9.75, 'KG', 90, 15, 1, true, true, 1);

-- ============================================
-- 3. Inserir Movimentações de Estoque (Será feito via API)
-- ============================================
-- As movimentações de estoque são feitas através das APIs de compra/venda
-- e não devem ser inseridas diretamente aqui para manter consistência
