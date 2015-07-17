-- -----------------------------------------------------------------------------
-- Create column requires_workplan_upload in the projects table
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS add_requires_workplan_upload_to_project;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_requires_workplan_upload_to_project()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='requires_workplan_upload')) THEN
    ALTER TABLE `projects` ADD COLUMN `requires_workplan_upload` TINYINT(1) NULL DEFAULT '0'  AFTER `liaison_user_id`;
  END IF;


END $$
DELIMITER ;

-- Execute the stored procedure
CALL add_requires_workplan_upload_to_project();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_requires_workplan_upload_to_project;