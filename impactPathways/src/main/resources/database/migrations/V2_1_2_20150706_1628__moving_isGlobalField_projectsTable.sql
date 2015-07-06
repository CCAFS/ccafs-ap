-- -----------------------------------------------------------------------------
--        Moving is_global field in projects
-- -----------------------------------------------------------------------------

ALTER TABLE projects 
CHANGE COLUMN `is_global` `is_global` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '' AFTER `type`;
