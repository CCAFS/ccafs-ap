-- -----------------------------------------------------------------------------
--        Create table `project_outcome_gender_contributions`
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS update_project_outcomes_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE update_project_outcomes_table()
BEGIN

  -- Add the gender_dimension column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_outcomes' AND column_name='gender_dimension')) THEN
    ALTER TABLE `project_outcomes` ADD COLUMN `gender_dimension` TEXT NULL  AFTER `stories` ;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL update_project_outcomes_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS update_project_outcomes_table;
