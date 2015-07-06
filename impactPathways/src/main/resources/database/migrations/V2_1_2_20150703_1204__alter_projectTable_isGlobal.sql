-- -----------------------------------------------------------------------------
--        Alter project table adding is_global field
-- -----------------------------------------------------------------------------

ALTER TABLE `projects` 
ADD COLUMN `is_global` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '' AFTER `modification_justification`;
