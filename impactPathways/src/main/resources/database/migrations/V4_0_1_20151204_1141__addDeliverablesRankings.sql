CREATE TABLE `deliverables_ranking` (
`id`  int NULL AUTO_INCREMENT ,
`deliverable_id`  bigint NULL ,
`address`  int NULL ,
`potential`  int NULL ,
`level`  int NULL ,
`personal_perspective`  int NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`)
)
;

