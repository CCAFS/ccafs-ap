-- -----------------------------------------------------------------------------
--                         Altering activities table 
-- -----------------------------------------------------------------------------

ALTER TABLE `activities` 
DROP FOREIGN KEY `FK_activities_project_partner_id`,
DROP FOREIGN KEY `FK_activities_leader_id`,
DROP FOREIGN KEY `FK_activities_expected_leader_id`;
ALTER TABLE `activities` 
DROP COLUMN `leader_id`,
DROP COLUMN `expected_leader_id`,
CHANGE COLUMN `project_partner_id` `leader_id` BIGINT(20) NOT NULL COMMENT '' ,
DROP INDEX `FK_activities_activity_leaders_idx` ,
DROP INDEX `FK_activities_expected_leader_id_idx` ;
ALTER TABLE `activities` 
ADD CONSTRAINT `FK_activities_project_partner_id`
  FOREIGN KEY (`leader_id`)
  REFERENCES `project_partners` (`id`);