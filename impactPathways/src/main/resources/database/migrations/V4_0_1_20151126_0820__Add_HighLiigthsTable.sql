-----------------------------------------------------------------------
--  Add Table project_highligths
-----------------------------------------------------------------------
CREATE TABLE `project_highligths` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`title`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`author`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`start_date`  date NULL DEFAULT NULL ,
`end_date`  date NULL DEFAULT NULL ,
`photo`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`objectives`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`description`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`results`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`partners`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`links`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`keywords`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`subject`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`contributor`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`publisher`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`relation`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`coverage`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`rights`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`is_global`  tinyint(1) NOT NULL ,
`leader`  int(11) NOT NULL ,
`type`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
;

