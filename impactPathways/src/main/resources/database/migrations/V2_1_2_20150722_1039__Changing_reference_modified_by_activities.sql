-- ************************************************************************************************************
-- ***********Changing reference of modified_by from employees to users  
-- ************************************************************************************************************

-- Delete old foreign key to employees
 ALTER TABLE activities DROP FOREIGN KEY fk_activities_employees_modified_by;

-- Update reference from employees to users
UPDATE activities act INNER JOIN employees e ON act.modified_by = e.id INNER JOIN users u ON e.user_id = u.id
SET act.modified_by = u.id;

-- Putting foreign key to users
ALTER TABLE activities ADD CONSTRAINT fk_activities_users_modified_by FOREIGN KEY (modified_by) REFERENCES users(id)