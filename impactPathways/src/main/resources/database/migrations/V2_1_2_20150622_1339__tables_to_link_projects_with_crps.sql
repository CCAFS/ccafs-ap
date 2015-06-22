-- -----------------------------------------------------------------------------
--        Create tables to link projects with CRPs
-- -----------------------------------------------------------------------------

CREATE  TABLE IF NOT EXISTS `crps` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `acronym` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );
  
CREATE  TABLE `project_crp_contributions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `project_id` BIGINT NOT NULL ,
  `crp_id` INT NOT NULL ,
  `is_active` TINYINT(1) NOT NULL DEFAULT '1' ,
  `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `created_by` BIGINT NOT NULL ,
  `modified_by` BIGINT NOT NULL ,
  `modification_justification` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_project_crp_contributions_projects_idx` (`project_id` ASC) ,
  INDEX `FK_project_crp_contributions_crps_idx` (`crp_id` ASC) ,
  INDEX `FK_project_crp_contributions_users_created_by_idx` (`created_by` ASC) ,
  INDEX `FK_project_crp_contributions_users_modified_by_idx` (`modified_by` ASC) ,
  CONSTRAINT `FK_project_crp_contributions_projects` FOREIGN KEY (`project_id` )
    REFERENCES `projects` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_crps` FOREIGN KEY (`crp_id` ) 
    REFERENCES `crps` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_users_created_by` FOREIGN KEY (`created_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_users_modified_by` FOREIGN KEY (`modified_by` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO `crps` (`name`, `acronym`) VALUES ('Agriculture for Nutrition and Health', 'a4nh');
INSERT INTO `crps` (`name`) VALUES ('Aquatic Agricultural Systems');
INSERT INTO `crps` (`name`, `acronym`) VALUES ('Climate Change, Agriculture and Food Security', 'CCAFS');
INSERT INTO `crps` (`name`) VALUES ('Dryland Cereals');
INSERT INTO `crps` (`name`) VALUES ('Dryland Systems');
INSERT INTO `crps` (`name`, `acronym`) VALUES ('Forests, Trees and Agroforestry', 'FTA');
INSERT INTO `crps` (`name`) VALUES ('Grain Legumes');
INSERT INTO `crps` (`name`) VALUES ('Integrated Systems for the Humid Tropics');
INSERT INTO `crps` (`name`) VALUES ('Livestock and Fish');
INSERT INTO `crps` (`name`) VALUES ('Policies, Institutions and Markets');
INSERT INTO `crps` (`name`) VALUES ('Maize');
INSERT INTO `crps` (`name`, `acronym`) VALUES ('Rice', 'GRiSP');
INSERT INTO `crps` (`name`, `acronym`) VALUES ('Roots, Tubers and Bananas', 'RTB');
INSERT INTO `crps` (`name`, `acronym`) VALUES ('Water, Land and Ecosystems', 'WLE');
INSERT INTO `crps` (`name`) VALUES ('Wheat');
INSERT INTO `crps` (`name`) VALUES ('Genebank');


ALTER TABLE `crps` ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `acronym` ;

ALTER TABLE `project_crp_contributions` ADD COLUMN `collaboration_nature` TEXT NULL  AFTER `crp_id` ;