CREATE TABLE `outcome_synthesis` (
`id`  int NOT NULL AUTO_INCREMENT ,
`ip_progam_id`  int NOT NULL ,
`year`  int NOT NULL ,
`mid_outcome_id`  int NOT NULL ,
`indicador_id`  int NOT NULL ,
`achieved`  double NULL ,
`synthesis_anual`  text NULL ,
`synthesis_gender`  text NULL ,
`discrepancy`  text NULL ,

PRIMARY KEY (`id`)
)
;

