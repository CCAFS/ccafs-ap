-- -----------------------------------------------------------------------------
-- Reorganization of the tables used to link an ip_elemnent with an ip_program
-- -----------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS change_ip_elements_relations;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE change_ip_elements_relations()
BEGIN

  -- Add the is_active column to the activities table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='ip_elements' AND column_name='ip_program_id')) THEN
		ALTER TABLE `ip_elements` ADD `ip_program_id` BIGINT NOT NULL ;		
		UPDATE `ip_elements` ie SET  `ip_program_id` = ( SELECT program_id FROM  ip_program_elements ipe WHERE element_id = ie.id AND relation_type_id = 1 )
		ALTER TABLE ip_elements ADD CONSTRAINT FK_ip_elements_ip_programs_owner_id FOREIGN KEY (`ip_program_id`)  REFERENCES ip_programs(id);
  END IF;
 
END $$
DELIMITER ;

-- Execute the stored procedure
CALL change_ip_elements_relations();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS change_ip_elements_relations;
