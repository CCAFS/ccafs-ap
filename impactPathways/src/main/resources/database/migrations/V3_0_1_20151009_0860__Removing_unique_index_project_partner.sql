-----------------------------------------------------------------------
--   Removing unique index project partner
-----------------------------------------------------------------------
ALTER TABLE `project_partners` 
DROP INDEX `UK_project_partners` ;


ALTER TABLE $[database]_history.project_partners 
DROP INDEX `UK_project_partners` ;


ALTER TABLE `project_partner_contributions` 
DROP INDEX `UK_project_partner_contributions` ;


ALTER TABLE $[database]_history.project_partner_contributions 
DROP INDEX `UK_project_partner_contributions` ;

ALTER TABLE `project_partner_persons` 
DROP INDEX `UK_project_partner_persons` ;


ALTER TABLE $[database]_history.project_partner_persons 
DROP INDEX `UK_project_partner_persons` ;