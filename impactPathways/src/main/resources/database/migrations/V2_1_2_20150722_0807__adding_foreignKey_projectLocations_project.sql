-- -----------------------------------------------------------------------------------
-- Adding foreign key to project_locations and renaming some indexes.
-- -----------------------------------------------------------------------------------

ALTER TABLE `project_locations` 
DROP INDEX `FK_activity_locations_loc_element_types_idx` ,
ADD INDEX `fk_project_locations_loc_element_types_idx` (`loc_element_id` ASC)  COMMENT '',
DROP INDEX `fk_activity_locations_users_created_by` ,
ADD INDEX `fk_project_locations_users_created_by` (`created_by` ASC)  COMMENT '',
DROP INDEX `fk_activity_locations_users_modified_by` ,
ADD INDEX `fk_project_locations_users_modified_by` (`modified_by` ASC)  COMMENT '',
ADD INDEX `fk_project_locations_projects_idx` (`project_id` ASC)  COMMENT '';
ALTER TABLE `project_locations` 
ADD CONSTRAINT `fk_project_locations_projects_idx`
  FOREIGN KEY (`project_id`)
  REFERENCES `projects` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;