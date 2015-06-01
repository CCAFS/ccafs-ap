-- -----------------------------------------------------------------------------
-- Create the fields first name and last name in the table users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS delete_persons_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE delete_persons_table()
BEGIN
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='users' AND column_name='first_name')) THEN
    ALTER TABLE `users` ADD COLUMN `first_name` varchar(255) NULL AFTER `id`;
  END IF;
  
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='users' AND column_name='last_name')) THEN
    ALTER TABLE `users` ADD COLUMN `last_name` varchar(255) NULL AFTER `first_name`;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL delete_persons_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS delete_persons_table;

-- -----------------------------------------------------------------------------
-- Copy the values from persons to users
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS move_persons_to_users;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE move_persons_to_users()
BEGIN
  DECLARE FoundCount INT;
  SELECT COUNT(1) INTO FoundCount FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = "persons";
  IF FoundCount = 1 THEN
    UPDATE users u 
    INNER JOIN persons p ON u.person_id = p.id
    SET u.first_name = p.first_name, u.last_name = p.last_name ;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL move_persons_to_users();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS move_persons_to_users;

-- -----------------------------------------------------------------------------
-- Drop the table persons and the column person_id in the table users
-- -----------------------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;

DROP PROCEDURE IF EXISTS delete_persons_table;
DELIMITER $$

CREATE PROCEDURE delete_persons_table()
BEGIN
  
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='users' AND column_name='person_id')) THEN
    ALTER TABLE `users` DROP COLUMN `person_id`;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL delete_persons_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS delete_persons_table;


DROP TABLE IF EXISTS persons;

SET FOREIGN_KEY_CHECKS = 1;