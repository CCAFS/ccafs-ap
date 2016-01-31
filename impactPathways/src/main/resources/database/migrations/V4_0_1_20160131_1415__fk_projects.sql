ALTER TABLE `cross_cutting_contribution`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `cross_cutting_contribution` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE `other_contributions`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `other_contributions` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `project_highligths`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `project_highligths` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `project_leverage`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `project_leverage` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `project_next_user`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `project_next_user` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE `cases_studies`
MODIFY COLUMN `project_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `cases_studies` ADD FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;