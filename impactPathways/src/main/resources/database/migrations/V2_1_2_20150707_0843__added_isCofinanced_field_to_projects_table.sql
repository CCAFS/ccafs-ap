-- -----------------------------------------------------------------------------
--        Moving is_global field in projects
-- -----------------------------------------------------------------------------

ALTER TABLE projects ADD COLUMN `is_cofinanced` TINYINT(1) NOT NULL DEFAULT '0'  AFTER `is_global` ;
