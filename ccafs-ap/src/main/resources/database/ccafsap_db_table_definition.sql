-- MySQL dump 10.13  Distrib 5.5.28, for Win64 (x86)
--
-- Host: mysql.amkn.org    Database: ccafsap_db
-- ------------------------------------------------------
-- Server version 5.1.56-log

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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `description` text NOT NULL,
  `milestone_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `is_global` tinyint(1) NOT NULL,
  `continuous_activity_id` int(11) DEFAULT NULL,
  `is_planning` tinyint(1) NOT NULL,
  `activity_status_id` int(11) NOT NULL,
  `status_description` text,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `milestone_fk` (`milestone_id`),
  KEY `activity_leader_fk` (`activity_leader_id`),
  KEY `continous_activity_fk` (`continuous_activity_id`),
  KEY `status_fk` (`activity_status_id`),
  CONSTRAINT `activities_ibfk_4` FOREIGN KEY (`activity_status_id`) REFERENCES `activity_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activities_ibfk_1` FOREIGN KEY (`milestone_id`) REFERENCES `milestones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activities_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activities_ibfk_3` FOREIGN KEY (`continuous_activity_id`) REFERENCES `activities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8 PACK_KEYS=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_budgets`
--

DROP TABLE IF EXISTS `activity_budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_budgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usd` double NOT NULL,
  `cg_funds` int(11) DEFAULT NULL,
  `bilateral` int(11) DEFAULT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cg_funds_fk` (`cg_funds`),
  KEY `bilateral_fk` (`bilateral`),
  KEY `budgets_activity_fk` (`activity_id`),
  CONSTRAINT `activity_budgets_ibfk_3` FOREIGN KEY (`bilateral`) REFERENCES `budget_percentages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_budgets_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_budgets_ibfk_2` FOREIGN KEY (`cg_funds`) REFERENCES `budget_percentages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=399 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_keywords`
--

DROP TABLE IF EXISTS `activity_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NOT NULL,
  `keyword_id` int(11) DEFAULT NULL,
  `other` text,
  PRIMARY KEY (`id`),
  KEY `ak_activity_fk` (`activity_id`),
  KEY `ak_keyword_fk` (`keyword_id`),
  CONSTRAINT `activity_keywords_ibfk_2` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_keywords_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1332 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_leaders`
--

DROP TABLE IF EXISTS `activity_leaders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_leaders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(20) NOT NULL DEFAULT 'ACRONYM',
  `name` text NOT NULL,
  `led_activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `led_activity_fk` (`led_activity_id`),
  CONSTRAINT `activity_leaders_ibfk_1` FOREIGN KEY (`led_activity_id`) REFERENCES `leader_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_objectives`
--

DROP TABLE IF EXISTS `activity_objectives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_fk` (`activity_id`),
  CONSTRAINT `activity_objectives_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_partner_roles`
--

DROP TABLE IF EXISTS `activity_partner_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_partner_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_partner_id` int(11) NOT NULL,
  `partner_role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_partner_fk` (`activity_partner_id`),
  KEY `partner_role_fk` (`partner_role_id`),
  CONSTRAINT `activity_partner_roles_ibfk_2` FOREIGN KEY (`partner_role_id`) REFERENCES `partner_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_partner_roles_ibfk_1` FOREIGN KEY (`activity_partner_id`) REFERENCES `activity_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_partners`
--

DROP TABLE IF EXISTS `activity_partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partner_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `contact_name` text,
  `contact_email` text,
  PRIMARY KEY (`id`),
  KEY `ap_partner_fk` (`partner_id`),
  KEY `ap_activity_fk` (`activity_id`),
  CONSTRAINT `activity_partners_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_partners_ibfk_1` FOREIGN KEY (`partner_id`) REFERENCES `partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activity_status`
--

DROP TABLE IF EXISTS `activity_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `benchmark_sites`
--

DROP TABLE IF EXISTS `benchmark_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `benchmark_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bs_id` text NOT NULL,
  `name` text NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_fk` (`country_iso2`),
  CONSTRAINT `benchmark_sites_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bs_locations`
--

DROP TABLE IF EXISTS `bs_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bs_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bs_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `bs_fk` (`bs_id`),
  KEY `activity_id` (`activity_id`),
  CONSTRAINT `bs_locations_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bs_locations_ibfk_1` FOREIGN KEY (`bs_id`) REFERENCES `benchmark_sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=528 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `budget_percentages`
