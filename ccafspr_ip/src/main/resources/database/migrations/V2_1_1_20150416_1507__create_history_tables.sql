-- -----------------------------------------------------------------------------
-- Creation of the database that will be used to keep the logs of the database 
-- changes. To run this script you should first create another database with 
-- name:   realDatabaseName_history
-- 
-- Where realDatabaseName is the name of the database, for example
-- ccafspr_history
-- -----------------------------------------------------------------------------


USE $[database]_history;

-- -----------------------------------------------------------------------------
--      Creation and modifications to the activities table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS activities LIKE $[database].activities; 
DROP PROCEDURE IF EXISTS update_activities_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activities_history_table()
BEGIN

	IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='record_id')) THEN
  	ALTER TABLE `activities` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='is_active')) THEN
  	ALTER TABLE `activities` DROP `is_active`;
  END IF;
  	
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='active_since')) THEN
  	ALTER TABLE `activities` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='active_until')) THEN
  	ALTER TABLE `activities` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activities' AND column_name='action')) THEN
	 ALTER TABLE `activities` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activities_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activities_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the activities table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the activity_budgets table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS activity_budgets LIKE $[database].activity_budgets; 
DROP PROCEDURE IF EXISTS update_activity_budgets_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_budgets_history_table()
BEGIN

	IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='record_id')) THEN
    ALTER TABLE `activity_budgets` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='is_active')) THEN
    ALTER TABLE `activity_budgets` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='active_since')) THEN
    ALTER TABLE `activity_budgets` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='active_until')) THEN
    ALTER TABLE `activity_budgets` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='action')) THEN
   ALTER TABLE `activity_budgets` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_budgets_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_budgets_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the activity_budgets table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the activity_cross_cutting_themes table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS activity_cross_cutting_themes LIKE $[database].activity_cross_cutting_themes; 
DROP PROCEDURE IF EXISTS update_activity_cross_cutting_themes_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_cross_cutting_themes_history_table()
BEGIN

	IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='record_id')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='is_active')) THEN
    ALTER TABLE `activity_cross_cutting_themes` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='active_since')) THEN
    ALTER TABLE `activity_cross_cutting_themes` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='active_until')) THEN
    ALTER TABLE `activity_cross_cutting_themes` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='action')) THEN
   ALTER TABLE `activity_cross_cutting_themes` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_cross_cutting_themes_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_cross_cutting_themes_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the activity_cross_cutting_themes table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the activity_locations table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS activity_locations LIKE $[database].activity_locations; 
DROP PROCEDURE IF EXISTS update_activity_locations_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_locations_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='record_id')) THEN
    ALTER TABLE `activity_locations` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='is_active')) THEN
    ALTER TABLE `activity_locations` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='active_since')) THEN
    ALTER TABLE `activity_locations` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='active_until')) THEN
    ALTER TABLE `activity_locations` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='action')) THEN
   ALTER TABLE `activity_locations` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_locations_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_locations_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the activity_locations table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the activity_partners table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS activity_partners LIKE $[database].activity_partners; 
DROP PROCEDURE IF EXISTS update_activity_partners_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_activity_partners_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='record_id')) THEN
    ALTER TABLE `activity_partners` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='is_active')) THEN
    ALTER TABLE `activity_partners` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='active_since')) THEN
    ALTER TABLE `activity_partners` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='active_until')) THEN
    ALTER TABLE `activity_partners` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_partners' AND column_name='action')) THEN
   ALTER TABLE `activity_partners` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_activity_partners_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_activity_partners_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the activity_partners table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the deliverables table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS deliverables LIKE $[database].deliverables; 
DROP PROCEDURE IF EXISTS update_deliverables_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_deliverables_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='record_id')) THEN
    ALTER TABLE `deliverables` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='is_active')) THEN
    ALTER TABLE `deliverables` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='active_since')) THEN
    ALTER TABLE `deliverables` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='active_until')) THEN
    ALTER TABLE `deliverables` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='deliverables' AND column_name='action')) THEN
   ALTER TABLE `deliverables` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_deliverables_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_deliverables_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the deliverables table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_activity_contributions table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_activity_contributions LIKE $[database].ip_activity_contributions; 
