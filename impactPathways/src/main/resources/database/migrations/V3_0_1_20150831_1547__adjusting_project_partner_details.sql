----------------------------------------------------------------------------
-- Adjusting columns and foreign keys in project partners contributions
----------------------------------------------------------------------------

-- This table should be empty, let truncate it, just in case anyone has test data
TRUNCATE TABLE `project_partner_contributions`;

ALTER TABLE `project_partner_contributions` 
  DROP FOREIGN KEY `FK_project_partners_contributions_institutions`, 
  DROP FOREIGN KEY `FK_project_partners_contributions_project_partner` ;

ALTER TABLE `project_partner_contributions` 
  CHANGE COLUMN `contribution_institution_id` `project_partner_contributor_id` BIGINT(20) NOT NULL , 
  ADD CONSTRAINT `FK_project_partners_contributions_partner` FOREIGN KEY (`project_partner_contributor_id` )
    REFERENCES `project_partners` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_partners_contributions_project_partner_contributior` FOREIGN KEY (`project_partner_id` )
    REFERENCES `project_partners` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `project_partner_contributions` 
ADD UNIQUE INDEX `UK_project_partner_contributions` (`project_partner_id` ASC, `project_partner_contributor_id` ASC) ;