--

DROP TABLE IF EXISTS `budget_percentages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_percentages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `percentage` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `case_studies`
--

DROP TABLE IF EXISTS `case_studies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_studies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `author` text NOT NULL,
  `date` date NOT NULL,
  `photo` text,
  `objectives` text,
  `description` text,
  `results` text,
  `partners` text,
  `links` text,
  `keywords` text,
  `logframe_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cs_logframe_fk` (`logframe_id`),
  KEY `cs_activity_leader` (`activity_leader_id`),
  CONSTRAINT `case_studies_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `case_studies_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `case_study_countries`
--

DROP TABLE IF EXISTS `case_study_countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `case_study_countries` (
  `id` int(11) NOT NULL,
  `case_study_id` int(11) NOT NULL AUTO_INCREMENT,
  `country_iso2` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `csc_case_study_fk` (`case_study_id`),
  KEY `csc_country_fk` (`country_iso2`),
  CONSTRAINT `case_study_countries_ibfk_2` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `case_study_countries_ibfk_1` FOREIGN KEY (`case_study_id`) REFERENCES `case_studies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_person`
--

DROP TABLE IF EXISTS `contact_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `activity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cp_activity_fk` (`activity_id`),
  CONSTRAINT `contact_person_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=413 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `countries`
--

DROP TABLE IF EXISTS `countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `countries` (
  `iso2` varchar(2) NOT NULL,
  `name` text NOT NULL,
  `region_id` int(11) NOT NULL,
  PRIMARY KEY (`iso2`),
  KEY `region_fk` (`region_id`),
  CONSTRAINT `countries_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country_locations`
--

DROP TABLE IF EXISTS `country_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_iso2` varchar(2) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `cl_country_fk` (`country_iso2`),
  KEY `cl_activity_fk` (`activity_id`),
  CONSTRAINT `country_locations_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `country_locations_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2312 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverable_formats`
--

DROP TABLE IF EXISTS `deliverable_formats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverable_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliverable_id` int(11) NOT NULL,
  `file_format_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file_format_id` (`file_format_id`),
  KEY `deliverable_id` (`deliverable_id`),
  CONSTRAINT `deliverable_formats_ibfk_2` FOREIGN KEY (`file_format_id`) REFERENCES `file_formats` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliverable_formats_ibfk_1` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverable_status`
--

DROP TABLE IF EXISTS `deliverable_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverable_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverable_types`
--

DROP TABLE IF EXISTS `deliverable_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverable_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deliverables`
--

DROP TABLE IF EXISTS `deliverables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `year` int(4) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `deliverable_type_id` int(11) NOT NULL,
  `is_expected` tinyint(1) NOT NULL,
  `deliverable_status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_fk2` (`activity_id`),
  KEY `deliverable_type_fk2` (`deliverable_type_id`),
  KEY `deliverable_status_fk2` (`deliverable_status_id`),
  CONSTRAINT `deliverables_ibfk_3` FOREIGN KEY (`deliverable_status_id`) REFERENCES `deliverable_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliverables_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `deliverables_ibfk_2` FOREIGN KEY (`deliverable_type_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=860 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_formats`
--

DROP TABLE IF EXISTS `file_formats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gender_integrations`
--

DROP TABLE IF EXISTS `gender_integrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gender_integrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gi_activity_fk` (`activity_id`),
  CONSTRAINT `gender_integrations_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keywords`
--

DROP TABLE IF EXISTS `keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leader_types`
--

DROP TABLE IF EXISTS `leader_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leader_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leverages`
--

DROP TABLE IF EXISTS `leverages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leverages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_id` int(11) NOT NULL,
  `partner` text NOT NULL,
  `project_title` text NOT NULL,
  `project_budget` double NOT NULL,
  `duration` varchar(9) NOT NULL DEFAULT 'year-year',
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lev_theme_fk` (`theme_id`),
  KEY `lev_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `leverages_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `leverages_ibfk_1` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logframes`
--

DROP TABLE IF EXISTS `logframes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logframes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(4) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `milestone_reports`
--

DROP TABLE IF EXISTS `milestone_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestone_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `milestone_id` int(11) NOT NULL,
  `milestone_status_id` int(11) DEFAULT NULL,
  `tl_description` text,
  `rpl_description` text,
  PRIMARY KEY (`id`),
  KEY `mr_milestone_fk` (`milestone_id`),
  KEY `mr_milestone_status_fk` (`milestone_status_id`),
  CONSTRAINT `milestone_reports_ibfk_2` FOREIGN KEY (`milestone_status_id`) REFERENCES `milestone_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `milestone_reports_ibfk_1` FOREIGN KEY (`milestone_id`) REFERENCES `milestones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `milestone_status`
--

DROP TABLE IF EXISTS `milestone_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestone_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `milestones`
--

DROP TABLE IF EXISTS `milestones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `year` int(4) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `output_fk` (`output_id`),
  CONSTRAINT `milestones_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `objectives`
--

DROP TABLE IF EXISTS `objectives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  `outcome_description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `theme_fk` (`theme_id`),
  CONSTRAINT `objectives_ibfk_1` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `other_sites`
--

DROP TABLE IF EXISTS `other_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `os_country_fk` (`country_iso2`),
  KEY `os_activity_fk` (`activity_id`),
  CONSTRAINT `other_sites_ibfk_2` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `other_sites_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `outcomes`
--

DROP TABLE IF EXISTS `outcomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outcomes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outcome` text NOT NULL,
  `outputs` text NOT NULL,
  `partners` text NOT NULL,
  `output_user` text NOT NULL,
  `how_used` text NOT NULL,
  `evidence` text NOT NULL,
  `logframe_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `out_logframe_fk` (`logframe_id`),
  KEY `out_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `outcomes_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `outcomes_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `output_summaries`
--

DROP TABLE IF EXISTS `output_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `os_output_fk` (`output_id`),
  KEY `os_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `output_summaries_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `output_summaries_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `outputs`
--

DROP TABLE IF EXISTS `outputs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outputs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `objective_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `objective_fk` (`objective_id`),
  CONSTRAINT `outputs_ibfk_1` FOREIGN KEY (`objective_id`) REFERENCES `objectives` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_roles`
--

DROP TABLE IF EXISTS `partner_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partner_types`
--

DROP TABLE IF EXISTS `partner_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partner_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(20) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partners`
--

DROP TABLE IF EXISTS `partners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `acronym` text,
  `country_iso2` varchar(2) DEFAULT NULL,
  `city` text,
  `partner_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_fk` (`partner_type_id`),
  KEY `country_iso2` (`country_iso2`),
  CONSTRAINT `partners_ibfk_2` FOREIGN KEY (`partner_type_id`) REFERENCES `partner_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `partners_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=474 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publication_types`
--

DROP TABLE IF EXISTS `publication_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publication_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publications`
--

DROP TABLE IF EXISTS `publications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publication_type_id` int(11) NOT NULL,
  `identifier` text NOT NULL,
  `citation` text NOT NULL,
  `logframe_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `p_publication_type_fk` (`publication_type_id`),
  KEY `p_logframe_fk` (`logframe_id`),
  KEY `p_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `publications_ibfk_3` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `publications_ibfk_1` FOREIGN KEY (`publication_type_id`) REFERENCES `publication_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `publications_ibfk_2` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regions`
--

DROP TABLE IF EXISTS `regions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `res_activity_fk` (`activity_id`),
  CONSTRAINT `resources_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rpl_shynthesis_reports`
--

DROP TABLE IF EXISTS `rpl_shynthesis_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rpl_shynthesis_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccafs_sites` text NOT NULL,
  `cross_center` text NOT NULL,
  `regional` text NOT NULL,
  `decision_support` text NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rplsr_activity_leader_fk` (`activity_leader_id`),
  KEY `rplsr_logframe_fk` (`logframe_id`),
  CONSTRAINT `rpl_shynthesis_reports_ibfk_2` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rpl_shynthesis_reports_ibfk_1` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `themes`
--

DROP TABLE IF EXISTS `themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `themes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` text NOT NULL,
  `description` text,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `logframe_fk` (`logframe_id`),
  CONSTRAINT `themes_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tl_output_summaries`
--

DROP TABLE IF EXISTS `tl_output_summaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tlos_output_fk` (`output_id`),
  KEY `tlos_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `tl_output_summaries_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tl_output_summaries_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `role` enum('Admin','CP','TL','RPL') NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_activity_leader_fk` (`activity_leader_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-01-03 13:55:36
