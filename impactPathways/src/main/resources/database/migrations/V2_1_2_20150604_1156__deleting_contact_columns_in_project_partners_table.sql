-- Deleting unused columns in Project Partners table
ALTER TABLE `project_partners` 
DROP COLUMN `contact_email`,
DROP COLUMN `contact_name`;
