ALTER TABLE `deliverables`
ADD COLUMN `status`  int NULL AFTER `year`,
ADD COLUMN `status_description`  text NULL AFTER `status`;