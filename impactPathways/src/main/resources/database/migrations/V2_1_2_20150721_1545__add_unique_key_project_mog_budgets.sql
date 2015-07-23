ALTER TABLE `project_mog_budgets` 
  ADD UNIQUE INDEX `UK_project_mog_budgets` (`project_id` ASC, `mog_id` ASC) ;