----------------------------------------------------------------------------
--    Removing 'gender' and 'stories' columns from project_outcomes table
--    keeping the history log.
----------------------------------------------------------------------------

-- Deleting columns in main table:
ALTER TABLE `project_outcomes` 
DROP COLUMN `gender_dimension`,
DROP COLUMN `stories`;

-- Deleting columns in History table:
ALTER TABLE $[database]_history.`project_outcomes` 
DROP COLUMN `gender_dimension`,
DROP COLUMN `stories`;

