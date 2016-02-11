-----------------------------------------------------------------------
--  Add Table project_highligths
-----------------------------------------------------------------------
ALTER TABLE `project_highligths`
DROP COLUMN `type`;

CREATE TABLE `project_highligths_types` (
`id`  int NOT NULL AUTO_INCREMENT ,
`project_highlights_id`  int NOT NULL ,
`id_type`  int NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`project_highlights_id`) REFERENCES `project_highligths` (`id`)
)
;

CREATE TABLE `project_highligths_country` (
`id`  int NOT NULL AUTO_INCREMENT ,
`project_highlights_id`  int NOT NULL ,
`id_country`  int NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`project_highlights_id`) REFERENCES `project_highligths` (`id`)
)
;