DROP PROCEDURE IF EXISTS update_ip_activity_contributions_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_activity_contributions_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='record_id')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_activity_contributions` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_activity_contributions` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='active_until')) THEN
    ALTER TABLE `ip_activity_contributions` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_contributions' AND column_name='action')) THEN
   ALTER TABLE `ip_activity_contributions` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_activity_contributions_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_activity_contributions_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_activity_contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_activity_indicators table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_activity_indicators LIKE $[database].ip_activity_indicators; 
DROP PROCEDURE IF EXISTS update_ip_activity_indicators_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_activity_indicators_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='record_id')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_activity_indicators` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_activity_indicators` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='active_until')) THEN
    ALTER TABLE `ip_activity_indicators` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_activity_indicators' AND column_name='action')) THEN
   ALTER TABLE `ip_activity_indicators` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_activity_indicators_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_activity_indicators_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_activity_indicators table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_deliverable_contributions table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_deliverable_contributions LIKE $[database].ip_deliverable_contributions; 
DROP PROCEDURE IF EXISTS update_ip_deliverable_contributions_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_deliverable_contributions_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='record_id')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_deliverable_contributions` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_deliverable_contributions` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='active_until')) THEN
    ALTER TABLE `ip_deliverable_contributions` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='action')) THEN
   ALTER TABLE `ip_deliverable_contributions` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_deliverable_contributions_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_deliverable_contributions_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_deliverable_contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_elements table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_elements LIKE $[database].ip_elements; 
DROP PROCEDURE IF EXISTS update_ip_elements_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_elements_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='record_id')) THEN
    ALTER TABLE `ip_elements` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='is_active')) THEN
    ALTER TABLE `ip_elements` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='active_since')) THEN
    ALTER TABLE `ip_elements` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='active_until')) THEN
    ALTER TABLE `ip_elements` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='action')) THEN
   ALTER TABLE `ip_elements` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_elements_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_elements_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_elements table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_indicators table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_indicators LIKE $[database].ip_indicators; 
DROP PROCEDURE IF EXISTS update_ip_indicators_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_indicators_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='record_id')) THEN
    ALTER TABLE `ip_indicators` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_indicators` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_indicators` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='active_until')) THEN
    ALTER TABLE `ip_indicators` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_indicators' AND column_name='action')) THEN
   ALTER TABLE `ip_indicators` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_indicators_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_indicators_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_indicators table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the projects table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS projects LIKE $[database].projects; 
DROP PROCEDURE IF EXISTS update_projects_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_projects_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='record_id')) THEN
    ALTER TABLE `projects` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='is_active')) THEN
    ALTER TABLE `projects` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='active_since')) THEN
    ALTER TABLE `projects` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='active_until')) THEN
    ALTER TABLE `projects` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='action')) THEN
   ALTER TABLE `projects` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_projects_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_projects_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the projects table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_other_contributions table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_other_contributions LIKE $[database].ip_other_contributions; 
DROP PROCEDURE IF EXISTS update_ip_other_contributions_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_other_contributions_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='record_id')) THEN
    ALTER TABLE `ip_other_contributions` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_other_contributions` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_other_contributions` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='active_until')) THEN
    ALTER TABLE `ip_other_contributions` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_other_contributions' AND column_name='action')) THEN
   ALTER TABLE `ip_other_contributions` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_other_contributions_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_other_contributions_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_other_contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_project_contributions table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_project_contributions LIKE $[database].ip_project_contributions; 
DROP PROCEDURE IF EXISTS update_ip_project_contributions_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_project_contributions_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='record_id')) THEN
    ALTER TABLE `ip_project_contributions` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='is_active')) THEN
    ALTER TABLE `ip_project_contributions` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='active_since')) THEN
    ALTER TABLE `ip_project_contributions` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='active_until')) THEN
    ALTER TABLE `ip_project_contributions` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_contributions' AND column_name='action')) THEN
   ALTER TABLE `ip_project_contributions` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_project_contributions_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_project_contributions_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_project_contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the ip_project_indicators table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS ip_project_indicators LIKE $[database].ip_project_indicators; 
