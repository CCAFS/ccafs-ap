----------------------------------------------------------------------------
--    Updating projects cofinancing status
----------------------------------------------------------------------------

-- Saving the  last register number on history project table
SELECT @last_register_history:= MAX(hpro.id) FROM ccafspr_ip_history.projects hpro;
SELECT @last_register_history;

-- Set all projects no cofinancing 
UPDATE ccafspr_ip.projects p
SET p.is_cofinancing = 0;

-- Search in project_cofinancing_linkages table the projects cofinancing and to update in project table.
UPDATE ccafspr_ip.projects p
SET p.is_cofinancing = 1
WHERE p.id IN (SELECT  pcl.bilateral_project_id FROM ccafspr_ip.project_cofinancing_linkages pcl WHERE pcl.bilateral_project_id = p.id);

-- removing the last register added on history project table
DELETE FROM ccafspr_ip_history.projects  WHERE id  > @last_register_history;