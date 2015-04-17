-- -----------------------------------------------------------------------------
-- In order to create the history tables all the tables that will have an history
-- should has an id column to allow the track of changes.
-- 
-- This script will add an id column to all the tables that don't have it yet.
-- -----------------------------------------------------------------------------



-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_budgets_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_activity_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_budget_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id_idx')) THEN
    ALTER TABLE activity_budgets DROP KEY `FK_activity_budgets_budget_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE activity_budgets DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='id')) THEN
    ALTER TABLE activity_budgets ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_cross_cutting_themes table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_cross_cutting_themes_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_cross_cutting_themes_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND constraint_name='FK_projects_themes_projects_idx')) THEN
    ALTER TABLE activity_cross_cutting_themes DROP FOREIGN KEY `FK_projects_themes_projects_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND constraint_name='FK_projects_themes_cutting_themes_idx')) THEN
    ALTER TABLE activity_cross_cutting_themes DROP FOREIGN KEY `FK_projects_themes_cutting_themes_idx`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND column_name='id')) THEN
    ALTER TABLE activity_cross_cutting_themes ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND constraint_name='UK_activity_cross_cutting_themes')) THEN
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT `UK_activity_cross_cutting_themes` UNIQUE (`activity_id`, `theme_id`);
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND constraint_name='FK_activity_cross_cutting_themes_activity_id')) THEN
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT `FK_activity_cross_cutting_themes_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_cross_cutting_themes' AND constraint_name='FK_activity_cross_cutting_themes')) THEN
    ALTER TABLE activity_cross_cutting_themes ADD CONSTRAINT `FK_activity_cross_cutting_themes` FOREIGN KEY (`theme_id`) REFERENCES `ip_cross_cutting_themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_cross_cutting_themes_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_cross_cutting_themes_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_cross_cutting_themes table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_locations table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_locations_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_locations_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='FK_activity_locations_activities')) THEN
    ALTER TABLE activity_locations DROP FOREIGN KEY `FK_activity_locations_activities`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='FK_activity_locations_loc_element_types')) THEN
    ALTER TABLE activity_locations DROP FOREIGN KEY `FK_activity_locations_loc_element_types`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='FK_activity_locations_loc_element_types_idx')) THEN
    ALTER TABLE activity_locations DROP KEY `FK_activity_locations_loc_element_types_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE activity_locations DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_locations' AND column_name='id')) THEN
    ALTER TABLE activity_locations ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='UK_activity_locations_key')) THEN
    ALTER TABLE activity_locations ADD CONSTRAINT `UK_activity_locations_key` UNIQUE (`activity_id`, `loc_element_id`);
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='FK_activity_locations_activities')) THEN
    ALTER TABLE activity_locations ADD CONSTRAINT `FK_activity_locations_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_locations' AND constraint_name='FK_activity_locations_loc_element_types')) THEN
    ALTER TABLE activity_locations ADD CONSTRAINT `FK_activity_locations_loc_element_types` FOREIGN KEY (`loc_element_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_locations_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_locations_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_locations table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the ip_deliverable_contributions table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_ip_deliverable_contributions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_ip_deliverable_contributions_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_contributionID')) THEN
    ALTER TABLE ip_deliverable_contributions DROP FOREIGN KEY `FK_deliverables_ipProjectContributions_contributionID`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_deliverableID')) THEN
    ALTER TABLE ip_deliverable_contributions DROP FOREIGN KEY `FK_deliverables_ipProjectContributions_deliverableID`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='UK_delverable_contributions')) THEN
    ALTER TABLE ip_deliverable_contributions DROP KEY `UK_delverable_contributions`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_contributionID_idx')) THEN
    ALTER TABLE ip_deliverable_contributions DROP KEY `FK_deliverables_ipProjectContributions_contributionID_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_deliverableID_idx')) THEN
    ALTER TABLE ip_deliverable_contributions DROP KEY `FK_deliverables_ipProjectContributions_deliverableID_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='PK_deliverable_contributions')) THEN
    ALTER TABLE ip_deliverable_contributions DROP KEY `PK_deliverable_contributions`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE ip_deliverable_contributions DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND column_name='id')) THEN
    ALTER TABLE ip_deliverable_contributions ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='UK_ip_deliverable_contributions_key')) THEN
    ALTER TABLE ip_deliverable_contributions ADD CONSTRAINT `UK_ip_deliverable_contributions_key` UNIQUE (`project_contribution_id`,`deliverable_id`);
  END IF;
  
	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_contributionID')) THEN
    ALTER TABLE ip_deliverable_contributions ADD CONSTRAINT `FK_deliverables_ipProjectContributions_contributionID` FOREIGN KEY (`project_contribution_id`) REFERENCES `ip_project_contributions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='ip_deliverable_contributions' AND constraint_name='FK_deliverables_ipProjectContributions_deliverableID')) THEN
    ALTER TABLE ip_deliverable_contributions ADD CONSTRAINT `FK_deliverables_ipProjectContributions_deliverableID` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_ip_deliverable_contributions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_ip_deliverable_contributions_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the ip_deliverable_contributions table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the project_focuses table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_project_focuses_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_project_focuses_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='FK_project_focus_project_id_idx')) THEN
    ALTER TABLE project_focuses DROP KEY `FK_project_focus_project_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='FK_project_focus_program_id_idx')) THEN
    ALTER TABLE project_focuses DROP KEY `FK_project_focus_program_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE project_focuses DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_focuses' AND column_name='id')) THEN
    ALTER TABLE project_focuses ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='UK_project_focuses_key')) THEN
    ALTER TABLE project_focuses ADD CONSTRAINT `UK_project_focuses_key` UNIQUE (`project_id`, `program_id`);
  END IF;
  
	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='FK_project_focuses_project_id')) THEN
    ALTER TABLE project_focuses ADD CONSTRAINT `FK_project_focuses_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='project_focuses' AND constraint_name='FK_project_focuses_ip_program_id')) THEN
    ALTER TABLE project_focuses ADD CONSTRAINT `FK_project_focuses_ip_program_id` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_project_focuses_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_project_focuses_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the project_focuses table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_budgets_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_activity_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_budget_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id_idx')) THEN
    ALTER TABLE activity_budgets DROP KEY `FK_activity_budgets_budget_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE activity_budgets DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='id')) THEN
    ALTER TABLE activity_budgets ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='UK_activity_budgets_key')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `UK_activity_budgets_key` UNIQUE (`activity_id`, `loc_element_id`);
  END IF;
  
	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_budgets_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_activity_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_budget_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id_idx')) THEN
    ALTER TABLE activity_budgets DROP KEY `FK_activity_budgets_budget_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE activity_budgets DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='id')) THEN
    ALTER TABLE activity_budgets ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='UK_activity_budgets_key')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `UK_activity_budgets_key` UNIQUE (`activity_id`, `loc_element_id`)
  END IF;
  
	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--      Add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_id_to_activity_budgets_table()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_activity_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets DROP FOREIGN KEY `FK_activity_budgets_budget_id`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id_idx')) THEN
    ALTER TABLE activity_budgets DROP KEY `FK_activity_budgets_budget_id_idx`;
  END IF;

	IF EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='PRIMARY')) THEN
    ALTER TABLE activity_budgets DROP PRIMARY KEY;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND column_name='id')) THEN
    ALTER TABLE activity_budgets ADD `id` BIGINT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY  (`id`);
  END IF;

  IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='UK_activity_budgets_key')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `UK_activity_budgets_key` UNIQUE (`activity_id`, `loc_element_id`)
  END IF;
  
	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_activity_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

	IF NOT EXISTS ((SELECT * FROM information_schema.table_constraints WHERE table_schema=DATABASE() AND table_name='activity_budgets' AND constraint_name='FK_activity_budgets_budget_id')) THEN
    ALTER TABLE activity_budgets ADD CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  END IF;

SET FOREIGN_KEY_CHECKS = 1;
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_id_to_activity_budgets_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_id_to_activity_budgets_table;

-- -----------------------------------------------------------------------------
--      End of add the ID column to the activity_budgets table
-- -----------------------------------------------------------------------------
