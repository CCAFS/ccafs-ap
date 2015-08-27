-----------------------------------------------------------
-- Fixing details in projects and project_partners table
-----------------------------------------------------------

ALTER TABLE `project_partners` DROP COLUMN `is_leader` ;

-- Deleting a record of a project that have 2 leaders
DELETE FROM `project_partner_persons` WHERE `id` = 805;
