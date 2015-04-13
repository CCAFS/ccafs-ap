-- MySQL dump 10.13  Distrib 5.5.39, for Win32 (x86)
--
-- ------------------------------------------------------
-- Server version 5.5.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `title` text,
  `description` text,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `is_global` tinyint(1) NOT NULL DEFAULT '0',
  `expected_leader_id` bigint(20) DEFAULT NULL,
  `leader_id` bigint(20) DEFAULT NULL,
  `expected_research_outputs` text,
  `expected_gender_contribution` text,
  `outcome` text,
  `gender_percentage` double DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`),
  KEY `FK_activities_activity_leaders_idx` (`leader_id`),
  KEY `FK_activities_expected_leader_id_idx` (`expected_leader_id`),
  CONSTRAINT `FK_activities_expected_leader_id` FOREIGN KEY (`expected_leader_id`) REFERENCES `expected_activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_activities_leader_id` FOREIGN KEY (`leader_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Activities_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_budgets`
--

DROP TABLE IF EXISTS `activity_budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_budgets` (
  `activity_id` bigint(20) NOT NULL,
  `budget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`activity_id`,`budget_id`),
  KEY `FK_activity_budgets_budget_id_idx` (`budget_id`),
  CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_cross_cutting_themes`
--

DROP TABLE IF EXISTS `activity_cross_cutting_themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_cross_cutting_themes` (
  `activity_id` bigint(20) NOT NULL,
  `theme_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_cross_cutting_themes',
  KEY `FK_projects_themes_projects_idx` (`activity_id`),
  KEY `FK_projects_themes_cutting_themes_idx` (`theme_id`),
  CONSTRAINT `FK_activity_cross_cutting_themes_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_themes_cutting_themes` FOREIGN KEY (`theme_id`) REFERENCES `ip_cross_cutting_themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_locations`
--

