

DROP PROCEDURE IF EXISTS organizing_project_partners_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE organizing_project_partners_table()
BEGIN
  -- -----------------------------------------------------------------------------
  -- Deleting trigger in Project Partners Table
  -- ----------------------------------------------------------------------------- 
  IF EXISTS (SELECT * FROM information_schema.triggers WHERE trigger_name =  'organizing_project_partners_table') THEN
    DROP TRIGGER after_project_partners_update;
  END IF;
	
  -- -----------------------------------------------------------------------------
  -- Adding column partner_type in Project Partners
  -- -----------------------------------------------------------------------------
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='partner_type')) THEN
    ALTER TABLE `project_partners` 
    ADD COLUMN `partner_type` ENUM('PPA', 'PL', 'PP', 'PC') NULL AFTER `contact_email`;    
  END IF;

  -- -----------------------------------------------------------------------------
  -- Creating column contact_id that will reference to the users table.
  -- -----------------------------------------------------------------------------
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='project_partners' AND column_name='user_id')) THEN
    ALTER TABLE `project_partners` 
    ADD COLUMN `user_id` BIGINT NULL AFTER `contact_email`,
    ADD INDEX `FK_project_partners_users_idx` (`user_id` ASC);
    ALTER TABLE `project_partners` 
    ADD CONSTRAINT `FK_project_partners_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
  END IF;
  
  -- -----------------------------------------------------------------------------
  -- Identifying which project partners are PPA or just PP and populate the partner_type column. 
  -- -----------------------------------------------------------------------------
  UPDATE project_partners pp
  INNER JOIN institutions i ON i.id = pp.partner_id
  SET pp.partner_type =
  CASE -- START CASE
    WHEN i.is_ppa IS TRUE
    THEN "PPA"
    ELSE "PP"
  END; -- END CASE

  -- -----------------------------------------------------------------------------
  -- Identifying which users already exist and reference them into the user_id column. 
  -- -----------------------------------------------------------------------------
  UPDATE project_partners pp
  SET pp.user_id =
  CASE -- START CASE
    WHEN (SELECT COUNT(u.id) FROM users u WHERE u.email = pp.contact_email) = 1
    THEN (SELECT u.id FROM users u WHERE u.email = pp.contact_email)
    ELSE NULL
  END; -- END CASE

END $$
DELIMITER ;


-- Execute the stored procedure
CALL organizing_project_partners_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS organizing_project_partners_table;















