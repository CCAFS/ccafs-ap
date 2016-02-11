ALTER TABLE `deliverables_ranking`
ADD COLUMN `process_data`  bit NULL AFTER `personal_perspective`,
ADD COLUMN `dictionary`  bit NULL AFTER `process_data`,
ADD COLUMN `tooldata`  bit NULL AFTER `dictionary`;
