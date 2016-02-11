ALTER TABLE `project_leverage`
DROP COLUMN `start_date`,
DROP COLUMN `end_date`;

ALTER TABLE `project_leverage`
ADD COLUMN `year`  int NULL AFTER `institution`;

DROP TRIGGER `after_project_leverage_insert`;

DROP TRIGGER `after_project_leverage_update`;


ALTER TABLE $[database]_history.`project_leverage`
DROP COLUMN `start_date`,
DROP COLUMN `end_date`,
ADD COLUMN `year`  int NULL AFTER `institution`;