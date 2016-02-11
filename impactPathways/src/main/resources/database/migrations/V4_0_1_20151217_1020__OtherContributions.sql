CREATE TABLE `other_contributions` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`project_id`  int(11) NOT NULL ,
`region`  varchar(5) NULL ,
`flagship`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`indicators`  text NULL ,
`description`  text NULL ,
`target`  int NULL ,
`is_active`  tinyint(1) NOT NULL DEFAULT 1 ,
`active_since`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`created_by`  bigint(20) NOT NULL ,
`modified_by`  bigint(20) NOT NULL ,
`modification_justification`  text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
PRIMARY KEY (`id`)
)
;

