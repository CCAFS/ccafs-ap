-- -----------------------------------------------------------------------------
--        Adding unique key in project_crp_contributions
-- -----------------------------------------------------------------------------

ALTER TABLE `project_crp_contributions` ADD UNIQUE INDEX `UK_project_crp_contributions` (`project_id` ASC, `crp_id` ASC) ;

