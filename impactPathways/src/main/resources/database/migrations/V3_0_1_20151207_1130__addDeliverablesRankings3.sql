ALTER TABLE `deliverables_ranking`
ADD COLUMN `process_dataFile`  varchar(200) NULL AFTER `tooldata`,
ADD COLUMN `tooldataComment`  varchar(200) NULL AFTER `process_dataFile`;

