ALTER TABLE `project_other_contributions`
ADD COLUMN `region`  varchar(10) NULL AFTER `crp_contributions_nature`,
ADD COLUMN `flagship`  varchar(10) NULL AFTER `region`,
ADD COLUMN `indicators`  varchar(20) NULL AFTER `flagship`,
ADD COLUMN `description`  text NULL AFTER `indicators`,
ADD COLUMN `target`  varchar(200) NULL AFTER `description`;

