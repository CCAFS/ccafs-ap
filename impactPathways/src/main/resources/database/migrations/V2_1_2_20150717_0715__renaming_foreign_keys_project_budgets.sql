ALTER TABLE `project_budgets` 
  DROP FOREIGN KEY `FK_project_bud_temp_budget_types`, 
  DROP FOREIGN KEY `FK_project_bud_temp_cofinance_projects` , 
  DROP FOREIGN KEY `FK_project_bud_temp_institutions` , 
  DROP FOREIGN KEY `FK_project_bud_temp_projects` , 
  DROP FOREIGN KEY `FK_project_bud_temp_users_created_by` , 
  DROP FOREIGN KEY `FK_project_bud_temp_users_modified_by` ;
  
ALTER TABLE `project_budgets` 
  ADD CONSTRAINT `FK_project_budgets_budget_types`  FOREIGN KEY (`budget_type` )
    REFERENCES `budget_types` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_projects_cofinance_id`  FOREIGN KEY (`cofinance_project_id` )
    REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_institutions` FOREIGN KEY (`institution_id` )
    REFERENCES `institutions` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_projects` FOREIGN KEY (`project_id` )
    REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_users_created_by` FOREIGN KEY (`created_by` ) 
    REFERENCES `users` (`id` ) ON DELETE CASCADE   ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_users_modified_by` FOREIGN KEY (`modified_by` ) 
    REFERENCES `users` (`id` ) ON DELETE CASCADE   ON UPDATE CASCADE, 
  DROP INDEX `FK_project_bud_temp_budget_types`, 
  ADD INDEX `FK_project_budgets_budget_types` (`budget_type` ASC) ;
