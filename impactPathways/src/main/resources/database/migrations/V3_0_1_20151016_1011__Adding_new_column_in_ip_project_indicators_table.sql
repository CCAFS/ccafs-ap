----------------------------------------------------------------------------------------------------------
-- Adding a new column in the table ip_project_indicators to get Gender contributions towards indicators.
----------------------------------------------------------------------------------------------------------

-- Creating column in database
ALTER TABLE `ip_project_indicators` 
CHANGE COLUMN `target` `target` TEXT NULL DEFAULT NULL COMMENT '' AFTER `id`,
ADD COLUMN `gender` TEXT NULL COMMENT '' AFTER `description`;

-- Creating column in history
ALTER TABLE $[database]_history.`ip_project_indicators` 
CHANGE COLUMN `target` `target` TEXT NULL DEFAULT NULL COMMENT '' AFTER `record_id`,
ADD COLUMN `gender` TEXT NULL COMMENT '' AFTER `description`;