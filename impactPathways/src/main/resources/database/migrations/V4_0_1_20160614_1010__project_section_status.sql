ALTER TABLE `section_statuses`
ADD COLUMN `year`  int NOT NULL AFTER `cycle`;

update section_statuses set year=2016  where cycle='Planning';
update section_statuses set year=2015  where cycle='Reporting';
