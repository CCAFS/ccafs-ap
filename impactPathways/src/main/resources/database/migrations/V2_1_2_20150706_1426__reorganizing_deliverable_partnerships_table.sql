-- -----------------------------------------------------------------------------
--        Reorganizing deliverable_partnerships table 
-- -----------------------------------------------------------------------------

-- Adding auto-increment
ALTER TABLE `deliverable_partnerships` 
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT;

-- Deleting foreign relation.
ALTER TABLE `deliverable_partnerships` 
DROP FOREIGN KEY `FK_deliverable_partnership_project_partner`;

-- Renaming column. 
ALTER TABLE `deliverable_partnerships` 
CHANGE COLUMN `project_partner_id` `institution_id` BIGINT(20) NOT NULL ;

-- making foreign key to instutions instead of project partners.
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnership_institution`
  FOREIGN KEY (`institution_id`)
  REFERENCES `ccafs_pr`.`institutions` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
