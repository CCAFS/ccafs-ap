-- -----------------------------------------------------------------------------
-- Create project roles table
-- -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS `project_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `project_id` BIGINT NOT NULL COMMENT 'Link to table projects' ,
  `user_id` BIGINT NOT NULL COMMENT 'Link to table users' ,
  `role_id` BIGINT NOT NULL COMMENT 'Link to table roles' ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_project_roles_projects_idx` (`project_id` ASC) ,
  INDEX `FK_project_roles_roles_idx` (`role_id` ASC) ,
  INDEX `FK_project_roles_users_idx` (`user_id` ASC) ,
  CONSTRAINT `FK_project_roles_projects`
    FOREIGN KEY (`project_id` )
    REFERENCES `projects` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_roles_users`
    FOREIGN KEY (`user_id` )
    REFERENCES `users` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_roles_roles`
    FOREIGN KEY (`role_id` )
    REFERENCES `roles` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;

