-----------------------------------------------------------------------
--   Adding a new column in section_statuses table to foreign key with
-----------------------------------------------------------------------

ALTER TABLE `section_statuses` 
DROP FOREIGN KEY `FK_section_statuses_project_id`;
ALTER TABLE `section_statuses` 
CHANGE COLUMN `project_id` `project_id` BIGINT(20) NULL COMMENT '' ,
ADD COLUMN `deliverable_id` BIGINT(20) NULL COMMENT '' AFTER `project_id`,
ADD INDEX `FK_section_statuses_deliverable_id_idx` (`deliverable_id` ASC)  COMMENT '';
ALTER TABLE `section_statuses` 
ADD CONSTRAINT `FK_section_statuses_project_id`
  FOREIGN KEY (`project_id`)
  REFERENCES `projects` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `FK_section_statuses_deliverable_id`
  FOREIGN KEY (`deliverable_id`)
  REFERENCES `deliverables` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
