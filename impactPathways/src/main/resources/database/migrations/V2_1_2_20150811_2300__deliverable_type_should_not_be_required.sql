-- -----------------------------------------------------------------------------
--        Deliverable type is not required any more.
-- -----------------------------------------------------------------------------

ALTER TABLE `deliverables` 
DROP FOREIGN KEY `deliverables_deliverables_type`;
ALTER TABLE `deliverables` 
CHANGE COLUMN `type_id` `type_id` BIGINT(20) NULL ;
ALTER TABLE `deliverables` 
ADD CONSTRAINT `deliverables_deliverables_type`
  FOREIGN KEY (`type_id`)
  REFERENCES `deliverable_types` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

