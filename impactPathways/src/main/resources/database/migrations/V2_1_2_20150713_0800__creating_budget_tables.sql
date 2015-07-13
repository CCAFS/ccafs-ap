-- -----------------------------------------------------------------------------
-- Creating budget tables.
-- -----------------------------------------------------------------------------

-- Copy temporal of budget types table for creating other tables.

DROP TABLE IF EXISTS budget_types_copy;
CREATE TABLE budget_types_copy (
id BIGINT(20) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
PRIMARY KEY (id)
);
INSERT INTO budget_types_copy VALUES (1,'W1 W2 Budget'),(2,'W3/Bilateral Budget');

-- Temporal table 
DROP TABLE IF EXISTS project_budgets_temp;
CREATE TABLE project_budgets_temp (
id BIGINT(20) NOT NULL AUTO_INCREMENT,
project_id BIGINT(20) NULL,
amount BIGINT(20) NULL,
`type` BIGINT(20) NULL,
year INT(4) NULL,
cofinance_project_id BIGINT(20) NULL,
institution_id BIGINT(20) NULL,
gender_percentage DOUBLE NULL,
PRIMARY KEY (id),
INDEX FK_project_budgets_temp_projects_idx (project_id ASC),
INDEX FK_project_cofinance_bud_temp_projects_idx (cofinance_project_id ASC),
INDEX FK_institutions_project_bud_temp_idx (institution_id ASC),

CONSTRAINT FK_project_bud_temp_projects
FOREIGN KEY (project_id)
REFERENCES projects (id)
ON DELETE CASCADE
ON UPDATE CASCADE,

CONSTRAINT FK_project_bud_temp_cofinance_projects
FOREIGN KEY (cofinance_project_id)
REFERENCES projects (id)
ON DELETE CASCADE
ON UPDATE CASCADE,

CONSTRAINT FK_project_bud_temp_institutions
FOREIGN KEY (institution_id)
REFERENCES institutions (id)
ON DELETE CASCADE
ON UPDATE CASCADE
,
CONSTRAINT FK_project_bud_temp_budget_types
FOREIGN KEY (`type`)
REFERENCES budget_types_copy(id)
ON DELETE CASCADE
ON UPDATE CASCADE);

-- Creating table project_mog_budgets that will be used to save the budget assigned to the mog.
DROP TABLE IF EXISTS project_mog_budgets;
CREATE TABLE project_mog_budgets(
id BIGINT(20) NOT NULL AUTO_INCREMENT,
project_id BIGINT(20) NOT NULL,
mog_id BIGINT(20) NOT NULL,
total_contribution INT NULL,
gender_contribution INT NULL,
PRIMARY KEY (id),
INDEX FK_project_mog_budgets_projects_idx (project_id ASC),
INDEX FK_project_mog_budget_ip_elements_idx (mog_id ASC),

CONSTRAINT FK_project_mog_budgets_projects
FOREIGN KEY (project_id)
REFERENCES projects (id)
ON DELETE CASCADE
ON UPDATE CASCADE,

CONSTRAINT FK_project_mog_budget_ip_elements
FOREIGN KEY (mog_id)
REFERENCES ip_elements (id)
ON DELETE CASCADE
ON UPDATE CASCADE);

-- Creating table project_budget_overheads that will be used for save budget overheads.
DROP TABLE IF EXISTS project_budget_overheads;
CREATE TABLE project_budget_overheads (
id BIGINT(20) NOT NULL AUTO_INCREMENT,
project_id BIGINT(20) NULL,
cost_recovered INT NULL,
contracted_overhead INT NULL,
PRIMARY KEY (id),
INDEX FK_project_budgets_ov_projects_idx (project_id ASC),

CONSTRAINT FK_project_budgets_ov_projects
FOREIGN KEY (project_id)
REFERENCES projects (id)
ON DELETE CASCADE
ON UPDATE CASCADE);