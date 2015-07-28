-- Deleting is_global column from activities table.
ALTER TABLE `activities` 
DROP COLUMN `is_global`;
