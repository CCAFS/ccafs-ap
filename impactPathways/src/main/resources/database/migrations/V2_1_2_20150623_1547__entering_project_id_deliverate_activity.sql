-- -----------------------------------------------------------------------------
--        Entered project_id in the table deliverables
-- -----------------------------------------------------------------------------

-- Adding the column project_id in table deliverables.
ALTER TABLE deliverables ADD project_id  bigint(20)  NOT NULL AFTER id;

-- Entering data of project_id in table deliverables
UPDATE deliverables del INNER JOIN activities act ON del.activity_id = act.id  
SET del.project_id = act.project_id;

-- Foreign Key between deliverables and project with the atributte project_id.
ALTER TABLE deliverables ADD CONSTRAINT  FK_deliverables_project FOREIGN KEY (project_id) REFERENCES projects(id);