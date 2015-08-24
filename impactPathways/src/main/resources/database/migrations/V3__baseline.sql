-- MySQL dump 10.13  Distrib 5.5.39, for Win32 (x86)
--
-- Host: localhost    Database: $[database]
-- ------------------------------------------------------
-- Server version	5.5.39

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
  `leader_id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`),
  KEY `fk_activities_employees_created_by_idx` (`created_by`),
  KEY `FK_activities_project_partner_id` (`leader_id`),
  KEY `fk_activities_users_modified_by` (`modified_by`),
  CONSTRAINT `fk_activities_employees_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Activities_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_activities_project_partner_id` FOREIGN KEY (`leader_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_activities_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_activities_insert AFTER INSERT ON activities FOR EACH ROW BEGIN UPDATE $[database]_history.activities SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.activities(  `record_id`, `project_id`, `title`, `description`, `startDate`, `endDate`, `leader_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`description`, NEW.`startDate`, NEW.`endDate`, NEW.`leader_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_activities_update AFTER UPDATE ON activities FOR EACH ROW BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`title`, OLD.`description`, OLD.`startDate`, OLD.`endDate`, OLD.`leader_id`, OLD.`created_by`, OLD.`is_active`, OLD.`active_since`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`description`, NEW.`startDate`, NEW.`endDate`, NEW.`leader_id`, NEW.`created_by`, NEW.`is_active`, NEW.`active_since`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.activities SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.activities(  `record_id`, `project_id`, `title`, `description`, `startDate`, `endDate`, `leader_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`description`, NEW.`startDate`, NEW.`endDate`, NEW.`leader_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.activities(  `record_id`, `project_id`, `title`, `description`, `startDate`, `endDate`, `leader_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`description`, NEW.`startDate`, NEW.`endDate`, NEW.`leader_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
-- Dumping data for table `board_messages`
--

LOCK TABLES `board_messages` WRITE;
/*!40000 ALTER TABLE `board_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget_types`
--

DROP TABLE IF EXISTS `budget_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget_types`
--

LOCK TABLES `budget_types` WRITE;
/*!40000 ALTER TABLE `budget_types` DISABLE KEYS */;
INSERT INTO `budget_types` VALUES (1,'W1 W2 Budget'),(2,'W3/Bilateral Budget');
/*!40000 ALTER TABLE `budget_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crps`
--

DROP TABLE IF EXISTS `crps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(45) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crps`
--

LOCK TABLES `crps` WRITE;
/*!40000 ALTER TABLE `crps` DISABLE KEYS */;
INSERT INTO `crps` VALUES (1,'Agriculture for Nutrition and Health','a4nh',1),(2,'Aquatic Agricultural Systems',NULL,1),(3,'Climate Change, Agriculture and Food Security','CCAFS',1),(4,'Dryland Cereals',NULL,1),(5,'Dryland Systems',NULL,1),(6,'Forests, Trees and Agroforestry','FTA',1),(7,'Grain Legumes',NULL,1),(8,'Integrated Systems for the Humid Tropics',NULL,1),(9,'Livestock and Fish',NULL,1),(10,'Policies, Institutions and Markets',NULL,1),(11,'Maize',NULL,1),(12,'Rice','GRiSP',1),(13,'Roots, Tubers and Bananas','RTB',1),(14,'Water, Land and Ecosystems','WLE',1),(15,'Wheat',NULL,1),(16,'Genebank',NULL,1);
/*!40000 ALTER TABLE `crps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliverable_partnerships`
--

DROP TABLE IF EXISTS `deliverable_partnerships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverable_partnerships` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliverable_id` bigint(20) NOT NULL,
  `partner_id` bigint(20) NOT NULL,
  `partner_type` enum('Resp','Other') NOT NULL DEFAULT 'Other',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverable_partnership_deliverable_idx` (`deliverable_id`),
  KEY `FK_deliverable_partnerships_users_created_by_idx` (`created_by`),
  KEY `FK_deliverable_partnerships_users_modified_by_idx` (`modified_by`),
  KEY `FK_deliverable_partnerships_projectPartners_partner_id_idx` (`partner_id`),
  CONSTRAINT `FK_deliverable_partnerships_deliverable` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_project_partners_partner_id` FOREIGN KEY (`partner_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliverable_partnerships`
--

LOCK TABLES `deliverable_partnerships` WRITE;
/*!40000 ALTER TABLE `deliverable_partnerships` DISABLE KEYS */;
/*!40000 ALTER TABLE `deliverable_partnerships` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_deliverable_partnerships_insert AFTER INSERT ON deliverable_partnerships FOR EACH ROW BEGIN UPDATE $[database]_history.deliverable_partnerships SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.deliverable_partnerships(  `record_id`, `deliverable_id`, `partner_id`, `partner_type`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`partner_id`, NEW.`partner_type`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_deliverable_partnerships_update AFTER UPDATE ON deliverable_partnerships FOR EACH ROW BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`deliverable_id`, OLD.`partner_id`, OLD.`partner_type`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`deliverable_id`, NEW.`partner_id`, NEW.`partner_type`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.deliverable_partnerships SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.deliverable_partnerships(  `record_id`, `deliverable_id`, `partner_id`, `partner_type`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`partner_id`, NEW.`partner_type`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.deliverable_partnerships(  `record_id`, `deliverable_id`, `partner_id`, `partner_type`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`partner_id`, NEW.`partner_type`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliverable_types`
--

LOCK TABLES `deliverable_types` WRITE;
/*!40000 ALTER TABLE `deliverable_types` DISABLE KEYS */;
INSERT INTO `deliverable_types` VALUES (1,'Data and information outputs, including datasets, databases and models',NULL,NULL),(2,'Reports, Reference Materials and Other Papers',NULL,NULL),(3,'Peer reviewed Publications',NULL,NULL),(4,'Communication Products and Multimedia',NULL,NULL),(5,'Case Study',NULL,NULL),(7,'Tools and Computer Software',NULL,NULL),(8,'Workshops',NULL,NULL),(9,'Capacity',NULL,-1),(10,'Data',1,12),(11,'Datasets',1,12),(12,'Databases',1,12),(13,'Information outputs',1,6),(14,'Research report (i.e. workshop report, consultant’s report, project reports, student thesis, etc.)',2,3),(15,'Policy briefs - Briefing paper',2,3),(16,'Working paper',2,3),(17,'Conference proceedings/papere',2,3),(18,'Seminar paper',2,3),(19,'Discussion paper',2,3),(20,'Reference material (booklets and training manuals for extension agents, etc.)',2,3),(21,'Peer-reviewed journal articles',3,6),(22,'Non-peer reviewed articles',2,3),(23,'Books',3,6),(24,'Book chapters',3,6),(26,'Articles for media or news (radio, TV, newspapers, newsletters, etc.)',4,0),(27,'Social media outputs (including web sites, blogs, wikis, linkedin group, facebook, yammer, etc.)',4,0),(28,'Poster',4,0),(29,'Presentations',4,0),(30,'Case Study',5,0),(31,'Video',4,3),(32,'Audio',4,3),(33,'Images',4,3),(34,'Platforms - Data Portals for dissemination',7,0),(35,'Maps (i.e. CCAFS Sites Atlas, cropland, etc.)',7,0),(36,'Tools (i.e. search engines, games, etc)',7,0),(37,'Models (i.e. Crop models)',1,12),(38,'Other (please specify)',7,0),(39,'Workshop',8,NULL),(40,'Capacity',9,NULL),(41,'Website',7,0);
/*!40000 ALTER TABLE `deliverable_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliverables`
--

DROP TABLE IF EXISTS `deliverables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliverables` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `title` text,
  `type_id` bigint(20) DEFAULT NULL,
  `type_other` text COMMENT 'Other type defined by the user.',
  `year` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverables_deliverables_type_idx` (`type_id`),
  KEY `fk_deliverables_users_created_by` (`created_by`),
  KEY `fk_deliverables_users_modified_by` (`modified_by`),
  KEY `FK_deliverables_project` (`project_id`),
  CONSTRAINT `deliverables_deliverables_type` FOREIGN KEY (`type_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverables_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_deliverables_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_deliverables_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliverables`
--

LOCK TABLES `deliverables` WRITE;
/*!40000 ALTER TABLE `deliverables` DISABLE KEYS */;
/*!40000 ALTER TABLE `deliverables` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_deliverables_insert AFTER INSERT ON deliverables FOR EACH ROW BEGIN UPDATE $[database]_history.deliverables SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.deliverables(  `record_id`, `project_id`, `title`, `type_id`, `type_other`, `year`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`type_id`, NEW.`type_other`, NEW.`year`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`ccafspruser`@`localhost`*/ /*!50003 TRIGGER after_deliverables_update AFTER UPDATE ON deliverables FOR EACH ROW BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`title`, OLD.`type_id`, OLD.`type_other`, OLD.`year`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`type_id`, NEW.`type_other`, NEW.`year`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.deliverables SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.deliverables(  `record_id`, `project_id`, `title`, `type_id`, `type_other`, `year`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`type_id`, NEW.`type_other`, NEW.`year`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.deliverables(  `record_id`, `project_id`, `title`, `type_id`, `type_other`, `year`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`title`, NEW.`type_id`, NEW.`type_other`, NEW.`year`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
  KEY `FK_employees_roles_idx` (`role_id`),
  CONSTRAINT `FK_employees_persons` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_employees_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `employees_backup`
--

LOCK TABLES `employees_backup` WRITE;
/*!40000 ALTER TABLE `employees_backup` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees_backup` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution_types`
--

LOCK TABLES `institution_types` WRITE;
/*!40000 ALTER TABLE `institution_types` DISABLE KEYS */;
INSERT INTO `institution_types` VALUES (1,'Academic Institution','AI'),(2,'Advanced Research Institution','ARI'),(3,'CGIAR Center','CG'),(4,'Challenge Research Program','CRP'),(5,'Donors','Donors'),(6,'End users','End_users'),(7,'Government office/department','GO'),(8,'National agricultural research and extension services','NARES'),(9,'Non-governmental organization/Development organization','NGO_DO'),(10,'Private Research Institution','PRI'),(11,'Regional Organization','RO'),(12,'Research network','Research_network'),(18,'Other','Other');
/*!40000 ALTER TABLE `institution_types` ENABLE KEYS */;
UNLOCK TABLES;

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
  `city` varchar(255) DEFAULT NULL,
  `is_ppa` tinyint(1) NOT NULL DEFAULT '0',
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
-- Dumping data for table `institutions`
--

LOCK TABLES `institutions` WRITE;
/*!40000 ALTER TABLE `institutions` DISABLE KEYS */;
/*!40000 ALTER TABLE `institutions` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_cross_cutting_themes`
--

LOCK TABLES `ip_cross_cutting_themes` WRITE;
/*!40000 ALTER TABLE `ip_cross_cutting_themes` DISABLE KEYS */;
INSERT INTO `ip_cross_cutting_themes` VALUES (1,'Gender and social differentiation'),(2,'Communication & Engagement'),(3,'Monitoring and Evaluation'),(4,'Data management'),(5,'Capacity Strengthening');
/*!40000 ALTER TABLE `ip_cross_cutting_themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ip_deliverable_contributions`
--

DROP TABLE IF EXISTS `ip_deliverable_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_deliverable_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_contribution_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references the table ip_project_contributions',
  `deliverable_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references the table deliverables',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ip_deliverable_contributions_key` (`project_contribution_id`,`deliverable_id`),
  KEY `FK_deliverables_ipProjectContributions_contributionID_idx` (`project_contribution_id`),
  KEY `FK_deliverables_ipProjectContributions_deliverableID_idx` (`deliverable_id`),
  KEY `PK_deliverable_contributions` (`project_contribution_id`,`deliverable_id`),
  KEY `fk_ip_deliverable_contributions_users_created_by` (`created_by`),
  KEY `fk_ip_deliverable_contributions_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_deliverables_ipProjectContributions_contributionID` FOREIGN KEY (`project_contribution_id`) REFERENCES `ip_project_contributions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverables_ipProjectContributions_deliverableID` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_deliverable_contributions_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_deliverable_contributions_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_deliverable_contributions`
--

LOCK TABLES `ip_deliverable_contributions` WRITE;
/*!40000 ALTER TABLE `ip_deliverable_contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_deliverable_contributions` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_element_types`
--

LOCK TABLES `ip_element_types` WRITE;
/*!40000 ALTER TABLE `ip_element_types` DISABLE KEYS */;
INSERT INTO `ip_element_types` VALUES (1,'IDOs'),(2,'Outcome 2025'),(3,'Outcome 2019'),(4,'MOG');
/*!40000 ALTER TABLE `ip_element_types` ENABLE KEYS */;
UNLOCK TABLES;

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
  `ip_program_id` bigint(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_element_element_type_idx` (`element_type_id`),
  KEY `FK_ip_elements_ip_programs_owner_id` (`ip_program_id`),
  KEY `fk_ip_elements_users_created_by` (`created_by`),
  KEY `fk_ip_elements_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_element_element_type` FOREIGN KEY (`element_type_id`) REFERENCES `ip_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ip_elements_ip_programs_owner_id` FOREIGN KEY (`ip_program_id`) REFERENCES `ip_programs` (`id`),
  CONSTRAINT `fk_ip_elements_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_elements_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_elements`
--

LOCK TABLES `ip_elements` WRITE;
/*!40000 ALTER TABLE `ip_elements` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_elements` ENABLE KEYS */;
UNLOCK TABLES;

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
  `ip_element_id` bigint(20) NOT NULL,
  `program_element_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT 'Foreign key to ip_indicators table. This field shows if the indicator contributes to another indicator',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_indicators_program_elements_idx` (`program_element_id`),
  KEY `FK_indicators_parent_indicator_idx` (`parent_id`),
  KEY `FK_ip_indicators_ip_element_element_id` (`ip_element_id`),
  KEY `fk_ip_indicators_users_created_by` (`created_by`),
  KEY `fk_ip_indicators_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_indicators_parent_indicator` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_indicators_program_elements` FOREIGN KEY (`program_element_id`) REFERENCES `ip_program_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ip_indicators_ip_element_element_id` FOREIGN KEY (`ip_element_id`) REFERENCES `ip_elements` (`id`),
  CONSTRAINT `fk_ip_indicators_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_indicators_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_indicators`
--

LOCK TABLES `ip_indicators` WRITE;
/*!40000 ALTER TABLE `ip_indicators` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_indicators` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_program_element_relation_types`
--

LOCK TABLES `ip_program_element_relation_types` WRITE;
/*!40000 ALTER TABLE `ip_program_element_relation_types` DISABLE KEYS */;
INSERT INTO `ip_program_element_relation_types` VALUES (1,'Created by'),(2,'Used by');
/*!40000 ALTER TABLE `ip_program_element_relation_types` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ip_program_elements`
--

LOCK TABLES `ip_program_elements` WRITE;
/*!40000 ALTER TABLE `ip_program_elements` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_program_elements` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_program_types`
--

LOCK TABLES `ip_program_types` WRITE;
/*!40000 ALTER TABLE `ip_program_types` DISABLE KEYS */;
INSERT INTO `ip_program_types` VALUES (1,'Admin System'),(2,'CGIAR research program'),(3,'Coordination program'),(4,'Flagship program'),(5,'Regional program');
/*!40000 ALTER TABLE `ip_program_types` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_programs`
--

LOCK TABLES `ip_programs` WRITE;
/*!40000 ALTER TABLE `ip_programs` DISABLE KEYS */;
INSERT INTO `ip_programs` VALUES (1,'Climate-smart practices','FP 1',NULL,4),(2,'Climate Information Services and Climate-Informed Safety Nets','FP 2',NULL,4),(3,'Low Emissions Agricultural Development','FP 3',NULL,4),(4,'Policies and Institutions for Climate-Resilient Food Systems','FP 4',NULL,4),(5,'Latin America','RP LAM',4,5),(6,'East Africa','RP EA',1,5),(7,'West Africa','RP WA',2,5),(8,'South Asia','RP SAs',3,5),(9,'South East Asia','RP SEA',5,5),(10,'Coordinating Unit','CU',NULL,3),(11,'Global','Global',6,5),(13,'System Admin','Admin',NULL,1),(14,'Climate Change, Agriculture and Food Security','CCAFS',NULL,2);
/*!40000 ALTER TABLE `ip_programs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ip_project_contribution_overviews`
--

DROP TABLE IF EXISTS `ip_project_contribution_overviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_project_contribution_overviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `output_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  `anual_contribution` text CHARACTER SET latin1,
  `gender_contribution` text CHARACTER SET latin1,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ip_project_contribution_overviews` (`project_id`,`output_id`,`year`),
  KEY `FK_project_contribution_overviews_ip_element_idx` (`output_id`),
  KEY `FK_project_contribution_overviews_projects_idx` (`project_id`),
  KEY `FK_project_contribution_overviews_users_created_by_idx` (`created_by`),
  KEY `FK_project_contribution_overviews_users_modified_by_idx` (`modified_by`),
  CONSTRAINT `FK_project_contribution_overviews_ip_element` FOREIGN KEY (`output_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_contribution_overviews_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_contribution_overviews_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_contribution_overviews_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_project_contribution_overviews`
--

LOCK TABLES `ip_project_contribution_overviews` WRITE;
/*!40000 ALTER TABLE `ip_project_contribution_overviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_project_contribution_overviews` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_contribution_overviews_insert`
AFTER INSERT ON `$[database]`.`ip_project_contribution_overviews`
FOR EACH ROW
BEGIN UPDATE $[database]_history.ip_project_contribution_overviews SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.ip_project_contribution_overviews(  `record_id`, `project_id`, `output_id`, `year`, `anual_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`output_id`, NEW.`year`, NEW.`anual_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_contribution_overviews_update`
AFTER UPDATE ON `$[database]`.`ip_project_contribution_overviews`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`output_id`, OLD.`year`, OLD.`anual_contribution`, OLD.`gender_contribution`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`output_id`, NEW.`year`, NEW.`anual_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.ip_project_contribution_overviews SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.ip_project_contribution_overviews(  `record_id`, `project_id`, `output_id`, `year`, `anual_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`output_id`, NEW.`year`, NEW.`anual_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.ip_project_contribution_overviews(  `record_id`, `project_id`, `output_id`, `year`, `anual_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`output_id`, NEW.`year`, NEW.`anual_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_contributions_unique` (`project_id`,`mog_id`,`midOutcome_id`),
  KEY `FK_projects_ipElements_ipElementID_idx` (`mog_id`),
  KEY `FK_projects_midOutcome_ipElementID_idx` (`midOutcome_id`),
  KEY `FK_projects_ipElements_projectID_idx` (`project_id`),
  KEY `fk_ip_project_contributions_users_created_by` (`created_by`),
  KEY `fk_ip_project_contributions_users_modified_by` (`modified_by`),
  CONSTRAINT `fk_ip_project_contributions_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_project_contributions_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_ipElements_ipElementID` FOREIGN KEY (`mog_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_ipElements_projectID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_midOutcome_ipElementID` FOREIGN KEY (`midOutcome_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_project_contributions`
--

LOCK TABLES `ip_project_contributions` WRITE;
/*!40000 ALTER TABLE `ip_project_contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_project_contributions` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_contributions_insert`
AFTER INSERT ON `$[database]`.`ip_project_contributions`
FOR EACH ROW
BEGIN UPDATE $[database]_history.ip_project_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.ip_project_contributions(  `record_id`, `project_id`, `mog_id`, `midOutcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`midOutcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_contributions_update`
AFTER UPDATE ON `$[database]`.`ip_project_contributions`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`mog_id`, OLD.`midOutcome_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`midOutcome_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.ip_project_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.ip_project_contributions(  `record_id`, `project_id`, `mog_id`, `midOutcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`midOutcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.ip_project_contributions(  `record_id`, `project_id`, `mog_id`, `midOutcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`midOutcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ipProjectIndicator` (`year`,`project_id`,`parent_id`,`outcome_id`),
  KEY `FK_ipProjectIndicators_ipIndicators_parentID_idx` (`parent_id`),
  KEY `FK_ipProjectIndicators_ipIndicators_projectID_idx` (`project_id`),
  KEY `fk_ip_project_indicators_users_created_by` (`created_by`),
  KEY `fk_ip_project_indicators_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_ipProectIndicators_ipIndicators_parentID` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_ipProjectIndicators_ipIndicators_projectID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_project_indicators_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_project_indicators_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_project_indicators`
--

LOCK TABLES `ip_project_indicators` WRITE;
/*!40000 ALTER TABLE `ip_project_indicators` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_project_indicators` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_indicators_insert`
AFTER INSERT ON `$[database]`.`ip_project_indicators`
FOR EACH ROW
BEGIN UPDATE $[database]_history.ip_project_indicators SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.ip_project_indicators(  `record_id`, `description`, `target`, `year`, `project_id`, `parent_id`, `outcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`description`, NEW.`target`, NEW.`year`, NEW.`project_id`, NEW.`parent_id`, NEW.`outcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_ip_project_indicators_update`
AFTER UPDATE ON `$[database]`.`ip_project_indicators`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`description`, OLD.`target`, OLD.`year`, OLD.`project_id`, OLD.`parent_id`, OLD.`outcome_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`description`, NEW.`target`, NEW.`year`, NEW.`project_id`, NEW.`parent_id`, NEW.`outcome_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.ip_project_indicators SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.ip_project_indicators(  `record_id`, `description`, `target`, `year`, `project_id`, `parent_id`, `outcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`description`, NEW.`target`, NEW.`year`, NEW.`project_id`, NEW.`parent_id`, NEW.`outcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.ip_project_indicators(  `record_id`, `description`, `target`, `year`, `project_id`, `parent_id`, `outcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`description`, NEW.`target`, NEW.`year`, NEW.`project_id`, NEW.`parent_id`, NEW.`outcome_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_relationship_type`
--

LOCK TABLES `ip_relationship_type` WRITE;
/*!40000 ALTER TABLE `ip_relationship_type` DISABLE KEYS */;
INSERT INTO `ip_relationship_type` VALUES (1,'contributes to'),(2,'translation of');
/*!40000 ALTER TABLE `ip_relationship_type` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `ip_relationships`
--

LOCK TABLES `ip_relationships` WRITE;
/*!40000 ALTER TABLE `ip_relationships` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_relationships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liaison_institutions`
--

DROP TABLE IF EXISTS `liaison_institutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liaison_institutions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_liaison_institutions_institutions_idx` (`institution_id`),
  CONSTRAINT `FK_liaison_institutions_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liaison_institutions`
--

LOCK TABLES `liaison_institutions` WRITE;
/*!40000 ALTER TABLE `liaison_institutions` DISABLE KEYS */;
/*!40000 ALTER TABLE `liaison_institutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liaison_users`
--

DROP TABLE IF EXISTS `liaison_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `liaison_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'This field is a link to the table users',
  `institution_id` bigint(20) NOT NULL COMMENT 'This field is a link to the table liaison_institutions',
  PRIMARY KEY (`id`),
  KEY `FK_liaison_users_users__idx` (`user_id`),
  KEY `FK_liaison_users_institutions__idx` (`institution_id`),
  CONSTRAINT `FK_liaison_users__liaison_institutions` FOREIGN KEY (`institution_id`) REFERENCES `liaison_institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_liaison_users__users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liaison_users`
--

LOCK TABLES `liaison_users` WRITE;
/*!40000 ALTER TABLE `liaison_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `liaison_users` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loc_element_types`
--

LOCK TABLES `loc_element_types` WRITE;
/*!40000 ALTER TABLE `loc_element_types` DISABLE KEYS */;
INSERT INTO `loc_element_types` VALUES (1,'Region',NULL),(2,'Country',NULL),(3,'Province',NULL),(4,'District',NULL),(5,'Ward',NULL),(6,'Permanent agricultural trial site',NULL),(7,'River Basin',NULL),(8,'Village',NULL),(9,'Household',NULL),(10,'Climate smart village',NULL),(11,'CCAFS Site',NULL);
/*!40000 ALTER TABLE `loc_element_types` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=630 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loc_elements`
--

LOCK TABLES `loc_elements` WRITE;
/*!40000 ALTER TABLE `loc_elements` DISABLE KEYS */;
INSERT INTO `loc_elements` VALUES (1,'East Africa','EA',NULL,1,NULL),(2,'West Africa','WA',NULL,1,NULL),(3,'South Asia','SA',NULL,1,NULL),(4,'Latin America','LAM',NULL,1,NULL),(5,'South East Asia','SEA',NULL,1,NULL),(6,'Other',NULL,NULL,1,NULL),(7,'Andorra','ad',6,2,NULL),(8,'United Arab Emirates','ae',6,2,NULL),(9,'Afghanistan','af',3,2,NULL),(10,'Antigua and Barbuda','ag',6,2,NULL),(11,'Anguilla','ai',6,2,NULL),(12,'Albania','al',6,2,NULL),(13,'Armenia','am',6,2,NULL),(14,'Netherlands Antilles','an',6,2,NULL),(15,'Angola','ao',6,2,NULL),(16,'Antarctica','aq',6,2,NULL),(17,'Argentina','ar',4,2,NULL),(18,'American Samoa','as',6,2,NULL),(19,'Austria','at',6,2,NULL),(20,'Australia','au',6,2,NULL),(21,'Aruba','aw',6,2,NULL),(22,'Azerbaijan','az',6,2,NULL),(23,'Bosnia and Herzegovina','ba',6,2,NULL),(24,'Barbados','bb',6,2,NULL),(25,'Bangladesh','bd',3,2,NULL),(26,'Belgium','be',6,2,NULL),(27,'Burkina Faso','bf',2,2,NULL),(28,'Bulgaria','bg',6,2,NULL),(29,'Bahrain','bh',6,2,NULL),(30,'Burundi','bi',6,2,NULL),(31,'Benin','bj',2,2,NULL),(32,'Saint Barthelemy','bl',6,2,NULL),(33,'Bermuda','bm',6,2,NULL),(34,'Brunei','bn',5,2,NULL),(35,'Bolivia','bo',4,2,NULL),(36,'Brazil','br',4,2,NULL),(37,'Bahamas','bs',6,2,NULL),(38,'Bhutan','bt',3,2,NULL),(39,'Botswana','bw',6,2,NULL),(40,'Belarus','by',6,2,NULL),(41,'Belize','bz',6,2,NULL),(42,'Canada','ca',6,2,NULL),(43,'Cocos (Keeling) Islands','cc',6,2,NULL),(44,'Democratic Republic of the Congo','cd',6,2,NULL),(45,'Central African Republic','cf',6,2,NULL),(46,'Republic of the Congo','cg',6,2,NULL),(47,'Switzerland','ch',6,2,NULL),(48,'Ivory Coast','ci',2,2,NULL),(49,'Cook Islands','ck',6,2,NULL),(50,'Chile','cl',4,2,NULL),(51,'Cameroon','cm',6,2,NULL),(52,'China','cn',6,2,NULL),(53,'Colombia','co',4,2,NULL),(54,'Costa Rica','cr',4,2,NULL),(55,'Cuba','cu',4,2,NULL),(56,'Cape Verde','cv',2,2,NULL),(57,'Christmas Island','cx',6,2,NULL),(58,'Cyprus','cy',6,2,NULL),(59,'Czech Republic','cz',6,2,NULL),(60,'Germany','de',6,2,NULL),(61,'Djibouti','dj',6,2,NULL),(62,'Denmark','dk',6,2,NULL),(63,'Dominica','dm',6,2,NULL),(64,'Dominican Republic','do',4,2,NULL),(65,'Algeria','dz',6,2,NULL),(66,'Ecuador','ec',4,2,NULL),(67,'Estonia','ee',6,2,NULL),(68,'Egypt','eg',6,2,NULL),(69,'Western Sahara','eh',6,2,NULL),(70,'Eritrea','er',6,2,NULL),(71,'Spain','es',6,2,NULL),(72,'Ethiopia','et',1,2,NULL),(73,'Finland','fi',6,2,NULL),(74,'Fiji','fj',6,2,NULL),(75,'Falkland Islands','fk',6,2,NULL),(76,'Micronesia','fm',6,2,NULL),(77,'Faroe Islands','fo',6,2,NULL),(78,'France','fr',6,2,NULL),(79,'Gabon','ga',6,2,NULL),(80,'United Kingdom','gb',6,2,NULL),(81,'Grenada','gd',6,2,NULL),(82,'Georgia','ge',6,2,NULL),(83,'Ghana','gh',2,2,NULL),(84,'Gibraltar','gi',6,2,NULL),(85,'Greenland','gl',6,2,NULL),(86,'Gambia','gm',2,2,NULL),(87,'Guinea','gn',2,2,NULL),(88,'Equatorial Guinea','gq',6,2,NULL),(89,'Greece','gr',6,2,NULL),(90,'Guatemala','gt',4,2,NULL),(91,'Guam','gu',6,2,NULL),(92,'Guinea-Bissau','gw',2,2,NULL),(93,'Guyana','gy',6,2,NULL),(94,'Hong Kong','hk',6,2,NULL),(95,'Honduras','hn',4,2,NULL),(96,'Croatia','hr',6,2,NULL),(97,'Haiti','ht',4,2,NULL),(98,'Hungary','hu',6,2,NULL),(99,'Indonesia','id',5,2,NULL),(100,'Ireland','ie',6,2,NULL),(101,'Israel','il',6,2,NULL),(102,'Isle of Man','im',6,2,NULL),(103,'India','in',3,2,NULL),(104,'British Indian Ocean Territory','io',6,2,NULL),(105,'Iraq','iq',6,2,NULL),(106,'Iran','ir',6,2,NULL),(107,'Iceland','is',6,2,NULL),(108,'Italy','it',6,2,NULL),(109,'Jersey','je',6,2,NULL),(110,'Jamaica','jm',6,2,NULL),(111,'Jordan','jo',6,2,NULL),(112,'Japan','jp',6,2,NULL),(113,'Kenya','ke',1,2,NULL),(114,'Kyrgyzstan','kg',6,2,NULL),(115,'Cambodia','kh',5,2,NULL),(116,'Kiribati','ki',6,2,NULL),(117,'Comoros','km',6,2,NULL),(118,'Saint Kitts and Nevis','kn',6,2,NULL),(119,'North Korea','kp',6,2,NULL),(120,'South Korea','kr',6,2,NULL),(121,'Kuwait','kw',6,2,NULL),(122,'Cayman Islands','ky',6,2,NULL),(123,'Kazakhstan','kz',6,2,NULL),(124,'Laos','la',5,2,NULL),(125,'Lebanon','lb',6,2,NULL),(126,'Saint Lucia','lc',6,2,NULL),(127,'Liechtenstein','li',6,2,NULL),(128,'Sri Lanka','lk',3,2,NULL),(129,'Liberia','lr',2,2,NULL),(130,'Lesotho','ls',6,2,NULL),(131,'Lithuania','lt',6,2,NULL),(132,'Luxembourg','lu',6,2,NULL),(133,'Libya','ly',6,2,NULL),(134,'Morocco','ma',6,2,NULL),(135,'Monaco','mc',6,2,NULL),(136,'Moldova','md',6,2,NULL),(137,'Montenegro','me',6,2,NULL),(138,'Saint Martin','mf',6,2,NULL),(139,'Madagascar','mg',6,2,NULL),(140,'Marshall Islands','mh',6,2,NULL),(141,'Macedonia','mk',6,2,NULL),(142,'Mali','ml',2,2,NULL),(143,'Burma','mm',5,2,NULL),(144,'Mongolia','mn',6,2,NULL),(145,'Macau','mo',6,2,NULL),(146,'Northern Mariana Islands','mp',6,2,NULL),(147,'Mauritania','mr',2,2,NULL),(148,'Montserrat','ms',6,2,NULL),(149,'Malta','mt',6,2,NULL),(150,'Mauritius','mu',6,2,NULL),(151,'Maldives','mv',3,2,NULL),(152,'Malawi','mw',6,2,NULL),(153,'Mexico','mx',4,2,NULL),(154,'Malaysia','my',5,2,NULL),(155,'Mozambique','mz',6,2,NULL),(156,'Namibia','na',6,2,NULL),(157,'New Caledonia','nc',6,2,NULL),(158,'Niger','ne',2,2,NULL),(159,'Nigeria','ng',2,2,NULL),(160,'Nicaragua','ni',4,2,NULL),(161,'Netherlands','nl',6,2,NULL),(162,'Norway','no',6,2,NULL),(163,'Nepal','np',3,2,NULL),(164,'Nauru','nr',6,2,NULL),(165,'Niue','nu',6,2,NULL),(166,'New Zealand','nz',6,2,NULL),(167,'Oman','om',6,2,NULL),(168,'Panama','pa',4,2,NULL),(169,'Peru','pe',4,2,NULL),(170,'French Polynesia','pf',6,2,NULL),(171,'Papua New Guinea','pg',6,2,NULL),(172,'Philippines','ph',5,2,NULL),(173,'Pakistan','pk',3,2,NULL),(174,'Poland','pl',6,2,NULL),(175,'Saint Pierre and Miquelon','pm',6,2,NULL),(176,'Pitcairn Islands','pn',6,2,NULL),(177,'Puerto Rico','pr',6,2,NULL),(178,'Portugal','pt',6,2,NULL),(179,'Palau','pw',6,2,NULL),(180,'Paraguay','py',4,2,NULL),(181,'Qatar','qa',6,2,NULL),(182,'Romania','ro',6,2,NULL),(183,'Serbia','rs',6,2,NULL),(184,'Russia','ru',6,2,NULL),(185,'Rwanda','rw',6,2,NULL),(186,'Saudi Arabia','sa',6,2,NULL),(187,'Solomon Islands','sb',6,2,NULL),(188,'Seychelles','sc',6,2,NULL),(189,'Sudan','sd',6,2,NULL),(190,'Sweden','se',6,2,NULL),(191,'Singapore','sg',5,2,NULL),(192,'Saint Helena','sh',6,2,NULL),(193,'Slovenia','si',6,2,NULL),(194,'Svalbard','sj',6,2,NULL),(195,'Slovakia','sk',6,2,NULL),(196,'Sierra Leone','sl',2,2,NULL),(197,'San Marino','sm',6,2,NULL),(198,'Senegal','sn',2,2,NULL),(199,'Somalia','so',6,2,NULL),(200,'Suriname','sr',6,2,NULL),(201,'Sao Tome and Principe','st',6,2,NULL),(202,'El Salvador','sv',4,2,NULL),(203,'Syria','sy',6,2,NULL),(204,'Swaziland','sz',6,2,NULL),(205,'Turks and Caicos Islands','tc',6,2,NULL),(206,'Chad','td',6,2,NULL),(207,'Togo','tg',2,2,NULL),(208,'Thailand','th',5,2,NULL),(209,'Tajikistan','tj',6,2,NULL),(210,'Tokelau','tk',6,2,NULL),(211,'East Timor','tl',5,2,NULL),(212,'Turkmenistan','tm',6,2,NULL),(213,'Tunisia','tn',6,2,NULL),(214,'Tonga','to',6,2,NULL),(215,'Turkey','tr',6,2,NULL),(216,'Trinidad and Tobago','tt',6,2,NULL),(217,'Tuvalu','tv',6,2,NULL),(218,'Taiwan','tw',6,2,NULL),(219,'Tanzania','tz',1,2,NULL),(220,'Ukraine','ua',6,2,NULL),(221,'Uganda','ug',1,2,NULL),(222,'United States','us',6,2,NULL),(223,'Uruguay','uy',4,2,NULL),(224,'Uzbekistan','uz',6,2,NULL),(225,'Holy See (Vatican City)','va',6,2,NULL),(226,'Saint Vincent and the Grenadines','vc',6,2,NULL),(227,'Venezuela','ve',4,2,NULL),(228,'British Virgin Islands','vg',6,2,NULL),(229,'US Virgin Islands','vi',6,2,NULL),(230,'Vietnam','vn',5,2,NULL),(231,'Vanuatu','vu',6,2,NULL),(232,'Wallis and Futuna','wf',6,2,NULL),(233,'Samoa','ws',6,2,NULL),(234,'Yemen','ye',6,2,NULL),(235,'Mayotte','yt',6,2,NULL),(236,'South Africa','za',6,2,NULL),(237,'Zambia','zm',6,2,NULL),(238,'Zimbabwe','zw',6,2,NULL);
/*!40000 ALTER TABLE `loc_elements` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `loc_geopositions`
--

LOCK TABLES `loc_geopositions` WRITE;
/*!40000 ALTER TABLE `loc_geopositions` DISABLE KEYS */;
/*!40000 ALTER TABLE `loc_geopositions` ENABLE KEYS */;
UNLOCK TABLES;

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
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FL_next_users_deliverables_idx` (`deliverable_id`),
  KEY `fk_next_users_users_created_by` (`created_by`),
  KEY `fk_next_users_users_modified_by` (`modified_by`),
  CONSTRAINT `fk_next_users_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_next_users_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FL_next_users_deliverables` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `next_users`
--

LOCK TABLES `next_users` WRITE;
/*!40000 ALTER TABLE `next_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `next_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_next_users_insert`
AFTER INSERT ON `$[database]`.`next_users`
FOR EACH ROW
BEGIN UPDATE $[database]_history.next_users SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.next_users(  `record_id`, `deliverable_id`, `user`, `expected_changes`, `strategies`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`user`, NEW.`expected_changes`, NEW.`strategies`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_next_users_update`
AFTER UPDATE ON `$[database]`.`next_users`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`deliverable_id`, OLD.`user`, OLD.`expected_changes`, OLD.`strategies`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`deliverable_id`, NEW.`user`, NEW.`expected_changes`, NEW.`strategies`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.next_users SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.next_users(  `record_id`, `deliverable_id`, `user`, `expected_changes`, `strategies`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`user`, NEW.`expected_changes`, NEW.`strategies`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.next_users(  `record_id`, `deliverable_id`, `user`, `expected_changes`, `strategies`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`deliverable_id`, NEW.`user`, NEW.`expected_changes`, NEW.`strategies`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_budget_overheads`
--

DROP TABLE IF EXISTS `project_budget_overheads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_budget_overheads` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `cost_recovered` tinyint(1) NOT NULL DEFAULT '0',
  `contracted_overhead` int(11) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_budget_overheads` (`project_id`),
  KEY `FK_project_budgets_ov_projects_idx` (`project_id`),
  KEY `FK_project_budgets_ov_users_created_by_idx` (`created_by`),
  KEY `FK_project_budgets_ov_users_modified_by_idx` (`modified_by`),
  CONSTRAINT `FK_project_budgets_ov_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_ov_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_ov_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_budget_overheads`
--

LOCK TABLES `project_budget_overheads` WRITE;
/*!40000 ALTER TABLE `project_budget_overheads` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_budget_overheads` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_budget_overheads_insert`
AFTER INSERT ON `$[database]`.`project_budget_overheads`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_budget_overheads SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_budget_overheads(  `record_id`, `project_id`, `cost_recovered`, `contracted_overhead`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`cost_recovered`, NEW.`contracted_overhead`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_budget_overheads_update`
AFTER UPDATE ON `$[database]`.`project_budget_overheads`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`cost_recovered`, OLD.`contracted_overhead`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`cost_recovered`, NEW.`contracted_overhead`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_budget_overheads SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_budget_overheads(  `record_id`, `project_id`, `cost_recovered`, `contracted_overhead`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`cost_recovered`, NEW.`contracted_overhead`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_budget_overheads(  `record_id`, `project_id`, `cost_recovered`, `contracted_overhead`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`cost_recovered`, NEW.`contracted_overhead`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_budgets`
--

DROP TABLE IF EXISTS `project_budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `budget_type` bigint(20) NOT NULL,
  `year` int(4) NOT NULL,
  `cofinance_project_id` bigint(20) DEFAULT NULL,
  `institution_id` bigint(20) NOT NULL,
  `gender_percentage` double DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_budgets` (`project_id`,`budget_type`,`year`,`institution_id`,`cofinance_project_id`),
  KEY `FK_project_budgets_temp_projects_idx` (`project_id`),
  KEY `FK_project_cofinance_bud_temp_projects_idx` (`cofinance_project_id`),
  KEY `FK_institutions_project_bud_temp_idx` (`institution_id`),
  KEY `FK_project_bud_temp_users_created_by_idx` (`created_by`),
  KEY `FK_project_bud_temp_users_modified_by_idx` (`modified_by`),
  KEY `FK_project_budgets_budget_types` (`budget_type`),
  CONSTRAINT `FK_project_budgets_budget_types` FOREIGN KEY (`budget_type`) REFERENCES `budget_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_projects_cofinance_id` FOREIGN KEY (`cofinance_project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_budgets_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_budgets`
--

LOCK TABLES `project_budgets` WRITE;
/*!40000 ALTER TABLE `project_budgets` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_budgets` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_budgets_insert`
AFTER INSERT ON `$[database]`.`project_budgets`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_budgets SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_budgets(  `record_id`, `project_id`, `amount`, `budget_type`, `year`, `cofinance_project_id`, `institution_id`, `gender_percentage`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`amount`, NEW.`budget_type`, NEW.`year`, NEW.`cofinance_project_id`, NEW.`institution_id`, NEW.`gender_percentage`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_budgets_update`
AFTER UPDATE ON `$[database]`.`project_budgets`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`amount`, OLD.`budget_type`, OLD.`year`, OLD.`cofinance_project_id`, OLD.`institution_id`, OLD.`gender_percentage`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`amount`, NEW.`budget_type`, NEW.`year`, NEW.`cofinance_project_id`, NEW.`institution_id`, NEW.`gender_percentage`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_budgets SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_budgets(  `record_id`, `project_id`, `amount`, `budget_type`, `year`, `cofinance_project_id`, `institution_id`, `gender_percentage`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`amount`, NEW.`budget_type`, NEW.`year`, NEW.`cofinance_project_id`, NEW.`institution_id`, NEW.`gender_percentage`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_budgets(  `record_id`, `project_id`, `amount`, `budget_type`, `year`, `cofinance_project_id`, `institution_id`, `gender_percentage`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`amount`, NEW.`budget_type`, NEW.`year`, NEW.`cofinance_project_id`, NEW.`institution_id`, NEW.`gender_percentage`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_cofinancing_linkages`
--

DROP TABLE IF EXISTS `project_cofinancing_linkages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_cofinancing_linkages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bilateral_project_id` bigint(20) NOT NULL COMMENT 'Link to table projects',
  `core_project_id` bigint(20) NOT NULL COMMENT 'Link to table projects',
  `created_by` bigint(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `modified_by` bigint(20) NOT NULL,
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_linked_core_projects` (`bilateral_project_id`,`core_project_id`),
  KEY `FK_linked_core_projects_bilateral_projects_idx` (`bilateral_project_id`),
  KEY `FK_linked_core_projects_core_projects_idx` (`core_project_id`),
  KEY `fk_linked_core_projects_users_created_by` (`created_by`),
  KEY `fk_linked_core_projects_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_linked_core_projects_bilateral_projects` FOREIGN KEY (`bilateral_project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_linked_core_projects_core_projects` FOREIGN KEY (`core_project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_linked_core_projects_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_linked_core_projects_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table links a bilateral co-founded project with at least one core project.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_cofinancing_linkages`
--

LOCK TABLES `project_cofinancing_linkages` WRITE;
/*!40000 ALTER TABLE `project_cofinancing_linkages` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_cofinancing_linkages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_crp_contributions`
--

DROP TABLE IF EXISTS `project_crp_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_crp_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `crp_id` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_crp_contributions` (`project_id`,`crp_id`),
  KEY `FK_project_crp_contributions_projects_idx` (`project_id`),
  KEY `FK_project_crp_contributions_crps_idx` (`crp_id`),
  KEY `FK_project_crp_contributions_users_created_by_idx` (`created_by`),
  KEY `FK_project_crp_contributions_users_modified_by_idx` (`modified_by`),
  CONSTRAINT `FK_project_crp_contributions_crps` FOREIGN KEY (`crp_id`) REFERENCES `crps` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_crp_contributions_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_crp_contributions`
--

LOCK TABLES `project_crp_contributions` WRITE;
/*!40000 ALTER TABLE `project_crp_contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_crp_contributions` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_crp_contributions_insert`
AFTER INSERT ON `$[database]`.`project_crp_contributions`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_crp_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_crp_contributions(  `record_id`, `project_id`, `crp_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`crp_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_crp_contributions_update`
AFTER UPDATE ON `$[database]`.`project_crp_contributions`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`crp_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`crp_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_crp_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_crp_contributions(  `record_id`, `project_id`, `crp_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`crp_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_crp_contributions(  `record_id`, `project_id`, `crp_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`crp_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_focuses`
--

DROP TABLE IF EXISTS `project_focuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_focuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `program_id` bigint(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_focuses_key` (`project_id`,`program_id`),
  KEY `FK_project_focus_project_id_idx` (`project_id`),
  KEY `FK_project_focus_program_id_idx` (`program_id`),
  KEY `fk_project_focuses_users_created_by` (`created_by`),
  KEY `fk_project_focuses_users_modified__by` (`modified_by`),
  CONSTRAINT `FK_project_focuses_ip_program_id` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_focuses_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_focuses_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_focuses_users_modified__by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_focuses`
--

LOCK TABLES `project_focuses` WRITE;
/*!40000 ALTER TABLE `project_focuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_focuses` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_focuses_insert`
AFTER INSERT ON `$[database]`.`project_focuses`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_focuses SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_focuses(  `record_id`, `project_id`, `program_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`program_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_focuses_update`
AFTER UPDATE ON `$[database]`.`project_focuses`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`program_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`program_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_focuses SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_focuses(  `record_id`, `project_id`, `program_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`program_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_focuses(  `record_id`, `project_id`, `program_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`program_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_locations`
--

DROP TABLE IF EXISTS `project_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_locations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `loc_element_id` bigint(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_projectID_locElementID` (`project_id`,`loc_element_id`),
  KEY `fk_project_locations_loc_element_types_idx` (`loc_element_id`),
  KEY `fk_project_locations_users_created_by` (`created_by`),
  KEY `fk_project_locations_users_modified_by` (`modified_by`),
  KEY `fk_project_locations_projects_idx` (`project_id`),
  CONSTRAINT `fk_project_locations_projects_idx` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_locations`
--

LOCK TABLES `project_locations` WRITE;
/*!40000 ALTER TABLE `project_locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_locations` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_locations_insert`
AFTER INSERT ON `$[database]`.`project_locations`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_locations SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_locations(  `record_id`, `project_id`, `loc_element_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`loc_element_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_locations_update`
AFTER UPDATE ON `$[database]`.`project_locations`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`loc_element_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`loc_element_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_locations SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_locations(  `record_id`, `project_id`, `loc_element_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`loc_element_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_locations(  `record_id`, `project_id`, `loc_element_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`loc_element_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_mog_budgets`
--

DROP TABLE IF EXISTS `project_mog_budgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_mog_budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `mog_id` bigint(20) NOT NULL,
  `total_contribution` int(11) DEFAULT NULL,
  `gender_contribution` int(11) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_mog_budgets` (`project_id`,`mog_id`,`year`),
  KEY `FK_project_mog_budgets_projects_idx` (`project_id`),
  KEY `FK_project_mog_budget_ip_elements_idx` (`mog_id`),
  KEY `FK_project_mog_budget_users_created_by_idx` (`created_by`),
  KEY `FK_project_mog_budget_users_modified_by_idx` (`modified_by`),
  CONSTRAINT `FK_project_mog_budgets_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_mog_budget_ip_elements` FOREIGN KEY (`mog_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_mog_budget_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_mog_budget_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_mog_budgets`
--

LOCK TABLES `project_mog_budgets` WRITE;
/*!40000 ALTER TABLE `project_mog_budgets` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_mog_budgets` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_mog_budgets_insert`
AFTER INSERT ON `$[database]`.`project_mog_budgets`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_mog_budgets SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_mog_budgets(  `record_id`, `project_id`, `mog_id`, `total_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`total_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_mog_budgets_update`
AFTER UPDATE ON `$[database]`.`project_mog_budgets`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`mog_id`, OLD.`total_contribution`, OLD.`gender_contribution`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`total_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_mog_budgets SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_mog_budgets(  `record_id`, `project_id`, `mog_id`, `total_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`total_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_mog_budgets(  `record_id`, `project_id`, `mog_id`, `total_contribution`, `gender_contribution`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`mog_id`, NEW.`total_contribution`, NEW.`gender_contribution`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_other_contributions`
--

DROP TABLE IF EXISTS `project_other_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_other_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `contribution` text,
  `additional_contribution` text,
  `crp_contributions_nature` text,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ip_other_contributions_idx` (`project_id`),
  KEY `fk_ip_other_contributions_users_created_by` (`created_by`),
  KEY `fk_ip_other_contributions_users_modified_by` (`modified_by`),
  CONSTRAINT `FK_ip_other_contributions` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_other_contributions_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ip_other_contributions_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_other_contributions`
--

LOCK TABLES `project_other_contributions` WRITE;
/*!40000 ALTER TABLE `project_other_contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_other_contributions` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_other_contributions_insert`
AFTER INSERT ON `$[database]`.`project_other_contributions`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_other_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_other_contributions(  `record_id`, `project_id`, `contribution`, `additional_contribution`, `crp_contributions_nature`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`contribution`, NEW.`additional_contribution`, NEW.`crp_contributions_nature`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_other_contributions_update`
AFTER UPDATE ON `$[database]`.`project_other_contributions`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`contribution`, OLD.`additional_contribution`, OLD.`crp_contributions_nature`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`contribution`, NEW.`additional_contribution`, NEW.`crp_contributions_nature`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_other_contributions SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_other_contributions(  `record_id`, `project_id`, `contribution`, `additional_contribution`, `crp_contributions_nature`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`contribution`, NEW.`additional_contribution`, NEW.`crp_contributions_nature`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_other_contributions(  `record_id`, `project_id`, `contribution`, `additional_contribution`, `crp_contributions_nature`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`contribution`, NEW.`additional_contribution`, NEW.`crp_contributions_nature`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
  `gender_dimension` text,
  `project_id` bigint(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`),
  KEY `fk_project_outcomes_users_created_by` (`created_by`),
  KEY `fk_project_outcomes_users_modified__by` (`modified_by`),
  CONSTRAINT `FK_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_outcomes_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_outcomes_users_modified__by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_outcomes`
--

LOCK TABLES `project_outcomes` WRITE;
/*!40000 ALTER TABLE `project_outcomes` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_outcomes` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_outcomes_insert`
AFTER INSERT ON `$[database]`.`project_outcomes`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_outcomes SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_outcomes(  `record_id`, `year`, `statement`, `stories`, `gender_dimension`, `project_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`year`, NEW.`statement`, NEW.`stories`, NEW.`gender_dimension`, NEW.`project_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_outcomes_update`
AFTER UPDATE ON `$[database]`.`project_outcomes`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`year`, OLD.`statement`, OLD.`stories`, OLD.`gender_dimension`, OLD.`project_id`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`year`, NEW.`statement`, NEW.`stories`, NEW.`gender_dimension`, NEW.`project_id`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_outcomes SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_outcomes(  `record_id`, `year`, `statement`, `stories`, `gender_dimension`, `project_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`year`, NEW.`statement`, NEW.`stories`, NEW.`gender_dimension`, NEW.`project_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_outcomes(  `record_id`, `year`, `statement`, `stories`, `gender_dimension`, `project_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`year`, NEW.`statement`, NEW.`stories`, NEW.`gender_dimension`, NEW.`project_id`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_partner_contributions`
--

DROP TABLE IF EXISTS `project_partner_contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_partner_contributions` (
  `project_partner_id` bigint(20) NOT NULL,
  `contribution_institution_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_partner_id`,`contribution_institution_id`),
  KEY `FK_project_partners_contributions_project_partner_idx` (`project_partner_id`),
  KEY `FK_project_partners_contributions_institutions_idx` (`contribution_institution_id`),
  CONSTRAINT `FK_project_partners_contributions_institutions` FOREIGN KEY (`contribution_institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_partners_contributions_project_partner` FOREIGN KEY (`project_partner_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='2-level partners contribution with PPA partners.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_partner_contributions`
--

LOCK TABLES `project_partner_contributions` WRITE;
/*!40000 ALTER TABLE `project_partner_contributions` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_partner_contributions` ENABLE KEYS */;
UNLOCK TABLES;

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
  `user_id` bigint(20) DEFAULT NULL,
  `partner_type` enum('PPA','PL','PP','PC') DEFAULT NULL,
  `activity_partner` tinyint(1) NOT NULL DEFAULT '0',
  `responsabilities` text,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_partners_projects_idx` (`project_id`),
  KEY `FK_project_partners_institutions_idx` (`partner_id`),
  KEY `FK_project_partners_users_idx` (`user_id`),
  KEY `fk_project_partners_users_created_by` (`created_by`),
  KEY `fk_project_partners_users_modified__by` (`modified_by`),
  CONSTRAINT `FK_project_partners_institutions` FOREIGN KEY (`partner_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_partners_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_partners_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_partners_users_modified__by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_partners_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_partners`
--

LOCK TABLES `project_partners` WRITE;
/*!40000 ALTER TABLE `project_partners` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_partners` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_partners_insert`
AFTER INSERT ON `$[database]`.`project_partners`
FOR EACH ROW
BEGIN UPDATE $[database]_history.project_partners SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.project_partners(  `record_id`, `project_id`, `partner_id`, `user_id`, `partner_type`, `activity_partner`, `responsabilities`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`partner_id`, NEW.`user_id`, NEW.`partner_type`, NEW.`activity_partner`, NEW.`responsabilities`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_project_partners_update`
AFTER UPDATE ON `$[database]`.`project_partners`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`project_id`, OLD.`partner_id`, OLD.`user_id`, OLD.`partner_type`, OLD.`activity_partner`, OLD.`responsabilities`, OLD.`is_active`, OLD.`active_since`, OLD.`created_by`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`project_id`, NEW.`partner_id`, NEW.`user_id`, NEW.`partner_type`, NEW.`activity_partner`, NEW.`responsabilities`, NEW.`is_active`, NEW.`active_since`, NEW.`created_by`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.project_partners SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.project_partners(  `record_id`, `project_id`, `partner_id`, `user_id`, `partner_type`, `activity_partner`, `responsabilities`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`partner_id`, NEW.`user_id`, NEW.`partner_type`, NEW.`activity_partner`, NEW.`responsabilities`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.project_partners(  `record_id`, `project_id`, `partner_id`, `user_id`, `partner_type`, `activity_partner`, `responsabilities`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`project_id`, NEW.`partner_id`, NEW.`user_id`, NEW.`partner_type`, NEW.`activity_partner`, NEW.`responsabilities`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `project_roles`
--

DROP TABLE IF EXISTS `project_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT 'Link to table projects',
  `user_id` bigint(20) NOT NULL COMMENT 'Link to table users',
  `role_id` bigint(20) NOT NULL COMMENT 'Link to table roles',
  PRIMARY KEY (`id`),
  KEY `FK_project_roles_projects_idx` (`project_id`),
  KEY `FK_project_roles_roles_idx` (`role_id`),
  KEY `FK_project_roles_users_idx` (`user_id`),
  CONSTRAINT `FK_project_roles_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_roles`
--

LOCK TABLES `project_roles` WRITE;
/*!40000 ALTER TABLE `project_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_roles` ENABLE KEYS */;
UNLOCK TABLES;

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
  `type` enum('CCAFS_CORE','CCAFS_COFUNDED','BILATERAL') DEFAULT 'CCAFS_CORE',
  `is_global` tinyint(1) NOT NULL DEFAULT '0',
  `is_cofinancing` tinyint(1) NOT NULL DEFAULT '0',
  `leader_responsabilities` text,
  `liaison_institution_id` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table liaison institutions',
  `liaison_user_id` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table liaison_users',
  `requires_workplan_upload` tinyint(1) DEFAULT '0',
  `workplan_name` varchar(255) DEFAULT NULL,
  `bilateral_contract_name` varchar(255) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL COMMENT 'foreign key to the table users',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_projects_liaison_institutions_idx` (`liaison_institution_id`),
  KEY `FK_projects_liaison_liaison_users_idx` (`liaison_user_id`),
  KEY `fk_projects_employees_created_by` (`created_by`),
  KEY `fk_projects_users_modified_by` (`modified_by`),
  CONSTRAINT `fk_projects_employees_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects_liaison_institutions` FOREIGN KEY (`liaison_institution_id`) REFERENCES `liaison_institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_projects_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_projects__liaison_users` FOREIGN KEY (`liaison_user_id`) REFERENCES `liaison_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_projects_insert`
AFTER INSERT ON `$[database]`.`projects`
FOR EACH ROW
BEGIN UPDATE $[database]_history.projects SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1;   INSERT INTO $[database]_history.projects(  `record_id`, `title`, `summary`, `start_date`, `end_date`, `type`, `is_global`, `is_cofinancing`, `leader_responsabilities`, `liaison_institution_id`, `liaison_user_id`, `requires_workplan_upload`, `workplan_name`, `bilateral_contract_name`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`title`, NEW.`summary`, NEW.`start_date`, NEW.`end_date`, NEW.`type`, NEW.`is_global`, NEW.`is_cofinancing`, NEW.`leader_responsabilities`, NEW.`liaison_institution_id`, NEW.`liaison_user_id`, NEW.`requires_workplan_upload`, NEW.`workplan_name`, NEW.`bilateral_contract_name`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'insert'); END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`$[user]`@`localhost`*/ /*!50003 TRIGGER `$[database]`.`after_projects_update`
AFTER UPDATE ON `$[database]`.`projects`
FOR EACH ROW
BEGIN DECLARE old_concat, new_concat text; SET old_concat = MD5(CONCAT_WS(',', OLD.`id`, OLD.`title`, OLD.`summary`, OLD.`start_date`, OLD.`end_date`, OLD.`type`, OLD.`is_global`, OLD.`is_cofinancing`, OLD.`leader_responsabilities`, OLD.`liaison_institution_id`, OLD.`liaison_user_id`, OLD.`requires_workplan_upload`, OLD.`workplan_name`, OLD.`bilateral_contract_name`, OLD.`created_by`, OLD.`is_active`, OLD.`active_since`)); SET new_concat = MD5(CONCAT_WS(',', NEW.`id`, NEW.`title`, NEW.`summary`, NEW.`start_date`, NEW.`end_date`, NEW.`type`, NEW.`is_global`, NEW.`is_cofinancing`, NEW.`leader_responsabilities`, NEW.`liaison_institution_id`, NEW.`liaison_user_id`, NEW.`requires_workplan_upload`, NEW.`workplan_name`, NEW.`bilateral_contract_name`, NEW.`created_by`, NEW.`is_active`, NEW.`active_since`)); IF old_concat <> new_concat THEN UPDATE $[database]_history.projects SET active_until=NOW() WHERE record_id = NEW.`id` ORDER BY active_since DESC LIMIT 1; IF OLD.`is_active` = 1 AND NEW.`is_active` = 0 THEN   INSERT INTO $[database]_history.projects(  `record_id`, `title`, `summary`, `start_date`, `end_date`, `type`, `is_global`, `is_cofinancing`, `leader_responsabilities`, `liaison_institution_id`, `liaison_user_id`, `requires_workplan_upload`, `workplan_name`, `bilateral_contract_name`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`title`, NEW.`summary`, NEW.`start_date`, NEW.`end_date`, NEW.`type`, NEW.`is_global`, NEW.`is_cofinancing`, NEW.`leader_responsabilities`, NEW.`liaison_institution_id`, NEW.`liaison_user_id`, NEW.`requires_workplan_upload`, NEW.`workplan_name`, NEW.`bilateral_contract_name`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'delete'); ELSE   INSERT INTO $[database]_history.projects(  `record_id`, `title`, `summary`, `start_date`, `end_date`, `type`, `is_global`, `is_cofinancing`, `leader_responsabilities`, `liaison_institution_id`, `liaison_user_id`, `requires_workplan_upload`, `workplan_name`, `bilateral_contract_name`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`) VALUES ( NEW.`id`, NEW.`title`, NEW.`summary`, NEW.`start_date`, NEW.`end_date`, NEW.`type`, NEW.`is_global`, NEW.`is_cofinancing`, NEW.`leader_responsabilities`, NEW.`liaison_institution_id`, NEW.`liaison_user_id`, NEW.`requires_workplan_upload`, NEW.`workplan_name`, NEW.`bilateral_contract_name`, NEW.`is_active`, NOW(), NULL, NEW.`modified_by`, NEW.`modification_justification`, 'update'); END IF; END IF; END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `roles_permission_roles_idx` (`role_id`),
  KEY `roles_permission_user_permission_idx` (`permission_id`),
  CONSTRAINT `FK_roles_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_roles_permission_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Admin','Admin'),(2,'Flagship program leader','FPL'),(3,'Regional program leader','RPL'),(4,'Contact point','CP'),(5,'Activity leader','AL'),(6,'Coordinating unit','CU'),(7,'Project leader','PL'),(8,'Guest','G'),(9,'Project contributos','PC');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `site_element_types`
--

LOCK TABLES `site_element_types` WRITE;
/*!40000 ALTER TABLE `site_element_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_element_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table users',
  `role_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table roles',
  PRIMARY KEY (`id`),
  KEY `FK_user_roles_user_index` (`user_id`),
  KEY `FK_user_roles_roles_index` (`role_id`),
  CONSTRAINT `FK_user_roles_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_user_roles_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_ccafs_user` tinyint(4) NOT NULL DEFAULT '0',
  `created_by` bigint(20) DEFAULT NULL,
  `is_active` tinyint(4) NOT NULL DEFAULT '1',
  `last_login` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FK_users_user_id_idx` (`created_by`),
  CONSTRAINT `FK_users_user_id` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-24  8:48:39
