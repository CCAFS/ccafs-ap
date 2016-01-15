CREATE TABLE `deliverable_data_sharing` (
`id`  int NOT NULL AUTO_INCREMENT ,
`deliverable_id`  int NOT NULL ,
`institutional_repository`  bit NULL ,
`link_institutional_repository`  varchar(500) NULL ,
`ccfas_host_greater`  bit NULL ,
`link_ccfas_host_greater`  varchar(500) NULL ,
`ccfas_host_smaller`  bit NULL ,
PRIMARY KEY (`id`)
)
;

