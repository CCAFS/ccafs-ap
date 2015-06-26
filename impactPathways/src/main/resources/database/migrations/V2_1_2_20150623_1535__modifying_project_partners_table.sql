-- -----------------------------------------------------------------------------
--        Setting the column modification_justification as not null
-- -----------------------------------------------------------------------------

UPDATE `project_partners` SET `modification_justification` = '' WHERE `modification_justification` IS NULL;
ALTER TABLE `project_partners` CHANGE COLUMN `modification_justification` `modification_justification` TEXT NOT NULL;