-- Create the linked core projects table 

CREATE  TABLE IF NOT EXISTS `linked_core_projects` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `bilateral_project_id` BIGINT NOT NULL COMMENT 'Link to table projects' ,
  `core_project_id` BIGINT NOT NULL COMMENT 'Link to table projects' ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_linked_core_projects_bilateral_projects_idx` (`bilateral_project_id` ASC) ,
  INDEX `FK_linked_core_projects_core_projects_idx` (`core_project_id` ASC) ,
  CONSTRAINT `FK_linked_core_projects_bilateral_projects`
    FOREIGN KEY (`bilateral_project_id` )
    REFERENCES `projects` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_linked_core_projects_core_projects`
    FOREIGN KEY (`core_project_id` )
    REFERENCES `projects` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET = utf8
COMMENT = 'This table links a bilateral co-founded project with at least one core project.';
