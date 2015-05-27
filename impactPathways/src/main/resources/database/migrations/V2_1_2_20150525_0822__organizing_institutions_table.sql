

DROP PROCEDURE IF EXISTS organizing_institutions_table;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE organizing_institutions_table()
BEGIN

  -- -----------------------------------------------------------------------------
  -- Removing unused columns in the table institutions.
  -- -----------------------------------------------------------------------------
  
  -- Deleting column contact_person_email
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='institutions' AND column_name='contact_person_email')) THEN
    ALTER TABLE `institutions` 
	DROP COLUMN `contact_person_email`;
  END IF;
  
  -- Deleting column contact_person_name
  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='institutions' AND column_name='contact_person_name')) THEN
    ALTER TABLE `institutions` 
	DROP COLUMN `contact_person_name`;
  END IF;

  -- Adding new column named as 'is_ppa' and setting up values
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='institutions' AND column_name='is_ppa')) THEN
    
    ALTER TABLE `institutions`	
    ADD COLUMN `is_ppa` TINYINT(1) NOT NULL DEFAULT 0 AFTER `city`;
    -- Identifying PPA partners
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='52';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='49';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='46';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='115';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='746';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='775';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1042';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='50';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='650';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='680';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='67';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='51';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='88';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1143';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='103';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='89';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1085';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='45';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='66';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='5';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='172';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1116';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='99';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1099';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='100';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='114';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='134';
    UPDATE `institutions` SET `is_ppa`='1' WHERE `id`='1053';
 
  END IF;
 
END $$
DELIMITER ;


-- Execute the stored procedure
CALL organizing_institutions_table();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS organizing_institutions_table;


