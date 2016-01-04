CREATE TABLE `cases_studies` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `project_id`  int NOT NULL  ,
  `comment` TEXT NULL,
  `title` TEXT NULL,
  `outcomeStatement` TEXT NULL,
  `researchOutputs` TEXT NULL,
  `researchPatern` TEXT NULL,
  `activities` TEXT NULL,
  `nonResearchPartneres` TEXT NULL,
  `outputUsers` TEXT NULL,
  
  `evidenceOutcome` TEXT NULL,
  `references` TEXT NULL,
  `file` VARCHAR(200) NULL,
  `is_active`  tinyint(1) NOT NULL ,
  `active_since`  timestamp ,
  `created_by`  bigint(20) ,
  `modified_by`  bigint(20),
  `modification_justification`  text ,
  PRIMARY KEY (`id`)



)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
