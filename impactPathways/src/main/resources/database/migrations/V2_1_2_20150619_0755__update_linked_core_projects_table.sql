-- -----------------------------------------------------------------------------
--        Add the columns required to maintain history of changes
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_linked_core_projects_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_linked_core_projects_table()
BEGIN
  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='linked_core_projects' AND column_name='created_by')) THEN
    ALTER TABLE `linked_core_projects` ADD `created_by` BIGINT NOT NULL AFTER `core_project_id`;
    UPDATE linked_core_projects a SET created_by = (SELECT liaison_user_id FROM projects p WHERE p.id = a.bilateral_project_id);
    ALTER TABLE linked_core_projects ADD CONSTRAINT fk_linked_core_projects_users_created_by FOREIGN KEY (`created_by`)  REFERENCES users(id);
  END IF;
  
  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='linked_core_projects' AND column_name='modified_by')) THEN
    ALTER TABLE `linked_core_projects` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE linked_core_projects SET modified_by = created_by;
    ALTER TABLE linked_core_projects ADD CONSTRAINT fk_linked_core_projects_users_modified_by FOREIGN KEY (`modified_by`)  REFERENCES users(id);
  END IF;
  
  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='linked_core_projects' AND column_name='is_active')) THEN
    ALTER TABLE `linked_core_projects` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE AFTER `created_by`;
  END IF;
  
    -- Add column "active_since" 
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='linked_core_projects' AND column_name='active_since')) THEN
    ALTER TABLE `linked_core_projects` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='linked_core_projects' AND column_name='modification_justification')) THEN
    ALTER TABLE `linked_core_projects` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
  
  
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_linked_core_projects_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_linked_core_projects_table;

-- -----------------------------------------------------------------------------
--                          End of Modifications 
-- -----------------------------------------------------------------------------