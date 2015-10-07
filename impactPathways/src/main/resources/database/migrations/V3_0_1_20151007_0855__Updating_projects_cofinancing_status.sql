----------------------------------------------------------------------------
--    Updating projects cofinancing status
----------------------------------------------------------------------------

-- Set all projects no cofinancing 
UPDATE projects p
SET p.is_cofinancing = 0;

-- Search in project_cofinancing_linkages table the projects cofinancing and to update in project table.
UPDATE projects p
SET p.is_cofinancing = 1
WHERE p.id IN (SELECT  pcl.bilateral_project_id FROM project_cofinancing_linkages pcl WHERE pcl.bilateral_project_id = p.id); 