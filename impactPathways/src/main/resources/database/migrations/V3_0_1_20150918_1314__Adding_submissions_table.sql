--------------------------------------------------------------------------------------------------------------
--  Adding table for saving the project submissions.
--------------------------------------------------------------------------------------------------------------
  CREATE TABLE `project_submissions` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '',
  `cycle` ENUM('Planning', 'Reporting') NOT NULL COMMENT 'Cycling period type.',
  `year` SMALLINT NOT NULL COMMENT 'Year to which the report is happening.',
  `project_id` BIGINT(20) NOT NULL COMMENT '',
  `user_id` BIGINT(20) NOT NULL COMMENT 'The user who is submitting the report.',
  `date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'The date time when the report was made.',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `FK_project_submissions_user_id_idx` (`user_id` ASC)  COMMENT '',
  INDEX `FK_project_submissions_project_id_idx` (`project_id` ASC)  COMMENT '',
  CONSTRAINT `FK_project_submissions_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_project_submissions_project_id`
    FOREIGN KEY (`project_id`)
    REFERENCES `projects` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
