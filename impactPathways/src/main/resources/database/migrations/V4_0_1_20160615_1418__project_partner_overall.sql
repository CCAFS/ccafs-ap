CREATE TABLE `project_partner_overall` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`project_partner_id`  bigint(20) NOT NULL ,
`year`  int NOT NULL ,
`overall`  text NOT NULL ,
PRIMARY KEY (`id`)
)
;

ALTER TABLE `project_partner_overall` ADD FOREIGN KEY (`project_partner_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

