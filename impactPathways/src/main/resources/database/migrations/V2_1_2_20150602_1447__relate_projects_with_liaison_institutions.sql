-- -----------------------------------------------------------------------------
-- Change the relation of projects with liaison institution instead of ip_programs
-- -----------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS create_liaison_institution_field;
DELIMITER $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE create_liaison_institution_field()
BEGIN

  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='liaison_institution_id')) THEN
    ALTER TABLE projects ADD `liaison_institution_id` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table liaison institutions' AFTER `liaison_institution_id`;
    ALTER TABLE projects ADD KEY `FK_projects_liaison_institutions_idx` (`liaison_institution_id`);
    ALTER TABLE projects ADD CONSTRAINT `FK_projects_liaison_institutions` FOREIGN KEY (`liaison_institution_id`) REFERENCES `liaison_institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
    
    UPDATE projects p, 
      (
        SELECT id, liaison_institution_id, 
        CASE liaison_institution_id 
        WHEN 5 THEN 7 
        WHEN 6 THEN liaison_institution_id
        WHEN 7 THEN 10
        WHEN 8 THEN liaison_institution_id
        WHEN 9 THEN liaison_institution_id
        ELSE liaison_institution_id +1 END as liaison_institution
        FROM projects 
      ) t 
    SET p.liaison_institution_id = t.liaison_institution
    WHERE p.id = t.id;
    
  END IF;

  IF EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='projects' AND column_name='liaison_institution_id')) THEN
    ALTER TABLE `projects` 
    DROP FOREIGN KEY `FK_projects_program_id`;
    ALTER TABLE `projects` 
    DROP COLUMN `liaison_institution_id`,
    DROP INDEX `FK_projects_program_id_idx`;
  END IF;

END $$
DELIMITER ;

-- Execute the stored procedure
CALL create_liaison_institution_field();
 
-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS create_liaison_institution_field;
