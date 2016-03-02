ALTER TABLE `outcome_synthesis`
MODIFY COLUMN `achieved`  double(2,0) NULL DEFAULT NULL AFTER `indicador_id`,
ADD COLUMN `achieved_expected`  double(2,0) NULL DEFAULT NULL AFTER `discrepancy`;

