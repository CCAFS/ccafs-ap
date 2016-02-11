DROP TRIGGER `after_activities_insert`;

DROP TRIGGER `after_activities_update`;

 
ALTER TABLE $[database]_history.`activities`
ADD COLUMN `activityStatus`  int(11) NULL DEFAULT NULL AFTER `action`,
ADD COLUMN `activityProgress`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `activityStatus`;


DROP TRIGGER `after_deliverables_insert`;

DROP TRIGGER `after_deliverables_update`;

ALTER TABLE $[database]_history.`deliverables`
ADD COLUMN `status`  int(11) NULL DEFAULT NULL AFTER `action`,
ADD COLUMN `status_description`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `status`;


DROP TRIGGER `after_project_outcomes_insert`;

DROP TRIGGER `after_project_outcomes_update`;

ALTER TABLE $[database]_history.`project_outcomes`
ADD COLUMN `anual_progress`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `action`,
ADD COLUMN `comunication`  text CHARACTER SET utf8 COLLATE utf8_general_ci  NULL AFTER `anual_progress`,
ADD COLUMN `file`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL AFTER `comunication`;



DROP TRIGGER `after_ip_project_indicators_insert`;

DROP TRIGGER `after_ip_project_indicators_update`;
ALTER TABLE $[database]_history.`ip_project_indicators`
ADD COLUMN `archived`  int NULL AFTER `outcome_id`,
ADD COLUMN `narrative_gender`  text NULL AFTER `archived`,
ADD COLUMN `narrative_targets`  text NULL AFTER `narrative_gender`;





DROP TRIGGER `after_ip_project_contribution_overviews_insert`;

DROP TRIGGER `after_ip_project_contribution_overviews_update`;
ALTER TABLE $[database]_history.`ip_project_contribution_overviews`
ADD COLUMN `brief_summary`  text NULL AFTER `anual_contribution`,
ADD COLUMN `summary_gender`  text  NULL AFTER `gender_contribution`;




DROP TRIGGER `after_projects_insert`;

DROP TRIGGER `after_projects_update`;
ALTER TABLE $[database]_history.`projects`
ADD COLUMN `annual_report_to_dornor`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `modification_justification`,
ADD COLUMN `status`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `annual_report_to_dornor`,
ADD COLUMN `status_description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `status`;