DROP PROCEDURE IF EXISTS update_ip_project_indicators_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_ip_project_indicators_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='record_id')) THEN
    ALTER TABLE `ip_project_indicators` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='is_active')) THEN
    ALTER TABLE `ip_project_indicators` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='active_since')) THEN
    ALTER TABLE `ip_project_indicators` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='active_until')) THEN
    ALTER TABLE `ip_project_indicators` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_project_indicators' AND column_name='action')) THEN
   ALTER TABLE `ip_project_indicators` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_ip_project_indicators_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_ip_project_indicators_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the ip_project_indicators table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the next_users table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS next_users LIKE $[database].next_users; 
DROP PROCEDURE IF EXISTS update_next_users_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_next_users_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='record_id')) THEN
    ALTER TABLE `next_users` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='is_active')) THEN
    ALTER TABLE `next_users` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='active_since')) THEN
    ALTER TABLE `next_users` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='active_until')) THEN
    ALTER TABLE `next_users` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='next_users' AND column_name='action')) THEN
   ALTER TABLE `next_users` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_next_users_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_next_users_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the next_users table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the project_budgets table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS project_budgets LIKE $[database].project_budgets; 
DROP PROCEDURE IF EXISTS update_project_budgets_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_budgets_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='record_id')) THEN
    ALTER TABLE `project_budgets` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='is_active')) THEN
    ALTER TABLE `project_budgets` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='active_since')) THEN
    ALTER TABLE `project_budgets` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='active_until')) THEN
    ALTER TABLE `project_budgets` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_budgets' AND column_name='action')) THEN
   ALTER TABLE `project_budgets` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_budgets_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_budgets_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the project_budgets table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the project_outcomes table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS project_outcomes LIKE $[database].project_outcomes; 
DROP PROCEDURE IF EXISTS update_project_outcomes_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_outcomes_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='record_id')) THEN
    ALTER TABLE `project_outcomes` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='is_active')) THEN
    ALTER TABLE `project_outcomes` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='active_since')) THEN
    ALTER TABLE `project_outcomes` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='active_until')) THEN
    ALTER TABLE `project_outcomes` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='action')) THEN
   ALTER TABLE `project_outcomes` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_outcomes_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_outcomes_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the project_outcomes table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the project_focuses table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS project_focuses LIKE $[database].project_focuses; 
DROP PROCEDURE IF EXISTS update_project_focuses_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_focuses_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='record_id')) THEN
    ALTER TABLE `project_focuses` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='is_active')) THEN
    ALTER TABLE `project_focuses` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='active_since')) THEN
    ALTER TABLE `project_focuses` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='active_until')) THEN
    ALTER TABLE `project_focuses` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='action')) THEN
   ALTER TABLE `project_focuses` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_focuses_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_focuses_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the project_focuses table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Creation and modifications to the project_partners table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS project_partners LIKE $[database].project_partners; 
DROP PROCEDURE IF EXISTS update_project_partners_history_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_partners_history_table()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='record_id')) THEN
    ALTER TABLE `project_partners` ADD `record_id` BIGINT NOT NULL AFTER `id`;
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='is_active')) THEN
    ALTER TABLE `project_partners` DROP `is_active`;
  END IF;
    
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='active_since')) THEN
    ALTER TABLE `project_partners` CHANGE `active_since` `active_since` DATETIME NOT NULL;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='active_until')) THEN
    ALTER TABLE `project_partners` ADD `active_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `active_since`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='action')) THEN
   ALTER TABLE `project_partners` ADD `action` varchar(15) NOT NULL;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_partners_history_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_partners_history_table;

-- -----------------------------------------------------------------------------
--      End of creation and modifications to the project_partners table
-- -----------------------------------------------------------------------------