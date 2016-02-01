ALTER TABLE `project_outcomes`
ADD COLUMN `comunication`  text NOT NULL AFTER `anual_progress`,
ADD COLUMN `file`  varchar(200) NULL AFTER `comunication`;

