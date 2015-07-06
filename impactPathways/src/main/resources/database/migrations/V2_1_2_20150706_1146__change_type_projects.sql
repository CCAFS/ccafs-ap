-- -----------------------------------------------------------------------------
--    Changing the model
-- -----------------------------------------------------------------------------

-- Deleting column type of table projects
ALTER TABLE projects DROP COLUMN type;

-- Adding again column enum with the new types.
ALTER TABLE projects  ADD type ENUM('CCAFS_CORE', 'CCAFS_COFUNDED', 'BILATERAL')  DEFAULT 'CCAFS_CORE' AFTER end_date;

-- Deleting trigger for update, because this creates problems in the update of registers.  
DROP TRIGGER IF EXISTS after_projects_update;

-- Updating of the register No 5, because that register is Bilateral.
UPDATE projects SET `type`='BILATERAL' WHERE `id`='5';

