-- -----------------------------------------------------------------------------
--        Update ip_project_contributions_overview table
-- -----------------------------------------------------------------------------

ALTER TABLE `ip_project_contribution_overviews` DROP FOREIGN KEY `FK_project_contribution` ;
ALTER TABLE `ip_project_contribution_overviews` CHANGE COLUMN `project_contribution_id` `output_id` BIGINT(20) NOT NULL, 
  ADD CONSTRAINT `FK_project_contribution_overviews_ip_element`
  FOREIGN KEY (`output_id` ) REFERENCES `ip_elements` (`id` )
    ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD INDEX `FK_project_contribution_overviews_ip_element_idx` (`output_id` ASC), 
  DROP INDEX `FK_project_contribution_idx` ;

  
ALTER TABLE `ip_project_contribution_overviews` CHARACTER SET = utf8 , ADD COLUMN `project_id` BIGINT NOT NULL  AFTER `id` , 
  ADD CONSTRAINT `FK_project_contribution_overviews_projects`
  FOREIGN KEY (`project_id` ) REFERENCES `projects` (`id` )
  ON DELETE NO ACTION ON UPDATE NO ACTION, 
  ADD INDEX `FK_project_contribution_overviews_projects_idx` (`project_id` ASC) ;
