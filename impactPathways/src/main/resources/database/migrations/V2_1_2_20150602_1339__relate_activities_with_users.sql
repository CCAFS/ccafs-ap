-- -----------------------------------------------------------------------------
-- Change the relation of activities with employees (leader_id field) to users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS change_activity_users_relation;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE change_activity_users_relation()
BEGIN
  -- As we are changing the table structure, we need to disable the trigger
  DROP TRIGGER IF EXISTS after_activities_update;

    -- Temporary column
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='leader')) THEN
    ALTER TABLE activities ADD `leader` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users' AFTER `leader_id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='leader_id')) THEN
    
    UPDATE activities a SET `leader` = (SELECT u.id FROM users u INNER JOIN employees e ON u.id = e.user_id WHERE e.id = a.leader_id);
  
    ALTER TABLE `activities` 
    DROP FOREIGN KEY `FK_activities_leader_id`;
    ALTER TABLE `activities` 
    DROP COLUMN `leader_id`,
    DROP INDEX `FK_activities_activity_leaders_idx`;
    
    ALTER TABLE activities CHANGE leader leader_id bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users';
    ALTER TABLE activities ADD KEY `FK_activities_activity_leaders_idx` (`leader_id`);
    ALTER TABLE activities ADD CONSTRAINT `FK_activities_leader_id` FOREIGN KEY (`leader_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;


END $$
DELIMITER ;

-- Execute the stored procedure
CALL change_activity_users_relation();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS change_activity_users_relation;

-- -----------------------------------------------------------------------------
-- Change the relation of activities with employees (created_by field) to users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS change_activity_created_by_relation;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE change_activity_created_by_relation()
BEGIN

	-- Temporary column
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created')) THEN
    ALTER TABLE activities ADD `created` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users' AFTER `created_by`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created_by')) THEN
		
    UPDATE activities a SET `created` = (SELECT u.id FROM users u INNER JOIN employees e ON u.id = e.user_id WHERE e.id = a.created_by);
  
    ALTER TABLE `activities` 
    DROP FOREIGN KEY `fk_activities_employees_created_by`;
    ALTER TABLE `activities` 
    DROP COLUMN `created_by`,
    DROP INDEX `fk_activities_employees_created_by`;
    
    ALTER TABLE activities CHANGE created created_by bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users';
    ALTER TABLE activities ADD KEY `fk_activities_employees_created_by_idx` (`created_by`);
    ALTER TABLE activities ADD CONSTRAINT `fk_activities_employees_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL change_activity_created_by_relation();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS change_activity_created_by_relation;