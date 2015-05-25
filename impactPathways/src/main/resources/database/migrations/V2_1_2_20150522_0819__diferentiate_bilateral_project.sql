-- -----------------------------------------------------------------------------
-- Reorganization of the tables used to link an ip_elemnent with an ip_program
-- -----------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS add_core_field_in_projects;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_core_field_in_projects()
BEGIN

  -- Add a column to know if a project is core o bilateral 
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='is_core')) THEN
    ALTER TABLE `projects` ADD `is_core` BOOLEAN NOT NULL DEFAULT TRUE AFTER `end_date`;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_core_field_in_projects();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_core_field_in_projects;

-- -----------------------------------------------------------------------------
-- We need to update the table in the log database too.
-- -----------------------------------------------------------------------------

use $[database]_history;

DROP PROCEDURE IF EXISTS add_core_field_in_projects;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_core_field_in_projects()
BEGIN

  -- Add a column to know if a project is core o bilateral 
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='is_core')) THEN
    ALTER TABLE `projects` ADD `is_core` BOOLEAN NOT NULL DEFAULT TRUE AFTER `end_date`;
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_core_field_in_projects();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_core_field_in_projects;

use $[database];