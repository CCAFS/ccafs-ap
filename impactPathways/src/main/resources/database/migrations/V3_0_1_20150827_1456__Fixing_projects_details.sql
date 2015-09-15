-----------------------------------------------------------
-- Fixing details in projects and project_partners table
-----------------------------------------------------------

ALTER TABLE `project_partners` DROP COLUMN `is_leader` ;

-- Deleting a record of a project that had 2 leaders
DELETE FROM `project_partner_persons` WHERE `id` = 805;

-- Removing all the budgets that belong to institution which are not partners or ppa partners of the project.
UPDATE project_budgets pb 
SET pb.is_active = FALSE 
WHERE pb.institution_id NOT IN
( SELECT  institution_id FROM project_partners pp
  INNER JOIN institutions i ON pp.institution_id = i.id
  WHERE pp.project_id = pb.project_id AND pp.is_active = TRUE AND i.is_ppa = TRUE
  GROUP BY pp.institution_id, pp.is_active
) 