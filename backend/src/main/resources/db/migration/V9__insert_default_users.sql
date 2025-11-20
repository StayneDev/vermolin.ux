-- ============================================
-- V9: Insert default users for authentication
-- ============================================

-- NOTE: Default users are now created programmatically via DataInitializationConfig
-- This ensures passwords are properly hashed using Spring Security's BCryptPasswordEncoder
-- Users will be created on first application startup if they don't exist

-- This migration is kept empty for version control purposes
-- The users created by the application are:
-- 1. gerente / gerente123 (GERENTE role)
-- 2. estoquista / estoquista123 (ESTOQUISTA role)
-- 3. caixa / caixa123 (CAIXA role)
