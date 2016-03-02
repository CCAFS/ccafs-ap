ALTER TABLE `outcome_synthesis`
MODIFY COLUMN `achieved`  double(11,2) NULL DEFAULT NULL AFTER `indicador_id`,
MODIFY COLUMN `achieved_expected`  double(11,2) NULL DEFAULT NULL AFTER `discrepancy`;

