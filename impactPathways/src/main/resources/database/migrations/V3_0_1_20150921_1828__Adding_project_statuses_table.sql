--------------------------------------------------------------------------------------------------------------
--  Adding table for saving the status of a specific project with regards the missing fields that are required 
--  to be filled by the system in a specific cycle (Planning or Reporting).
--------------------------------------------------------------------------------------------------------------
CREATE TABLE `project_statuses` (
  `id` BIGINT(20) NOT NULL COMMENT '',
  `project_id` BIGINT(20) NOT NULL COMMENT '',
  `cycle` ENUM('Planning', 'Reporting') NOT NULL COMMENT '',
  `section_name` VARCHAR(255) NOT NULL COMMENT 'some section name (would be the action name)',
  `missing_fields` TEXT NULL COMMENT 'The list of missing fields per section',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `FK_project_statuses_project_id_idx` (`project_id` ASC)  COMMENT '',
  CONSTRAINT `FK_project_statuses_project_id`
    FOREIGN KEY (`project_id`)
    REFERENCES `projects` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
COMMENT = 'This table will save the current project status with regards the missing fields that are required to be filled by the system.';
