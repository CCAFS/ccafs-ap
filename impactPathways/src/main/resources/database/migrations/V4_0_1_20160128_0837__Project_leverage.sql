CREATE TABLE `project_leverage` (
`id`  int NOT NULL AUTO_INCREMENT ,
`project_id`  int NOT NULL ,
`title`  varchar(100) NOT NULL ,
`institution`  int NULL ,
`start_date`  date NULL ,
`end_date`  date NULL ,
`flagship`  int NULL ,
`budget`  double NULL ,
`is_active`  tinyint(1) NOT NULL DEFAULT 1 ,
`active_since`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`created_by`  bigint(20) NOT NULL ,
`modified_by`  bigint(20) NOT NULL ,
`modification_justification`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
;

