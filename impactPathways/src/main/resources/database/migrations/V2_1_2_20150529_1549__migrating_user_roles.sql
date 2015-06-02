-- -----------------------------------------------------------------------------
-- Create the fields first name and last name in the table users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS migrate_user_roles_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE migrate_user_roles_table()
BEGIN
  
  DECLARE FoundCount INT;
  SELECT COUNT(1) INTO FoundCount FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = "employees";
  
  IF FoundCount = 1 THEN
    INSERT INTO `user_roles` (user_id, role_id) SELECT user_id, role_id FROM employees_backup GROUP BY user_id, role_id ORDER BY user_id;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL migrate_user_roles_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS migrate_user_roles_table;