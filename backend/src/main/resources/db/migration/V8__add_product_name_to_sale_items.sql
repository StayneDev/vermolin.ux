-- ============================================
-- V8: Add product_name column to sale_items if missing
-- ============================================

ALTER TABLE sale_items
ADD COLUMN IF NOT EXISTS product_name VARCHAR(100) NOT NULL DEFAULT 'Unknown';

-- Remove the default after adding the column for future inserts
-- The column is defined NOT NULL but we set a default for the migration
ALTER TABLE sale_items
ALTER COLUMN product_name DROP DEFAULT;
