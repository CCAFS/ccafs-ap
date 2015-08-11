-- -----------------------------------------------------------------------------
--        Deleting unused columns in the deliverable_partnerships table.
-- -----------------------------------------------------------------------------

-- Deleting triggers
DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_insert` $$
DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_update` $$
DELIMITER ;

-- Deleting column
ALTER TABLE `deliverable_partnerships` 
DROP FOREIGN KEY `FK_deliverable_partnerships_project_partners_partner_id`,
DROP FOREIGN KEY `FK_deliverable_partnerships_users_user_id`,
DROP FOREIGN KEY `FK_deliverable_partnerships_institution`;
ALTER TABLE `deliverable_partnerships` 
DROP COLUMN `user_id`,
DROP COLUMN `institution_id`,
CHANGE COLUMN `partner_id` `partner_id` BIGINT(20) NOT NULL ,
DROP INDEX `deliverable_partnership_user_idx` ,
DROP INDEX `deliverable_project_partner_idx` ;
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnerships_project_partners_partner_id`
  FOREIGN KEY (`partner_id`)
  REFERENCES `project_partners` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



