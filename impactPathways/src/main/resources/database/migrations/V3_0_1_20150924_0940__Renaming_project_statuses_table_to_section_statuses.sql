----------------------------------------------------------------------------
--    Renaming 'project_statuses' table to 'section_statuses' 
----------------------------------------------------------------------------
ALTER TABLE `project_statuses` 
DROP FOREIGN KEY `FK_project_statuses_project_id`;
ALTER TABLE `project_statuses` 
CHANGE COLUMN `section_name` `section_name` VARCHAR(255) NOT NULL COMMENT 'Some section name (would be the action name)' , 
COMMENT = 'This table will save the current section status with regards the missing fields that are required to be filled by the system.' , RENAME TO  `section_statuses` ;
ALTER TABLE `project_statuses` 
ADD CONSTRAINT `FK_section_statuses_project_id`
  FOREIGN KEY (`project_id`)
  REFERENCES `projects` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

