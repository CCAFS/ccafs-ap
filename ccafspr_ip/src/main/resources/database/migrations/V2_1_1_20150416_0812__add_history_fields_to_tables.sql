-- -----------------------------------------------------------------------------
--            Modifications to the activities table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_activities_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activities_table()
BEGIN
  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created_by')) THEN
    ALTER TABLE `activities` ADD `created_by` BIGINT NOT NULL AFTER `gender_percentage`;
    UPDATE activities a SET created_by = (SELECT IFNULL( project_leader_id, project_owner_id) FROM projects p WHERE p.id = a.project_id);
    ALTER TABLE activities ADD CONSTRAINT fk_activities_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;
  
    -- Change the name of the "created" column to "active_since" 
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='created')) THEN
    ALTER TABLE `activities` CHANGE `created` `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
  END IF;
  
  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='modified_by')) THEN
    ALTER TABLE `activities` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activities SET modified_by = created_by;
    ALTER TABLE activities ADD CONSTRAINT fk_activities_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='modification_justification')) THEN
    ALTER TABLE `activities` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
  
  -- Add the is_active column to the table, if it doesn't already exist
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='is_active')) THEN
    ALTER TABLE `activity_budgets` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='active_since')) THEN
    ALTER TABLE `activity_budgets` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_budgets ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='created_by')) THEN
    ALTER TABLE `activity_budgets` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_budgets ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_budgets ADD CONSTRAINT fk_activity_budgets_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_budgets` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_budgets SET modified_by = created_by;
    ALTER TABLE activity_budgets ADD CONSTRAINT fk_activity_budgets_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='is_active')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='active_since')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_cross_cutting_themes ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='created_by')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_cross_cutting_themes ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT fk_activity_cross_cutting_themes_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_cross_cutting_themes SET modified_by = created_by;
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT fk_activity_cross_cutting_themes_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='is_active')) THEN
    ALTER TABLE `activity_locations` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='active_since')) THEN
    ALTER TABLE `activity_locations` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_locations ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='created_by')) THEN
    ALTER TABLE `activity_locations` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_locations ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_locations ADD CONSTRAINT fk_activity_locations_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_locations` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_locations SET modified_by = created_by;
    ALTER TABLE activity_locations ADD CONSTRAINT fk_activity_locations_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='is_active')) THEN
    ALTER TABLE `activity_partners` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='active_since')) THEN
    ALTER TABLE `activity_partners` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE activity_partners ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='created_by')) THEN
    ALTER TABLE `activity_partners` ADD `created_by` BIGINT NOT NULL;
    UPDATE activity_partners ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE activity_partners ADD CONSTRAINT fk_activity_partners_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='modified_by')) THEN
    ALTER TABLE `activity_partners` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE activity_partners SET modified_by = created_by;
    ALTER TABLE activity_partners ADD CONSTRAINT fk_activity_partners_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='is_active')) THEN
    ALTER TABLE `deliverables` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='active_since')) THEN
    ALTER TABLE `deliverables` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE deliverables ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='created_by')) THEN
    ALTER TABLE `deliverables` ADD `created_by` BIGINT NOT NULL;
    UPDATE deliverables ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE deliverables ADD CONSTRAINT fk_deliverables_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='modified_by')) THEN
    ALTER TABLE `deliverables` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE deliverables SET modified_by = created_by;
    ALTER TABLE deliverables ADD CONSTRAINT fk_deliverables_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_activity_contributions ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='created_by')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_activity_contributions ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE ip_activity_contributions ADD CONSTRAINT fk_ip_activity_contributions_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_activity_contributions SET modified_by = created_by;
    ALTER TABLE ip_activity_contributions ADD CONSTRAINT fk_ip_activity_contributions_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
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

-- -----------------------------------------------------------------------------
--            Modifications to the ip activity indicators table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_activity_indicators_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_activity_indicators_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_activity_indicators ab SET ab.active_since = (SELECT a.active_since FROM activities a WHERE ab.activity_id = a.id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='created_by')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_activity_indicators ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a WHERE ab.activity_id = a.id);
    ALTER TABLE ip_activity_indicators ADD CONSTRAINT fk_ip_activity_indicators_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_activity_indicators SET modified_by = created_by;
    ALTER TABLE ip_activity_indicators ADD CONSTRAINT fk_ip_activity_indicators_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_activity_indicators_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_activity_indicators_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip activity indicators table
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
--            Modifications to the ip deliverable contributions table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_deliverable_contributions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_deliverable_contributions_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_deliverable_contributions ab SET ab.active_since = (SELECT a.active_since FROM activities a INNER JOIN deliverables d ON a.id = d.activity_id WHERE d.id = ab.deliverable_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='created_by')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_deliverable_contributions ab SET ab.created_by = (SELECT IFNULL(a.leader_id, a.created_by) FROM activities a INNER JOIN deliverables d ON a.id = d.activity_id WHERE d.id = ab.deliverable_id);
    ALTER TABLE ip_deliverable_contributions ADD CONSTRAINT fk_ip_deliverable_contributions_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_deliverable_contributions SET modified_by = created_by;
    ALTER TABLE ip_deliverable_contributions ADD CONSTRAINT fk_ip_deliverable_contributions_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_deliverable_contributions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_deliverable_contributions_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip deliverable contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the ip elements table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_elements_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_elements_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='is_active')) THEN
    ALTER TABLE `ip_elements` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  -- This value is assigned arbitrarily given that we don't have a value for this, the date used is an estimated of when the ip elements were created
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='active_since')) THEN
    ALTER TABLE `ip_elements` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_elements ab SET ab.active_since = '2014-08-01 10:00:00';
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  -- The created_by value is assigned arbitrarily to the leader of the program
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='created_by')) THEN
    ALTER TABLE `ip_elements` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_elements ab SET ab.created_by = (SELECT IF( pe.program_id = 1, 51, IF( pe.program_id = 2, 52, IF( pe.program_id = 3,  54, 26 ))) FROM ip_program_elements pe  WHERE pe.element_id = 1 and relation_type_id = 2); 
    ALTER TABLE ip_elements ADD CONSTRAINT fk_ip_elements_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_elements` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_elements SET modified_by = created_by;
    ALTER TABLE ip_elements ADD CONSTRAINT fk_ip_elements_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_elements` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_elements_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_elements_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip elements table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the ip indicators table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_indicators_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_indicators_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_indicators` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_indicators` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_indicators ii SET ii.active_since = (SELECT ie.active_since FROM ip_elements ie WHERE ie.id = ii.ip_element_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='created_by')) THEN
    ALTER TABLE `ip_indicators` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_indicators ii SET ii.created_by = (SELECT ie.created_by FROM ip_elements ie WHERE ie.id = ii.ip_element_id); 
    ALTER TABLE ip_indicators ADD CONSTRAINT fk_ip_indicators_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_indicators` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_indicators SET modified_by = created_by;
    ALTER TABLE ip_indicators ADD CONSTRAINT fk_ip_indicators_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_indicators` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_indicators_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_indicators_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip elements table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the projects table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_projects_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_projects_table()
BEGIN
	
  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='created_by')) THEN
    ALTER TABLE `projects` ADD `created_by` BIGINT NOT NULL AFTER `project_owner_id`;
    UPDATE projects a SET created_by = project_owner_id;
    ALTER TABLE projects ADD CONSTRAINT fk_projects_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;
  
    -- Change the name of the "created" column to "active_since" 
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='created')) THEN
    ALTER TABLE `projects` CHANGE `created` `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
  END IF;
  
  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='modified_by')) THEN
    ALTER TABLE `projects` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE projects SET modified_by = created_by;
    ALTER TABLE projects ADD CONSTRAINT fk_projects_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the projects table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='modification_justification')) THEN
    ALTER TABLE `projects` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
  
  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='is_active')) THEN
    ALTER TABLE `projects` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE AFTER `created_by`;
  END IF;
  
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_projects_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_projects_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the projects table
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
--            Modifications to the ip other contributions table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_other_contributions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_other_contributions_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_other_contributions` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_other_contributions` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_other_contributions ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='created_by')) THEN
    ALTER TABLE `ip_other_contributions` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_other_contributions ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE ip_other_contributions ADD CONSTRAINT fk_ip_other_contributions_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_other_contributions` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_other_contributions SET modified_by = created_by;
    ALTER TABLE ip_other_contributions ADD CONSTRAINT fk_ip_other_contributions_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_other_contributions` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_other_contributions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_other_contributions_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip other contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the ip project contributions table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_project_contributions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_project_contributions_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_project_contributions` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_project_contributions` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_project_contributions ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='created_by')) THEN
    ALTER TABLE `ip_project_contributions` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_project_contributions ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE ip_project_contributions ADD CONSTRAINT fk_ip_project_contributions_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_project_contributions` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_project_contributions SET modified_by = created_by;
    ALTER TABLE ip_project_contributions ADD CONSTRAINT fk_ip_project_contributions_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_project_contributions` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_project_contributions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_project_contributions_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip project contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the ip project indicators table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_ip_project_indicators_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_project_indicators_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_project_indicators` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_project_indicators` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE ip_project_indicators ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='created_by')) THEN
    ALTER TABLE `ip_project_indicators` ADD `created_by` BIGINT NOT NULL;
    UPDATE ip_project_indicators ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE ip_project_indicators ADD CONSTRAINT fk_ip_project_indicators_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='modified_by')) THEN
    ALTER TABLE `ip_project_indicators` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE ip_project_indicators SET modified_by = created_by;
    ALTER TABLE ip_project_indicators ADD CONSTRAINT fk_ip_project_indicators_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='modification_justification')) THEN
    ALTER TABLE `ip_project_indicators` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_project_indicators_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_project_indicators_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip project indicators table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the next users table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_next_users_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_next_users_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='is_active')) THEN
    ALTER TABLE `next_users` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='active_since')) THEN
    ALTER TABLE `next_users` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE next_users ii SET ii.active_since = (SELECT active_since FROM deliverables d WHERE d.id = ii.deliverable_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='created_by')) THEN
    ALTER TABLE `next_users` ADD `created_by` BIGINT NOT NULL;
    UPDATE next_users ii SET ii.created_by = (SELECT d.created_by FROM deliverables d WHERE d.id = ii.deliverable_id); 
    ALTER TABLE next_users ADD CONSTRAINT fk_next_users_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='modified_by')) THEN
    ALTER TABLE `next_users` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE next_users SET modified_by = created_by;
    ALTER TABLE next_users ADD CONSTRAINT fk_next_users_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='modification_justification')) THEN
    ALTER TABLE `next_users` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_next_users_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_next_users_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the next_users table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the project budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_project_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_budgets_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='is_active')) THEN
    ALTER TABLE `project_budgets` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='active_since')) THEN
    ALTER TABLE `project_budgets` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE project_budgets ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='created_by')) THEN
    ALTER TABLE `project_budgets` ADD `created_by` BIGINT NOT NULL;
    UPDATE project_budgets ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE project_budgets ADD CONSTRAINT fk_project_budgets_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='modified_by')) THEN
    ALTER TABLE `project_budgets` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE project_budgets SET modified_by = created_by;
    ALTER TABLE project_budgets ADD CONSTRAINT fk_project_budgets_employees_modified_by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='modification_justification')) THEN
    ALTER TABLE `project_budgets` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_budgets_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip project budgets table
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
--            Modifications to the project focuses table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_project_outcomes_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_outcomes_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='is_active')) THEN
    ALTER TABLE `project_outcomes` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='active_since')) THEN
    ALTER TABLE `project_outcomes` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE project_outcomes ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='created_by')) THEN
    ALTER TABLE `project_outcomes` ADD `created_by` BIGINT NOT NULL;
    UPDATE project_outcomes ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE project_outcomes ADD CONSTRAINT fk_project_outcomes_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='modified_by')) THEN
    ALTER TABLE `project_outcomes` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE project_outcomes SET modified_by = created_by;
    ALTER TABLE project_outcomes ADD CONSTRAINT fk_project_outcomes_employees_modified__by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='modification_justification')) THEN
    ALTER TABLE `project_outcomes` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_outcomes_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_outcomes_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the ip project focuses table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the project outcomes table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_project_outcomes_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_outcomes_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='is_active')) THEN
    ALTER TABLE `project_outcomes` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='active_since')) THEN
    ALTER TABLE `project_outcomes` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE project_outcomes ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='created_by')) THEN
    ALTER TABLE `project_outcomes` ADD `created_by` BIGINT NOT NULL;
    UPDATE project_outcomes ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE project_outcomes ADD CONSTRAINT fk_project_outcomes_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='modified_by')) THEN
    ALTER TABLE `project_outcomes` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE project_outcomes SET modified_by = created_by;
    ALTER TABLE project_outcomes ADD CONSTRAINT fk_project_outcomes_employees_modified__by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='modification_justification')) THEN
    ALTER TABLE `project_outcomes` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_outcomes_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_outcomes_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the project outcomes table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--            Modifications to the project partners table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_project_partners_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_partners_table()
