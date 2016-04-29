ALTER TABLE `project_evaluation`
MODIFY COLUMN `user_id`  bigint(20) NULL ,
ADD COLUMN `program_id`  bigint(20) NULL AFTER `type_evaluation`;

ALTER TABLE `project_evaluation` ADD FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

RENAME TABLE  `project_evaluation` TO  `project_evaluations`
