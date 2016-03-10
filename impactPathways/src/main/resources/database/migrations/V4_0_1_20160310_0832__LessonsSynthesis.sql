ALTER TABLE `project_component_lessons`
MODIFY COLUMN `project_id`  bigint(20) NULL AFTER `id`,
ADD COLUMN `ip_program_id`  int NULL AFTER `cycle`;

