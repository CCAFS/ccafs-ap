-----------------------------------------------------------------------
--   Removing unique index project partner
-----------------------------------------------------------------------
ALTER TABLE `project_partners` 
DROP INDEX `UK_project_partners` ;

use ccafspr_ip_history;
ALTER TABLE `project_partners` 
DROP INDEX `UK_project_partners` ;