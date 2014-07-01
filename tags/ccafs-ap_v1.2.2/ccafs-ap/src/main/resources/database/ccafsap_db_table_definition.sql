-- MySQL dump 10.13  Distrib 5.5.28, for Win64 (x86)
--
-- ------------------------------------------------------
-- Server version 5.5.30

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
-- Estructura de tabla para la tabla `activities`
--

DROP TABLE IF EXISTS `activities`;
CREATE TABLE IF NOT EXISTS `activities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` varchar(10) DEFAULT NULL,
  `title` text NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `year` int(11) NOT NULL,
  `description` text,
  `milestone_id` int(11) DEFAULT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `is_global` tinyint(1) DEFAULT NULL,
  `has_partners` tinyint(1) DEFAULT NULL,
  `continuous_activity_id` int(11) DEFAULT NULL,
  `activity_status_id` int(11) DEFAULT NULL,
  `status_description` text,
  `is_commissioned` tinyint(1) NOT NULL DEFAULT '0',
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `milestone_fk` (`milestone_id`),
  KEY `activity_leader_fk` (`activity_leader_id`),
  KEY `continous_activity_fk` (`continuous_activity_id`),
  KEY `status_fk` (`activity_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 PACK_KEYS=1 AUTO_INCREMENT=1031 ;

--
-- Disparadores `activities`
--
DROP TRIGGER IF EXISTS `activity_id_insert_trigger`;
DELIMITER //
CREATE TRIGGER `activity_id_insert_trigger` BEFORE INSERT ON `activities`
 FOR EACH ROW IF( NEW.continuous_activity_id IS NULL ) THEN
    SET NEW.activity_id = CONCAT( (SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ccafsap' AND TABLE_NAME = 'activities'), "-", NEW.`year`);
    ELSE
    SET NEW.activity_id = CONCAT( NEW.continuous_activity_id, "-", NEW.`year`);
    END IF
//
DELIMITER ;
DROP TRIGGER IF EXISTS `activity_id_update_trigger`;
DELIMITER //
CREATE TRIGGER `activity_id_update_trigger` BEFORE UPDATE ON `activities`
 FOR EACH ROW IF( NEW.continuous_activity_id IS NULL ) THEN
    SET NEW.activity_id = CONCAT( NEW.id, "-", NEW.`year`);
    ELSE
    SET NEW.activity_id = CONCAT( NEW.continuous_activity_id, "-", NEW.`year`);
    END IF
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_budgets`
--

DROP TABLE IF EXISTS `activity_budgets`;
CREATE TABLE IF NOT EXISTS `activity_budgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usd` double NOT NULL,
  `cg_funds` int(11) DEFAULT NULL,
  `bilateral` int(11) DEFAULT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cg_funds_fk` (`cg_funds`),
  KEY `bilateral_fk` (`bilateral`),
  KEY `budgets_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1015 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_keywords`
--

DROP TABLE IF EXISTS `activity_keywords`;
CREATE TABLE IF NOT EXISTS `activity_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NOT NULL,
  `keyword_id` int(11) DEFAULT NULL,
  `other` text,
  PRIMARY KEY (`id`),
  KEY `ak_activity_fk` (`activity_id`),
  KEY `ak_keyword_fk` (`keyword_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5997 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_leaders`
--

DROP TABLE IF EXISTS `activity_leaders`;
CREATE TABLE IF NOT EXISTS `activity_leaders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(20) NOT NULL DEFAULT 'ACRONYM',
  `name` text NOT NULL,
  `led_activity_id` int(11) NOT NULL,
  `region_id` int(11) DEFAULT NULL,
  `theme_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `led_activity_fk` (`led_activity_id`),
  KEY `region_id_fk` (`region_id`),
  KEY `theme_id_fk` (`theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_objectives`
--

DROP TABLE IF EXISTS `activity_objectives`;
CREATE TABLE IF NOT EXISTS `activity_objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2248 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_partners`
--

DROP TABLE IF EXISTS `activity_partners`;
CREATE TABLE IF NOT EXISTS `activity_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partner_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `contact_name` text,
  `contact_email` text,
  PRIMARY KEY (`id`),
  KEY `ap_partner_fk` (`partner_id`),
  KEY `ap_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4070 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_partner_roles`
--

DROP TABLE IF EXISTS `activity_partner_roles`;
CREATE TABLE IF NOT EXISTS `activity_partner_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_partner_id` int(11) NOT NULL,
  `partner_role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_partner_fk` (`activity_partner_id`),
  KEY `partner_role_fk` (`partner_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_status`
--

DROP TABLE IF EXISTS `activity_status`;
CREATE TABLE IF NOT EXISTS `activity_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_validations`
--

DROP TABLE IF EXISTS `activity_validations`;
CREATE TABLE IF NOT EXISTS `activity_validations` (
  `activity_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `activity_id_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `benchmark_sites`
--

DROP TABLE IF EXISTS `benchmark_sites`;
CREATE TABLE IF NOT EXISTS `benchmark_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bs_id` text NOT NULL,
  `name` text NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `country_fk` (`country_iso2`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bs_locations`
--

DROP TABLE IF EXISTS `bs_locations`;
CREATE TABLE IF NOT EXISTS `bs_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bs_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `bs_fk` (`bs_id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1043 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `budget_percentages`
--

DROP TABLE IF EXISTS `budget_percentages`;
CREATE TABLE IF NOT EXISTS `budget_percentages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `percentage` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_studies`
--

DROP TABLE IF EXISTS `case_studies`;
CREATE TABLE IF NOT EXISTS `case_studies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `author` text NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `photo` text,
  `objectives` text,
  `description` text,
  `results` text,
  `partners` text,
  `links` text,
  `keywords` text,
  `is_global` tinyint(1) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cs_logframe_fk` (`logframe_id`),
  KEY `cs_activity_leader` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=170 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_study_countries`
--

DROP TABLE IF EXISTS `case_study_countries`;
CREATE TABLE IF NOT EXISTS `case_study_countries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_study_id` int(11) NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `csc_case_study_fk` (`case_study_id`),
  KEY `csc_country_fk` (`country_iso2`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1676 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_study_types`
--

DROP TABLE IF EXISTS `case_study_types`;
CREATE TABLE IF NOT EXISTS `case_study_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `communications`
--

DROP TABLE IF EXISTS `communications`;
CREATE TABLE IF NOT EXISTS `communications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `media_campaigns` text,
  `blogs` text,
  `websites` text,
  `social_media_campaigns` text,
  `newsletters` text,
  `events` text,
  `videos_multimedia` text,
  `other_communications` text,
  `activity_leader_id` int(11) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_communications_logframe_id` (`logframe_id`),
  KEY `FK_communications_activity_leader_id` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contact_person`
--

DROP TABLE IF EXISTS `contact_person`;
CREATE TABLE IF NOT EXISTS `contact_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `email` text,
  `activity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cp_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2102 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `countries`
--

DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (
  `iso2` varchar(2) NOT NULL,
  `name` text NOT NULL,
  `region_id` int(11) NOT NULL,
  PRIMARY KEY (`iso2`),
  KEY `region_fk` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `country_locations`
--

DROP TABLE IF EXISTS `country_locations`;
CREATE TABLE IF NOT EXISTS `country_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_iso2` varchar(2) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `cl_country_fk` (`country_iso2`),
  KEY `cl_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4906 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cs_types`
--

DROP TABLE IF EXISTS `cs_types`;
CREATE TABLE IF NOT EXISTS `cs_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_study_id` int(11) NOT NULL,
  `case_study_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `case_study_id` (`case_study_id`),
  KEY `case_study_type_id` (`case_study_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1962 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverables`
--

DROP TABLE IF EXISTS `deliverables`;
CREATE TABLE IF NOT EXISTS `deliverables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `year` int(4) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `deliverable_type_id` int(11) NOT NULL,
  `is_expected` tinyint(1) NOT NULL,
  `deliverable_status_id` int(11) NOT NULL,
  `filename` text,
  `description_update` text,
  PRIMARY KEY (`id`),
  KEY `activity_fk2` (`activity_id`),
  KEY `deliverable_type_fk2` (`deliverable_type_id`),
  KEY `deliverable_status_fk2` (`deliverable_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3448 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_formats`
--

DROP TABLE IF EXISTS `deliverable_formats`;
CREATE TABLE IF NOT EXISTS `deliverable_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliverable_id` int(11) NOT NULL,
  `file_format_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file_format_id` (`file_format_id`),
  KEY `deliverable_id` (`deliverable_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1815 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_status`
--

DROP TABLE IF EXISTS `deliverable_status`;
CREATE TABLE IF NOT EXISTS `deliverable_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_types`
--

DROP TABLE IF EXISTS `deliverable_types`;
CREATE TABLE IF NOT EXISTS `deliverable_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `file_formats`
--

DROP TABLE IF EXISTS `file_formats`;
CREATE TABLE IF NOT EXISTS `file_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gender_integrations`
--

DROP TABLE IF EXISTS `gender_integrations`;
CREATE TABLE IF NOT EXISTS `gender_integrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gi_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=792 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicators`
--

DROP TABLE IF EXISTS `indicators`;
CREATE TABLE IF NOT EXISTS `indicators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serial` varchar(5) NOT NULL,
  `name` text NOT NULL,
  `description` text NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `indicator_type_id` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `indicator_type_id_fk` (`indicator_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=34 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicator_reports`
--

DROP TABLE IF EXISTS `indicator_reports`;
CREATE TABLE IF NOT EXISTS `indicator_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `target` text,
  `next_target` text,
  `actual` text,
  `support_links` text,
  `deviation` text,
  `activity_leader_id` int(11) NOT NULL,
  `indicator_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_activity_leader_id_fk` (`activity_leader_id`),
  KEY `FK_indicator_id_fk` (`indicator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1674 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicator_types`
--

DROP TABLE IF EXISTS `indicator_types`;
CREATE TABLE IF NOT EXISTS `indicator_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `keywords`
--

DROP TABLE IF EXISTS `keywords`;
CREATE TABLE IF NOT EXISTS `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=103 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `leader_types`
--

DROP TABLE IF EXISTS `leader_types`;
CREATE TABLE IF NOT EXISTS `leader_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `leverages`
--

DROP TABLE IF EXISTS `leverages`;
CREATE TABLE IF NOT EXISTS `leverages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `budget` double DEFAULT NULL,
  `start_year` int(4) DEFAULT NULL,
  `end_year` int(4) DEFAULT NULL,
  `theme_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `partner_name` varchar(255) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_theme_id` (`theme_id`),
  KEY `FK_leader_id` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=74 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logframes`
--

DROP TABLE IF EXISTS `logframes`;
CREATE TABLE IF NOT EXISTS `logframes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(4) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestones`
--

DROP TABLE IF EXISTS `milestones`;
CREATE TABLE IF NOT EXISTS `milestones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `year` int(4) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `output_fk` (`output_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=335 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestone_reports`
--

DROP TABLE IF EXISTS `milestone_reports`;
CREATE TABLE IF NOT EXISTS `milestone_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `milestone_id` int(11) NOT NULL,
  `milestone_status_id` int(11) DEFAULT NULL,
  `tl_description` text,
  `rpl_description` text,
  PRIMARY KEY (`id`),
  KEY `mr_milestone_fk` (`milestone_id`),
  KEY `mr_milestone_status_fk` (`milestone_status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=289 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestone_status`
--

DROP TABLE IF EXISTS `milestone_status`;
CREATE TABLE IF NOT EXISTS `milestone_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objectives`
--

DROP TABLE IF EXISTS `objectives`;
CREATE TABLE IF NOT EXISTS `objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  `outcome_description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `theme_fk` (`theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=37 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `open_access`
--

DROP TABLE IF EXISTS `open_access`;
CREATE TABLE IF NOT EXISTS `open_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `other_sites`
--

DROP TABLE IF EXISTS `other_sites`;
CREATE TABLE IF NOT EXISTS `other_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `os_country_fk` (`country_iso2`),
  KEY `os_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=225 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcomes`
--

DROP TABLE IF EXISTS `outcomes`;
CREATE TABLE IF NOT EXISTS `outcomes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
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
  KEY `out_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=72 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcome_indicators`
--

DROP TABLE IF EXISTS `outcome_indicators`;
CREATE TABLE IF NOT EXISTS `outcome_indicators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  `description` text NOT NULL,
  `theme_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_theme_id` (`theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcome_indicator_reports`
--

DROP TABLE IF EXISTS `outcome_indicator_reports`;
CREATE TABLE IF NOT EXISTS `outcome_indicator_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outcome_indicator_id` int(11) NOT NULL,
  `achievements` text,
  `evidence` text,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_outcome_indicator_id` (`outcome_indicator_id`),
  KEY `FK_activity_leader_id` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=347 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outputs`
--

DROP TABLE IF EXISTS `outputs`;
CREATE TABLE IF NOT EXISTS `outputs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `objective_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `objective_fk` (`objective_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=91 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `output_summaries`
--

DROP TABLE IF EXISTS `output_summaries`;
CREATE TABLE IF NOT EXISTS `output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `os_output_fk` (`output_id`),
  KEY `os_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=355 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partners`
--

DROP TABLE IF EXISTS `partners`;
CREATE TABLE IF NOT EXISTS `partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `acronym` text,
  `country_iso2` varchar(2) DEFAULT NULL,
  `city` text,
  `partner_type_id` int(11) NOT NULL,
  `website` text,
  PRIMARY KEY (`id`),
  KEY `type_fk` (`partner_type_id`),
  KEY `country_iso2` (`country_iso2`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=933 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partner_roles`
--

DROP TABLE IF EXISTS `partner_roles`;
CREATE TABLE IF NOT EXISTS `partner_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partner_types`
--

DROP TABLE IF EXISTS `partner_types`;
CREATE TABLE IF NOT EXISTS `partner_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(20) NOT NULL,
  `name` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publications`
--

DROP TABLE IF EXISTS `publications`;
CREATE TABLE IF NOT EXISTS `publications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publication_type_id` int(11) NOT NULL,
  `identifier` text,
  `citation` text NOT NULL,
  `file_url` text,
  `logframe_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `open_access_id` int(11) DEFAULT NULL,
  `ccafs_acknowledge` tinyint(1) NOT NULL,
  `isi_publication` tinyint(1) DEFAULT NULL,
  `nars_coauthor` tinyint(1) DEFAULT NULL,
  `earth_system_coauthor` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `p_publication_type_fk` (`publication_type_id`),
  KEY `p_logframe_fk` (`logframe_id`),
  KEY `p_activity_leader_fk` (`activity_leader_id`),
  KEY `open_access_id` (`open_access_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=921 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_themes`
--

DROP TABLE IF EXISTS `publication_themes`;
CREATE TABLE IF NOT EXISTS `publication_themes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_themes_reporting`
--

DROP TABLE IF EXISTS `publication_themes_reporting`;
CREATE TABLE IF NOT EXISTS `publication_themes_reporting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publication_id` int(11) NOT NULL,
  `publication_theme_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_publication_publication_themes_reporting_id` (`publication_id`),
  KEY `FK_publication_theme_publication_themes_reporting_id` (`publication_theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15854 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_types`
--

DROP TABLE IF EXISTS `publication_types`;
CREATE TABLE IF NOT EXISTS `publication_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `regions`
--

DROP TABLE IF EXISTS `regions`;
CREATE TABLE IF NOT EXISTS `regions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `region_locations`
--

DROP TABLE IF EXISTS `region_locations`;
CREATE TABLE IF NOT EXISTS `region_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `region_id` (`region_id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=502 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resources`
--

DROP TABLE IF EXISTS `resources`;
CREATE TABLE IF NOT EXISTS `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `res_activity_fk` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=202 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rpl_synthesis_reports`
--

DROP TABLE IF EXISTS `rpl_synthesis_reports`;
CREATE TABLE IF NOT EXISTS `rpl_synthesis_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ccafs_sites` text NOT NULL,
  `cross_center` text NOT NULL,
  `regional` text NOT NULL,
  `decision_support` text NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rplsr_activity_leader_fk` (`activity_leader_id`),
  KEY `rplsr_logframe_fk` (`logframe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `submissions`
--

DROP TABLE IF EXISTS `submissions`;
CREATE TABLE IF NOT EXISTS `submissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_leader_id` int(11) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `section` enum('Planning','Reporting') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_leader_fk_idx` (`activity_leader_id`),
  KEY `logframe_id_fk_idx` (`logframe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `themes`
--

DROP TABLE IF EXISTS `themes`;
CREATE TABLE IF NOT EXISTS `themes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` text NOT NULL,
  `description` text,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `logframe_fk` (`logframe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tl_output_summaries`
--

DROP TABLE IF EXISTS `tl_output_summaries`;
CREATE TABLE IF NOT EXISTS `tl_output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tlos_output_fk` (`output_id`),
  KEY `tlos_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=47 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `role` enum('Admin','CP','TL','RPL','PI') NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=223 ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `activities`
--
ALTER TABLE `activities`
  ADD CONSTRAINT `activities_ibfk_1` FOREIGN KEY (`milestone_id`) REFERENCES `milestones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activities_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activities_ibfk_3` FOREIGN KEY (`continuous_activity_id`) REFERENCES `activities` (`id`),
  ADD CONSTRAINT `activities_ibfk_4` FOREIGN KEY (`activity_status_id`) REFERENCES `activity_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_budgets`
--
ALTER TABLE `activity_budgets`
  ADD CONSTRAINT `activity_budgets_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_budgets_ibfk_2` FOREIGN KEY (`cg_funds`) REFERENCES `budget_percentages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_budgets_ibfk_3` FOREIGN KEY (`bilateral`) REFERENCES `budget_percentages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_keywords`
--
ALTER TABLE `activity_keywords`
  ADD CONSTRAINT `activity_keywords_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_keywords_ibfk_2` FOREIGN KEY (`keyword_id`) REFERENCES `keywords` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_leaders`
--
ALTER TABLE `activity_leaders`
  ADD CONSTRAINT `activity_leaders_ibfk_1` FOREIGN KEY (`led_activity_id`) REFERENCES `leader_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_leaders_ibfk_2` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_leaders_ibfk_3` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_objectives`
--
ALTER TABLE `activity_objectives`
  ADD CONSTRAINT `activity_objectives_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_partners`
--
ALTER TABLE `activity_partners`
  ADD CONSTRAINT `activity_partners_ibfk_1` FOREIGN KEY (`partner_id`) REFERENCES `partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_partners_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_partner_roles`
--
ALTER TABLE `activity_partner_roles`
  ADD CONSTRAINT `activity_partner_roles_ibfk_1` FOREIGN KEY (`activity_partner_id`) REFERENCES `activity_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `activity_partner_roles_ibfk_2` FOREIGN KEY (`partner_role_id`) REFERENCES `partner_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_validations`
--
ALTER TABLE `activity_validations`
  ADD CONSTRAINT `activity_id_fk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `benchmark_sites`
--
ALTER TABLE `benchmark_sites`
  ADD CONSTRAINT `benchmark_sites_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `bs_locations`
--
ALTER TABLE `bs_locations`
  ADD CONSTRAINT `bs_locations_ibfk_1` FOREIGN KEY (`bs_id`) REFERENCES `benchmark_sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bs_locations_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `case_studies`
--
ALTER TABLE `case_studies`
  ADD CONSTRAINT `case_studies_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `case_studies_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `case_study_countries`
--
ALTER TABLE `case_study_countries`
  ADD CONSTRAINT `case_study_countries_ibfk_1` FOREIGN KEY (`case_study_id`) REFERENCES `case_studies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `case_study_countries_ibfk_2` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `communications`
--
ALTER TABLE `communications`
  ADD CONSTRAINT `FK_communications_logframe_id` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_communications_activity_leader_id` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `contact_person`
--
ALTER TABLE `contact_person`
  ADD CONSTRAINT `contact_person_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `countries`
--
ALTER TABLE `countries`
  ADD CONSTRAINT `countries_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `country_locations`
--
ALTER TABLE `country_locations`
  ADD CONSTRAINT `country_locations_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `country_locations_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cs_types`
--
ALTER TABLE `cs_types`
  ADD CONSTRAINT `cs_types_fk01` FOREIGN KEY (`case_study_id`) REFERENCES `case_studies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cs_types_fk02` FOREIGN KEY (`case_study_type_id`) REFERENCES `case_study_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `deliverables`
--
ALTER TABLE `deliverables`
  ADD CONSTRAINT `deliverables_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `deliverables_ibfk_2` FOREIGN KEY (`deliverable_type_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `deliverables_ibfk_3` FOREIGN KEY (`deliverable_status_id`) REFERENCES `deliverable_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `deliverable_formats`
--
ALTER TABLE `deliverable_formats`
  ADD CONSTRAINT `deliverable_formats_ibfk_1` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `deliverable_formats_ibfk_2` FOREIGN KEY (`file_format_id`) REFERENCES `file_formats` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `gender_integrations`
--
ALTER TABLE `gender_integrations`
  ADD CONSTRAINT `gender_integrations_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `indicators`
--
ALTER TABLE `indicators`
  ADD CONSTRAINT `FK_indicator_type_id` FOREIGN KEY (`indicator_type_id`) REFERENCES `indicator_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `indicator_reports`
--
ALTER TABLE `indicator_reports`
  ADD CONSTRAINT `FK_activity_leader_id` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_indicator_id` FOREIGN KEY (`indicator_id`) REFERENCES `indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `leverages`
--
ALTER TABLE `leverages`
  ADD CONSTRAINT `FK_theme_id` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_leader_id` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `milestones`
--
ALTER TABLE `milestones`
  ADD CONSTRAINT `milestones_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `milestone_reports`
--
ALTER TABLE `milestone_reports`
  ADD CONSTRAINT `milestone_reports_ibfk_1` FOREIGN KEY (`milestone_id`) REFERENCES `milestones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `milestone_reports_ibfk_2` FOREIGN KEY (`milestone_status_id`) REFERENCES `milestone_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `objectives`
--
ALTER TABLE `objectives`
  ADD CONSTRAINT `objectives_ibfk_1` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `other_sites`
--
ALTER TABLE `other_sites`
  ADD CONSTRAINT `other_sites_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `other_sites_ibfk_2` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `outcomes`
--
ALTER TABLE `outcomes`
  ADD CONSTRAINT `outcomes_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `outcomes_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `outcome_indicators`
--
ALTER TABLE `outcome_indicators`
  ADD CONSTRAINT `FK_outcome_indicators_theme` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `outcome_indicator_reports`
--
ALTER TABLE `outcome_indicator_reports`
  ADD CONSTRAINT `FK_outcome_indicator_id` FOREIGN KEY (`outcome_indicator_id`) REFERENCES `outcome_indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_outcome_indicator_report_leader_id` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `outputs`
--
ALTER TABLE `outputs`
  ADD CONSTRAINT `outputs_ibfk_1` FOREIGN KEY (`objective_id`) REFERENCES `objectives` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `output_summaries`
--
ALTER TABLE `output_summaries`
  ADD CONSTRAINT `output_summaries_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `output_summaries_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `partners`
--
ALTER TABLE `partners`
  ADD CONSTRAINT `partners_ibfk_1` FOREIGN KEY (`country_iso2`) REFERENCES `countries` (`iso2`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `partners_ibfk_2` FOREIGN KEY (`partner_type_id`) REFERENCES `partner_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `publications`
--
ALTER TABLE `publications`
  ADD CONSTRAINT `publications_ibfk_1` FOREIGN KEY (`publication_type_id`) REFERENCES `publication_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `publications_ibfk_2` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `publications_ibfk_3` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `publications_ibfk_4` FOREIGN KEY (`open_access_id`) REFERENCES `open_access` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `publication_themes_reporting`
--
ALTER TABLE `publication_themes_reporting`
  ADD CONSTRAINT `FK_publication_id` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_publication_theme_id` FOREIGN KEY (`publication_theme_id`) REFERENCES `publication_themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `region_locations`
--
ALTER TABLE `region_locations`
  ADD CONSTRAINT `FK_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_region_id` FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `resources`
--
ALTER TABLE `resources`
  ADD CONSTRAINT `resources_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rpl_synthesis_reports`
--
ALTER TABLE `rpl_synthesis_reports`
  ADD CONSTRAINT `rpl_synthesis_reports_ibfk_1` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rpl_synthesis_reports_ibfk_2` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `submissions`
--
ALTER TABLE `submissions`
  ADD CONSTRAINT `activity_leader_fk` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `logframe_id_fk` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `themes`
--
ALTER TABLE `themes`
  ADD CONSTRAINT `themes_ibfk_1` FOREIGN KEY (`logframe_id`) REFERENCES `logframes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `tl_output_summaries`
--
ALTER TABLE `tl_output_summaries`
  ADD CONSTRAINT `tl_output_summaries_ibfk_1` FOREIGN KEY (`output_id`) REFERENCES `outputs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tl_output_summaries_ibfk_2` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`activity_leader_id`) REFERENCES `activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
