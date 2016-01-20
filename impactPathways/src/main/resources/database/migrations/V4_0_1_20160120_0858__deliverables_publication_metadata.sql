CREATE TABLE `deliverable_publication_metadata` (
`id`  int NOT NULL AUTO_INCREMENT ,
`deliverable_id`  int NOT NULL ,
`open_acess_status`  varchar(50) NULL ,
`isi_publication`  bit NULL ,
`nars_co_author`  bit NULL ,
`academic_co-author`  bit NULL ,
`citation`  text NULL ,
`acknowledge_ccafs`  bit NULL ,
`fp1`  bit NULL ,
`fp2`  bit NULL ,
`fp3`  bit NULL ,
`fp4`  bit NULL ,
PRIMARY KEY (`id`)
)
;