BEGIN

  -- Add the is_active column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='is_active')) THEN
    ALTER TABLE `project_partners` ADD `is_active` BOOLEAN NOT NULL DEFAULT TRUE;
  END IF;

  -- Add the `active_since` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='active_since')) THEN
    ALTER TABLE `project_partners` ADD `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;    
    UPDATE project_partners ii SET ii.active_since = (SELECT active_since FROM projects p WHERE p.id = ii.project_id);
  END IF;

  -- Add the `created_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='created_by')) THEN
    ALTER TABLE `project_partners` ADD `created_by` BIGINT NOT NULL;
    UPDATE project_partners ii SET ii.created_by = (SELECT p.created_by FROM projects p WHERE p.id = ii.project_id); 
    ALTER TABLE project_partners ADD CONSTRAINT fk_project_partners_employees_created_by FOREIGN KEY (`created_by`)  REFERENCES employees(id);
  END IF;

  -- Add the `modified_by` column to the table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='modified_by')) THEN
    ALTER TABLE `project_partners` ADD `modified_by` BIGINT NOT NULL ;
    UPDATE project_partners SET modified_by = created_by;
    ALTER TABLE project_partners ADD CONSTRAINT fk_project_partners_employees_modified__by FOREIGN KEY (`modified_by`)  REFERENCES employees(id);
  END IF;
  
  -- Add the modification_justification column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='modification_justification')) THEN
    ALTER TABLE `project_partners` ADD `modification_justification` TEXT NOT NULL ;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_partners_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_partners_table;

-- -----------------------------------------------------------------------------
--            End of modifications to the project partners table
-- -----------------------------------------------------------------------------