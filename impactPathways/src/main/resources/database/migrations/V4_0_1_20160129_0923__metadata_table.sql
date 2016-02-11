CREATE TABLE `metadata_elements` (
`id`  int NOT NULL AUTO_INCREMENT ,
`schema`  varchar(20) NULL ,
`element`  varchar(100) NULL ,
`qualifier`  varchar(100) NULL ,
`econded_name`  varchar(100) NULL ,
`status`  varchar(100) NULL ,
`vocabulary`  varchar(200) NULL ,
`definitation`  text NULL ,
PRIMARY KEY (`id`)
)
;