DROP TABLE IF EXISTS `activity_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_locations` (
  `activity_id` bigint(20) NOT NULL,
  `loc_element_id` bigint(20) NOT NULL,
  PRIMARY KEY (`activity_id`,`loc_element_id`),
  KEY `FK_activity_locations_loc_element_types_idx` (`loc_element_id`),
  CONSTRAINT `FK_activity_locations_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_activity_locations_loc_element_types` FOREIGN KEY (`loc_element_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_partners`
--

DROP TABLE IF EXISTS `activity_partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contribution` text,
  PRIMARY KEY (`id`),
  KEY `FL_activity_partners_institutions.id_idx` (`institution_id`),
  KEY `FK_activity_partners_activities_idx` (`activity_id`),
  CONSTRAINT `FK_activity_partners_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_activity_partners_institutions.id` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board_messages`
--

DROP TABLE IF EXISTS `board_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budget_types`
--

DROP TABLE IF EXISTS `budget_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budgets`
--

DROP TABLE IF EXISTS `budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `budget_type` int(11) NOT NULL COMMENT 'Foreign key to the budget_types table.',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to the institutions table.',
  `amount` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_institution_id_idx` (`institution_id`),
  KEY `FK_budget_type_idx` (`budget_type`),
  CONSTRAINT `FK_budget_type` FOREIGN KEY (`budget_type`) REFERENCES `budget_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_institution_id` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverable_types`
--

DROP TABLE IF EXISTS `deliverable_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverable_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `timeline` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverable_types_parent_id_idx` (`parent_id`),
  CONSTRAINT `deliverable_types_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverables`
--

DROP TABLE IF EXISTS `deliverables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverables` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `title` text,
  `type_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverables_activities_idx` (`activity_id`),
  KEY `deliverables_deliverables_type_idx` (`type_id`),
  CONSTRAINT `deliverables_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliverables_deliverables_type` FOREIGN KEY (`type_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table users',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table institutions.',
  `role_id` bigint(20) NOT NULL COMMENT 'Positions inside CCAFS',
  `is_main` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_employees_persons_idx` (`user_id`),
  KEY `FK_employees_institutions_idx` (`institution_id`),
  KEY `FK_employees_roles_idx` (`role_id`),
  CONSTRAINT `FK_employees_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employees_persons` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employees_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employees_backup`
--

DROP TABLE IF EXISTS `employees_backup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees_backup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table users',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table institutions.',
  `role_id` bigint(20) NOT NULL COMMENT 'Positions inside CCAFS',
  `is_main` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_employees_persons_idx` (`user_id`),
  KEY `FK_employees_institutions_idx` (`institution_id`),
  KEY `FK_employees_roles_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expected_activity_leaders`
--

DROP TABLE IF EXISTS `expected_activity_leaders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expected_activity_leaders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_official` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_activity_leaders_institutions_idx` (`institution_id`),
  CONSTRAINT `FK_activity_leaders_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `expected_project_leaders`
--

DROP TABLE IF EXISTS `expected_project_leaders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expected_project_leaders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact_first_name` varchar(255) DEFAULT NULL,
  `contact_last_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to Institution id',
  PRIMARY KEY (`id`),
  KEY `PK_Institution_idx` (`institution_id`),
  CONSTRAINT `PK_Institution` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `institution_locations`
--

DROP TABLE IF EXISTS `institution_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution_locations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_institution_locations_institutions_idx` (`institution_id`),
  KEY `FK_institution_locations_loc_elements_idx` (`country_id`),
  CONSTRAINT `FK_institution_locations_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_institution_locations_loc_elements` FOREIGN KEY (`country_id`) REFERENCES `loc_elements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `institution_types`
--

DROP TABLE IF EXISTS `institution_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `institutions`
--

DROP TABLE IF EXISTS `institutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institutions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `acronym` varchar(45) DEFAULT NULL,
  `contact_person_name` varchar(255) DEFAULT NULL,
  `contact_person_email` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `website_link` varchar(255) DEFAULT NULL,
  `program_id` bigint(20) DEFAULT NULL,
  `institution_type_id` bigint(20) NOT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_ip_program_lead_institutions_idx` (`program_id`),
  KEY `FK_institutions_institution_types_idx` (`institution_type_id`),
  KEY `FK_loc_elements_id_idx` (`country_id`),
  CONSTRAINT `FK_institution_institution_type` FOREIGN KEY (`institution_type_id`) REFERENCES `institution_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_institution_loc_elements` FOREIGN KEY (`country_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ip_program_lead_institutions` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_activity_contributions`
--

DROP TABLE IF EXISTS `ip_activity_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_activity_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table activities.',
  `mog_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table IP Elements.\nThe vales referenced in this column should be of type ''Outputs'' but this constraint is checked at application level.',
  `midOutcome_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table IP Elements.\nThe vales referenced in this column should be of type ''midOutcome'' but this constraint is checked at application level.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_activity_contributions_unique` (`activity_id`,`mog_id`),
  KEY `FK_activities_ipElements_activityID_idx` (`activity_id`),
  KEY `FK_activities_ipElements_ipElementID_idx` (`mog_id`),
  KEY `FK_activities_midOutcome_ipElementID_idx` (`midOutcome_id`),
  CONSTRAINT `aa` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dd` FOREIGN KEY (`mog_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ff` FOREIGN KEY (`midOutcome_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_activity_indicators`
--

DROP TABLE IF EXISTS `ip_activity_indicators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_activity_indicators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `target` text,
  `activity_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table activities',
  `parent_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table ip_indicators.',
  PRIMARY KEY (`id`),
  KEY `FK_ipActivityIndicators_ipIndicators_parentID_idx` (`parent_id`),
  KEY `FK_ipActivityIndicators_ipIndicators_activityID_idx` (`activity_id`),
  CONSTRAINT `FK_ipActivityIndicators_ipIndicators_activityID` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ipActivityIndicators_ipIndicators_parentID` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_cross_cutting_themes`
--

DROP TABLE IF EXISTS `ip_cross_cutting_themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_cross_cutting_themes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_deliverable_contributions`
--

DROP TABLE IF EXISTS `ip_deliverable_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_deliverable_contributions` (
  `project_contribution_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references the table ip_project_contributions',
  `deliverable_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references the table deliverables',
  PRIMARY KEY (`project_contribution_id`,`deliverable_id`),
  UNIQUE KEY `UK_delverable_contributions` (`project_contribution_id`,`deliverable_id`),
  KEY `FK_deliverables_ipProjectContributions_contributionID_idx` (`project_contribution_id`),
  KEY `FK_deliverables_ipProjectContributions_deliverableID_idx` (`deliverable_id`),
  KEY `PK_deliverable_contributions` (`project_contribution_id`,`deliverable_id`),
  CONSTRAINT `FK_deliverables_ipProjectContributions_contributionID` FOREIGN KEY (`project_contribution_id`) REFERENCES `ip_project_contributions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverables_ipProjectContributions_deliverableID` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_element_types`
--

DROP TABLE IF EXISTS `ip_element_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_elements`
--

DROP TABLE IF EXISTS `ip_elements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `element_type_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_element_types',
  PRIMARY KEY (`id`),
  KEY `FK_element_element_type_idx` (`element_type_id`),
  CONSTRAINT `FK_element_element_type` FOREIGN KEY (`element_type_id`) REFERENCES `ip_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_indicators`
--

DROP TABLE IF EXISTS `ip_indicators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_indicators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `target` text,
  `program_element_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT 'Foreign key to ip_indicators table. This field shows if the indicator contributes to another indicator',
  PRIMARY KEY (`id`),
  KEY `FK_indicators_program_elements_idx` (`program_element_id`),
  KEY `FK_indicators_parent_indicator_idx` (`parent_id`),
  CONSTRAINT `FK_indicators_parent_indicator` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_indicators_program_elements` FOREIGN KEY (`program_element_id`) REFERENCES `ip_program_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_other_contributions`
--

DROP TABLE IF EXISTS `ip_other_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_other_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `contribution` text,
  `additional_contribution` text,
  PRIMARY KEY (`id`),
  KEY `FK_ip_other_contributions_idx` (`project_id`),
  CONSTRAINT `FK_ip_other_contributions` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_program_element_relation_types`
--

DROP TABLE IF EXISTS `ip_program_element_relation_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_program_element_relation_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'This field describe the relation between the element and the program',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_program_elements`
--

DROP TABLE IF EXISTS `ip_program_elements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_program_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_elements',
  `program_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_programs',
  `relation_type_id` int(11) DEFAULT NULL COMMENT 'Foreign key to the table ip_program_element_relation_types',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`element_id`,`program_id`,`relation_type_id`) COMMENT ' /* comment truncated */ /*This index prevents that a program has more than one relation to the same element.*/',
  KEY `FK_program_elements_program_idx` (`program_id`),
  KEY `FK_program_elements_elements_idx` (`element_id`),
  KEY `FK_program_elements_element_relation_types_idx` (`relation_type_id`),
  CONSTRAINT `FK_program_elements_elements` FOREIGN KEY (`element_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_program_elements_element_relation_types` FOREIGN KEY (`relation_type_id`) REFERENCES `ip_program_element_relation_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_program_elements_program` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_program_types`
--

DROP TABLE IF EXISTS `ip_program_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_program_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_programs`
--

DROP TABLE IF EXISTS `ip_programs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_programs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text,
  `acronym` varchar(45) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ip_program_program_type_idx` (`type_id`),
  KEY `FK_ip_program_ip_region_idx` (`region_id`),
  CONSTRAINT `FK_ip_program_ip_region` FOREIGN KEY (`region_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ip_program_program_type` FOREIGN KEY (`type_id`) REFERENCES `ip_program_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_project_contributions`
--

DROP TABLE IF EXISTS `ip_project_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_project_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table projects.',
  `mog_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table IP Elements.\nThe vales referenced in this column should be of type ''Outputs'' but this constraint is checked at application level.',
  `midOutcome_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table IP Elements.\nThe vales referenced in this column should be of type midOutcome but this constraint is checked at application level.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_contributions_unique` (`project_id`,`mog_id`,`midOutcome_id`),
  KEY `FK_projects_ipElements_ipElementID_idx` (`mog_id`),
  KEY `FK_projects_midOutcome_ipElementID_idx` (`midOutcome_id`),
  KEY `FK_projects_ipElements_projectID_idx` (`project_id`),
  CONSTRAINT `FK_projects_ipElements_ipElementID` FOREIGN KEY (`mog_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_ipElements_projectID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_midOutcome_ipElementID` FOREIGN KEY (`midOutcome_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_project_indicators`
--

DROP TABLE IF EXISTS `ip_project_indicators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_project_indicators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `target` text,
  `year` int(4) NOT NULL,
  `project_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table activities',
  `parent_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table ip_indicators.',
  `outcome_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ipProjectIndicator` (`year`,`project_id`,`parent_id`,`outcome_id`),
  KEY `FK_ipProjectIndicators_ipIndicators_parentID_idx` (`parent_id`),
  KEY `FK_ipProjectIndicators_ipIndicators_projectID_idx` (`project_id`),
  CONSTRAINT `FK_ipProectIndicators_ipIndicators_parentID` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ipProjectIndicators_ipIndicators_projectID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_relationship_type`
--

DROP TABLE IF EXISTS `ip_relationship_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_relationship_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'This field describes the type of relation between the child element and the parent element',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_relationships`
--

DROP TABLE IF EXISTS `ip_relationships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_relationships` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `child_id` bigint(20) NOT NULL,
  `relation_type_id` bigint(20) NOT NULL DEFAULT '1' COMMENT 'Foreign key to the table ip_relationship_type, by default the value is 1 (''Contributes to''  type)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `KEY_unique_index` (`parent_id`,`child_id`) COMMENT ' /* comment truncated */ /*Each element have to be related with another element only once.*/',
  KEY `FK_element_relations_child_idx` (`parent_id`),
  KEY `test_idx` (`child_id`),
  KEY `FK_element_relations_relationship_types_idx` (`relation_type_id`),
  CONSTRAINT `FK_element_relations_child` FOREIGN KEY (`parent_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_element_relations_parent` FOREIGN KEY (`child_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_element_relations_relationship_types` FOREIGN KEY (`relation_type_id`) REFERENCES `ip_relationship_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loc_element_types`
--

DROP TABLE IF EXISTS `loc_element_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loc_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(245) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_element_type_parent_idx` (`parent_id`),
  CONSTRAINT `FK_loc_element_type_parent` FOREIGN KEY (`parent_id`) REFERENCES `loc_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loc_elements`
--

DROP TABLE IF EXISTS `loc_elements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loc_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `code` varchar(45) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `element_type_id` bigint(20) NOT NULL,
  `geoposition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_element_loc_element_type_id_idx` (`element_type_id`),
  KEY `FK_loc_elements_loc_geopositions_idx` (`geoposition_id`),
  CONSTRAINT `FK_loc_elements_loc_geopositions` FOREIGN KEY (`geoposition_id`) REFERENCES `loc_geopositions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_loc_element_loc_element_type_id` FOREIGN KEY (`element_type_id`) REFERENCES `loc_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `loc_geopositions`
--

DROP TABLE IF EXISTS `loc_geopositions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loc_geopositions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_geoposition_loc_geopositon_idx` (`parent_id`),
  CONSTRAINT `FK_loc_geoposition_loc_geopositon` FOREIGN KEY (`parent_id`) REFERENCES `loc_geopositions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `next_users`
--

DROP TABLE IF EXISTS `next_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `next_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliverable_id` bigint(20) NOT NULL,
  `user` text,
  `expected_changes` text,
  `strategies` text,
  PRIMARY KEY (`id`),
  KEY `FL_next_users_deliverables_idx` (`deliverable_id`),
  CONSTRAINT `FL_next_users_deliverables` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table site_elements',
  `role_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table roles',
  `permission` enum('Create','Update','Read','Delete') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_permissions_elements_idx` (`element_id`),
  KEY `FK_permissions_roles_idx` (`role_id`),
  CONSTRAINT `FK_permissions_elements` FOREIGN KEY (`element_id`) REFERENCES `site_elements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_permissions_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_budgets`
--

DROP TABLE IF EXISTS `project_budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `budget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_id_idx` (`project_id`),
  KEY `FK_budget_id_idx` (`budget_id`),
  CONSTRAINT `FK_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_focuses`
--

DROP TABLE IF EXISTS `project_focuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_focuses` (
  `project_id` bigint(20) NOT NULL,
  `program_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`program_id`),
  KEY `FK_project_focus_project_id_idx` (`project_id`),
  KEY `FK_project_focus_program_id_idx` (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_outcomes`
--

DROP TABLE IF EXISTS `project_outcomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_outcomes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `statement` text,
  `stories` text,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`),
  CONSTRAINT `FK_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_partner_budget`
--

DROP TABLE IF EXISTS `project_partner_budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_partner_budget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  `total` double DEFAULT NULL,
  `ccafs_funding` int(11) DEFAULT NULL,
  `bilateral` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_budget_projects_idx` (`project_id`),
  CONSTRAINT `FK_project_budget_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_partners`
--

DROP TABLE IF EXISTS `project_partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT 'Foreign key to projects table',
  `partner_id` bigint(20) NOT NULL COMMENT 'Foreign key to institutions table',
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `responsabilities` text,
  PRIMARY KEY (`id`),
  KEY `FK_project_partners_projects_idx` (`project_id`),
  KEY `FK_project_partners_institutions_idx` (`partner_id`),
  CONSTRAINT `FK_project_partners_institutions` FOREIGN KEY (`partner_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_partners_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` text,
  `summary` text,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `leader_responsabilities` text,
  `expected_project_leader_id` bigint(20) DEFAULT NULL,
  `project_leader_id` bigint(20) DEFAULT NULL COMMENT 'Foreign key to the ccafs employees table',
  `program_creator_id` bigint(20) DEFAULT NULL,
  `project_owner_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_projects_employees_idx` (`project_leader_id`),
  KEY `FK_projects_owner_employees_idx` (`project_owner_id`),
  KEY `FK_projects_expected_project_leaders_idx` (`expected_project_leader_id`),
  KEY `FK_projects_program_id_idx` (`program_creator_id`),
  CONSTRAINT `FK_projects_employees` FOREIGN KEY (`project_leader_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_expected_project_leaders` FOREIGN KEY (`expected_project_leader_id`) REFERENCES `expected_project_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_owner_employees` FOREIGN KEY (`project_owner_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_program_id` FOREIGN KEY (`program_creator_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site_element_types`
--

DROP TABLE IF EXISTS `site_element_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `site_elements`
--

DROP TABLE IF EXISTS `site_elements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `site_element_type_id` bigint(20) NOT NULL COMMENT 'foreign key to the table site_elements_types',
  PRIMARY KEY (`id`),
  KEY `FK_elements_element_types_idx` (`site_element_type_id`),
  CONSTRAINT `FK_elements_element_types` FOREIGN KEY (`site_element_type_id`) REFERENCES `site_element_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `person_id` bigint(20) NOT NULL COMMENT 'Foreign key to persons table',
  `is_ccafs_user` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` bigint(20) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  `last_login` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FK_users_person_id_idx` (`person_id`),
  KEY `FK_users_user_id_idx` (`created_by`),
  CONSTRAINT `FK_users_person_id` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_users_user_id` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-13 10:19:26
