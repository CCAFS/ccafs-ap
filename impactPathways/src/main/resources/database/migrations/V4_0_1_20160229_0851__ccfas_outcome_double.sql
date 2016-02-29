ALTER TABLE `ip_project_indicators`
MODIFY COLUMN `archived`  double(11,0) NULL DEFAULT NULL AFTER `outcome_id`;

