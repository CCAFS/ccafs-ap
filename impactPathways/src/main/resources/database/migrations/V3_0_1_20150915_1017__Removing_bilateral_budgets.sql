--------------------------------------------------------------------------------------------------------------
--  Removing the bilateral budgets filled in previous years as they don't have the information about
--  which project is the co-financing
--------------------------------------------------------------------------------------------------------------

UPDATE project_budgets pb 
SET is_active = FALSE
WHERE budget_type = 2 AND cofinance_project_id IS NULL;