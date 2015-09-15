----------------------------------------------------------------------------
-- Deleting triggers
----------------------------------------------------------------------------
DROP TRIGGER IF EXISTS `after_deliverable_partnerships_insert`;
DROP TRIGGER IF EXISTS `after_deliverable_partnerships_update`;

----------------------------------------------------------------------------
-- Removing all the Partners contributing to the deliverables.
----------------------------------------------------------------------------
TRUNCATE deliverable_partnerships;

----------------------------------------------------------------------------
-- Adding a missing foreign key
----------------------------------------------------------------------------
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnerships_partner_person_id`
  FOREIGN KEY (`partner_person_id`)
  REFERENCES `project_partner_persons` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



