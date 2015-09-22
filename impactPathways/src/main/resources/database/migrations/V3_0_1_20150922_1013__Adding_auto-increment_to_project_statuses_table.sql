----------------------------------------------------------------------------
--    Applying auto-increment to the id in project_statuses table.
----------------------------------------------------------------------------
ALTER TABLE `project_statuses` 
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '' ;
