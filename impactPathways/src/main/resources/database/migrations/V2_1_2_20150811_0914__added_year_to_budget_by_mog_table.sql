-- -----------------------------------------------------------------------------
--        Adding year to the table of project budget by MOG.
-- -----------------------------------------------------------------------------

ALTER TABLE `project_mog_budgets` ADD COLUMN `year` INT NOT NULL  AFTER `gender_contribution` ;
