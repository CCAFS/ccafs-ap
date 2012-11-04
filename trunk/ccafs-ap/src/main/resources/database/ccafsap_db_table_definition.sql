SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `ccafsap_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `ccafsap_db` ;

-- -----------------------------------------------------
-- Table `logframes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `logframes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `themes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `themes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `code` TEXT NOT NULL ,
  `description` TEXT NULL ,
  `logframe_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `logframe_fk`
    FOREIGN KEY (`logframe_id` )
    REFERENCES `logframes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `objectives`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `objectives` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `theme_id` INT NOT NULL ,
  `code` TEXT NOT NULL ,
  `description` TEXT NOT NULL ,
  `outcome_description` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `theme_fk`
    FOREIGN KEY (`theme_id` )
    REFERENCES `themes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `outputs`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `outputs` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `objective_id` INT NOT NULL ,
  `code` TEXT NOT NULL ,
  `description` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `objective_fk`
    FOREIGN KEY (`objective_id` )
    REFERENCES `objectives` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `milestones`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `milestones` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `output_id` INT NOT NULL ,
  `code` TEXT NOT NULL ,
  `year` INT(4) NOT NULL ,
  `description` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `output_fk`
    FOREIGN KEY (`output_id` )
    REFERENCES `outputs` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `leader_types`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `leader_types` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_leaders`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_leaders` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `led_activity_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `led_activity_fk`
    FOREIGN KEY (`led_activity_id` )
    REFERENCES `leader_types` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_status`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_status` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activities`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activities` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `title` TEXT NOT NULL ,
  `start_date` DATE NULL ,
  `end_date` DATE NULL ,
  `description` TEXT NOT NULL ,
  `milestone_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  `is_global` TINYINT(1) NOT NULL ,
  `continuous_activity_id` INT NOT NULL ,
  `is_planning` TINYINT(1) NULL ,
  `activity_status_id` INT NULL ,
  `status_description` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `milestone_fk`
    FOREIGN KEY (`milestone_id` )
    REFERENCES `milestones` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `continous_activity_fk`
    FOREIGN KEY (`continuous_activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `status_fk`
    FOREIGN KEY (`activity_status_id` )
    REFERENCES `activity_status` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
PACK_KEYS = 1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_objectives`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_objectives` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `activity_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `deliverable_types`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deliverable_types` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `deliverable_status`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deliverable_status` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `deliverables`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deliverables` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `year` INT(4) NOT NULL ,
  `activity_id` INT NOT NULL ,
  `deliverable_type_id` INT NOT NULL ,
  `is_expected` TINYINT(1) NOT NULL ,
  `deliverable_status_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `activity_fk2`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `deliverable_type_fk2`
    FOREIGN KEY (`deliverable_type_id` )
    REFERENCES `deliverable_types` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `deliverable_status_fk2`
    FOREIGN KEY (`deliverable_status_id` )
    REFERENCES `deliverable_status` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `gender_integrations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gender_integrations` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `activity_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `gi_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `budget_percentages`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `budget_percentages` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `percentage` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_budgets`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_budgets` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `usd` DOUBLE NOT NULL ,
  `cg_funds` INT NULL ,
  `bilateral` INT NULL ,
  `activity_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `cg_funds_fk`
    FOREIGN KEY (`cg_funds` )
    REFERENCES `budget_percentages` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `bilateral_fk`
    FOREIGN KEY (`bilateral` )
    REFERENCES `budget_percentages` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `budgets_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `regions`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `regions` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `countries`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `countries` (
  `iso2` VARCHAR(2) NOT NULL ,
  `name` TEXT NOT NULL ,
  `region_id` INT NOT NULL ,
  PRIMARY KEY (`iso2`) ,
  CONSTRAINT `region_fk`
    FOREIGN KEY (`region_id` )
    REFERENCES `regions` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `benchmark_sites`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `benchmark_sites` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `country_iso2` VARCHAR(2) NOT NULL ,
  `longitude` DOUBLE NOT NULL ,
  `latitude` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `country_fk`
    FOREIGN KEY (`country_iso2` )
    REFERENCES `countries` (`iso2` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `keywords`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `keywords` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_keywords`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_keywords` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `activity_id` INT NOT NULL ,
  `keyword_id` INT NULL ,
  `other` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `ak_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `ak_keyword_fk`
    FOREIGN KEY (`keyword_id` )
    REFERENCES `keywords` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `resources`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `resources` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `activity_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `res_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `contact_person`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `contact_person` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `email` TEXT NOT NULL ,
  `activity_id` INT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `cp_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `bs_locations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `bs_locations` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `bs_id` INT NOT NULL ,
  `activity_id` INT NOT NULL ,
  `details` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `bs_fk`
    FOREIGN KEY (`bs_id` )
    REFERENCES `benchmark_sites` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `activity_id`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `country_locations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `country_locations` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `country_iso2` VARCHAR(2) NOT NULL ,
  `activity_id` INT NOT NULL ,
  `details` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `cl_country_fk`
    FOREIGN KEY (`country_iso2` )
    REFERENCES `countries` (`iso2` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `cl_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `partner_types`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `partner_types` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `partners`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `partners` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `acronym` TEXT NULL ,
  `country_iso2` VARCHAR(2) NOT NULL ,
  `city` TEXT NOT NULL ,
  `partner_type_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `type_fk`
    FOREIGN KEY (`partner_type_id` )
    REFERENCES `partner_types` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_partners`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_partners` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `partner_id` INT NOT NULL ,
  `activity_id` INT NOT NULL ,
  `contact_name` TEXT NULL ,
  `contact_email` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `ap_partner_fk`
    FOREIGN KEY (`partner_id` )
    REFERENCES `partners` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `ap_activity_fk`
    FOREIGN KEY (`activity_id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `other_sites`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `other_sites` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `activity_id` INT NOT NULL ,
  `longitude` DOUBLE NOT NULL ,
  `latitude` DOUBLE NOT NULL ,
  `country_iso2` VARCHAR(2) NOT NULL ,
  `details` TEXT NOT NULL ,
  CONSTRAINT `os_activity_fk`
    FOREIGN KEY (`id` )
    REFERENCES `activities` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `os_country_fk`
    FOREIGN KEY (`country_iso2` )
    REFERENCES `countries` (`iso2` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `deliverable_formats`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deliverable_formats` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` TEXT NOT NULL ,
  `deliverable_id` INT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `deliverable_id`
    FOREIGN KEY (`deliverable_id` )
    REFERENCES `deliverables` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `user_roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `user_roles` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  `role_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `u_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `u_role_fk`
    FOREIGN KEY (`role_id` )
    REFERENCES `user_roles` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `output_summaries`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `output_summaries` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `description` TEXT NOT NULL ,
  `output_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `os_output_fk`
    FOREIGN KEY (`output_id` )
    REFERENCES `outputs` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `os_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `publication_types`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `publication_types` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `publications`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `publications` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `publication_type_id` INT NOT NULL ,
  `identifier` TEXT NOT NULL ,
  `citation` TEXT NOT NULL ,
  `logframe_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `p_publication_type_fk`
    FOREIGN KEY (`publication_type_id` )
    REFERENCES `publication_types` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `p_logframe_fk`
    FOREIGN KEY (`logframe_id` )
    REFERENCES `logframes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `p_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `case_studies`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `case_studies` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `title` TEXT NOT NULL ,
  `author` TEXT NOT NULL ,
  `date` DATE NOT NULL ,
  `photo` TEXT NULL ,
  `objectives` TEXT NULL ,
  `description` TEXT NULL ,
  `results` TEXT NULL ,
  `partners` TEXT NULL ,
  `links` TEXT NULL ,
  `keywords` TEXT NULL ,
  `logframe_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `cs_logframe_fk`
    FOREIGN KEY (`logframe_id` )
    REFERENCES `logframes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `cs_activity_leader`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `case_study_countries`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `case_study_countries` (
  `id` INT NOT NULL ,
  `case_study_id` INT NOT NULL AUTO_INCREMENT ,
  `country_iso2` VARCHAR(2) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `csc_case_study_fk`
    FOREIGN KEY (`case_study_id` )
    REFERENCES `case_studies` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `csc_country_fk`
    FOREIGN KEY (`country_iso2` )
    REFERENCES `countries` (`iso2` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `outcomes`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `outcomes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `outcome` TEXT NOT NULL ,
  `outputs` TEXT NOT NULL ,
  `partners` TEXT NOT NULL ,
  `output_user` TEXT NOT NULL ,
  `how_used` TEXT NOT NULL ,
  `evidence` TEXT NOT NULL ,
  `logframe_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `out_logframe_fk`
    FOREIGN KEY (`logframe_id` )
    REFERENCES `logframes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `out_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `leverages`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `leverages` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `theme_id` INT NOT NULL ,
  `partner` TEXT NOT NULL ,
  `project_title` TEXT NOT NULL ,
  `project_budget` DOUBLE NOT NULL ,
  `duration` VARCHAR(9) NOT NULL DEFAULT 'year-year' ,
  `activity_leader_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `lev_theme_fk`
    FOREIGN KEY (`theme_id` )
    REFERENCES `themes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `lev_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tl_output_summaries`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tl_output_summaries` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `output_id` INT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  `description` TEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `tlos_output_fk`
    FOREIGN KEY (`output_id` )
    REFERENCES `outputs` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `tlos_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rpl_shynthesis_reports`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `rpl_shynthesis_reports` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `ccafs_sites` TEXT NOT NULL ,
  `cross_center` TEXT NOT NULL ,
  `regional` TEXT NOT NULL ,
  `decision_support` TEXT NOT NULL ,
  `activity_leader_id` INT NOT NULL ,
  `logframe_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `rplsr_activity_leader_fk`
    FOREIGN KEY (`activity_leader_id` )
    REFERENCES `activity_leaders` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `rplsr_logframe_fk`
    FOREIGN KEY (`logframe_id` )
    REFERENCES `logframes` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `milestone_status`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `milestone_status` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `status` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `milestone_reports`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `milestone_reports` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `milestone_id` INT NOT NULL ,
  `milestone_status_id` INT NULL ,
  `tl_description` TEXT NULL ,
  `rpl_description` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `mr_milestone_fk`
    FOREIGN KEY (`milestone_id` )
    REFERENCES `milestones` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `mr_milestone_status_fk`
    FOREIGN KEY (`milestone_status_id` )
    REFERENCES `milestone_status` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `partner_roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `partner_roles` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `activity_partner_roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `activity_partner_roles` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `activity_partner_id` INT NOT NULL ,
  `partner_role_id` INT NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `activity_partner_fk`
    FOREIGN KEY (`activity_partner_id` )
    REFERENCES `activity_partners` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `partner_role_fk`
    FOREIGN KEY (`partner_role_id` )
    REFERENCES `partner_roles` (`id` )
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
