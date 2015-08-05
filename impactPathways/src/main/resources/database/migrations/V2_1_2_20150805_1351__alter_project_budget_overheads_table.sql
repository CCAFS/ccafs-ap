-- -----------------------------------------------------------------------------
--                         Altering project_budget_overheads table 
-- -----------------------------------------------------------------------------

ALTER TABLE `project_budget_overheads` 
  CHANGE COLUMN `cost_recovered` `cost_recovered` TINYINT(1) NOT NULL DEFAULT '0'  ;
