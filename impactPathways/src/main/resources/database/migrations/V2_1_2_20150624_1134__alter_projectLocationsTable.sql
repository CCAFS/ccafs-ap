-- -----------------------------------------------------------------------------
--        Migrated Activity Locations as Project Locations
-- -----------------------------------------------------------------------------

ALTER TABLE `project_locations` 
DROP FOREIGN KEY `FK_activity_locations_activities`;
ALTER TABLE `project_locations` 
CHANGE COLUMN `activity_id` `activity_id` BIGINT(20) NULL COMMENT '' ;
ALTER TABLE `project_locations` 
ADD CONSTRAINT `FK_activity_locations_activities`
  FOREIGN KEY (`activity_id`)
  REFERENCES `activities` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
