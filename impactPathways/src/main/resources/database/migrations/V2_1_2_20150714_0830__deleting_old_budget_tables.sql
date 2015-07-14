-- -----------------------------------------------------------------------------
-- Deleting budget and project_budgets tables 
-- -----------------------------------------------------------------------------

-- Changing name column type 
 ALTER TABLE project_budgets_temp CHANGE type budget_type BIGINT(20);

-- Deleting foreign keys of table budgets
 ALTER TABLE activity_budgets DROP FOREIGN KEY FK_activity_budgets_budget_id;
ALTER TABLE project_budgets DROP FOREIGN KEY FK_budget_id;
ALTER TABLE budgets DROP FOREIGN KEY FK_budget_type;

-- Deleting tables project_budgets and budgets
DROP TABLE IF EXISTS project_budgets;
DROP TABLE IF EXISTS budgets;
DROP TABLE IF EXISTS budget_types;

-- Renamed temporal tables 
 ALTER TABLE project_budgets_temp RENAME project_budgets;
 ALTER TABLE budget_types_copy RENAME budget_types;
