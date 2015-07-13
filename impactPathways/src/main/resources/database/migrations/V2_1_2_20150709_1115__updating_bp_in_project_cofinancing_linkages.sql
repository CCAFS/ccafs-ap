-- -----------------------------------------------------------------------------
-- Updating bilateral projects in table project cofinancing linkages 
-- -----------------------------------------------------------------------------

-- Adding temporal column for to save the  id.
 ALTER TABLE project_cofinancing_linkages ADD is_cofinancing  tinyint(1) DEFAULT 0 AFTER is_active;
 
 UPDATE project_cofinancing_linkages pcl INNER JOIN projects p ON pcl.bilateral_project_id = p.id 
 SET pcl.is_cofinancing = 1
 WHERE p.type = "BILATERAL";