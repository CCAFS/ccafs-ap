DROP TRIGGER `after_activities_insert`;

DROP TRIGGER `after_activities_update`;
DROP TRIGGER `after_deliverables_insert`;

DROP TRIGGER `after_deliverables_update`;


DROP TRIGGER `after_project_outcomes_insert`;

DROP TRIGGER `after_project_outcomes_update`;


 
ALTER TABLE $[database]_history.`activities`
DROP COLUMN activityStatus,
DROP COLUMN activityProgress;


ALTER TABLE $[database]_history.`activities`
ADD COLUMN `activityStatus`  int(11) NULL DEFAULT NULL AFTER `is_active`,
ADD COLUMN `activityProgress`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `activityStatus`;


ALTER TABLE $[database]_history.`deliverables`
DROP COLUMN status,
DROP COLUMN status_description;

ALTER TABLE $[database]_history.`deliverables`
ADD COLUMN `status`  int(11) NULL DEFAULT NULL AFTER `is_active`,
ADD COLUMN `status_description`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `status`;


ALTER TABLE $[database]_history.`project_outcomes`
DROP COLUMN anual_progress,
DROP COLUMN comunication;
DROP COLUMN `file`;

ALTER TABLE $[database]_history.`project_outcomes`
ADD COLUMN `anual_progress`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `is_active`,
ADD COLUMN `comunication`  text CHARACTER SET utf8 COLLATE utf8_general_ci  NULL AFTER `anual_progress`,
ADD COLUMN `file`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL AFTER `comunication`;

