CREATE TABLE `Case_Studie_Indicators` (
`id`  int NULL AUTO_INCREMENT ,
`id_case_studie`  int NOT NULL ,
`id_indicator`  int NOT NULL ,
FOREIGN KEY (`id_case_studie`) REFERENCES `cases_studies` (`id`),
PRIMARY KEY (`id`)
)
;

