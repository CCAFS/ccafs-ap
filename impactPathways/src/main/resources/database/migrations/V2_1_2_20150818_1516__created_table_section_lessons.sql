-- -----------------------------------------------------------------------------
--        Adding table section_lessons
-- -----------------------------------------------------------------------------

CREATE  TABLE `project_component_lessons` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `project_id` BIGINT NOT NULL ,
  `component_name` VARCHAR(255) NOT NULL ,
  `lessons` TEXT NULL ,
  `year` INT(4) NOT NULL ,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1' ,
  `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `created_by` BIGINT NOT NULL ,
  `modified_by` BIGINT NOT NULL ,
  `modification_justification` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_section_lessons_users_created_by_idx` (`created_by` ASC) ,
  INDEX `FK_section_lessons_users_modified_by_idx` (`modified_by` ASC) ,
  CONSTRAINT `FK_section_lessons_users_created_by` FOREIGN KEY (`created_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_section_lessons_users_modified_by` FOREIGN KEY (`modified_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_section_lessons_projects` FOREIGN KEY (`project_id` )
    REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE
) DEFAULT CHARACTER SET = utf8;
