-- -----------------------------------------------------------------------------------
-- Add the columns used to track changes in some tables that still don't have them
-- -----------------------------------------------------------------------------------

-- Project budgets temp table

ALTER TABLE `project_budgets_temp` 
  DROP FOREIGN KEY `FK_project_bud_temp_projects` , 
  DROP FOREIGN KEY `FK_project_bud_temp_institutions` , 
  DROP FOREIGN KEY `FK_project_bud_temp_budget_types` ;
  
ALTER TABLE `project_budgets_temp` 
  CHANGE COLUMN `project_id` `project_id` BIGINT(20) NOT NULL  , 
  CHANGE COLUMN `type` `type` BIGINT(20) NOT NULL, 
  CHANGE COLUMN `year` `year` INT(4) NOT NULL, 
  CHANGE COLUMN `institution_id` `institution_id` BIGINT(20) NOT NULL, 
  ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `gender_percentage`, 
  ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `is_active`, 
  ADD COLUMN `created_by` BIGINT(20) NOT NULL  AFTER `active_since`, 
  ADD COLUMN `modified_by` BIGINT(20) NOT NULL  AFTER `created_by`, 
  ADD COLUMN `modification_justification` TEXT NOT NULL  AFTER `modified_by`, 
  ADD CONSTRAINT `FK_project_bud_temp_projects`
  FOREIGN KEY (`project_id` ) REFERENCES `projects` (`id` )
  ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_bud_temp_institutions` FOREIGN KEY (`institution_id` )
    REFERENCES `institutions` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_bud_temp_budget_types` FOREIGN KEY (`type` )
    REFERENCES `budget_types_copy` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_bud_temp_users_created_by` FOREIGN KEY (`created_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_bud_temp_users_modified_by` FOREIGN KEY (`modified_by` )
  REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD INDEX `FK_project_bud_temp_users_created_by_idx` (`created_by` ASC), 
  ADD INDEX `FK_project_bud_temp_users_modified_by_idx` (`modified_by` ASC) ;

  
  -- Project mog budgets
  ALTER TABLE `project_mog_budgets` ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `gender_contribution` , ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `is_active` , ADD COLUMN `created_by` BIGINT(20) NOT NULL  AFTER `active_since` , ADD COLUMN `modified_by` BIGINT(20) NOT NULL  AFTER `created_by` , ADD COLUMN `modification_justification` TEXT NOT NULL  AFTER `modified_by` , 
  ADD CONSTRAINT `FK_project_mog_budget_users_created_by`  FOREIGN KEY (`created_by` ) 
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_mog_budget_users_modified_by` FOREIGN KEY (`modified_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD INDEX `FK_project_mog_budget_users_created_by_idx` (`created_by` ASC), 
  ADD INDEX `FK_project_mog_budget_users_modified_by_idx` (`modified_by` ASC) ;

  -- Project budgets overhead
ALTER TABLE `project_budget_overheads` DROP FOREIGN KEY `FK_project_budgets_ov_projects` ;
ALTER TABLE `project_budget_overheads` CHANGE COLUMN `project_id` `project_id` BIGINT(20) NOT NULL  , ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `contracted_overhead` , ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `is_active` , ADD COLUMN `created_by` BIGINT(20) NOT NULL  AFTER `active_since` , ADD COLUMN `modified_by` BIGINT(20) NOT NULL  AFTER `created_by` , ADD COLUMN `modification_justification` TEXT NOT NULL  AFTER `modified_by` , 
  ADD CONSTRAINT `FK_project_budgets_ov_projects` FOREIGN KEY (`project_id` )
  REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_ov_users_created_by` FOREIGN KEY (`created_by` )
  REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_budgets_ov_users_modified_by` FOREIGN KEY (`modified_by` )
  REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE
, ADD INDEX `FK_project_budgets_ov_users_created_by_idx` (`created_by` ASC) 
, ADD INDEX `FK_project_budgets_ov_users_modified_by_idx` (`modified_by` ASC) ;

-- ip project contribution overview
ALTER TABLE `ip_project_contribution_overviews` DROP FOREIGN KEY `FK_project_contribution_overviews_projects` ;
ALTER TABLE `ip_project_contribution_overviews` ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `gender_contribution` , ADD COLUMN `created_by` BIGINT(20) NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `is_active` , ADD COLUMN `modified_by` BIGINT(20) NOT NULL  AFTER `created_by` , ADD COLUMN `modification_justification` TEXT NOT NULL  AFTER `modified_by` , 
  ADD CONSTRAINT `FK_project_contribution_overviews_projects` FOREIGN KEY (`project_id` ) 
  REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_contribution_overviews_users_created_by` FOREIGN KEY (`created_by` ) 
  REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_contribution_overviews_users_modified_by` FOREIGN KEY (`modified_by` ) 
  REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD INDEX `FK_project_contribution_overviews_users_created_by_idx` (`created_by` ASC), 
  ADD INDEX `FK_project_contribution_overviews_users_modified_by_idx` (`modified_by` ASC) ;

