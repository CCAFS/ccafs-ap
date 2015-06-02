

DROP PROCEDURE IF EXISTS migrating_project_leaders_to_project_partners_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE migrating_project_leaders_to_project_partners_table()
BEGIN
  
  -- -----------------------------------------------------------------------------
  -- As of now, it is assumed that there are not any PLs in the project_partners table. 
  -- If so, this procedure will not run.
  -- -----------------------------------------------------------------------------
  
  DECLARE total_leaders INT DEFAULT 0;
  
  SELECT COUNT(pl.id) INTO total_leaders FROM project_partners pl WHERE pl.partner_type = "PL"; 
  -- -----------------------------------------------------------------------------
  -- Inserting Project Leaders into project_partners table
  -- -----------------------------------------------------------------------------
  IF total_leaders = 0 THEN
    INSERT INTO project_partners(`project_id`, `partner_id`, `contact_name`, `contact_email`, `user_id`, `partner_type`, `responsabilities`, `is_active`, `active_since`, `created_by`, `modified_by`, `modification_justification`)
    SELECT p.id as 'project_id', 
    i.id as 'partner_id',
    CONCAT(per.first_name, " ", per.last_name) as 'contact_name',
    u.email as 'contact_email',
    u.id as 'user_id',
    "PL" as 'partner_type',
    p.leader_responsabilities as 'responsibilities',
    "1" as 'is_active',
    p.active_since as 'active_since',
    p.created_by as 'created_by',
    p.modified_by as 'modified_by',
    "" as 'modification_justification'
    FROM projects p
    INNER JOIN employees e ON e.id = p.project_leader_id
    INNER JOIN users u ON u.id = e.user_id
    INNER JOIN persons per ON per.id = u.person_id
    INNER JOIN institutions i ON i.id = e.institution_id;
    
    -- -----------------------------------------------------------------------------
    -- Once migration is done, project_leader_id column in projects table is not needed anymore.
    -- -----------------------------------------------------------------------------
    ALTER TABLE `projects` 
    DROP FOREIGN KEY `FK_projects_employees`;
    ALTER TABLE `projects` 
    DROP COLUMN `project_leader_id`,
    DROP INDEX `FK_projects_employees_idx` ;
    
  END IF;
  
  



END $$
DELIMITER ;


-- Execute the stored procedure
CALL migrating_project_leaders_to_project_partners_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS migrating_project_leaders_to_project_partners_table;















