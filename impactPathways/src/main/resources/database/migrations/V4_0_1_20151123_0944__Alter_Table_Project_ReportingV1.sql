-----------------------------------------------------------------------
--   Alter_Table_Project_ReportingV1
-----------------------------------------------------------------------
ALTER TABLE `projects`
ADD COLUMN `annual_report_to_dornor`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `modification_justification`,
ADD COLUMN `status`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `annual_report_to_dornor`,
ADD COLUMN `status_description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `status`;

