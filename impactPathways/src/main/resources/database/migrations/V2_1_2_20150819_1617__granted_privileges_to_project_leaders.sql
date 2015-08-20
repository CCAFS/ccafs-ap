-- -----------------------------------------------------------------------------
--    Granting privileges to project leaders to let them edit their projects
-- -----------------------------------------------------------------------------

INSERT INTO project_roles (project_id, user_id, role_id )
SELECT project_id, user_id, 7
FROM `project_partners` 
WHERE `partner_type` = 'PL';