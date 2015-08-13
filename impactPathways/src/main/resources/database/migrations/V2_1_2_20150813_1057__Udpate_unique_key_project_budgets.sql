-- -----------------------------------------------------------------------------
--        Deliverable type is not required any more.
-- -----------------------------------------------------------------------------

ALTER TABLE `project_budgets` 
  DROP INDEX `UK_project_budgets`, 
  ADD UNIQUE INDEX `UK_project_budgets` (`project_id` ASC, `budget_type` ASC, `year` ASC, `institution_id` ASC, `cofinance_project_id` ASC) ;


