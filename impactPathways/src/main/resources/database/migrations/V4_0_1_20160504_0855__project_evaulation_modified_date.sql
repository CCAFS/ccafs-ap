ALTER TABLE `project_evaluations`
ADD COLUMN `modified_date`  timestamp NULL DEFAULT NULL AFTER `modification_justification`;

