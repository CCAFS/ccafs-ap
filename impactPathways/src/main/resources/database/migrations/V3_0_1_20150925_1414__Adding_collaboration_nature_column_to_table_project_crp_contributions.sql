----------------------------------------------------------------------------
--    Adding 'collaboration_nature' column to table 'project_crp_contributions' 
----------------------------------------------------------------------------
ALTER TABLE `project_crp_contributions` 
ADD COLUMN `collaboration_nature` TEXT NULL AFTER `crp_id`;

