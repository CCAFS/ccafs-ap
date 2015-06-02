-- -----------------------------------------------------------------------------
-- Change the relation of projects with employees (project_owner_id field) to users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS change_project_users_relation;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE change_project_users_relation()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='management_liaison_id')) THEN
    ALTER TABLE projects ADD `management_liaison_id` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users' AFTER `project_owner_id`;
    ALTER TABLE projects ADD KEY `FK_projects_management_liaison_users_idx` (`management_liaison_id`);
    ALTER TABLE projects ADD CONSTRAINT `FK_projects_management_liaison_users` FOREIGN KEY (`management_liaison_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='project_owner_id')) THEN
    UPDATE projects p SET `management_liaison_id` = (SELECT u.id FROM users u INNER JOIN employees e ON u.id = e.user_id WHERE e.id = p.project_owner_id);
  
    ALTER TABLE `projects` 
    DROP FOREIGN KEY `FK_projects_owner_employees`;
    ALTER TABLE `projects` 
    DROP COLUMN `project_owner_id`,
    DROP INDEX `FK_projects_owner_employees_idx`;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL change_project_users_relation();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS change_project_users_relation;

-- -----------------------------------------------------------------------------
-- Change the relation of projects with employees (created_by field) to users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS change_project_created_by_relation;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE change_project_created_by_relation()
BEGIN

	-- Temporary column
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='created')) THEN
    ALTER TABLE projects ADD `created` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users' AFTER `created_by`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='created_by')) THEN
		-- As we are changing the table structure, we need to disable the trigger
		DROP TRIGGER IF EXISTS after_projects_update;
		
    UPDATE projects p SET `created` = (SELECT u.id FROM users u INNER JOIN employees e ON u.id = e.user_id WHERE e.id = p.created_by);
  
    ALTER TABLE `projects` 
    DROP FOREIGN KEY `fk_projects_employees_created_by`;
    ALTER TABLE `projects` 
    DROP COLUMN `created_by`,
    DROP INDEX `fk_projects_employees_created_by`;
    
    ALTER TABLE projects CHANGE created created_by bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users';
    ALTER TABLE projects ADD KEY `fk_projects_employees_created_by` (`created_by`);
    ALTER TABLE projects ADD CONSTRAINT `fk_projects_employees_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL change_project_created_by_relation();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS change_project_created_by_relation;