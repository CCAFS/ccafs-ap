ALTER TABLE `deliverable_data_sharing`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverable_data_sharing` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `deliverable_data_sharing_file`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverable_data_sharing_file` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;



ALTER TABLE `deliverable_dissemination`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverable_dissemination` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE `deliverable_metadata_elements`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverable_metadata_elements` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `deliverable_publication_metadata`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverable_publication_metadata` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `deliverables_ranking`
MODIFY COLUMN `deliverable_id`  bigint(20) NOT NULL AFTER `id`;
ALTER TABLE `deliverables_ranking` ADD FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
