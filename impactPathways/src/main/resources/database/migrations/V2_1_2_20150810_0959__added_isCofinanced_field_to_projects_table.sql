-- -----------------------------------------------------------------------------
--        Moving is_global field in projects
-- -----------------------------------------------------------------------------

ALTER TABLE projects ADD COLUMN `is_cofinancing` TINYINT(1) NOT NULL DEFAULT '0'  AFTER `is_global` ;

-- Set the is_cofinancing column to one for the bilateral projects that are already cofinancing core projects.
UPDATE projects p 
INNER JOIN `project_cofinancing_linkages` pcl ON p.id = pcl.bilateral_project_id 
SET p.`is_cofinancing` = 1 


-- Removing unused column 
ALTER TABLE `project_cofinancing_linkages` DROP COLUMN `is_cofinancing` ;


