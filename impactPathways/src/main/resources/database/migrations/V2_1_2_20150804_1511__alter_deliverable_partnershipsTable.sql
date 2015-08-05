-- -----------------------------------------------------------------------------
--                         Altering deliverable_partnerships table 
-- -----------------------------------------------------------------------------
DROP TRIGGER IF EXISTS `deliverable_partnerships_AFTER_INSERT`;
DROP TRIGGER IF EXISTS `deliverable_partnerships_AFTER_UPDATE`;
ALTER TABLE `deliverable_partnerships` 
ADD COLUMN `partner_id` BIGINT(20) NULL DEFAULT NULL COMMENT '' AFTER `deliverable_id`,
ADD INDEX `FK_deliverable_partnerships_projectPartners_partner_id_idx` (`partner_id` ASC)  COMMENT '';
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnerships_project_partners_partner_id`
  FOREIGN KEY (`partner_id`)
  REFERENCES `project_partners` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
