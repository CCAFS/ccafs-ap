-- -----------------------------------------------------------------------------
--            Modifications to the activities table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_activities_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activities_table()
BEGIN
  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created_by')) THEN
    ALTER TABLE `activities` ADD `created_by` BIGINT NOT NULL AFTER `gender_percentage`;
    UPDATE activities a SET created_by = (SELECT IFNULL( project_leader_id, project_owner_id) FROM projects p WHERE p.id = a.project_id);
    ALTER TABLE activities ADD CONSTRAINT fk_activities_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;
  
    -- Change the name of the "created" column to "active_since" 
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created')) THEN
    ALTER TABLE `activities` CHANGE `created` `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
  END IF;
  
  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='modified_by')) THEN
    ALTER TABLE `activities` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activities SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='modification_justification')) THEN
    ALTER TABLE `activities` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
  
  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='is_active')) THEN
    ALTER TABLE `activities` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE AFTER `created_by`;
  END IF;
  
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activities_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activities_table;

-- -----------------------------------------------------------------------------
--            End of Modifications to the activities table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the activity budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_activity_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_budgets_table()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='is_active')) THEN
    ALTER TABLE `activity_budgets` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='active_since')) THEN
    ALTER TABLE `activity_budgets` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_budgets ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='created_by')) THEN
    ALTER TABLE `activity_budgets` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_budgets ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_budgets ADD CONSTRAINT fk_activity_budgets_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_budgets` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_budgets SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='modification_justification')) THEN
    ALTER TABLE `activity_budgets` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_budgets_table;

-- -----------------------------------------------------------------------------
--            End of Modifications to the activity budgets table
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
--            Modifications to the activity cross cutting themes
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS activity_cross_cutting_themes;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE activity_cross_cutting_themes()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='is_active')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='active_since')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_cross_cutting_themes ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='created_by')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_cross_cutting_themes ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT fk_activity_cross_cutting_themes_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_cross_cutting_themes SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='modification_justification')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL activity_cross_cutting_themes();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS activity_cross_cutting_themes;

-- -----------------------------------------------------------------------------
--            End of Modifications to the activity cross cutting themes
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the activity locations table
-- -----------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS update_activity_locations_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_locations_table()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='is_active')) THEN
    ALTER TABLE `activity_locations` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='active_since')) THEN
    ALTER TABLE `activity_locations` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_locations ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='created_by')) THEN
    ALTER TABLE `activity_locations` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_locations ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_locations ADD CONSTRAINT fk_activity_locations_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_locations` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_locations SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='modification_justification')) THEN
    ALTER TABLE `activity_locations` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_locations_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_locations_table;

-- -----------------------------------------------------------------------------
--            End of Modifications to the activity cross cutting themes
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the activity partners table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_activity_partners_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_partners_table()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='is_active')) THEN
    ALTER TABLE `activity_partners` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='active_since')) THEN
    ALTER TABLE `activity_partners` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_partners ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='created_by')) THEN
    ALTER TABLE `activity_partners` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_partners ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_partners ADD CONSTRAINT fk_activity_partners_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_partners` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_partners SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='modification_justification')) THEN
    ALTER TABLE `activity_partners` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_partners_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_partners_table;

-- -----------------------------------------------------------------------------
--            End of Modifications to the activity partners table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the deliverables table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_deliverables_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_deliverables_table()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='is_active')) THEN
    ALTER TABLE `deliverables` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='active_since')) THEN
    ALTER TABLE `deliverables` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE deliverables ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='created_by')) THEN
    ALTER TABLE `deliverables` ADD `created_by` BIGINT NOT NULL;
    UPDATE deliverables ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE deliverables ADD CONSTRAINT fk_deliverables_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='modified_by')) THEN
    ALTER TABLE `deliverables` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE deliverables SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='modification_justification')) THEN
    ALTER TABLE `deliverables` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_deliverables_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_deliverables_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the deliverables table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the ip activity contributions table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_activity_contributions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_activity_contributions_table()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_activity_contributions ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the created_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='created_by')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_activity_contributions ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE ip_activity_contributions ADD CONSTRAINT fk_ip_activity_contributions_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the modified_by column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_activity_contributions SET modified_by = created_by;
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_activity_contributions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_activity_contributions_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip activity contributions table
-- -----------------------------------------------------------------------------
