-- -----------------------------------------------------------------------------
-- Migrating project budgets.
-- -----------------------------------------------------------------------------

INSERT INTO project_budgets_temp (project_id, amount, type, year, institution_id, cofinance_project_id, gender_percentage)
SELECT pb.project_id, b.amount, IF(b.budget_type = 7, 1, 2), b.year, b.institution_id, NULL, NULL   
FROM budgets b INNER JOIN project_budgets pb ON b.id = pb.budget_id
WHERE b.budget_type = 7 OR budget_type = 8;