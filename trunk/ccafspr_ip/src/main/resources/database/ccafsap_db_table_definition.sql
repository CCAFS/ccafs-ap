-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: davinci.ciat.cgiar.org
-- Tiempo de generación: 13-08-2014 a las 23:18:59
-- Versión del servidor: 5.5.30
-- Versión de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de datos: `ccafspr_ip`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activities`
--

CREATE TABLE IF NOT EXISTS `activities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `custom_id` varchar(50) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `expected_leader_id` bigint(20) DEFAULT NULL,
  `leader_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`),
  KEY `FK_activities_activity_leaders_idx` (`leader_id`),
  KEY `FK_activities_expected_leader_id_idx` (`expected_leader_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_budgets`
--

CREATE TABLE IF NOT EXISTS `activity_budgets` (
  `activity_id` bigint(20) NOT NULL,
  `budget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`activity_id`,`budget_id`),
  KEY `FK_activity_budgets_budget_id_idx` (`budget_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_locations`
--

CREATE TABLE IF NOT EXISTS `activity_locations` (
  `activity_id` bigint(20) NOT NULL,
  `loc_element_id` bigint(20) NOT NULL,
  PRIMARY KEY (`activity_id`,`loc_element_id`),
  KEY `FK_activity_locations_loc_element_types_idx` (`loc_element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_partners`
--

CREATE TABLE IF NOT EXISTS `activity_partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contribution` text,
  PRIMARY KEY (`id`),
  KEY `FL_activity_partners_institutions.id_idx` (`institution_id`),
  KEY `FK_activity_partners_activities_idx` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `budgets`
--

CREATE TABLE IF NOT EXISTS `budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `budget_type` int(11) NOT NULL COMMENT 'Foreign key to the budget_types table.',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to the institutions table.',
  `amount` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_institution_id_idx` (`institution_id`),
  KEY `FK_budget_type_idx` (`budget_type`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `budget_types`
--

CREATE TABLE IF NOT EXISTS `budget_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverables`
--

CREATE TABLE IF NOT EXISTS `deliverables` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `title` text,
  `type_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverables_activities_idx` (`activity_id`),
  KEY `deliverables_deliverables_type_idx` (`type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_types`
--

CREATE TABLE IF NOT EXISTS `deliverable_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `timeline` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverable_types_parent_id_idx` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employees`
--

CREATE TABLE IF NOT EXISTS `employees` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table users',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table institutions.',
  `role_id` bigint(20) NOT NULL COMMENT 'Positions inside CCAFS',
  `is_main` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_employees_persons_idx` (`user_id`),
  KEY `FK_employees_institutions_idx` (`institution_id`),
  KEY `FK_employees_roles_idx` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `expected_activity_leaders`
--

CREATE TABLE IF NOT EXISTS `expected_activity_leaders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_activity_leaders_institutions_idx` (`institution_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `expected_project_leaders`
--

CREATE TABLE IF NOT EXISTS `expected_project_leaders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact_first_name` varchar(255) DEFAULT NULL,
  `contact_last_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to Institution id',
  PRIMARY KEY (`id`),
  KEY `PK_Institution_idx` (`institution_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `institutions`
--

CREATE TABLE IF NOT EXISTS `institutions` (
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
  PRIMARY KEY (`id`),
  KEY `FK_ip_program_lead_institutions_idx` (`program_id`),
  KEY `FK_institutions_institution_types_idx` (`institution_type_id`),
  KEY `FK_loc_elements_id_idx` (`country_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `institution_locations`
--

CREATE TABLE IF NOT EXISTS `institution_locations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `institution_id` bigint(20) NOT NULL,
  `country_id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_institution_locations_institutions_idx` (`institution_id`),
  KEY `FK_institution_locations_loc_elements_idx` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `institution_types`
--

CREATE TABLE IF NOT EXISTS `institution_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_activity_contributions`
--

CREATE TABLE IF NOT EXISTS `ip_activity_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table activities.',
  `ip_element_id` bigint(20) NOT NULL COMMENT 'This field is a foreign key to the table IP Elements.\nThe vales referenced in this column should be of type ''Outputs'' but this constraint is checked at application level.',
  PRIMARY KEY (`id`),
  KEY `FK_activities_ipElements_activityID_idx` (`activity_id`),
  KEY `FK_activities_ipElements_ipElementID_idx` (`ip_element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_activity_indicators`
--

CREATE TABLE IF NOT EXISTS `ip_activity_indicators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `target` text,
  `activity_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table activities',
  `parent_id` bigint(20) NOT NULL COMMENT 'This column is a foreign key that references to the table ip_indicators.',
  PRIMARY KEY (`id`),
  KEY `FK_ipActivityIndicators_ipIndicators_parentID_idx` (`parent_id`),
  KEY `FK_ipActivityIndicators_ipIndicators_activityID_idx` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_cross_cutting_themes`
--

CREATE TABLE IF NOT EXISTS `ip_cross_cutting_themes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_elements`
--

CREATE TABLE IF NOT EXISTS `ip_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text COLLATE latin1_general_ci,
  `element_type_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_element_types',
  PRIMARY KEY (`id`),
  KEY `FK_element_element_type_idx` (`element_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_element_types`
--

CREATE TABLE IF NOT EXISTS `ip_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_indicators`
--

CREATE TABLE IF NOT EXISTS `ip_indicators` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text COLLATE latin1_general_ci,
  `target` text COLLATE latin1_general_ci,
  `program_element_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT 'Foreign key to ip_indicators table. This field shows if the indicator contributes to another indicator',
  PRIMARY KEY (`id`),
  KEY `FK_indicators_program_elements_idx` (`program_element_id`),
  KEY `FK_indicators_parent_indicator_idx` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_other_contributions`
--

CREATE TABLE IF NOT EXISTS `ip_other_contributions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `contribution` text,
  `additional_contribution` text,
  PRIMARY KEY (`id`),
  KEY `GK_ip_other_contributions_idx` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_programs`
--

CREATE TABLE IF NOT EXISTS `ip_programs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE latin1_general_ci,
  `acronym` varchar(45) COLLATE latin1_general_ci DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ip_program_program_type_idx` (`type_id`),
  KEY `FK_ip_program_ip_region_idx` (`region_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_program_elements`
--

CREATE TABLE IF NOT EXISTS `ip_program_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_elements',
  `program_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_programs',
  `relation_type_id` int(11) DEFAULT NULL COMMENT 'Foreign key to the table ip_program_element_relation_types',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`element_id`,`program_id`,`relation_type_id`) COMMENT ' /* comment truncated */ /*This index prevents that a program has more than one relation to the same element.*/',
  KEY `FK_program_elements_program_idx` (`program_id`),
  KEY `FK_program_elements_elements_idx` (`element_id`),
  KEY `FK_program_elements_element_relation_types_idx` (`relation_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_program_element_relation_types`
--

CREATE TABLE IF NOT EXISTS `ip_program_element_relation_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'This field describe the relation between the element and the program',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_program_types`
--

CREATE TABLE IF NOT EXISTS `ip_program_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_relationships`
--

CREATE TABLE IF NOT EXISTS `ip_relationships` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `child_id` bigint(20) NOT NULL,
  `relation_type_id` bigint(20) NOT NULL DEFAULT '1' COMMENT 'Foreign key to the table ip_relationship_type, by default the value is 1 (''Contributes to''  type)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `KEY_unique_index` (`parent_id`,`child_id`) COMMENT ' /* comment truncated */ /*Each element have to be related with another element only once.*/',
  KEY `FK_element_relations_child_idx` (`parent_id`),
  KEY `test_idx` (`child_id`),
  KEY `FK_element_relations_relationship_types_idx` (`relation_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_relationship_type`
--

CREATE TABLE IF NOT EXISTS `ip_relationship_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT 'This field describes the type of relation between the child element and the parent element',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `loc_elements`
--

CREATE TABLE IF NOT EXISTS `loc_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `code` varchar(45) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `element_type_id` bigint(20) NOT NULL,
  `geoposition_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_element_loc_element_type_id_idx` (`element_type_id`),
  KEY `FK_loc_elements_loc_geopositions_idx` (`geoposition_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `loc_element_types`
--

CREATE TABLE IF NOT EXISTS `loc_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(245) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_element_type_parent_idx` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `loc_geopositions`
--

CREATE TABLE IF NOT EXISTS `loc_geopositions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_loc_geoposition_loc_geopositon_idx` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `next_users`
--

CREATE TABLE IF NOT EXISTS `next_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliverable_id` bigint(20) NOT NULL,
  `user` text,
  `expected_changes` text,
  `strategies` text,
  PRIMARY KEY (`id`),
  KEY `FL_next_users_deliverables_idx` (`deliverable_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permissions`
--

CREATE TABLE IF NOT EXISTS `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `element_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table site_elements',
  `role_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table roles',
  `permission` enum('Create','Update','Read','Delete') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_permissions_elements_idx` (`element_id`),
  KEY `FK_permissions_roles_idx` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persons`
--

CREATE TABLE IF NOT EXISTS `persons` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
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
  KEY `FK_projects_program_id_idx` (`program_creator_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_budgets`
--

CREATE TABLE IF NOT EXISTS `project_budgets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `budget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_id_idx` (`project_id`),
  KEY `FK_budget_id_idx` (`budget_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_cross_cutting_themes`
--

CREATE TABLE IF NOT EXISTS `project_cross_cutting_themes` (
  `project_id` bigint(20) NOT NULL,
  `theme_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table ip_cross_cutting_themes',
  PRIMARY KEY (`project_id`,`theme_id`),
  KEY `FK_projects_themes_projects_idx` (`project_id`),
  KEY `FK_projects_themes_cutting_themes_idx` (`theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_focuses`
--

CREATE TABLE IF NOT EXISTS `project_focuses` (
  `project_id` bigint(20) NOT NULL,
  `program_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`program_id`),
  KEY `FK_project_focus_project_id_idx` (`project_id`),
  KEY `FK_project_focus_program_id_idx` (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_outcomes`
--

CREATE TABLE IF NOT EXISTS `project_outcomes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `statement` text,
  `stories` text,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Projects_id_idx` (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_partners`
--

CREATE TABLE IF NOT EXISTS `project_partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT 'Foreign key to projects table',
  `partner_id` bigint(20) NOT NULL COMMENT 'Foreign key to institutions table',
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `responsabilities` text,
  PRIMARY KEY (`id`),
  KEY `FK_project_partners_projects_idx` (`project_id`),
  KEY `FK_project_partners_institutions_idx` (`partner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project_partner_budget`
--

CREATE TABLE IF NOT EXISTS `project_partner_budget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL,
  `total` double DEFAULT NULL,
  `ccafs_funding` int(11) DEFAULT NULL,
  `bilateral` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_budget_projects_idx` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `site_elements`
--

CREATE TABLE IF NOT EXISTS `site_elements` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `site_element_type_id` bigint(20) NOT NULL COMMENT 'foreign key to the table site_elements_types',
  PRIMARY KEY (`id`),
  KEY `FK_elements_element_types_idx` (`site_element_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `site_element_types`
--

CREATE TABLE IF NOT EXISTS `site_element_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `person_id` bigint(20) NOT NULL COMMENT 'Foreign key to persons table',
  `is_ccafs_user` tinyint(1) DEFAULT '0',
  `created_by` bigint(20) DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_users_person_id_idx` (`person_id`),
  KEY `FK_users_user_id_idx` (`created_by`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `activities`
--
ALTER TABLE `activities`
  ADD CONSTRAINT `FK_activities_expected_leader_id` FOREIGN KEY (`expected_leader_id`) REFERENCES `expected_activity_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_activities_leader_id` FOREIGN KEY (`leader_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Activities_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_budgets`
--
ALTER TABLE `activity_budgets`
  ADD CONSTRAINT `FK_activity_budgets_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_activity_budgets_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_locations`
--
ALTER TABLE `activity_locations`
  ADD CONSTRAINT `FK_activity_locations_loc_element_types` FOREIGN KEY (`loc_element_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_activity_locations_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `activity_partners`
--
ALTER TABLE `activity_partners`
  ADD CONSTRAINT `FK_activity_partners_institutions.id` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_activity_partners_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `budgets`
--
ALTER TABLE `budgets`
  ADD CONSTRAINT `FK_institution_id` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_budget_type` FOREIGN KEY (`budget_type`) REFERENCES `budget_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `deliverables`
--
ALTER TABLE `deliverables`
  ADD CONSTRAINT `deliverables_activities` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `deliverables_deliverables_type` FOREIGN KEY (`type_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `deliverable_types`
--
ALTER TABLE `deliverable_types`
  ADD CONSTRAINT `deliverable_types_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `deliverable_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `FK_employees_persons` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_employees_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_employees_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `expected_activity_leaders`
--
ALTER TABLE `expected_activity_leaders`
  ADD CONSTRAINT `FK_activity_leaders_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `expected_project_leaders`
--
ALTER TABLE `expected_project_leaders`
  ADD CONSTRAINT `PK_Institution` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `institutions`
--
ALTER TABLE `institutions`
  ADD CONSTRAINT `FK_institution_institution_type` FOREIGN KEY (`institution_type_id`) REFERENCES `institution_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_institution_loc_elements` FOREIGN KEY (`country_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ip_program_lead_institutions` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `institution_locations`
--
ALTER TABLE `institution_locations`
  ADD CONSTRAINT `FK_institution_locations_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_institution_locations_loc_elements` FOREIGN KEY (`country_id`) REFERENCES `loc_elements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ip_activity_contributions`
--
ALTER TABLE `ip_activity_contributions`
  ADD CONSTRAINT `FK_activities_ipElements_activityID` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_activities_ipElements_ipElementID` FOREIGN KEY (`ip_element_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ip_activity_indicators`
--
ALTER TABLE `ip_activity_indicators`
  ADD CONSTRAINT `FK_ipActivityIndicators_ipIndicators_parentID` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ipActivityIndicators_ipIndicators_activityID` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ip_elements`
--
ALTER TABLE `ip_elements`
  ADD CONSTRAINT `FK_element_element_type` FOREIGN KEY (`element_type_id`) REFERENCES `ip_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ip_indicators`
--
ALTER TABLE `ip_indicators`
  ADD CONSTRAINT `FK_indicators_program_elements` FOREIGN KEY (`program_element_id`) REFERENCES `ip_program_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_indicators_parent_indicator` FOREIGN KEY (`parent_id`) REFERENCES `ip_indicators` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ip_other_contributions`
--
ALTER TABLE `ip_other_contributions`
  ADD CONSTRAINT `GK_ip_other_contributions` FOREIGN KEY (`activity_id`) REFERENCES `activities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ip_programs`
--
ALTER TABLE `ip_programs`
  ADD CONSTRAINT `FK_ip_program_ip_region` FOREIGN KEY (`region_id`) REFERENCES `loc_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ip_program_program_type` FOREIGN KEY (`type_id`) REFERENCES `ip_program_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ip_program_elements`
--
ALTER TABLE `ip_program_elements`
  ADD CONSTRAINT `FK_program_elements_elements` FOREIGN KEY (`element_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_program_elements_element_relation_types` FOREIGN KEY (`relation_type_id`) REFERENCES `ip_program_element_relation_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_program_elements_program` FOREIGN KEY (`program_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ip_relationships`
--
ALTER TABLE `ip_relationships`
  ADD CONSTRAINT `FK_element_relations_child` FOREIGN KEY (`parent_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_element_relations_parent` FOREIGN KEY (`child_id`) REFERENCES `ip_elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_element_relations_relationship_types` FOREIGN KEY (`relation_type_id`) REFERENCES `ip_relationship_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `loc_elements`
--
ALTER TABLE `loc_elements`
  ADD CONSTRAINT `FK_loc_element_loc_element_type_id` FOREIGN KEY (`element_type_id`) REFERENCES `loc_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_loc_elements_loc_geopositions` FOREIGN KEY (`geoposition_id`) REFERENCES `loc_geopositions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `loc_element_types`
--
ALTER TABLE `loc_element_types`
  ADD CONSTRAINT `FK_loc_element_type_parent` FOREIGN KEY (`parent_id`) REFERENCES `loc_element_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `loc_geopositions`
--
ALTER TABLE `loc_geopositions`
  ADD CONSTRAINT `FK_loc_geoposition_loc_geopositon` FOREIGN KEY (`parent_id`) REFERENCES `loc_geopositions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `next_users`
--
ALTER TABLE `next_users`
  ADD CONSTRAINT `FL_next_users_deliverables` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `permissions`
--
ALTER TABLE `permissions`
  ADD CONSTRAINT `FK_permissions_elements` FOREIGN KEY (`element_id`) REFERENCES `site_elements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_permissions_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `FK_projects_employees` FOREIGN KEY (`project_leader_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_projects_expected_project_leaders` FOREIGN KEY (`expected_project_leader_id`) REFERENCES `expected_project_leaders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_projects_owner_employees` FOREIGN KEY (`project_owner_id`) REFERENCES `employees` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_projects_program_id` FOREIGN KEY (`program_creator_id`) REFERENCES `ip_programs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `project_budgets`
--
ALTER TABLE `project_budgets`
  ADD CONSTRAINT `FK_project_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_budget_id` FOREIGN KEY (`budget_id`) REFERENCES `budgets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `project_cross_cutting_themes`
--
ALTER TABLE `project_cross_cutting_themes`
  ADD CONSTRAINT `FK_projects_themes_cutting_themes` FOREIGN KEY (`theme_id`) REFERENCES `ip_cross_cutting_themes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_projects_themes_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `project_outcomes`
--
ALTER TABLE `project_outcomes`
  ADD CONSTRAINT `FK_Projects_id` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `project_partners`
--
ALTER TABLE `project_partners`
  ADD CONSTRAINT `FK_project_partners_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_project_partners_institutions` FOREIGN KEY (`partner_id`) REFERENCES `institutions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `project_partner_budget`
--
ALTER TABLE `project_partner_budget`
  ADD CONSTRAINT `FK_project_budget_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `site_elements`
--
ALTER TABLE `site_elements`
  ADD CONSTRAINT `FK_elements_element_types` FOREIGN KEY (`site_element_type_id`) REFERENCES `site_element_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_users_person_id` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_users_user_id` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
