-- ============================================
-- V2: Criar tabela de fornecedores
-- ============================================

CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) UNIQUE NOT NULL,
    contact_name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE INDEX idx_suppliers_cnpj ON suppliers(cnpj);
CREATE INDEX idx_suppliers_active ON suppliers(active);

-- Inserir fornecedores de teste
INSERT INTO suppliers (name, cnpj, contact_name, phone, email, address, created_by)
VALUES 
('Hortifruti Verde Ltda', '12345678000190', 'Carlos Silva', '11987654321', 'contato@verde.com.br', 'Rua das Flores, 123', 1),
('Fazenda Orgânica', '98765432000180', 'Ana Santos', '11876543210', 'vendas@organica.com.br', 'Estrada Rural, Km 45', 1);
