-- -----------------------------------------------------------------------------
-- Dropping column that refers the deliverable to the activity.
-- -----------------------------------------------------------------------------

ALTER TABLE `deliverables` 
DROP FOREIGN KEY `deliverables_activities`;
ALTER TABLE `deliverables` 
DROP COLUMN `activity_id`,
DROP INDEX `deliverables_activities_idx` ;