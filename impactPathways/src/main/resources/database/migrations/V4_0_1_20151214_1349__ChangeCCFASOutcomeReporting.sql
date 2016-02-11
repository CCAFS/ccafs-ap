ALTER TABLE `ip_project_indicators`
ADD COLUMN `archived`  int NULL AFTER `outcome_id`,
ADD COLUMN `narrative_gender`  text NULL AFTER `archived`,
ADD COLUMN `narrative_targets`  text NULL AFTER `narrative_gender`;

