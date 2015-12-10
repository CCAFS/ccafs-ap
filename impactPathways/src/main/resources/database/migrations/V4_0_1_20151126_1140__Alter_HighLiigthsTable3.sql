-----------------------------------------------------------------------
--  Add Table project_highligths
-----------------------------------------------------------------------
ALTER TABLE `project_highligths`
ADD COLUMN `year`  bigint(20)   NULL AFTER `type`,
ADD COLUMN `status`  bigint(20)  NULL  AFTER `is_active`


