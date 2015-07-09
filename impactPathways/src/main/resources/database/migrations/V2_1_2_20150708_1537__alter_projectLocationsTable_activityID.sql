-- -----------------------------------------------------------------------------
--        Altering project_locations table deleting activity_id field
-- -----------------------------------------------------------------------------

ALTER TABLE `project_locations` 
DROP COLUMN `activity_id`,
DROP INDEX `UK_activity_locations_key`;