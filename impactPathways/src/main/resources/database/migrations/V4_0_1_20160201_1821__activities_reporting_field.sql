ALTER TABLE `activities`
ADD COLUMN `activityStatus`  int NULL AFTER `leader_id`,
ADD COLUMN `activityProgress`  text NULL AFTER `activityStatus`;

