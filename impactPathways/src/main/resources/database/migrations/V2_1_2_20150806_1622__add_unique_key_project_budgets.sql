-- -----------------------------------------------------------------------------
--                         Altering project_budget_overheads table 
-- -----------------------------------------------------------------------------

ALTER TABLE `project_budgets` 
ADD UNIQUE INDEX `UK_project_budgets` (`project_id` ASC, `budget_type` ASC, `year` ASC, `institution_id` ASC) ;
