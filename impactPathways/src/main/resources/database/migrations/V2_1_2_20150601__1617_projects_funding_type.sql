-- -----------------------------------------------------------------------------
-- Create the fields first name and last name in the table users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS projects_funding_type;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE projects_funding_type()
BEGIN
  
	-- Adding new column that will differenciate the project type.
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='type')) THEN
    ALTER TABLE `projects` 
    ADD COLUMN `type` ENUM('CORE', 'BILATERAL_COFUNDED', 'BILATERAL_STANDALONE') NOT NULL DEFAULT 'CORE' AFTER `end_date`;
  END IF;
  
  -- Deleting expected_project_leader column as it is not needed any more.
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='expected_project_leader_id')) THEN
    ALTER TABLE `projects` 
    DROP FOREIGN KEY `FK_projects_expected_project_leaders`;
		ALTER TABLE `projects` 
		DROP COLUMN `expected_project_leader_id`,
		DROP INDEX `FK_projects_expected_project_leaders_idx`;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL projects_funding_type();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS projects_funding_type;