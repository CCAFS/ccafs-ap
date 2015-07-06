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
  REFERENCES `institutions` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
-- Deleting foreign key names for renaming purposes.  
ALTER TABLE `deliverable_partnerships` 
DROP FOREIGN KEY `FK_deliverable_partnership_deliverable`,
DROP FOREIGN KEY `FK_deliverable_partnership_institution`,
DROP FOREIGN KEY `FK_deliverable_partnership_user`;

-- Adding log columns
ALTER TABLE `deliverable_partnerships` 
ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT 1 AFTER `user_id`,
ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `is_active`,
ADD COLUMN `created_by` BIGINT(20) NOT NULL AFTER `active_since`,
ADD COLUMN `modified_by` BIGINT(20) NOT NULL AFTER `created_by`,
ADD COLUMN `modification_justification` TEXT NOT NULL AFTER `modified_by`,
ADD INDEX `FK_deliverable_partnerships_users_created_by_idx` (`created_by` ASC),
ADD INDEX `FK_deliverable_partnerships_users_modified_by_idx` (`modified_by` ASC);

-- Adding new and old foreign keys.
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnerships_deliverable`
  FOREIGN KEY (`deliverable_id`)
  REFERENCES `deliverables` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `FK_deliverable_partnerships_institution`
  FOREIGN KEY (`institution_id`)
  REFERENCES `institutions` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `FK_deliverable_partnerships_users_user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `FK_deliverable_partnerships_users_created_by`
  FOREIGN KEY (`created_by`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
ADD CONSTRAINT `FK_deliverable_partnerships_users_modified_by`
  FOREIGN KEY (`modified_by`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

