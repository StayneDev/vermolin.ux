-- ============================================
-- V10: Clean up and reinitialize users
-- ============================================

-- Delete the old users with invalid password hashes from V9
DELETE FROM users WHERE username IN ('gerente', 'estoquista', 'caixa');

-- The users will now be created programmatically by DataInitializationConfig
-- on next application startup with properly hashed passwords
