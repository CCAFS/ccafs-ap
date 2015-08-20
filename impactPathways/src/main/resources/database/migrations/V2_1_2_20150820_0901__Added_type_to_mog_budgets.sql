-- -----------------------------------------------------------------------------
--    Adding column type to table project_mog_budgets
-- -----------------------------------------------------------------------------

ALTER TABLE `project_mog_budgets` 
  ADD COLUMN `budget_type` INT(2) NOT NULL  AFTER `gender_contribution` ;
