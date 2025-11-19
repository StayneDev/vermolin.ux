-- ============================================
-- V7: Inserir dados iniciais (Fornecedores, Produtos, Movimentações de Estoque e Vendas)
-- ============================================

-- ============================================
-- 1. Inserir Fornecedores
-- ============================================
INSERT INTO suppliers (name, contact_person, phone, email, address, active, created_by)
VALUES 
('Produções Agrícolas Silva', 'José Silva', '(11) 98765-4321', 'jose@silvaagro.com', 'Rua Rural 100, São Paulo, SP', true, 1),
('Exportadora Hortaliças Brasil', 'Maria Santos', '(11) 3456-7890', 'maria@hortibrasil.com', 'Av. Industrial 200, São Paulo, SP', true, 1),
('Distribuidora Frutas Premium', 'Carlos Oliveira', '(21) 99123-4567', 'carlos@frutaspremium.com', 'Estrada Rio-São Paulo, km 50, RJ', true, 1);

-- ============================================
-- 2. Inserir Produtos
-- ============================================
-- Hortaliças (Folhosas)
INSERT INTO products (name, description, category, price, supplier_id, active, created_by)
VALUES 
('Alface Crespa', 'Alface crespa fresca, ideal para saladas', 'Hortaliças', 5.99, 1, true, 1),
('Espinafre Orgânico', 'Espinafre 100% orgânico sem agrotóxicos', 'Hortaliças', 8.50, 2, true, 1),
('Rúcula Especial', 'Rúcula de sabor intenso, recém colhida', 'Hortaliças', 6.75, 1, true, 1);

-- Frutas
INSERT INTO products (name, description, category, price, supplier_id, active, created_by)
VALUES 
('Maçã Fuji', 'Maçã Fuji vermelha importada', 'Frutas', 12.50, 3, true, 1),
('Banana Prata', 'Banana prata madura ideal para consumo', 'Frutas', 3.50, 1, true, 1),
('Laranja Pêra', 'Laranja pêra suculenta, suco natural', 'Frutas', 7.20, 2, true, 1),
('Morango Fresco', 'Morango vermelho fresco importado', 'Frutas', 18.99, 3, true, 1);

-- Raízes e Tubérculos
INSERT INTO products (name, description, category, price, supplier_id, active, created_by)
VALUES 
('Batata Inglesa', 'Batata inglesa tipo extra, selecionada', 'Raízes', 8.90, 1, true, 1),
('Cenoura Fresca', 'Cenoura fresca, tamanho médio', 'Raízes', 4.50, 2, true, 1),
('Batata Doce', 'Batata doce roxa, rica em nutrientes', 'Raízes', 9.75, 3, true, 1);

-- ============================================
-- 3. Inserir Movimentações de Estoque (Entrada de Mercadoria)
-- ============================================
-- Alface
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(1, 50, 'ENTRADA', 'NF-001-Fornecedor Silva', 1);

-- Espinafre
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(2, 30, 'ENTRADA', 'NF-002-Fornecedor Hortibrasil', 1);

-- Rúcula
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(3, 25, 'ENTRADA', 'NF-003-Fornecedor Silva', 1);

-- Maçã
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(4, 100, 'ENTRADA', 'NF-004-Fornecedor Premium', 1);

-- Banana
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(5, 150, 'ENTRADA', 'NF-005-Fornecedor Silva', 1);

-- Laranja
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(6, 80, 'ENTRADA', 'NF-006-Fornecedor Hortibrasil', 1);

-- Morango
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(7, 40, 'ENTRADA', 'NF-007-Fornecedor Premium', 1);

-- Batata Inglesa
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(8, 200, 'ENTRADA', 'NF-008-Fornecedor Silva', 1);

-- Cenoura
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(9, 120, 'ENTRADA', 'NF-009-Fornecedor Hortibrasil', 1);

-- Batata Doce
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(10, 90, 'ENTRADA', 'NF-010-Fornecedor Premium', 1);

-- ============================================
-- 4. Inserir Vendas de Teste (com Data e Itens)
-- ============================================
-- Venda 1: Compra diversos itens
INSERT INTO sales (user_id, total_amount, sale_date, created_by)
VALUES 
(3, 53.25, CURRENT_TIMESTAMP, 1);

-- Itens da Venda 1
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal, created_by)
VALUES 
(1, 1, 3, 5.99, 17.97, 1),    -- 3x Alface Crespa
(1, 5, 5, 3.50, 17.50, 1),    -- 5x Banana Prata
(1, 9, 2, 4.50, 9.00, 1);     -- 2x Cenoura Fresca

-- Registrar saída de estoque para a venda 1
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(1, -3, 'SAÍDA', 'Venda ID-1', 3),
(5, -5, 'SAÍDA', 'Venda ID-1', 3),
(9, -2, 'SAÍDA', 'Venda ID-1', 3);

-- Venda 2: Outra venda de exemplo
INSERT INTO sales (user_id, total_amount, sale_date, created_by)
VALUES 
(3, 38.45, CURRENT_TIMESTAMP, 1);

-- Itens da Venda 2
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal, created_by)
VALUES 
(2, 4, 2, 12.50, 25.00, 1),   -- 2x Maçã Fuji
(2, 6, 1, 7.20, 7.20, 1),     -- 1x Laranja Pêra
(2, 2, 1, 8.50, 8.50, 1);     -- 1x Espinafre

-- Registrar saída de estoque para a venda 2
INSERT INTO stock_movements (product_id, quantity, movement_type, reference, created_by)
VALUES 
(4, -2, 'SAÍDA', 'Venda ID-2', 3),
(6, -1, 'SAÍDA', 'Venda ID-2', 3),
(2, -1, 'SAÍDA', 'Venda ID-2', 3);
