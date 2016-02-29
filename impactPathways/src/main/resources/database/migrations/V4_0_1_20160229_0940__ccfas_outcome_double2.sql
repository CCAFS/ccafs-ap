ALTER TABLE `ip_project_indicators`
MODIFY COLUMN `archived`  double(11,2) NULL DEFAULT NULL AFTER `outcome_id`;

