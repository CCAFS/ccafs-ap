-----------------------------------------------------------------------
--   Alter_Table_Project_ReportingV1
-----------------------------------------------------------------------
ALTER TABLE `project_component_lessons`
ADD COLUMN `cycle`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `modification_justification`;

update project_component_lessons set cycle='Planning'