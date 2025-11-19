-- ============================================
-- V1: Criar tabela de usuários
-- ============================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);

-- Inserir usuário administrador padrão (senha: admin123)
INSERT INTO users (username, password, full_name, role, active)
VALUES ('admin', '$2a$10$8K1p/8S0GF4tD3tEyJRk/OxVqGwF9XP7Yq4v5YiJwPvHLqvX7GzHa', 'Administrador', 'GERENTE', true);

-- Inserir usuários de teste
-- Senha para todos: senha123
INSERT INTO users (username, password, full_name, role, active, created_by)
VALUES 
('estoquista1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhka', 'João Estoquista', 'ESTOQUISTA', true, 1),
('caixa1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhka', 'Maria Caixa', 'CAIXA', true, 1);
