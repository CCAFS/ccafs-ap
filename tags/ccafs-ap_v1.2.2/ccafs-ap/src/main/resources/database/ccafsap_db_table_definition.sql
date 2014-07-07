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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_keywords`
--

CREATE TABLE IF NOT EXISTS `activity_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NOT NULL,
  `keyword_id` int(11) DEFAULT NULL,
  `other` text,
  PRIMARY KEY (`id`),
  KEY `ak_activity_fk` (`activity_id`),
  KEY `ak_keyword_fk` (`keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_leaders`
--

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

--
-- Volcado de datos para la tabla `activity_leaders`
--

INSERT INTO `activity_leaders` (`id`, `acronym`, `name`, `led_activity_id`, `region_id`, `theme_id`) VALUES
(1, 'AfricaRice', 'Africa Rice Center', 1, NULL, NULL),
(2, 'Bioversity', 'Bioversity International', 1, NULL, NULL),
(3, 'CIAT', 'Centro Internacional de Agricultura Tropical', 1, NULL, NULL),
(4, 'CIFOR', 'Center for International Forestry Research', 1, NULL, NULL),
(5, 'CIMMYT', 'Centro Internacional de Mejoramiento de Maiz y Trigo', 1, NULL, NULL),
(6, 'CIP', 'Centro Internacional de la Papa', 1, NULL, NULL),
(7, 'ICARDA', 'International Center for Agricultural Research in the Dry Areas', 1, NULL, NULL),
(8, 'ICRISAT', 'International Crops Research Institute for the Semi-Arid Tropics', 1, NULL, NULL),
(9, 'IFPRI', 'International Food Policy Research Institute', 1, NULL, NULL),
(10, 'IITA', 'International Institute of Tropical Agriculture', 1, NULL, NULL),
(11, 'ILRI', 'International Livestock Research Institute', 1, NULL, NULL),
(12, 'IRRI', 'International Rice Research Institute', 1, NULL, NULL),
(13, 'IWMI', 'International Water Management Institute', 1, NULL, NULL),
(14, 'ICRAF', 'World Agroforestry Centre', 1, NULL, NULL),
(15, 'WorldFish', 'WorldFish Center', 1, NULL, NULL),
(16, 'RPL EA', 'Eastern Africa', 2, 1, NULL),
(17, 'RPL LAM', 'Latin America', 2, 4, NULL),
(18, 'RPL SAs', 'South Asia', 2, 3, NULL),
(19, 'RPL SEA', 'South East Asia', 2, 5, NULL),
(20, 'RPL WA', 'West Africa', 2, 2, NULL),
(21, 'Theme 1', 'Adaptation to Progressive Climate Change', 3, NULL, 1),
(22, 'Theme 2', 'Adaptation through Managing Climate Risk', 3, NULL, 2),
(23, 'Theme 3', 'Pro-poor Climate Change Mitigation', 3, NULL, 3),
(24, 'Theme 4.1', 'Linking Knowledge with Action', 3, NULL, 4),
(25, 'Theme 4.2', 'Assemble Data and Tools for Analysis and Planning', 3, NULL, 4),
(26, 'Theme 4.3', 'Refine Frameworks for Policy Analysis', 3, NULL, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_objectives`
--

CREATE TABLE IF NOT EXISTS `activity_objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_partners`
--

CREATE TABLE IF NOT EXISTS `activity_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partner_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `contact_name` text,
  `contact_email` text,
  PRIMARY KEY (`id`),
  KEY `ap_partner_fk` (`partner_id`),
  KEY `ap_activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_partner_roles`
--

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

CREATE TABLE IF NOT EXISTS `activity_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `activity_status`
--

INSERT INTO `activity_status` (`id`, `name`) VALUES
(1, 'Complete'),
(2, 'Partially complete'),
(3, 'Incomplete');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity_validations`
--

CREATE TABLE IF NOT EXISTS `activity_validations` (
  `activity_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `activity_id_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `benchmark_sites`
--

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

--
-- Volcado de datos para la tabla `benchmark_sites`
--

INSERT INTO `benchmark_sites` (`id`, `bs_id`, `name`, `country_iso2`, `longitude`, `latitude`, `is_active`) VALUES
(1, 'KE01', 'Nyando (Katuk Odeyo)', 'ke', 35.022, -0.315, 1),
(2, 'KE02', 'Makueni (Wote)', 'ke', 37.326, -1.581, 1),
(3, 'UG01', 'Albertine Rift (Hoima)', 'ug', 31.501, 1.49, 1),
(4, 'UG02', 'Kagera Basin (Rakai)', 'ug', 31.44, -0.667, 1),
(5, 'TZ01', 'Usambara (Lushoto)', 'tz', 38.359, -4.82, 1),
(6, 'ET01', 'Borana (Yabero)', 'et', 38.43, 4.822, 1),
(7, 'BF01', 'Yatenga (Tougou)', 'bf', -2.252, 13.691, 1),
(8, 'GH01', 'Lawra-Jirapa (Lawra)', 'gh', -2.768, 10.596, 1),
(9, 'MA01', 'Segou (Cinzana)', 'ml', -5.762, 13.368, 1),
(10, 'NI01', 'Kollo (Fakara)', 'ne', 2.687, 13.517, 1),
(11, 'SE01', 'Kaffrine', 'sn', -15.54, 14.106, 1),
(12, 'BA01', 'Satkhira (Kalijang-Shyamnagar)', 'bd', 89.137, 22.4, 0),
(13, 'BA02', 'Satkhira (Sadar-Tala)', 'bd', 89.087, 22.4, 0),
(14, 'BA03', 'Khulna', 'bd', 86.374, 22.615, 0),
(15, 'BA04', 'Bagerhat (Morrelganj)', 'bd', 89.86, 22.506, 1),
(16, 'BA05', 'Jhalokathi', 'bd', 90.12, 22.4766, 0),
(17, 'BA06', 'Potuakhali', 'bd', 90.199, 22.05, 0),
(18, 'BA07', 'Cox’s Bazar', 'bd', 92, 21.62, 0),
(19, 'IN08', 'Bihta', 'in', 84.902, 25.54, 0),
(20, 'IN09', 'Piro', 'in', 84.39, 25.366, 0),
(21, 'IN10', 'Jamui', 'in', 86.202, 28.959, 0),
(22, 'IN11', 'Nautan', 'in', 84.204, 26.29, 0),
(23, 'IN12', 'Pusa', 'in', 85.73, 25.98, 0),
(24, 'IN13', 'Madhepura', 'in', 86.74, 25.96, 0),
(25, 'IN14', 'Katihar', 'in', 87.541, 25.562, 0),
(26, 'IN15', 'Punjab-Haryana', 'in', 75.921, 30.1855, 0),
(27, 'NE01', 'Sunsari', 'np', 87.224, 26.62, 0),
(28, 'NE02', 'Sarlahai', 'np', 85.62, 27, 0),
(29, 'NE03', 'Rupandehi', 'np', 83.45, 27.54, 1),
(30, 'NE04', 'Banke', 'np', 81.63, 28.139, 0),
(31, 'NE05', 'Kanchanpur', 'np', 80.365, 28.93, 0),
(32, 'IN16', 'Vaishali', 'in', 85.344, 25.7585, 1),
(33, 'IN07', 'Karnal', 'in', 76.938, 29.796, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bs_locations`
--

CREATE TABLE IF NOT EXISTS `bs_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bs_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `bs_fk` (`bs_id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `budget_percentages`
--

CREATE TABLE IF NOT EXISTS `budget_percentages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `percentage` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `budget_percentages`
--

INSERT INTO `budget_percentages` (`id`, `percentage`) VALUES
(1, '0% - 20%'),
(2, '20% - 40%'),
(3, '40% - 60%'),
(4, '60% - 80%'),
(5, '80% - 100%');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_studies`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_study_countries`
--

CREATE TABLE IF NOT EXISTS `case_study_countries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_study_id` int(11) NOT NULL,
  `country_iso2` varchar(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `csc_case_study_fk` (`case_study_id`),
  KEY `csc_country_fk` (`country_iso2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `case_study_types`
--

CREATE TABLE IF NOT EXISTS `case_study_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Volcado de datos para la tabla `case_study_types`
--

INSERT INTO `case_study_types` (`id`, `name`) VALUES
(1, 'Social differentiation and gender'),
(2, 'Successful communications'),
(3, 'Inter-center collaboration'),
(4, 'Innovative non-research partnerships'),
(5, 'Capacity enhancement'),
(6, 'Policy engagement'),
(7, 'Participatory action research '),
(8, 'Breakthrough science'),
(9, 'Food security');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `communications`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contact_person`
--

CREATE TABLE IF NOT EXISTS `contact_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `email` text,
  `activity_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cp_activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `countries`
--

CREATE TABLE IF NOT EXISTS `countries` (
  `iso2` varchar(2) NOT NULL,
  `name` text NOT NULL,
  `region_id` int(11) NOT NULL,
  PRIMARY KEY (`iso2`),
  KEY `region_fk` (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `countries`
--

INSERT INTO `countries` (`iso2`, `name`, `region_id`) VALUES
('ad', 'Andorra', 6),
('ae', 'United Arab Emirates', 6),
('af', 'Afghanistan', 3),
('ag', 'Antigua and Barbuda', 6),
('ai', 'Anguilla', 6),
('al', 'Albania', 6),
('am', 'Armenia', 6),
('an', 'Netherlands Antilles', 6),
('ao', 'Angola', 6),
('aq', 'Antarctica', 6),
('ar', 'Argentina', 4),
('as', 'American Samoa', 6),
('at', 'Austria', 6),
('au', 'Australia', 6),
('aw', 'Aruba', 6),
('az', 'Azerbaijan', 6),
('ba', 'Bosnia and Herzegovina', 6),
('bb', 'Barbados', 6),
('bd', 'Bangladesh', 3),
('be', 'Belgium', 6),
('bf', 'Burkina Faso', 2),
('bg', 'Bulgaria', 6),
('bh', 'Bahrain', 6),
('bi', 'Burundi', 6),
('bj', 'Benin', 2),
('bl', 'Saint Barthelemy', 6),
('bm', 'Bermuda', 6),
('bn', 'Brunei', 5),
('bo', 'Bolivia', 4),
('br', 'Brazil', 4),
('bs', 'Bahamas', 6),
('bt', 'Bhutan', 3),
('bw', 'Botswana', 6),
('by', 'Belarus', 6),
('bz', 'Belize', 6),
('ca', 'Canada', 6),
('cc', 'Cocos (Keeling) Islands', 6),
('cd', 'Democratic Republic of the Congo', 6),
('cf', 'Central African Republic', 6),
('cg', 'Republic of the Congo', 6),
('ch', 'Switzerland', 6),
('ci', 'Ivory Coast', 2),
('ck', 'Cook Islands', 6),
('cl', 'Chile', 4),
('cm', 'Cameroon', 6),
('cn', 'China', 6),
('co', 'Colombia', 4),
('cr', 'Costa Rica', 4),
('cu', 'Cuba', 4),
('cv', 'Cape Verde', 2),
('cx', 'Christmas Island', 6),
('cy', 'Cyprus', 6),
('cz', 'Czech Republic', 6),
('de', 'Germany', 6),
('dj', 'Djibouti', 6),
('dk', 'Denmark', 6),
('dm', 'Dominica', 6),
('do', 'Dominican Republic', 4),
('dz', 'Algeria', 6),
('ec', 'Ecuador', 4),
('ee', 'Estonia', 6),
('eg', 'Egypt', 6),
('eh', 'Western Sahara', 6),
('er', 'Eritrea', 6),
('es', 'Spain', 6),
('et', 'Ethiopia', 1),
('fi', 'Finland', 6),
('fj', 'Fiji', 6),
('fk', 'Falkland Islands', 6),
('fm', 'Micronesia', 6),
('fo', 'Faroe Islands', 6),
('fr', 'France', 6),
('ga', 'Gabon', 6),
('gb', 'United Kingdom', 6),
('gd', 'Grenada', 6),
('ge', 'Georgia', 6),
('gh', 'Ghana', 2),
('gi', 'Gibraltar', 6),
('gl', 'Greenland', 6),
('gm', 'Gambia', 2),
('gn', 'Guinea', 2),
('gq', 'Equatorial Guinea', 6),
('gr', 'Greece', 6),
('gt', 'Guatemala', 4),
('gu', 'Guam', 6),
('gw', 'Guinea-Bissau', 2),
('gy', 'Guyana', 6),
('hk', 'Hong Kong', 6),
('hn', 'Honduras', 4),
('hr', 'Croatia', 6),
('ht', 'Haiti', 4),
('hu', 'Hungary', 6),
('id', 'Indonesia', 5),
('ie', 'Ireland', 6),
('il', 'Israel', 6),
('im', 'Isle of Man', 6),
('in', 'India', 3),
('io', 'British Indian Ocean Territory', 6),
('iq', 'Iraq', 6),
('ir', 'Iran', 6),
('is', 'Iceland', 6),
('it', 'Italy', 6),
('je', 'Jersey', 6),
('jm', 'Jamaica', 6),
('jo', 'Jordan', 6),
('jp', 'Japan', 6),
('ke', 'Kenya', 1),
('kg', 'Kyrgyzstan', 6),
('kh', 'Cambodia', 5),
('ki', 'Kiribati', 6),
('km', 'Comoros', 6),
('kn', 'Saint Kitts and Nevis', 6),
('kp', 'North Korea', 6),
('kr', 'South Korea', 6),
('kw', 'Kuwait', 6),
('ky', 'Cayman Islands', 6),
('kz', 'Kazakhstan', 6),
('la', 'Laos', 5),
('lb', 'Lebanon', 6),
('lc', 'Saint Lucia', 6),
('li', 'Liechtenstein', 6),
('lk', 'Sri Lanka', 3),
('lr', 'Liberia', 2),
('ls', 'Lesotho', 6),
('lt', 'Lithuania', 6),
('lu', 'Luxembourg', 6),
('ly', 'Libya', 6),
('ma', 'Morocco', 6),
('mc', 'Monaco', 6),
('md', 'Moldova', 6),
('me', 'Montenegro', 6),
('mf', 'Saint Martin', 6),
('mg', 'Madagascar', 6),
('mh', 'Marshall Islands', 6),
('mk', 'Macedonia', 6),
('ml', 'Mali', 2),
('mm', 'Burma', 5),
('mn', 'Mongolia', 6),
('mo', 'Macau', 6),
('mp', 'Northern Mariana Islands', 6),
('mr', 'Mauritania', 2),
('ms', 'Montserrat', 6),
('mt', 'Malta', 6),
('mu', 'Mauritius', 6),
('mv', 'Maldives', 3),
('mw', 'Malawi', 6),
('mx', 'Mexico', 4),
('my', 'Malaysia', 5),
('mz', 'Mozambique', 6),
('na', 'Namibia', 6),
('nc', 'New Caledonia', 6),
('ne', 'Niger', 2),
('ng', 'Nigeria', 2),
('ni', 'Nicaragua', 4),
('nl', 'Netherlands', 6),
('no', 'Norway', 6),
('np', 'Nepal', 3),
('nr', 'Nauru', 6),
('nu', 'Niue', 6),
('nz', 'New Zealand', 6),
('om', 'Oman', 6),
('pa', 'Panama', 4),
('pe', 'Peru', 4),
('pf', 'French Polynesia', 6),
('pg', 'Papua New Guinea', 6),
('ph', 'Philippines', 5),
('pk', 'Pakistan', 3),
('pl', 'Poland', 6),
('pm', 'Saint Pierre and Miquelon', 6),
('pn', 'Pitcairn Islands', 6),
('pr', 'Puerto Rico', 6),
('pt', 'Portugal', 6),
('pw', 'Palau', 6),
('py', 'Paraguay', 4),
('qa', 'Qatar', 6),
('ro', 'Romania', 6),
('rs', 'Serbia', 6),
('ru', 'Russia', 6),
('rw', 'Rwanda', 6),
('sa', 'Saudi Arabia', 6),
('sb', 'Solomon Islands', 6),
('sc', 'Seychelles', 6),
('sd', 'Sudan', 6),
('se', 'Sweden', 6),
('sg', 'Singapore', 5),
('sh', 'Saint Helena', 6),
('si', 'Slovenia', 6),
('sj', 'Svalbard', 6),
('sk', 'Slovakia', 6),
('sl', 'Sierra Leone', 2),
('sm', 'San Marino', 6),
('sn', 'Senegal', 2),
('so', 'Somalia', 6),
('sr', 'Suriname', 6),
('st', 'Sao Tome and Principe', 6),
('sv', 'El Salvador', 4),
('sy', 'Syria', 6),
('sz', 'Swaziland', 6),
('tc', 'Turks and Caicos Islands', 6),
('td', 'Chad', 6),
('tg', 'Togo', 2),
('th', 'Thailand', 5),
('tj', 'Tajikistan', 6),
('tk', 'Tokelau', 6),
('tl', 'East Timor', 5),
('tm', 'Turkmenistan', 6),
('tn', 'Tunisia', 6),
('to', 'Tonga', 6),
('tr', 'Turkey', 6),
('tt', 'Trinidad and Tobago', 6),
('tv', 'Tuvalu', 6),
('tw', 'Taiwan', 6),
('tz', 'Tanzania', 1),
('ua', 'Ukraine', 6),
('ug', 'Uganda', 1),
('us', 'United States', 6),
('uy', 'Uruguay', 4),
('uz', 'Uzbekistan', 6),
('va', 'Holy See (Vatican City)', 6),
('vc', 'Saint Vincent and the Grenadines', 6),
('ve', 'Venezuela', 4),
('vg', 'British Virgin Islands', 6),
('vi', 'US Virgin Islands', 6),
('vn', 'Vietnam', 5),
('vu', 'Vanuatu', 6),
('wf', 'Wallis and Futuna', 6),
('ws', 'Samoa', 6),
('ye', 'Yemen', 6),
('yt', 'Mayotte', 6),
('za', 'South Africa', 6),
('zm', 'Zambia', 6),
('zw', 'Zimbabwe', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `country_locations`
--

CREATE TABLE IF NOT EXISTS `country_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_iso2` varchar(2) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `cl_country_fk` (`country_iso2`),
  KEY `cl_activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cs_types`
--

CREATE TABLE IF NOT EXISTS `cs_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `case_study_id` int(11) NOT NULL,
  `case_study_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `case_study_id` (`case_study_id`),
  KEY `case_study_type_id` (`case_study_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverables`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_formats`
--

CREATE TABLE IF NOT EXISTS `deliverable_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deliverable_id` int(11) NOT NULL,
  `file_format_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `file_format_id` (`file_format_id`),
  KEY `deliverable_id` (`deliverable_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_status`
--

CREATE TABLE IF NOT EXISTS `deliverable_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `deliverable_status`
--

INSERT INTO `deliverable_status` (`id`, `name`) VALUES
(1, 'Complete'),
(2, 'Partially complete'),
(3, 'Incomplete');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deliverable_types`
--

CREATE TABLE IF NOT EXISTS `deliverable_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `deliverable_types`
--

INSERT INTO `deliverable_types` (`id`, `name`) VALUES
(1, 'Data'),
(2, 'Capacity'),
(3, 'Communication products'),
(4, 'Model tools and software'),
(5, 'Reports, publications'),
(6, 'Workshops'),
(7, 'Other');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `file_formats`
--

CREATE TABLE IF NOT EXISTS `file_formats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Volcado de datos para la tabla `file_formats`
--

INSERT INTO `file_formats` (`id`, `name`) VALUES
(1, 'Plain text (*.txt)'),
(2, 'Presentation (*.ppt, *.odp)'),
(3, 'Video (*.avi, *.mpeg, etc)'),
(4, 'Blogpost'),
(5, 'Document (*.doc, *.odt, *.pdf)'),
(6, 'Database (*.sql, *.mdb, etc)'),
(7, 'GIS raster (ESRI Grids, GeoTiff, etc)'),
(8, 'Spreadsheet (*.xls, *.ods)'),
(9, 'Image (*.jpg, *.png, etc)'),
(10, 'GIS vector (shapefiles)'),
(11, 'Other');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gender_integrations`
--

CREATE TABLE IF NOT EXISTS `gender_integrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gi_activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicators`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicator_reports`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `indicator_types`
--

CREATE TABLE IF NOT EXISTS `indicator_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `indicator_types`
--

INSERT INTO `indicator_types` (`id`, `name`) VALUES
(1, 'Knowledge, tools, data'),
(2, 'Capacity enhancement and innovation platforms'),
(3, 'Technologies/practices in various stages of development'),
(4, 'Pollicies in varius stages of development'),
(5, 'Outcomes on the ground');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `keywords`
--

CREATE TABLE IF NOT EXISTS `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=103 ;

--
-- Volcado de datos para la tabla `keywords`
--

INSERT INTO `keywords` (`id`, `name`) VALUES
(1, 'adaptive capacity'),
(2, 'agroforestry'),
(3, 'banana'),
(4, 'beans'),
(5, 'breeding strategies'),
(6, 'carbon footprint'),
(7, 'carbon markets'),
(8, 'carbon sequestration'),
(9, 'cassava'),
(10, 'change in growing season'),
(11, 'changes in cropping systems'),
(12, 'climate change impacts'),
(13, 'climate information services'),
(14, 'climate modeling'),
(15, 'climate shocks'),
(16, 'climate-smart agriculture'),
(17, 'communicating climate change'),
(18, 'conservation agriculture'),
(19, 'crop breeding'),
(20, 'crop diversification'),
(21, 'crop modeling approaches'),
(22, 'crop vulnerability '),
(23, 'crop/livestock evaluation'),
(24, 'data and tools'),
(25, 'decision-making'),
(26, 'delivery of climate information'),
(27, 'desertification'),
(28, 'downscaling'),
(29, 'drought'),
(30, 'early warning'),
(31, 'economic incentives'),
(32, 'enabling policies'),
(33, 'environmental data'),
(34, 'erratic rainfall'),
(35, 'extreme weather events'),
(36, 'feasibility of mitigation practices'),
(37, 'fisheries'),
(38, 'floods'),
(39, 'food delivery'),
(40, 'forages'),
(41, 'gender'),
(42, 'germplasm evaluation'),
(43, 'GHG accounting'),
(44, 'GHG emissions reduction'),
(45, 'high-carbon landscapes'),
(46, 'household surveys'),
(47, 'improved forecasts'),
(48, 'improved varieties'),
(49, 'index-based insurance'),
(50, 'information networks'),
(51, 'knowledge sharing'),
(52, 'knowledge systems'),
(53, 'land use change'),
(54, 'land/soil management'),
(55, 'learning alliances'),
(56, 'livelihood diversification'),
(57, 'livestock insurance'),
(58, 'local knowledge'),
(59, 'maize'),
(60, 'measurement technologies'),
(61, 'meteorological data'),
(62, 'national adaptation programs'),
(63, 'negotiation tools'),
(64, 'pest and diseases changes'),
(65, 'pest and diseases modeling'),
(66, 'pests and diseases management'),
(67, 'planning tools'),
(68, 'policy analysis'),
(69, 'policy recommendations'),
(70, 'potato'),
(71, 'rangelands'),
(72, 'rice'),
(73, 'rural institutions'),
(74, 'safety nets'),
(75, 'scenario building'),
(76, 'sea level rise'),
(77, 'seasonal forecasting'),
(78, 'seed systems'),
(79, 'silvopastoralism'),
(80, 'social learning'),
(81, 'social return on investment'),
(82, 'socioeconomic data'),
(83, 'soil degradation'),
(84, 'sorghum'),
(85, 'spatial data'),
(86, 'suitability change'),
(87, 'sustainable intensification'),
(88, 'thermal stress'),
(89, 'vulnerability assessment'),
(90, 'water management'),
(92, 'weather based agro-advisories'),
(93, 'wheat'),
(94, 'yield changes'),
(95, 'baseline'),
(96, 'food security'),
(97, 'food availability'),
(98, 'food access'),
(99, 'food utilization'),
(100, 'community resources'),
(101, 'organizational landscape'),
(102, 'participatory assessment');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `leader_types`
--

CREATE TABLE IF NOT EXISTS `leader_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `leader_types`
--

INSERT INTO `leader_types` (`id`, `name`) VALUES
(1, 'CCAFS Center Led Activities'),
(2, 'CCAFS Region Led Activities'),
(3, 'CCAFS Theme Led Activities');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `leverages`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logframes`
--

CREATE TABLE IF NOT EXISTS `logframes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(4) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `logframes`
--

INSERT INTO `logframes` (`id`, `year`, `name`) VALUES
(1, 2011, 'Logframe 2011 - 2015'),
(2, 2012, 'Logframe 2012 - 2015'),
(3, 2013, 'Logframe 2013 - 2015'),
(4, 2014, 'Logframe 2014 - 2015');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestones`
--

CREATE TABLE IF NOT EXISTS `milestones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `year` int(4) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `output_fk` (`output_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=335 ;

--
-- Volcado de datos para la tabla `milestones`
--

INSERT INTO `milestones` (`id`, `output_id`, `code`, `year`, `description`) VALUES
(1, 1, '1.1.1 2012 (1)', 2012, 'Platform established for multi-location trials of technologies and genotypes for GxE interaction analysis and the calibration and evaluation of crop models'),
(2, 1, '1.1.1 2013', 2013, 'Tools and guidelines developed to support the selection (and / or maintenance) of the most appropriate water storage options and/ or their combinations for river basin development planning under conditions of increasing climate variability; options most likely to benefit or adversely affect marginal social groups including women assessed. Reviews of tools and guidelines, including links to individual guidelines and access to tools, with explicit recognition of gender and social differentiation'),
(3, 1, '1.1.1 2014', 2014, 'Analogue based evaluation and conservation of germplasm of at least 2 crops supported in a minimum of 6 analogue sites'),
(4, 1, '1.1.1 2015 (1)', 2015, 'One to five flagship technologies  that are gender-and socially-responsive identified, developed and demonstrated in each of the 3 initial target regions which would directly enhance the adaptive capacity of the farming systems to the climate change conditions. Launch through high level engagement with key stakeholders at a key international meeting'),
(5, 1, '1.1.1 2012 (2)', 2012, 'Robust method developed for calculating spatial and temporal analogue of climate. Partner co-authored peer-reviewable method developed and tested codes using pattern scaled HadCM3 climate output. Case studies conducted in at least 2 analogue sites in each region'),
(6, 1, '1.1.1 2015 (2)', 2015, 'Analogue Research results synthesized, documented, published and communicated at all levels.'),
(7, 1, '1.1.1 2012 (3)', 2012, 'Practices developed that enhance the efficiency of water use in aquaculture and small scale irrigation (eg, increased productivity per unit use of water; increased irrigable area with same amount of water); Time series differential productivity and irrigated area analysis. The social and gender implications of applying these practices assessed. '),
(8, 1, '1.1.1 2012 (4)', 2012, 'Assessment of the potential for exploitation of ground water for crop production in at least three basins'),
(9, 2, '1.1.2 2012', 2012, 'At least 10 countries capacitated to spatial and temporal analogues in EA, WA and IGP.  Training workshop(s) organized and videos produced on the use of the Analogue methodology (for examining both spatial and temporal analogues based on multiple climate projections, see milestone 1.1.1 2012 (2)). Engagement of key  IGP stakeholders  such as national universities, NARC, ICAR (DWR), BARC, NGOs; Farmer exchanges including at least 40% women convened among analogue sites integrating analysis of social, cultural and gender–disaggregated barriers to adaptation'),
(10, 2, '1.1.2 2013 (1)', 2013, 'New knowledge developed on (1) the potential application domains for agricultural and water management practices, technologies and policies (including maps), prioritized on the basis of their potential benefits for marginal social groups, especially women and (2) best means of transferring these technologies and ensuring their adoption to gender and socially-differentiated beneficiary groups; findings synthesized and presented in report and journal articles'),
(11, 2, '1.1.2 2014 (1)', 2014, 'Researchers and development agents trained on socially and gender-sensitive strategies for the conservation and use of local biodiversity within the climate change context'),
(12, 2, '1.1.2 2013 (2)', 2013, 'Research and development partners (especially female and young scientists) in at least 11 countries trained in using new monitoring and modelling tools for climate change adaptation for different crops including underutilized species; outcomes summarized in report'),
(13, 2, '1.1.2 2014 (2)', 2014, 'Gender-sensitive and socially differentiated strategies developed for conservation and use of local biodiversity within the climate change context; findings presented in strategy document, journal article'),
(14, 2, '1.1.2 2013 (3)', 2013, 'Capacities raised in at least 6 countries to assess the impacts of climate change on crops and identifying pro-poor and gender-responsive adaptation strategies at the subnational scale using crop models and gender-differentiated local knowledge (links with T4.2).  Additional case studies on climate analogues initiated in at least 12 more analogue sites'),
(15, 3, '1.1.3 2012 (1)', 2012, 'Approaches, methods and tools for gender and socially-sensitive participatory assessment of where and when biodiversity rich practices facilitate adaptation to climate change reviewed; findings summarized in report'),
(16, 3, '1.1.3 2013 (1)', 2013, 'Germplasm (wild and domesticated) with traits important for adapting to climate change and traits with potential benefits for different user groups conserved in local, national and regional ex situ collections and  made available to target users; findings presented in peer-reviewed journal articles and genebank reports; databases augmented'),
(17, 3, '1.1.3 2014 (1)', 2014, 'Accessions identified with potential adaptive traits for climate change adaptation for at least 5  crops using innovative methods and prioritized on the basis of traits with potential benefits for the poor and women users. Methodology to select genebank material adapted to local current climate conditions and future climate shifts developed and tested and crop suitability atlases for priority crops (as defined by fraction of total production accounted for) produced; findings presented in reports and journal articles'),
(18, 3, '1.1.3 2015 (1)', 2015, 'Assessment of the contribution of crop, livestock, fish diversity to climate change adaptation carried out; findings summarized in reports, case study narratives, including assessment of their importance to  marginalized farmers and women'),
(19, 3, '1.1.3 2012 (2)', 2012, 'Baseline survey and analysis of centers’ and partners’ acquisitions, and distributions of adapted germplasm carried out; Comparative survey and analysis conducted; findings summarized in reports.  '),
(20, 3, '1.1.3 2013 (2)', 2013, 'Guidelines for enhanced seed systems to accelerate adaptation and  for building up community-based, gender-responsive participatory monitoring of conservation and use of agricultural biodiversity at community level in the IGP region and East Africa produced and disseminated'),
(21, 3, '1.1.3 2014 (2)', 2014, 'Methods and tools for participatory, gender-responsive monitoring of deployment of biodiversity and knowledge by communities for climate change adaptation tested out in at least 5 countries (including gender-disaggregated community surveys); findings synthesized in report'),
(22, 3, '1.1.3 2015 (2)', 2015, 'Germplasm information on potential for climate change adaptation integrated in global information systems and accessible online.  (1) Databases of priority collections  augmented with georef erenced passport data and trait information useful to the diversity analysis for climate change impacts and adaptation effectively linked to global systems, (2) important trait information accessible in global systems, including GENEYSYS, and Crop Trait ontology augmented with traits of interest to Climate Change , (3) complementary data sources on wild species identified through GBIF, (4) training materials, (5) list of and information on newly and already collected germplasm (domesticated and wild) adapted to climate change; Materials of interest safely duplicated in Global Collection  and made available'),
(23, 3, '1.1.3 2013 (3)', 2013, 'Farmers’ traditional, gender-differentiated knowledge on use of diversity and climate change adaptation documented and made available in at least 3 countries; findings presented in databases, reports and peer- reviewed article'),
(24, 3, '1.1.3 2015 (3)', 2015, 'Case studies documented of potential role of informal seed systems for pro-poor and gender-responsive diffusion of adapted germplasm'),
(25, 3, '1.1.3 2013 (4)', 2013, 'Data gathered on how communities enhance conservation and use of local biodiversity within the climate change context, disaggregated by gender and other social strata; findings summarized in technical reports, factsheets and journal articles.'),
(26, 4, '1.2.1 2012', 2012, 'Crop breeding institutions coordinated in development of climate-proofed crops for a 2030-2050 world; Document written jointly by CCAFS and crop breeding institutions outlining coordinated plans for breeding.'),
(27, 4, '1.2.1 2013 (1)', 2013, 'Methodological framework developed for assessing the impact of new technologies which are adapted to climate change conditions including their potential for positive social and gender-responsive impact; suitable framework selected by partners / an international panel.'),
(28, 4, '1.2.1 2014', 2014, 'Set of “virtual crops” designed and assessed for their efficacy in addressing the likely future conditions in terms of the economic, social, and cultural benefits expected; findings presented in summary report and journal article. Engagement of ARI modelling groups (e.g. Leeds University), NARES scientists.'),
(29, 4, '1.2.1 2015 (1)', 2015, 'Detailed crop-by-crop strategies and plans of action for crop improvement developed, incorporating portfolio of national, regional and global priorities including those priorities relevant for pro-poor and gender –responsive targeting; findings presented in summary report.'),
(30, 4, '1.2.1 2013 (2)', 2013, 'Range of crop modeling approaches (to inform breeding) developed and evaluated for biotic and abiotic constraints for the period 2020 to 2050; findings presented in summary report and at key stakeholders'' meetings; *including modelling approaches to evaluate the impacts of climate change and the effects of adaptation technologies such as supplemental irrigation and water harvesting on water availability for crops and their productivity under decadal futures from 2020 to 2050. '),
(31, 4, '1.2.1 2015 (2)', 2015, 'Set of breeding strategies identified and widely shared with partners including funding bodies, national and international organizations, universities and other actors; findings presented in summary report and policy briefs (including percentage of total food crop production (in recent history)  accounted for by set of breeding strategies).'),
(32, 4, '1.2.1 2015 (3)', 2015, 'Climate change impact on key global commodities (major Musa groups,) and selected pest and diseases modeled and reviewed by commodity network country partners and possible response strategies identified.'),
(33, 5, '1.2.2 2015 (1)', 2015, 'High-level meetings held with key stakeholders resulting in mainstreaming of new breeding strategies that include attention to men’s and women’s crops in work plans and existing breeding programs.'),
(34, 5, '1.2.2 2015 (2)', 2015, 'Global, regional and national policy briefs produced to guide best-value investments in climate-proofed crop breeding initiatives with promise for pro-poor and gender-responsive impact and policy briefing meeting organized in 3 target regions.'),
(35, 6, '1.3.1 2012 (1)', 2012, 'Document produced that synthesizes institutional arrangements, policies and mechanisms for improving the adaptive capacity of agricultural sector actors (addresses what is working where, how and why, with disaggregation by gender and other social strata).'),
(36, 6, '1.3.1 2013', 2013, 'Socially and gender- differentiated knowledge developed on distribution of local seed material (seed systems) and its effectiveness in climate change adaptation strategies; findings summarized in reports, case study narratives and seed system maps.'),
(37, 6, '1.3.1 2014 (1)', 2014, 'Socially and gender- disaggregated participatory methods tested for grounding climate change model results to community-level decision making processes that address food security issues.'),
(38, 6, '1.3.1 2015', 2015, 'Roles of gender and different social groups in adaptation strategies for climate change analyzed in target countries and highlighted through fact sheets, project briefs and technical articles. Approaches, methods and outcomes of supportive interventions promoted through collaborative projects and shared with the broader stakeholder community through relevant meetings, conferences and journal articles.'),
(39, 6, '1.3.1 2012 (2)', 2012, 'Video testimonials produced on gender-specific farmer adaptation and mitigation strategies (including indigenous knowledge, coping mechanisms and current challenges) in 1-3 sites in each of the 3 initial target regions.'),
(40, 6, '1.3.1 2014 (2)', 2014, 'Community-based holistic adaptation options trialed in at least three sites, in order to understand the social (including gender), cultural, economic and institutional barriers to effective adaptation; outcomes presented in summary report. '),
(41, 7, '1.3.2 2012', 2012, 'Baseline national adaptation policy and plans evaluated in at least 5 target countries and published in a synthesis report and policy brief.'),
(42, 7, '1.3.2 2013', 2013, 'Regional training workshop on approaches and methods for evaluating cost/benefit of adaptation strategies on a national scale.'),
(43, 7, '1.3.2 2014', 2014, 'Sector specific adaptation strategies and plans produced based on socially and gender-differentiated adaptation options using cost/benefit analysis in at least 5 countries, results shared with key policy makers in target countries.'),
(44, 7, '1.3.2 2015 (1)', 2015, 'Synthesized lessons learned published in policy briefs and synthesis reports and papers on methods and approaches for prioritizing pro-poor and gender-responsive adaptation options within national adaptation plans.'),
(45, 7, '1.3.2 2015 (2)', 2015, 'Policy recommendations provided to national agencies, policy makers and key actors in the agricultural sector on how to target strategies to enable equitable access to breeding materials and strategies by different social groups (e.g. pastoralists, fishers, urban farmers) and by women and men.'),
(46, 8, '1.3.3 2013', 2013, 'Policy guidelines produced for centers and partners to address challenges associated with obtaining, using and distributing germplasm as part of climate change related research (with particular focus on addressing challenges associated with access and benefit sharing, IPR, biosafety policies and laws).'),
(47, 8, '1.3.3 2014', 2014, 'Technical contributions to international processes support the development of international policies enabling access to and use of genetic resources in climate change research and adaptation strategies.'),
(48, 8, '1.3.3 2015', 2015, 'Analysis of institutions and policies that impact on the flow of adapted materials through seed systems; National strategies developed to implement the International Treaty''s Multilateral system on Access and Benefit-Sharing  in 4 countries; Policy options produced at national, provincial and community levels and evaluated for their potential positive or adverse effects on socially marginal groups, especially women to improve existing policies, local management and seed systems to facilitate diffusion and uptake of adapted germplasm.'),
(49, 9, '2.1.1 2012', 2012, 'Synthesis of knowledge and priority knowledge gaps reported for three risk management innovations (livelihood diversification, index-based insurance, local traditional risk management strategies), with clear analysis of likely impacts across socially differentiated groups and gender.'),
(50, 9, '2.1.1 2013', 2013, 'Knowledge synthesis deepened, and best practice guidance reported and communicated for gender- and socially-differentiated climate risk management interventions; Climate-related risks and vulnerabilities to four key agricultural commodities and systems reported.'),
(51, 9, '2.1.1 2014', 2014, 'Knowledge synthesis products incorporated into best practice guidelines and research strategy that addresses social and gender equity; and communicated to development and policy stakeholders.'),
(52, 9, '2.1.1 2015', 2015, 'Lessons from CCAFS research on risk management innovations (livelihood diversification, index-based insurance, local traditional risk management strategies); and impacts across socially-differentiated groups and gender; synthesized, communicated widely, and incorporated into strategic planning and policy dialog.'),
(53, 10, '2.1.2 2012', 2012, 'Analytical framework reported, and household and intra-household-level modeling requirements specified for targeting and evaluating risk management interventions for climate-resilient rural livelihoods; Multi-scale structural modeling design for aggregate impacts of household and intra-household risk management changes; Evaluation and refinement of models for crop and water management in response to climate fluctuations.'),
(54, 10, '2.1.2 2013', 2013, 'Prototype household and intra-household modeling tools tested and adapted for evaluating impacts of climate risk and risk management interventions on rural livelihood resilience; Multi-scale structural model developed and tested; Models for crop and water management applied to climate risk and its management in 4 countries.'),
(55, 10, '2.1.2 2014', 2014, 'Methodology  for designing and targeting comprehensive and equitable agricultural risk management strategies implemented, documented and applied at 6 locations; Multi-scale structural model applied in 3 countries; Assessment framework to understand gender differences in climate risk perception and its influence on management.'),
(56, 10, '2.1.2 2015', 2015, 'Capacity to apply household, intra-household and multi-scale modeling to target and evaluate risk management innovations, enhanced through curriculum and training.'),
(57, 11, '2.1.3 2012 (1)', 2012, 'Gender- and socially-equitable participatory pilot demonstrations of portfolios of agricultural risk management innovations and traditional local knowledge established, applying consistent methodology for diagnosis, prioritization based on  potential benefits for different types of users and evaluation; and traditional local risk management strategies documented, in 5 countries in EA, WA and IGP.'),
(58, 11, '2.1.3 2013 (1)', 2013, 'Gender- and socially equitable participatory evaluation of risk management interventions, and report on gender and social equity of traditional risk management strategies and access to services at 2 locations in each of EA, WA and IGP; Information and video testimonials about farmers’ traditional risk management strategies exchanged via a web platform; Methodology and lessons from initial sites applied to 4 additional locations.'),
(59, 11, '2.1.3 2014 (1)', 2014, 'Quantitative evaluation of impact of risk management interventions on household and intra-household livelihood resilience initiated within pilot demonstrations in EA, WA and IGP; Potential up-scaling mechanisms identified and engaged; Global conference for cross-regional comparisons and lessons.'),
(60, 11, '2.1.3 2015 (1)', 2015, 'Quantitative evaluation of impact of risk management interventions on household and intra-household livelihood resilience in EA, WA and IGP reported; Lessons and evidence on climate risk management interventions and traditional, gender-differentiated local knowledge synthesized across participatory pilot demonstrations, reported, and shared through a web-based knowledge management platform; Development and policy stakeholders engaged to explore opportunities to scale up successful interventions.'),
(61, 11, '2.1.3 2012 (2)', 2012, 'Practices, technologies and production systems selected for demonstration based on assessment of their acceptability to and positive impact on welfare of different user groups. Gender- and socially equitable participatory demonstration and evaluation of impacts of promising production and NRM technologies, and production systems, on livelihood risk and resilience in the face of climate variability, initiated or continued in 5 countries.'),
(62, 11, '2.1.3 2013 (2)', 2013, 'Gender- and socially equitable participatory demonstration and evaluation of impacts of promising production and NRM  technologies, and production systems, on livelihood risk and resilience in the face of climate variability, deepened in 5 countries; Cross-site knowledge exchange activities conducted.'),
(63, 11, '2.1.3 2014 (2)', 2014, 'Results, evidence and lessons, from participatory, gender-sensitive evaluation of impacts of promising production and NRM technologies, and production systems, synthesized across locations, and shared through a web-based knowledge management platform.'),
(64, 11, '2.1.3 2015 (2)', 2015, 'Development and policy stakeholders engaged to apply lessons from participatory, gender-sensitive action research, target appropriate technologies and production systems, and explore opportunities to scale up technologies and systems with the greatest potential to enhance resilience to climate variability and change, equitably across socially-differentiated groups and gender.'),
(65, 11, '2.1.3 2012 (3)', 2012, 'Gender- and socially equitable participatory demonstration and evaluation of impacts of social capital, institutional and financial services, and policy interventions, on livelihood risk and resilience in the face of climate variability, initiated or continued in 5 countries.'),
(66, 11, '2.1.3 2013 (3)', 2013, 'Gender- and socially equitable participatory demonstration and evaluation of impacts of social capital, institutional  and financial services, and policy interventions, on livelihood risk and resilience in the face of climate variability, deepened in 5 countries; Cross-site knowledge-exchange activities conducted.'),
(67, 11, '2.1.3 2014 (3)', 2014, 'Results, evidence and lessons, from participatory, gender-sensitive evaluation of impacts of social capital, institutional and financial services, and policy interventions, synthesized across locations, and shared through a web-based knowledge management platform.'),
(68, 11, '2.1.3 2015 (3)', 2015, 'Development and policy stakeholders engaged to apply lessons from participatory, gender-sensitive action research, target appropriate interventions, and explore opportunities to scale up institutional and policy interventions with the greatest potential to enhance resilience to climate variability and change, equitably across socially-differentiated groups and gender.'),
(69, 12, '2.2.1 2012', 2012, 'National to global food system stakeholders engaged to identify and explore potential improved response strategies in the face of climate fluctuations; Impacts of climate variability on components (e.g., production, prices, rural incomes, consumption, trade, humanitarian assistance, ,social and gender equity) of food security reported, and policies to mediate impacts reviewed in EA, WA and IGP.'),
(70, 12, '2.2.1 2013', 2013, 'Pilot testing of food system response strategies in the face of climate fluctuations; Policy-oriented analysis of climate impacts on food security components, and gender- and socially-differentiated costs and benefits of alternative food security interventions, communicated with key food system stakeholders.'),
(71, 12, '2.2.1 2014', 2014, 'Evaluation  of national to global food system interventions in the face of climate fluctuations, and policy implications; Synthesis of knowledge and policy guidance on gender- and socially-differentiated impacts of food system climate risk management interventions and policies.'),
(72, 12, '2.2.1 2015', 2015, 'Up-scaling and mainstreaming results of research and evaluation related to constraints and opportunities posed by climate variability on food systems at national, regional, and global scales, including food delivery, trade, crisis response, post-crisis recovery, and social protection and their implications for different population segments including women and the poor.'),
(73, 13, '2.3.1 2012', 2012, 'Crop and rangeland production forecasting methodology review and platform design; Feasibility of reconstructing historic daily meteorological data required for agricultural modeling demonstrated and evaluated in two countries or regional institutions; Capacity on seasonal forecasting enhanced in 6 countries.'),
(74, 13, '2.3.1 2013', 2013, 'Historic gridded daily meteorological data sets developed and evaluated, and institutional capacity enhanced, in 2 countries or regional institutions; Prototype crop/rangeland forecasting tools developed; Accuracy of improved crop forecasting methods assessed and reported; Institutions in 2 regions engaged to implement crop forecasting tools tailored to priority crops and local needs.'),
(75, 13, '2.3.1 2014', 2014, 'Crop and rangeland production forecasting platform, documentation and training materials developed; Early warning systems developed for 2 major biological threats; Tools developed and institutional capacity enhanced to downscale seasonal forecasts for local agricultural decision-making in 2 countries or regional institutions.'),
(76, 13, '2.3.1 2015', 2015, 'Improved, downscaled seasonal forecast products, tailored to agricultural and food security decision-making, operational in 2 countries or regional institutions; Improved crop forecasting methodology operational in 2 countries or regional institutions.'),
(77, 14, '2.3.2 2012', 2012, 'Prototype gender- and socially-equitable climate information delivery mechanisms demonstrated and evaluated with representative socially and gender-differentiated user groups in rural communities at 2 locations each in EA, WA and IGP. Social and gender-differentiated demand for and use of different types of climate-information content and its presentation assessed and implications for design of delivery mechanisms identified.'),
(78, 14, '2.3.2 2013', 2013, 'Tested protocols for communicating climate information with representative socially and gender-differentiated user groups in rural communities, and capacity of communication intermediaries enhanced, at 2 locations each in EA, WA and IGP; Summary report on gender and social equity of climate information sources and delivery mechanisms and policy advice to enable equitable access.'),
(79, 14, '2.3.2 2014', 2014, 'Demonstration and evaluation of gender- and socially-equitable climate service delivery at 2 locations each in EA, WA, IGP, and new CCAFS regions; Up-scaling of improved climate information services demonstrated in one country or region.'),
(80, 14, '2.3.2 2015', 2015, 'Curriculum developed for intermediaries on overcoming gender and social inequities in communicating climate information; Roadmap for improving climate information services for agriculture and food security in three regions.'),
(81, 15, '3.1.1 2012', 2012, 'Analysis and frameworks for planning low carbon agricultural development and understanding  trade-offs, including ensembles of global integrated assessment models to examine food-energy trade-offs and social returns of investments in mitigation,  ex-ante impacts assessed of options with different trade-offs for men, women and the poor (ILRI- linked to T4, CIAT-Colombia, T3).'),
(82, 15, '3.1.1 2013', 2013, 'Research established on mitigation implications and trade-offs of agricultural development strategies in 3 countries including assessment of their likely  implications for socially and gender-differentiated target beneficiary  populations (T3).'),
(83, 15, '3.1.1 2014', 2014, 'Research implemented on mitigation implications and trade-offs of agricultural development strategies in three countries (T3).'),
(84, 15, '3.1.1 2015', 2015, 'Research findings shared on mitigation implications and trade-offs of alternative development strategies in three countries (T3).'),
(85, 16, '3.1.2 2012', 2012, 'Synthesis reports and data for IPCC and national and regional bodies on sectoral and cross-sectoral mitigation potentials: (i) livestock, agriculture and forestry (ILRI); (ii) aquaculture sector, analyzed through supply chain (WorldFish). Includes differentiation of livestock, crops, agroforestry and aquaculture systems of known importance to women and the poor. Includes capacity building of decision makers in inventories and use of appropriate tools and data in three initial regions (ILRI, ICRAF, T3). See also 3.3.1 2012-15 for sectoral data from on-farm trials.'),
(86, 16, '3.1.2 2013 (1)', 2013, 'Synthesis of sectoral mitigation potentials for IPCC and national and regional bodies covering 3 additional production systems, with a focus on intensification. Includes assessment of the implications of ntensification for different segments of the population  economically engaged in these production systems, including women and the poor. Includes analysis of agriculture vs. other sectors. Includes case study of reducing agricultural emissions at the national level for Colombia (CIAT).'),
(87, 16, '3.1.2 2013 (2)', 2013, 'Capacity building of decision makers and national stakeholders in use of appropriate tools, data and knowledge including gender analysis in two additional regions, Includes training on data and methods for carbon measurement and monitoring for integrated agricultural systems, i.e. landscapes, integrated agriculture, forestry, rangeland and livestock (ILRI, ICRAF, EA, WA, IGP).'),
(88, 16, '3.1.2 2014 (1)', 2014, 'Options for mitigation differentiating their potential benefits for different target beneficiary groups including women and the poor in each sector identified and shared with policy makers, researchers and actors in the sector through consultations, workshop, analysis and syntheses, including development of low carbon development plans (CIAT). Links to 3.3.1 2014 (2)'),
(89, 17, '3.2.1 2012 (1)', 2012, 'Review of economic incentives and benefits to socially and gender differentiated farmers and other stakeholders for adoption of integrated practices in two regions (conservation agriculture in rice-wheat systems in IGP, sustainable land management in maize-legume systems and agroforestry in EA). (CIMMYT, T3). Development of comparative framework. Linked to Milestone 3.3.1 (2013) and 3.3.1 2012-2015.'),
(90, 17, '3.2.1 2013', 2013, 'Analytical framework and field data collection on incentives and factors affecting socially and gender-disaggregated adoption of integrated practices in two regions, including potential for conservation agriculture in maize-legume systems in EA (CIMMYT). Linked to Milestones 3.2.1 2012 (1) and 3.3.1 2012-2015.'),
(91, 17, '3.2.1 2014', 2014, 'Synthesis reports, policy briefs, dissemination on increased adoption, economic incentives and benefits to men and women farmers for integrated mitigation practices (CIMMYT).'),
(92, 17, '3.2.1 2012 (2)', 2012, 'Synthesis and development of novel institutional options for mitigation payments to farmers which have been assessed for the potential distribution of their benefits among different social groups, including women and the poor: PES for livestock (ILRI), bundling of ecosystem services (IFPRI), national carbon offsets (WorldFish), carbon market project design in EA (T3).'),
(93, 18, '3.2.2 2012', 2012, 'Training for national policy makers, project implementers and communities on designing payments for carbon to benefit poor farmers and women (ICRAF- EA, WA, SEA).'),
(94, 18, '3.2.2 2013', 2013, 'Decision-makers in target regions better informed re policy options and gender implications for incentivizing and rewarding smallholders for GHG emission reductions.'),
(95, 18, '3.2.2 2014', 2014, 'Learning mechanisms and outreach to smallholder men and women farmers on high potential schemes for large- scale impact (CIAT).'),
(96, 19, '3.3.1 2012 (1)', 2012, 'Assessment of feasibility and impacts of mitigation practices on farms and different social groups within the rural population, including women and the poor for (i) conservation agriculture, sustainable land management and irrigated farming systems in rice-wheat and maize-legume systems in 3 target regions (CIMMYT, IFPRI), (ii) improved irrigation and fertilizer management of rice and the viability of region- specific approaches for CDM (IRRI), (iii) dryland cropping systems (ICRISAT),  (iv) agroforestry and complex agro-ecosystems (ICRAF), (v) livestock (ILRI), (vi) potatoes and sweet potatoes (CIP), (vii) coffee and cacao agroforestry in EA and WA (IITA, ICRAF, CIAT) at crop system and landscape scales, (viii) biochar (IFPRI and ICRAF), (ix) cereal biomass production and SOC of contour ridge tillage (ICRISAT), (x) pasture (CIAT), (xi) low-input fruit systems (CIAT) and (xii) coffee systems for Central America (CIAT), (xiii) land use change, land rehabilitation and poplar agroforestry, (xiv) oil palm (CIFOR). Linked to 3.3.2 2013.'),
(97, 19, '3.3.1 2013 (1)', 2013, 'Assessment of feasibility and impacts for mitigation practices on farms (i) soil carbon dynamics under different management practices (CIMMYT), (ii) biochar (IFPRI and ICRAF), (iii) agro- silvi- horti- pastoral farming systems in India (ICRAF), (iv) conducive settings for CDM in rice production through improved irrigation and fertilizer management (IRRI), (vi) sweet potato and potato systems (CIP), (vii) coffee and cocoa agroforestry (IITA), (viii) pasture and low-input fruit systems (CIAT); (ix) coffee and cocoa systems at landscape level (CIAT); (x) land use change, land rehabilitation and poplar agroforestry (CIFOR), (xi) smallholder biofuel production (ICRAF). Ex-ante assessment conducted of socio-economic impacts of selected mitigation practices and the potential distribution of benefits across different social groups, including women and the poor.'),
(98, 19, '3.3.1 2014 (1)', 2014, 'Impact and trade-off analysis of farm management strategies and adoption at the regional scale, including C sequestration and N management in CA-based intensification in rice-wheat and maize-legume systems (CIMMYT); analysis of biomass for efficient pyrolysis liquid fuel production (ICRAF), low-input fruit production (CIAT); coffee and cocoa systems at landscape level (CIAT); land use change, land rehabilitation and poplar agroforestry (CIFOR).'),
(99, 19, '3.3.1 2015', 2015, 'Synthesis of results from applying framework and tools for assessing feasibility and impacts.'),
(100, 19, '3.3.1 2012 (2)', 2012, 'Options for low climate impact sustainable agricultural intensification identified, tested and documented in at least 6 countries and tested on farms in at least 2 sites in each region: conservation agriculture, water management, agroforestry, sustainable land management, fertilizer micro-dosing (EA, WA, IGP). Linked to T1.'),
(101, 19, '3.3.1 2013 (2)', 2013, 'Analysis of the impacts of on-farm and landscape level mitigation practices in EA and one other region, including impacts on women and other marginalized farmers (CIMMYT).'),
(102, 19, '3.3.1 2014 (2)', 2014, 'Learning and exchange among national agencies to review farm-level mitigation options and their socially and gender-differentiated impacts. Links to 3.1.2 2014 (2).'),
(103, 20, '3.3.2 2012', 2012, 'Review of methods for the quantification of agricultural greenhouse gases for smallholders, including needs of men and women users and recommendations for improvement; includes case study of methodology development for carbon analysis in landscapes and coffee farming systems in EA (T3, ICRAF).'),
(104, 20, '3.3.2 2013', 2013, 'Methods for GHG emissions and carbon measurement and monitoring for integrated agricultural systems and resulting data (complex landscapes, irrigated IGP agroecosystems, integrated agriculture, agroforestry, forestry and aquaculture, rangeland and livestock) (CIMMYT, ICRAF, WorldFish, ICARDA, ILRI); includes assessing feasibility of an all-GHG analysis. Linked to 3.3.1 2012-15.'),
(105, 20, '3.3.2 2014', 2014, 'Monitoring guidelines for smallholder agriculture in developing countries produced and contributing to global standards (CIFOR).'),
(106, 20, '3.3.2 2015', 2015, 'Workshop with standard setting bodies (VCS, ACR, etc) to share methods synthesis guidelines for smallholder agriculture in developing countries. '),
(107, 21, '3.3.3 2012', 2012, 'Assessment reports on technical and institutional capacity for national-level measurement and monitoring in 3 target countries (T3). Network of practice established for C sequestration in rangelands for Africa.'),
(108, 21, '3.3.3 2013', 2013, 'Working groups established in three regions to develop methods for national and project management and MRV of greenhouse gas emissions and their landscape-level implications, while strengthening the capacity of national institutions  (EA, WA, IGP).'),
(109, 21, '3.3.3 2014', 2014, 'Regional working groups test and refine monitoring and measurement methods).'),
(110, 21, '3.3.3 2015', 2015, 'Regional working groups build capacity in national entities.'),
(111, 22, '4.1.1 2012', 2012, 'Three regional teams produce prototype scenarios and vulnerability targeting products that are used in visioning and strategy exercises with key policymakers, private sector and NGOs.'),
(112, 22, '4.1.1 2013', 2013, 'Regional scenarios finalized in a process that includes developing capacity in regional agencies and other key stakeholders.'),
(113, 23, '4.1.2 2012', 2012, 'Participatory Action Research process established in 13 sites and gender-sensitive activities related to risk management, adaptation and mitigation implemented, with engagement and communication strategies aimed at users of the knowledge generated pursued, and scaling up mechanisms in place.'),
(114, 23, '4.1.2 2013', 2013, 'First results from PAR, including gender analyses, are published and fed into national and regional policy processes.'),
(115, 23, '4.1.2 2014', 2014, 'Cross-site visits and scaling up of PAR approaches and synthesis of insights widely disseminated into regional and national policy processes; with explicit recognition of social differentiation and gender. '),
(116, 23, '4.1.2 2015', 2015, 'Synthesis of knowledge on men and women farmers’ adaptation strategies disseminated and fed into adaptation and mitigation strategies and national policies and economic development plans in at least 3 regions.'),
(117, 24, '4.1.3 2012', 2012, 'Tools for engagement to ensure gender and pro-poor outcomes, communication approaches and tools for understanding climate change-gender relationships tested and disseminated.'),
(118, 24, '4.1.3 2013', 2013, 'Syntheses and other joint partner communication products based on findings from CCAFS work with women and marginalized groups.'),
(119, 24, '4.1.3 2014', 2014, 'Regional capacity in gender and climate change action research developed in at least 3 regions, and partner institutions implementing more gender and pro-poor targeted activities.'),
(120, 25, '4.1.4 2012 (1)', 2012, 'Partner-led engagement and communication approaches, knowledge networks, and capacity of farmers’ organizations, government and regional organization partners’ strengthened for mainstreaming CCAFS-related, gender disaggregated research evidence.'),
(121, 25, '4.1.4 2013 (1)', 2013, 'Support to negotiators, civil society and government agencies to fully contribute to the UNFCCC work program on agriculture, with explicit support to marginalized groups to build their capacity to participate in policy development to improve food security; Assessment of effectiveness of CCAFS learning approach and utilization by a diverse range of partners of CCAFS-generated knowledge.'),
(122, 25, '4.1.4 2014 (1)', 2014, 'Support to regional and global processes to clarify the ecological footprint of agriculture and the ways it can be reduced, without compromising poverty and equity objectives; and building the links to the post Rio+20 process.'),
(123, 25, '4.1.4 2015', 2015, 'Network further expanded to help inform national, regional and global players of the opportunities for a UNFCCC Agreement on Agriculture and Climate Change, options developed for national policy processes.'),
(124, 25, '4.1.4 2012 (2)', 2012, 'Local institutional capacity strengthened in land health surveillance methods including soil carbon measurement in CCAFS regions; Scoping studies undertaken on linking landscape-level land health and carbon measures with socioeconomic data from CCAFS baselines and other site studies.'),
(125, 25, '4.1.4 2013 (2)', 2013, 'Synthesis and research reports developed on lessons from linking land health and soil carbon measures with socioeconomic information from CCAFS sites; Local institutional capacity strengthened in land health surveillance methods including soil carbon measurement in additional regions.'),
(126, 25, '4.1.4 2014 (2)', 2014, 'Land health and soil carbon measurement being used by partners to inform national and regional land, agriculture and climate change policies.'),
(127, 26, '4.2.1 2012 (1)', 2012, 'Regional site characterization and gender-disaggregated baseline data collection completed and initial analyses in three target regions at three levels: household, village, and institution.'),
(128, 26, '4.2.1 2013 (1)', 2013, 'Regional site characterizations and baseline data synthesized in cross-regional comparisons.'),
(129, 26, '4.2.1 2014 (1)', 2014, 'Regional site characterizations and baseline data initiated and initial analyses in two additional target regions at three levels: household, village, and institution.'),
(130, 26, '4.2.1 2015', 2015, 'Second round of baseline surveys implemented in three initial target regions, revisiting the same sites as the first round and initial analyses across time.'),
(131, 26, '4.2.1 2012 (2)', 2012, 'Downscaled climate data and methods tested and harmonized.'),
(132, 26, '4.2.1 2013 (2)', 2013, 'Downscaled climate data and methods available for application; and regional climate characterization and evaluation of global and regional climate model performance for two additional target regions.'),
(133, 26, '4.2.1 2014 (2)', 2014, 'Downscaled climate data and methods are being applied in CCAFS regions.'),
(134, 26, '4.2.1 2012 (3)', 2012, 'First sets of databases,  database tools, and meta-data on agricultural impact models collated and/or developed and made available, to enable stakeholders to assess impacts and evaluate options (including soil profile descriptions; global ag system classification; global cropland extent).'),
(135, 26, '4.2.1 2013 (3)', 2013, 'Databases and tools further elaborated and managed to enable stakeholders to assess impacts and evaluate options, with additional data layers added for pastureland; weather data; household level agricultural systems data.'),
(136, 26, '4.2.1 2014 (3)', 2014, 'Based on interaction with and feedback from users, the second generation of databases and tools designed, managed and applied to enable stakeholders to assess impacts and evaluate options.'),
(137, 26, '4.2.1 2012 (4)', 2012, 'Scoping of innovative decadal/near-term climate products to improve near-term climate prediction.'),
(138, 26, '4.2.1 2014 (4)', 2014, 'Innovative decadal/near-term climate products developed to improve near-term climate prediction.'),
(139, 26, '4.2.1 2012 (5)', 2012, 'Assessment toolkit components developed to analyze likely effects of specific adaptation and mitigation options in target regions, with a focus on rangelands, vulnerability assessment at sub-national levels.'),
(140, 26, '4.2.1 2013 (5)', 2013, 'Assessment toolkit components further evaluated, refined  and used to analyze likely effects of specific adaptation and mitigation options in target regions, with a focus on household and intra-household model data and testing and intercomparison of global and regional integrated assessment models.'),
(141, 26, '4.2.1 2014 (5)', 2014, 'Assessment toolkit components utilized in target regions; high-level engagement with key users initiated to build capacity in use of tools and data.'),
(142, 27, '4.2.2 2012', 2012, 'Partnership and strategy development for targeting decision support tools.'),
(143, 27, '4.2.2 2013', 2013, 'Decision aids developed in selected sites in 3 initial target regions that build on the information needs of socially- and gender differentiated target groups.'),
(144, 27, '4.2.2 2014', 2014, 'Decision aids tested in selected sites in target regions that build on the information needs of socially- and gender differentiated target groups. '),
(145, 27, '4.2.2 2015', 2015, 'Collation of decision aids and tools for prioritizing adaptation and mitigation actions at national/sub-national scales, with pilot testing in IGP region.'),
(146, 28, '4.3.1 2012 (1)', 2012, 'Land use modeling and aquaculture (WorldFish activity) added to the IMPACT model and model intercomparisons with other major global model undertaken.'),
(147, 28, '4.3.1 2013 (1)', 2013, 'Soft linkages between IMPACT and CGE models completed. Intercomparisons of livestock-related components of global modeling completed (ILRI).'),
(148, 28, '4.3.1 2014 (1)', 2014, 'Land use, hydrology and CGE model linkages developed.'),
(149, 28, '4.3.1 2012 (2)', 2012, 'Analysis and report for the United Nations Committee on Food Security (CFS) on Food Security and Climate Change.'),
(150, 28, '4.3.1 2013 (2)', 2013, 'National and regional studies complementary to the global mitigation study. Review national policies relevant to agroforestry in 3 West African countries, with goal of identifying policy changes that could enhance adaptation and mitigation (ICRAF). Identification of the role of aquaculture and fisheries in climate change adaptation and support for inclusion in revision of NAPAS in 3 countries in the CCAFS regions (WorldFish).'),
(151, 28, '4.3.1 2014 (2)', 2014, 'Report on new technologies and international policies to support their development for socially inclusive and gender-responsive adaptation and mitigation.'),
(152, 29, '4.3.2 2012', 2012, 'National and regional studies complementary to the CFS global study.'),
(153, 29, '4.3.2 2013', 2013, 'National and regional studies complementary to the global mitigation study. Review completed of national policies relevant to agroforestry in 3 West African countries, with goal of identifying policy changes that could enhance adaptation and mitigation highlighting potential for positive benefits for socially marginal groups and women (ICRAF). Identification of the role of aquaculture and fisheries in climate change adaptation and support for. inclusion in revision of NAPAS in 3 countries in the CCAFS regions (WorldFish).'),
(154, 29, '4.3.2 2014', 2014, 'National and regional studies complementary to the global technology policy study.'),
(155, 30, '4.3.3 2012', 2012, 'Activities held at CGIAR, NARS, and international organizations to build capacity to utilize the modeling tools developed under milestone 4.3.1. 2012.'),
(156, 30, '4.3.3 2014', 2014, 'Activities held at NARS, and international organizations to build capacity to utilize the modeling tools developed under milestone 4.3.1 2013.'),
(157, 31, '1.1.1 2013', 2013, 'Tools and guidelines developed to support the selection (and / or maintenance) of the most appropriate water storage options and/ or their combinations for river basin development planning under conditions of increasing climate variability; options most likely to benefit or adversely affect marginal social groups including women assessed. Reviews of tools and guidelines, including links to individual guidelines and access to tools, with explicit recognition of gender and social differentiation.'),
(158, 31, '1.1.1 2014', 2014, 'Analogue based evaluation and conservation of germplasm of at least 2 crops supported in a minimum of 6 analogue sites.'),
(159, 31, '1.1.1 2015 (1)', 2015, 'One to five flagship technologies  that are gender-and socially-responsive identified, developed and demonstrated in each of the 3 initial target regions which would directly enhance the adaptive capacity of the farming systems to the climate change conditions. Launch through high level engagement with key stakeholders at a key international meeting.'),
(160, 31, '1.1.1 2015 (2).', 2015, 'Analogue Research results synthesized, documented, published and communicated at all levels'),
(161, 31, '1.1.1 2012 (3).', 2012, 'Practices developed that enhance the efficiency of water use in aquaculture and small scale irrigation (eg, increased productivity per unit use of water; increased irrigable area with same amount of water); Time series differential productivity and irrigated area analysis. The social and gender implications of applying these practices assessed.'),
(162, 32, '1.1.2 2013 (1).', 2013, 'New knowledge developed on (1) the potential application domains for agricultural and water management practices, technologies and policies (including maps), prioritized on the basis of their potential benefits for marginal social groups, especially women and (2) best means of transferring these technologies and ensuring their adoption to gender and socially-differentiated beneficiary groups; findings synthesized and presented in report and journal articles'),
(163, 32, '1.1.2 2014 (1).', 2014, 'Researchers and development agents trained on socially and gender-sensitive strategies for the conservation and use of local biodiversity within the climate change context.'),
(164, 32, '1.1.2 2013 (2).', 2013, 'Research and development partners (especially female and young scientists) in at least 11 countries trained in using new monitoring and modelling tools for climate change adaptation for different crops including underutilized species; outcomes summarized in report'),
(165, 32, '1.1.2 2014 (2).', 2014, 'Gender-sensitive and socially differentiated strategies developed for conservation and use of local biodiversity within the climate change context; findings presented in strategy document, journal article.');
INSERT INTO `milestones` (`id`, `output_id`, `code`, `year`, `description`) VALUES
(166, 32, '1.1.2 2013 (3).', 2013, 'Capacities raised in at least 6 countries to assess the impacts of climate change on crops and identifying pro-poor and gender-responsive adaptation strategies at the subnational scale using crop models and gender-differentiated local knowledge (links with T4.2).  Additional case studies on climate analogues initiated in at least 12 more analogue sites.'),
(167, 33, '1.1.3 2013 (1).', 2013, 'Germplasm (wild and domesticated) with traits important for adapting to climate change and traits with potential benefits for different user groups conserved in local, national and regional ex situ collections and  made available to target users; findings presented in peer-reviewed journal articles and genebank reports; databases augmented '),
(168, 33, '1.1.3 2014 (1).', 2014, 'Accessions identified with potential adaptive traits for climate change adaptation for at least 5  crops using innovative methods and prioritized on the basis of traits with potential benefits for the poor and women users. Methodology to select genebank material adapted to local current climate conditions and future climate shifts developed and tested and crop suitability atlases for priority crops (as defined by fraction of total production accounted for) produced; findings presented in reports and journal articles '),
(169, 33, '1.1.3 2015 (1).', 2015, 'Assessment of the contribution of crop, livestock, fish diversity to climate change adaptation carried out; findings summarized in reports, case study narratives, including assessment of their importance to  marginalized farmers and women.  '),
(170, 33, '1.1.3 2014 (2).', 2014, 'Methods and tools for participatory, gender-responsive monitoring of deployment of biodiversity and knowledge by communities for climate change adaptation tested out in at least 5 countries (including gender-disaggregated community surveys); findings synthesized in report  '),
(171, 33, '1.1.3 2015 (2).', 2015, 'Germplasm information on potential for climate change adaptation integrated in global information systems and accessible online.  (1) Databases of priority collections  augmented with georef erenced passport data and trait information useful to the diversity analysis for climate change impacts and adaptation effectively linked to global systems, (2) important trait information accessible in global systems, including GENEYSYS, and Crop Trait ontology augmented with traits of interest to Climate Change , (3) complementary data sources on wild species identified through GBIF, (4) training materials, (5) list of and information on newly and already collected germplasm (domesticated and wild) adapted to climate change; Materials of interest safely duplicated in Global Collection  and made available.'),
(172, 33, '1.1.3 2013 (2).', 2013, 'Farmers’ traditional, gender-differentiated knowledge on use of diversity and climate change adaptation documented and made available in at least 3 countries; findings presented in databases, reports and peer- reviewed article '),
(173, 33, '1.1.3 2015 (3).', 2015, 'Case studies documented of potential role of informal seed systems for pro-poor and gender-responsive diffusion of adapted germplasm '),
(174, 33, '1.1.3 2013 (3).', 2013, 'Data gathered on how communities enhance conservation and use of local biodiversity within the climate change context, disaggregated by gender and other social strata; findings summarized in technical reports, factsheets and journal articles '),
(175, 34, '1.2.1 2013 (1).', 2013, 'Methodological framework developed for assessing the impact of new technologies which are adapted to climate change conditions including their potential for positive social and gender-responsive impact; suitable framework selected by partners / an international panel. '),
(176, 34, '1.2.1 2014.', 2014, 'Set of “virtual crops” designed and assessed for their efficacy in addressing the likely future conditions in terms of the economic, social, and cultural benefits expected; findings presented in summary report and journal article. Engagement of ARI modelling groups (e.g. Leeds University), NARES scientists. '),
(177, 34, '1.2.1 2015 (1).', 2015, 'Detailed crop-by-crop strategies and plans of action for crop improvement developed, incorporating portfolio of national, regional and global priorities  including those priorities relevant for pro-poor and gender –responsive targeting; findings presented in summary report. '),
(178, 34, '1.2.1 2013 (2).', 2013, 'Range of crop modeling approaches (to inform breeding) developed and evaluated for biotic and abiotic constraints for the period 2020 to 2050; findings presented in summary report and at key stakeholders'' meetings ; *including modelling approaches to evaluate the impacts of climate change and the effects of adaptation technologies such as supplemental irrigation and water harvesting on water availability for crops and their productivity under decadal futures from 2020 to 2050. '),
(179, 34, '1.2.1 2015 (2).', 2015, 'Set of breeding strategies identified and widely shared with partners including funding bodies, national and international organizations, universities and other actors; findings presented in summary report and policy briefs (including percentage of total food crop production (in recent history)  accounted for by set of breeding strategies).  '),
(180, 34, '1.2.1 2015 (3).', 2015, 'Climate change impact on key global commodities (major Musa groups,) and selected pest and diseases modeled and reviewed by commodity network country partners and possible response strategies identified. '),
(181, 35, '1.2.2 2015 (1).', 2015, 'High-level meetings held with key stakeholders resulting in mainstreaming of new breeding strategies that include attention to men’s and women’s crops in work plans and existing breeding programs '),
(182, 35, '1.2.2 2015 (2).', 2015, 'Global, regional and national policy briefs produced to guide best-value investments in climate-proofed crop breeding initiatives with promise for pro-poor and gender-responsive impact and policy briefing meeting organized in 3 target regions  '),
(183, 36, '1.3.1 2013.', 2013, 'Socially and gender- differentiated knowledge developed on distribution of local seed material (seed systems) and its effectiveness in climate change adaptation strategies; findings summarized in reports, case study narratives and seed system maps. '),
(184, 36, '1.3.1 2014 (1).', 2014, 'Socially and gender- disaggregated participatory methods tested for grounding climate change model results to community-level decision making processes that address food security issues'),
(185, 36, '1.3.1 2015.', 2015, 'Roles of gender and different social groups in adaptation strategies for climate change analyzed in target countries and highlighted through fact sheets, project briefs and technical articles. Approaches, methods and outcomes of supportive interventions promoted through collaborative projects and shared with the broader stakeholder community through relevant meetings, conferences and journal articles '),
(186, 36, '1.3.1 2014 (2).', 2014, 'Community-based holistic adaptation options trialed in at least three sites, in order to understand the social (including gender), cultural, economic and institutional barriers to effective adaptation; outcomes presented in summary report.'),
(187, 37, '1.3.2 2013.', 2013, 'Regional training workshop on approaches and methods for evaluating cost/benefit of adaptation strategies on a national scale. '),
(188, 37, '1.3.2 2014. ', 2014, 'Sector specific adaptation strategies and plans produced based on socially and gender-differentiated adaptation options using cost/benefit analysis in at least 5 countries, results shared with key policy makers in target countries.'),
(189, 37, '1.3.2 2015 (1).', 2015, 'Synthesized lessons learned published in policy briefs and synthesis reports and papers on methods and approaches for prioritizing pro-poor and gender-responsive adaptation options within national adaptation plans.'),
(190, 37, '1.3.2 2015 (2).', 2015, 'Policy recommendations provided to national agencies, policy makers and key actors in the agricultural sector on how to target strategies to enable equitable access to breeding materials and strategies by different social groups (e.g. pastoralists, fishers, urban farmers) and by women and men. '),
(191, 38, '1.3.3 2013.', 2013, 'Policy guidelines produced for centers and partners to address challenges associated with obtaining, using and distributing germplasm as part of climate change related research (with particular focus on addressing challenges associated with access and benefit sharing, IPR, biosafety policies and laws).'),
(192, 38, '1.3.3 2014.', 2014, 'Technical contributions to international processes support the development of international policies enabling access to and use of genetic resources in climate change research and adaptation strategies; '),
(193, 38, '1.3.3 2015.', 2015, 'Analysis of institutions and policies that impact on the flow of adapted materials through seed systems; National strategies developed to implement the International Treaty''s Multilateral system on Access and Benefit-Sharing  in 4 countries; Policy options produced at national, provincial and community levels and evaluated for their potential positive or adverse effects on socially marginal groups, especially women to improve existing policies, local management and seed systems to facilitate diffusion and uptake of adapted germplasm.  '),
(194, 39, '2.1.1 2013.', 2013, 'Knowledge synthesis deepened, reported and communicated for three gender- and socially-equitable climate risk management interventions; Climate-related risks and vulnerabilities to four key agricultural commodities and /or systems reported.'),
(195, 39, '2.1.1 2014.', 2014, 'Knowledge synthesis products incorporated into good practice guidelines and research strategy that addresses social and gender equity; and communicated to development and policy stakeholders; Analyses of climate-related vulnerabilities of 4 key agricultural commodities and/or systems '),
(196, 39, '2.1.1 2015.', 2015, 'Lessons from CCAFS research on risk management innovations, and impacts across socially-differentiated groups and gender, synthesized, communicated widely, and incorporated into strategic planning and policy dialog.'),
(197, 40, '2.1.2 2013.', 2013, 'Household modeling tools tested and adapted for evaluating impacts of climate risk and risk management interventions on rural livelihood resilience in 2 countries; Models for crop and water management applied to climate risk and its management in 4 countries.'),
(198, 40, '2.1.2 2014.', 2014, 'Methodology for designing and targeting comprehensive and equitable agricultural risk management strategies implemented, documented and applied at 6 locations; Two model-based climate risk management decision support tool prototypes developed; Assessment framework to understand gender differences in climate risk perception and risk management.'),
(199, 40, '2.1.2 2015.', 2015, 'Capacity to apply household, and intra-household modeling to target and evaluate risk management innovations, enhanced through curriculum and training. Use of model-based decision support tools for local climate risk management demonstrated in 4 countries.'),
(200, 41, '2.1.3 2013 (1).', 2013, 'Gender- and socially-equitable participatory evaluation of risk management interventions, at 2 locations in each of EA, WA and SA; Assessment of transferability and equity of traditional risk management strategies and access to services at 2 locations each in EA, WA and SA; Methodology guidelines for participatory evaluation of climate risk management strategies and their gender and social equity; Methodology and lessons from initial sites applied to 4 additional locations.'),
(201, 41, '2.1.3 2014 (1).', 2014, 'Quantitative evaluation of impact of risk management interventions on household and intra-household livelihood resilience initiated within pilot demonstrations in EA, WA and SA; Potential up-scaling mechanisms and partners identified and engaged in EA, WA and SA; Participatory pilot demonstrations initiated at 2 locations each in SEA and LA.'),
(202, 41, '2.1.3 2015 (1).', 2015, 'Quantitative evaluation of impact of risk management interventions on household and intra-household livelihood resilience in EA, WA and IGP reported; Quantitative evaluation of impact of climate risk management interventions on household and intra-household livelihood resilience initiated within pilot demonstrations in SEA and LA; Lessons and evidence on gender- and socially-equitable climate risk management interventions synthesized across participatory pilot demonstrations, reported, and shared; Conference for cross-regional comparisons and lessons; Development and policy stakeholders engaged to explore opportunities to scale up successful interventions.'),
(203, 41, '2.1.3 2013 (2).', 2013, 'Gender- and socially equitable participatory demonstration and evaluation of impacts of promising production and NRM technologies, and production systems, on livelihood risk and resilience in the face of climate variability, deepened in 5 countries.'),
(204, 41, '2.1.3 2014 (2).', 2014, 'Results, evidence and lessons, from participatory, gender-sensitive evaluation of impacts of promising production and NRM technologies, and production systems, synthesized across locations, and shared through a web-based knowledge management platform.'),
(205, 41, '2.1.3 2015 (2).', 2015, 'Development and policy stakeholders engaged to apply lessons from participatory, gender-sensitive action research, target appropriate technologies and production systems, and explore opportunities to scale up technologies and systems with the greatest potential to enhance resilience to climate variability and change, equitably across socially-differentiated groups and gender.'),
(206, 41, '2.1.3 2013 (3).', 2013, 'Gender- and socially equitable participatory demonstration and evaluation of impacts of social capital, institutional  and financial services, and policy interventions, on livelihood risk and resilience in the face of climate variability, deepened in 5 countries.'),
(207, 41, '2.1.3 2014 (3).', 2014, 'Results, evidence and lessons, from participatory, gender-sensitive evaluation of impacts of social capital, institutional and financial services, and policy interventions, synthesized across locations, and shared through a web-based knowledge management platform.'),
(208, 41, '2.1.3 2015 (3).', 2015, 'Development and policy stakeholders engaged to apply lessons from participatory, gender-sensitive action research, target appropriate interventions, and explore opportunities to scale up institutional and policy interventions with the greatest potential to enhance resilience to climate variability and change, equitably across socially-differentiated groups and gender.'),
(209, 42, '2.2.1 2013.', 2013, 'Policy-oriented analysis of climate impacts on food security components, and gender- and socially-differentiated costs and benefits of alternative food security interventions, communicated with key food system stakeholders; Food system decision makers enagaged in refining and testing disiion support tools for food security response strategies.'),
(210, 42, '2.2.1 2014.', 2014, 'Evaluation  of national to global food system interventions in the face of climate fluctuations, and policy implications; Synthesis of knowledge and policy guidance on gender- and socially-differentiated impacts of food system climate risk management interventions and policies; Integration of decision support tools into national food security decision-making processes.'),
(211, 42, '2.2.1 2015.', 2015, 'Up-scaling and mainstreaming results of research and evaluation related to constraints and opportunities posed by climate variability on food systems at national, regional, and global scales, including food delivery, trade, crisis response, post-crisis recovery, and social protection and their implications for different population segments including women and the poor.'),
(212, 43, '2.3.1 2013.', 2013, 'Historic gridded daily meteorological data sets developed and evaluated, and institutional capacity enhanced, in 2 countries or regional institutions; Crop/rangeland forecasting tools developed; Institutions in 4 countries engaged to develop and test crop forecasting tools tailored to priority crops and local needs.'),
(213, 43, '2.3.1 2014.', 2014, 'Crop and rangeland production forecasting platform, documentation and training materials developed and disseminated; Accuracy of crop forecasting methods assessed and reported; Crop and rangeland forecasting capacity developed in 6 additional countries or regional institutions; Early warning systems developed for 2 major biological threats; Tools developed and institutional capacity enhanced to downscale seasonal forecasts for local agricultural decision-making in 2 countries or regional institutions.'),
(214, 43, '2.3.1 2015.', 2015, 'Improved, downscaled seasonal forecast products, tailored to agricultural and food security decision-making, operational in 2 countries or regional institutions; Improved crop forecasting methodology operational in 2 countries or regional institutions; Biological threat early warning systems implemented and tested in 2 countries or regional institutions.'),
(215, 44, '2.3.2 2013.', 2013, 'Evaluation of agrometeorlogical advisory services in 2 countries; Tested protocols for designing and communicating salient climate information with rural communities, with attention to the needs of women and socially marginalized; Summary report on gender and social equity of climate information sources and delivery mechanisms, and policy advice to enable equitable access; Synthesis report on status, gaps, opportunities for climate services for agriculture and food security in EA, WA, SA.'),
(216, 44, '2.3.2 2014.', 2014, 'Curriculum developed on designing and communicating salient climate information with rural communities, including overcoming gender and social inequities; Capacity of communication intermediaries enhanced at 4 locations; Demonstration and evaluation of gender- and socially-equitable climate service delivery for rural communities at 4 locations in EA, WA, IGP; Roadmaps developed for strengthening climate services for agriculture and food security in 2 regions;'),
(217, 44, '2.3.2 2015.', 2015, 'Demonstration and evaluation of gender- and socially-equitable climate service delivery at 3 additional locations; Roadmaps developed for improving climate information services for agriculture and food security in 2 additional regions; Up-scaling of improved climate information services demonstrated in 2 countries or regions.'),
(218, 45, '3.1.1 2013.', 2013, 'Analysis of mitigation trade-offs for agricultural development pathways in 3-6 countries (CIAT, IFPRI, T3).'),
(219, 45, '3.1.1 2014.', 2014, 'Comparative analysis of mitigation tradeoffs for agricultural development pathways across 3-6 countries (CIAT, IFPRI, T3).'),
(220, 45, '3.1.1 2015.', 2015, 'Policy engagement on synthesis of findings on low emissions agricultural development pathways (CIAT, IFPRI, T3). '),
(221, 46, '3.1.2 2013 (1).', 2013, 'Capacity building of decision makers and national stakeholders in use of appropriate tools, data, and knowledge (ILRI, T3). '),
(222, 46, '3.1.2 2015.', 2015, 'Emissions factors and mitigation potentials for key categories, with a focus on intensification.'),
(223, 47, '3.2.1 2013 (1).', 2013, 'Research established on economic incentives and benefits for mitigation practices (CIMMYT, ILRI, IITA). Linked to Milestone 3.3.1 (2013-2015). '),
(224, 47, '3.2.1 2013 (2).', 2013, 'Testing of institutional arrangements for carbon finance, markets and mitigation standards (T3, IFPRI) Linked to CRP6.4.'),
(225, 47, '3.2.1 2014 (1).', 2014, 'Analysis of economic incentives and benefits for mitigation practices, including analysis of social and gender differentiation. Linked to Milestone 3.3.1 (2013-2015). '),
(226, 47, '3.2.1 2014 (2).', 2014, 'Testing of institutional arrangements for carbon finance and markets and mitigation standards (IFPRI, T3) Linked to CRP6.4.'),
(227, 47, '3.2.1 2015.', 2015, 'Synthesis of knowledge on economic incentives and policy instruments for stimulating adoption of low emissions agriculture in EA and IGP (CIMMYT, IITA).'),
(228, 48, '3.2.2 2014.', 2014, 'Decision-makers in target regions better informed about policy options and gender implications for incentivizing smallholders for GHG emission reductions (EA, SEA, LA)'),
(229, 48, '3.2.2 2015.', 2015, 'Decision-makers in target regions better informed regarding policy options and gender implications for incentivizing and rewarding smallholders for GHG emission reductions (ICRAF).'),
(230, 49, '3.3.1 2013.', 2013, 'Assessment of feasibility and impacts for mitigation practices on farms: (i) soil carbon dynamics under different management practices (EA, IGP, Mexico) and irrigated farming systems in rice-wheat and maize-legume systems (IGP, Mexico) (CIMMYT); (ii) water and nutrient  management and avoided straw burning in rice-based production systems (IRRI); (iii) agro- silvi- horti- pastoral farming systems in India (ICRAF); (iv)  major crops of Subsaharan Africa, coffee and cocoa agroforestry (IITA); (v) dryland Jatropha sites (ICRISAT); (v) pasture and coffee systems (CIAT, with IFPRI); (vi) land use change, land rehabilitation, and peatland management under oil palm (CIFOR); (vii) biochar, integrated smallholder agroforestry, smallholder biofuel production (ICRAF); and (viii) livestock, rangelands (ILRI).  See also 3.2.1 2013 (2) on biochar (IFPRI).'),
(231, 49, '3.3.1 2014.', 2014, 'Impact and trade-off analysis of farm management strategies, for C sequestration and nutrient management in rice-wheat and maize-legume systems (IGP, EA, Mexico) (CIMMYT); (ii) water and nutrient management and avoided straw burning in rice-based production systems (IRRI), (iii) major crops of Subsaharan Africa, coffee and cocoa agroforestry (IITA),  (iv) pasture and coffee systems (CIAT, with IFPRI); (v) land use change, land rehabilitation, and peatland management under oil palm (CIFOR), (vi) response to N fertilizers, biochar, manure, soil and water conservation in dryland Jatropha sites (ICRISAT), (vii) wood energy and agroforestry, analysis of biomass for efficient pyrolysis liquid fuel production (ICRAF), low-input fruit production (CIAT); coffee and cocoa systems at landscape level (CIAT); land use change, land rehabilitation, and peat land management under oil palm (CIFOR);  and pasture, rangelands (ILRI).'),
(232, 49, '3.3.1 2015 (1).', 2015, 'Impact analysis, integrated assessment and management recommendations for: CA at regional scale for range of agroecosystems (CIMMYT); major crops of Subsaharan Africa; rice-based systems (IRRI), land use change, land rehabilitation, and peat land management under oil palm (CIFOR); nutrient management and soil and water conservation in dryland Jatropha sites across an agroecological gradient (ICRISAT); rangelands (ILRI); and wood energy and agroforestry (ICRAF).'),
(233, 49, '3.3.1 2015 (2).', 2015, 'Learning among national agencies to review farm-level mitigation options and their socially differentiated impacts. Linked to 3.1.2 2014 (1) and (2).'),
(234, 50, '3.3.2 2013.', 2013, 'Research established to develop a protocol for quantification of whole farm and landscape GHG emissions among smallholders (ICRAF, ILRI, IRRI, CIMMYT, CIAT, T3). Linked to Milestones 3.3.1 2013-15, 3.2.1 2013 and T4.2. '),
(235, 50, '3.3.2 2014.', 2014, 'Draft protocol for whole farm and landscape GHG emission quantification (ICRAF, ILRI, IRRI, CIMMYT, CIAT). Linked to Milestones 3.3.1 2013-15 and T4.2. '),
(236, 51, '3.3.3 2015.', 2015, 'Regional working groups build capacity in national entities.'),
(237, 52, '4.1.1 2013.', 2013, 'EA and WA regional scenarios finalized in a process that has developed capacity in key national and regional agencies, and policy advisors using the scenarios, or lessons learned through developing them, in informing forward-looking agricultural development, food security, and climate change-related policies and programs.   Scenarios partners and processes launched in Latin America and South Asia.'),
(238, 53, '4.1.2 2013.', 2013, 'First results from PAR, including gender analyses, are published and fed into national and regional policy processes; Synthesis and research reports developed on lessons from linking land health and soil carbon measures with socioeconomic information from CCAFS sites; Local institutional capacity strengthened in land health surveillance methods including soil carbon measurement in additional regions'),
(239, 53, '4.1.2 2014.', 2014, 'Cross-site visits and scaling up of PAR approaches and synthesis of insights widely disseminated into regional and national policy processes; with explicit recognition of social differentiation and gender.  Land health and soil carbon measurement being used by partners to inform national and regional land, agriculture and climate change policies'),
(240, 53, '4.1.2 2015.', 2015, 'Synthesis of knowledge on men and women farmers’ adaptation strategies disseminated and fed into adaptation and mitigation strategies and national policies and economic development plans in at least 3 regions.'),
(241, 54, '4.1.3 2013.', 2013, 'Syntheses and other joint partner communication products based on findings from CCAFS work with women and marginalized groups'),
(242, 54, '4.1.3 2014.', 2014, 'Regional capacity in gender and climate change action research developed in at least 3 regions, and partner institutions implementing more gender and pro-poor targeted activities.'),
(243, 55, '4.1.4 2013 (1).', 2013, 'Support to negotiators, civil society and government agencies to fully contribute to the UNFCCC work program on agriculture, with explicit support to marginalized groups to build their capacity to participate in policy development to improve food security; Assessment of effectiveness of CCAFS learning approach and utilization by a diverse range of partners of CCAFS-generated knowledge.'),
(244, 55, '4.1.4 2014 (1).', 2014, 'Support to regional and global processes to clarify the ecological footprint of agriculture and the ways it can be reduced, without compromising poverty and equity objectives; and building the links to the post Rio+20 process'),
(245, 55, '4.1.4 2015.', 2015, 'Network further expanded to help inform national, regional and global players of the opportunities for a UNFCCC Agreement on Agriculture and Climate Change, options developed for national policy processes '),
(246, 56, '4.2.1 2013 (1).', 2013, 'Regional site characterizations and baseline data synthesized in cross-regional comparisons in the initial three target regions; Regional site characterizations and baseline data collection completed and initial analyses initiated in two additional target regions at three levels: household, village, and institution'),
(247, 56, '4.2.1 2014 (1).', 2014, 'Regional site characterizations and baseline data analyses completed and cross-regional comparisons initiated including all target regions at three levels: household, village, and institution'),
(248, 56, '4.2.1 2014 (1).', 2014, 'Regional site characterizations and baseline data analyses completed and cross-regional comparisons initiated including all target regions at three levels: household, village, and institution'),
(249, 56, '4.2.1 2015 (1).', 2015, 'Second round of baseline surveys implemented in three initial target regions, revisiting the same sites as the first round and initial analyses across time'),
(250, 56, '4.2.1 2013 (2).', 2013, 'Downscaled climate data and methods available for application; and regional climate characterization and evaluation of global and regional climate model performance for two additional target regions.'),
(251, 56, '4.2.1 2014 (2).', 2014, 'Downscaled climate data and methods are being applied in CCAFS regions'),
(252, 56, '4.2.1 2013 (3).', 2013, 'Databases and tools further elaborated and managed to enable stakeholders to assess impacts and evaluate options, including weather data products and household level agricultural systems data'),
(253, 56, '4.2.1 2014 (3).', 2014, 'Based on interaction with and feedback from users, the second generation of databases and tools is being redesigned to enable stakeholders to assess impacts and evaluate options'),
(254, 56, '4.2.1 2014 (4).', 2014, 'Innovative decadal/near-term climate products developed to improve near-term climate prediction'),
(255, 56, '4.2.1 2013 (5).', 2013, 'Assessment toolkit components further evaluated, refined and used to analyze likely effects of specific adaptation and mitigation options in target regions, with a focus on household and intra-household model data and testing and intercomparison of global and regional integrated assessment models'),
(256, 56, '4.2.1 2014 (5).', 2014, 'Ensemble approach to assessment toolkits designed in target regions; engagement with key users initiated to build capacity in use of tools and data'),
(257, 56, '4.2.1 2015 (5).', 2015, 'Assessment toolkit ensemble utilized in target regions; engagement with key users to build capacity in use of tools and data'),
(258, 57, '4.2.2 2013.', 2013, 'Outcome-oriented approaches to decision aids developed in selected sites in 3 initial target regions that engage with socially- and gender differentiated target groups'),
(259, 57, '4.2.2 2014.', 2014, 'Selected approaches to decision aids tested and evaluated in selected sites in target regions that engage socially- and gender differentiated target groups as key stakeholders in the process'),
(260, 58, '4.3.1 2013', 2013, 'Improvements to a modeling environment for policy evaluation and ex-ante assessment of promising technologies related to climate change'),
(261, 58, '4.3.1 2014.', 2014, 'Report on new technologies and international policies to support their development for socially inclusive and gender-responsive adaptation and mitigation'),
(262, 59, '4.3.2 2013.', 2013, 'Global model intercomparisons for analysis of climate change impact, related to mitigation and adaptation policy choices in the agricultural sector. Integration of modeling work into foresight and strategic scenarios building'),
(263, 59, '4.3.2 2014.', 2014, 'National and regional studies complementary to the global technology policy study'),
(264, 60, '4.3.3 2013.', 2013, 'Collaboration with CGIAR centers, NARS, and international organizations to further increase capacity in utilizing and developing modeling tools, to perform global and regional analyses in the context of promising technologies related to climate change'),
(265, 60, '4.3.3 2014.', 2014, 'Activities held at NARS, and international organizations to build capacity to utilize the modeling tools developed under milestone 4.3.1 2013.'),
(266, 61, '1.1.1 2014', 2014, 'Analogue based evaluation and conservation of germplasm of at least 2 crops supported in a minimum of 6 analogue sites.'),
(267, 61, '1.1.1 2015 (1)', 2015, 'One to five flagship technologies  that are gender-and socially-responsive identified, developed and demonstrated in each of the 3 initial target regions which would directly enhance the adaptive capacity of the farming systems to the climate change conditions. Launch through high level engagement with key stakeholders at a key international meeting. '),
(268, 61, '1.1.1 2015 (2)', 2015, 'Analogue Research results synthesized, documented, published and communicated at all levels.'),
(269, 62, '1.1.2 2014 (1)', 2014, 'Researchers and development agents trained on socially and gender-sensitive strategies for the conservation and use of local biodiversity within the climate change context. '),
(270, 62, '1.1.2 2014 (2)', 2014, 'Gender-sensitive and socially differentiated strategies developed for conservation and use of local biodiversity within the climate change context; findings presented in journal article and policy brief. '),
(271, 63, '1.1.3 2014 (1)', 2014, 'Accessions identified with potential adaptive traits for climate change adaptation for at least 5  crops using innovative methods and prioritized on the basis of traits with potential benefits for the poor and women users. Methodology to select genebank material adapted to local current climate conditions and future climate shifts developed and tested and crop suitability atlases for priority crops (as defined by fraction of total production accounted for) produced; findings presented in reports and journal articles.'),
(272, 63, '1.1.3 2015 (1)', 2015, 'Assessment of the contribution of crop, livestock, fish diversity to climate change adaptation carried out; findings summarized in reports, case study narratives, including assessment of their importance to  marginalized farmers and women.'),
(273, 63, '1.1.3 2014 (2)', 2014, 'Methods and tools for participatory, gender-responsive monitoring of deployment of biodiversity and knowledge by communities for climate change adaptation tested out in at least 5 countries (including gender-disaggregated community surveys); findings synthesized in report.'),
(274, 63, '1.1.3 2015 (2)', 2015, 'Germplasm information on potential for climate change adaptation integrated in global information systems and accessible online.  (1) Databases of priority collections  augmented with georef erenced passport data and trait information useful to the diversity analysis for climate change impacts and adaptation effectively linked to global systems, (2) important trait information accessible in global systems, including GENEYSYS, and Crop Trait ontology augmented with traits of interest to Climate Change , (3) complementary data sources on wild species identified through GBIF, (4) training materials, (5) list of and information on newly and already collected germplasm (domesticated and wild) adapted to climate change; Materials of interest safely duplicated in Global Collection  and made available.'),
(275, 63, '1.1.3 2015 (3)', 2015, 'Case studies documented of potential role of informal seed systems for pro-poor and gender-responsive diffusion of adapted germplasm.'),
(276, 64, '1.2.1 2014', 2014, 'Set of “virtual crops” designed and assessed for their efficacy in addressing the likely future conditions in terms of the economic, social, and cultural benefits expected; findings presented in summary report and journal article. Engagement of ARI modelling groups (e.g. Leeds University), NARES scientists.'),
(277, 64, '1.2.1 2015 (1)', 2015, 'Detailed crop-by-crop strategies and plans of action for crop improvement developed, incorporating portfolio of national, regional and global priorities  including those priorities relevant for pro-poor and gender –responsive targeting; findings presented in summary report.'),
(278, 64, '1.2.1 2015 (2)', 2015, 'Set of breeding strategies identified and widely shared with partners including funding bodies, national and international organizations, universities and other actors; findings presented in summary report and policy briefs (including percentage of total food crop production (in recent history)  accounted for by set of breeding strategies).  '),
(279, 64, '1.2.1 2015 (3)', 2015, 'Climate change impact on key global commodities (major Musa groups,) and selected pest and diseases modeled and reviewed by commodity network country partners and possible response strategies identified. '),
(280, 65, '1.2.2 2015 (1)', 2015, 'High-level meetings held with key stakeholders resulting in mainstreaming of new breeding strategies that include attention to men’s and women’s crops in work plans and existing breeding programs.'),
(281, 65, '1.2.2 2015 (2)', 2015, 'Global, regional and national policy briefs produced to guide best-value investments in climate-proofed crop breeding initiatives with promise for pro-poor and gender-responsive impact and policy briefing meeting organized in 3 target regions.'),
(282, 66, '1.3.1 2014 (1)', 2014, 'Socially and gender- disaggregated participatory methods tested for grounding climate change model results to community-level decision making processes that address food security issues.'),
(283, 66, '1.3.1 2015', 2015, 'Roles of gender and different social groups in adaptation strategies for climate change analyzed in target countries and highlighted through fact sheets, project briefs and technical articles. Approaches, methods and outcomes of supportive interventions promoted through collaborative projects and shared with the broader stakeholder community through relevant meetings, conferences and journal articles.'),
(284, 66, '1.3.1 2014 (2)', 2014, 'Community-based holistic adaptation options trialed in at least three sites, in order to understand the social (including gender), cultural, economic and institutional barriers to effective adaptation; outcomes presented in summary report.'),
(285, 67, '1.3.2 2014', 2014, 'Sector specific adaptation strategies and plans produced based on socially and gender-differentiated adaptation options using cost/benefit analysis in at least 5 countries, results shared with key policy makers in target countries.'),
(286, 67, '1.3.2 2015 (1)', 2015, 'Synthesized lessons learned published in policy briefs and synthesis reports and papers on methods and approaches for prioritizing pro-poor and gender-responsive adaptation options within national adaptation plans.'),
(287, 67, '1.3.2 2015 (2)', 2015, 'Policy recommendations provided to national agencies, policy makers and key actors in the agricultural sector on how to target strategies to enable equitable access to breeding materials and strategies by different social groups (e.g. pastoralists, fishers, urban farmers) and by women and men. '),
(288, 68, '1.3.3 2014.', 2014, 'Technical contributions to international processes support the development of international policies enabling access to and use of genetic resources in climate change research and adaptation strategies.'),
(289, 68, '1.3.3 2015', 2015, 'Analysis of institutions and policies that impact on the flow of adapted materials through seed systems; National strategies developed to implement the International Treaty''s Multilateral system on Access and Benefit-Sharing  in 4 countries; Policy options produced at national, provincial and community levels and evaluated for their potential positive or adverse effects on socially marginal groups, especially women to improve existing policies, local management and seed systems to facilitate diffusion and uptake of adapted germplasm.'),
(290, 69, '2.1.1 2014', 2014, 'Synthesized knowledge incorporated into climate risk management good practice guidelines that addresses social and gender equity, and communicated to development and policy stakeholders in 4 locations; Analyses of climate-related vulnerabilities of 3 key agricultural commodities and/or systems incorporated into strategic planning and policy dialog.'),
(291, 69, '2.1.1 2015', 2015, 'Lessons from CCAFS research on risk management innovations, and impacts across socially-differentiated groups and gender, synthesized, communicated widely, and incorporated into strategic planning and policy dialog.'),
(292, 70, '2.1.2 2014', 2014, 'Methodology for designing and targeting comprehensive and equitable agricultural risk management strategies implemented, documented and applied at 4 locations; One model-based climate risk management decision support tool prototype developed.'),
(293, 70, '2.1.2 2015', 2015, 'Capacity to apply household, and intra-household modelling to target and evaluate risk management innovations, enhanced through curriculum and training. Use of model-based decision support tools for local climate risk management demonstrated in 4 countries.'),
(294, 71, '2.1.3 2014', 2014, 'Results, evidence and lessons, from participatory, gender-sensitive evaluation of impacts of promising risk management interventions (production technologies, production systems, institutional services, policy interventions) on rural communities documented at shared with relevant stakeholders at 6 locations; Potential up-scaling mechanisms and partners identified and engaged in 3 locations.'),
(295, 71, '2.1.3 2015', 2015, 'Development and policy stakeholders engaged to apply lessons and evidence from participatory action research, and explore opportunities to scale up risk management interventions (production technologies, production systems, institutional services, policy interventions) with potential to equitably enhance resilience of rural livelihoods.  '),
(296, 72, '2.2.1 2014', 2014, 'Enhanced food system interventions or information systems for responding to climate shocks tested in four countries; Integration of new climate-related information or decision support tools into national food security decision-making processes.'),
(297, 72, '2.2.1 2015', 2015, 'Up-scaling and mainstreaming results of research and evaluation related to constraints and opportunities posed by climate variability on food systems at national, regional, and global scales, including food delivery, trade, crisis response, post-crisis recovery, and social protection and their implications for different population segments including women and the poor.'),
(298, 73, '2.3.1 2014', 2014, 'Crop and rangeland production forecasting platform, documentation and training materials developed and disseminated; Accuracy of crop forecasting methods assessed and reported; Crop and rangeland forecasting capacity developed in 6 countries or regional institutions; Tools developed and institutional capacity enhanced to downscale seasonal forecasts for local agricultural decision-making in 2 countries or regional institutions.'),
(299, 73, '2.3.1 2015', 2015, 'Improved, downscaled seasonal forecast products, tailored to agricultural and food security decision-making, operational in 2 countries or regional institutions; Improved crop forecasting methodology operational in 2 countries or regional institutions.'),
(300, 74, '2.3.2 2014', 2014, 'Curriculum developed on designing and communicating salient climate information with rural communities, including overcoming gender and social inequities; Capacity of communication intermediaries enhanced at 4 locations; Demonstration and evaluation of gender- and socially-equitable climate service delivery for rural communities at 4 locations; Roadmaps developed for strengthening climate services for agriculture and food security in 2 regions.'),
(301, 74, '2.3.2 2015', 2014, 'Demonstration and evaluation of gender- and socially-equitable climate service delivery at 3 additional locations; Roadmaps developed for improving climate information services for agriculture and food security in 2 additional regions; Up-scaling of improved climate information services demonstrated in 2 countries or regions.'),
(302, 75, '3.1.1 2014', 2014, 'Comparative analysis of mitigation trade-offs for agricultural development pathways across 3-6 countries (IFPRI, CIAT, ICRAF, T3). Linked to CRP6 and CRP5.'),
(303, 75, '3.1.1 2015', 2015, 'Policy engagement on synthesis of findings on low emissions agricultural development pathways (CIAT, IFPRI).'),
(304, 76, '3.1.2 2014', 2014, 'Capacity building of decision makers and national stakeholders in use of appropriate tools, data, and knowledge (ILRI, T3).'),
(305, 76, '3.1.2 2015', 2015, 'Emissions factors and mitigation potentials for key categories, with a focus on intensification.'),
(306, 77, '3.2.1 2014 (1)', 2014, 'Analysis of economic incentives and benefits for mitigation practices, including analysis of social and gender differentiation. Linked to Milestone 3.3.1 (2013-2015). (CIMMYT, IFPRI, IITA, T3).'),
(307, 77, '3.2.1 2014 (2)', 2014, 'Testing of institutional arrangements for carbon finance and markets and mitigation standards (T3, ILRI, ICRAF, IITA, CIAT) Linked to CRP6.4.'),
(308, 77, '3.2.1 2015', 2015, 'Synthesis of knowledge on economic incentives and policy instruments for stimulating adoption of low emissions agriculture in EA and SA (CIMMYT, IITA).'),
(309, 78, '3.2.2 2014', 2014, 'Decision-makers in target regions better informed about policy options and gender implications for incentivizing smallholders for GHG emission intensity reductions (EA, LA, T3).'),
(310, 79, '3.3.1 2014', 2014, 'Impact and trade-off analysis of farm management strategies, for C sequestration and nutrient management in rice-wheat and maize-legume systems (IGP, EA, Mexico) (CIMMYT); (ii) water and nutrient management and avoided straw burning in rice-based production systems (IRRI), (iii) coffee and cocoa agroforestry (IITA),  (iv) pasture and coffee systems (CIAT, with IFPRI); (v) land use change, land rehabilitation, and peatland management under oil palm (CIFOR), (vii) wood energy and agroforestry, analysis of biomass for efficient pyrolysis liquid fuel production (ICRAF), low-input fruit production (CIAT); coffee and cocoa systems at landscape level (CIAT); and pasture, rangelands (ILRI).'),
(311, 79, '3.3.1 2015 (1)', 2015, 'Impact analysis, integrated assessment and management recommendations for: CA at regional scale for range of agroecosystems (CIMMYT); major crops of Subsaharan Africa; rice-based systems (IRRI), land use change, land rehabilitation, and peat land management under oil palm (CIFOR); rangelands (ILRI); and wood energy and agroforestry (ICRAF).'),
(312, 79, '3.3.1 2015 (2)', 2015, 'Learning among national agencies to review farm-level mitigation options and their socially differentiated impacts. Linked to 3.1.2 2014 (1) and (2).'),
(313, 80, '3.3.2 2014', 2014, 'Draft protocol and data for whole farm and landscape GHG emission quantification (ICRAF, ILRI, IRRI, CIMMYT, CIAT, T3). Linked to Milestones 3.3.1 2013-15 and T4.2. '),
(314, 80, '3.3.2 2015', 2015, 'Protocol and data for quantification of whole farm and landscape GHG emissions among smallholders. (ICRAF, ILRI, IRRI, CIMMYT, CIAT) Linked to 3.3.1 2013-15. '),
(315, 81, '3.3.3 2014', 2014, 'Regional working groups test and refine monitoring and measurement methods (EA, SA, T3).'),
(316, 81, '3.3.3 2015', 2015, 'Regional working groups build capacity in national entities.'),
(317, 83, '4.1.2 2014', 2014, 'Trainings and action research implemented in all regions, with continued learning & evaluation of climate smart village approach; hundreds of women’s and other groups trained in CSA practices across 5 regions; development of scaling up of PAR approaches and synthesis of insights widely disseminated into regional and national policy processes, with explicit recognition of social differentiation and gender. Partners monitoring indicators measuring uptake and benefits of CSA practices.'),
(318, 83, '4.1.2 2015', 2015, 'New investments by at least 2 int’l development partners in CSA programs in CCAFS tartge countries. Synthesis of knowledge on men and women farmers’ adaptation strategies disseminated and fed into adaptation and mitigation strategies and national policies and economic development plans in at least 3 regions. Tens of thousands of farmers (male and female) adopting CSA practices and strategies in 5 regions.'),
(319, 84, '4.1.3 2014', 2014, 'Regional capacity in gender and climate change action research, and new qualitative and quantitative tools being implemented by partners in at least 3 regions, and partner institutions implementing more gender and pro-poor targeted activities.'),
(320, 84, '4.1.3 2015', 2015, 'International conference sharing CCAFS-catalyzed evidence from 3 regions on gender implications of climate smart agricultural options and approaches. Capacity enhanced and gender and social differentiation research tools being scaled out by partners in at least 20 countries and 5 regions.'),
(321, 85, '4.1.4 2014 (1)', 2014, 'Support to regional and global processes to clarify the ecological footprint of agriculture and the ways it can be reduced, without compromising poverty and equity objectives; and building the links to the post Rio+20 process. Many and diverse sub-nat’l and nat’l partners are using CCAFS scenarios and related K in adaptation and mitigation forward-planning exercises and in engagement in global climate change and food security processes.'),
(322, 85, '4.1.4 2015', 2015, 'Network further expanded to help inform national, regional and global players of the opportunities for a UNFCCC Agreement on Agriculture and Climate Change, options developed for national policy processes '),
(323, 86, '4.2.1 2014 (1)', 2014, 'Regional site characterizations and baseline data analyses completed and cross-regional comparisons initiated including all five target regions at three levels: household, village, and institution.'),
(324, 86, '4.2.1 2015 (1)', 2015, 'Midline surveys implemented in three initial target regions, revisiting the same sites as the first round and initial analyses across time.'),
(325, 86, '4.2.1 2014 (2)', 2014, 'Downscaled climate data and methods are being applied in CCAFS regions to help set priorities and evaluate national and local impacts of climate change'),
(326, 86, '4.2.1 2014 (3)', 2014, 'Based on interaction with and feedback from users, databases and tools are being modified to enable stakeholders to assess impacts of climate change and evaluate options for strengthening the resilience of agricultural and food systems.'),
(327, 86, '4.2.1 2014 (4)', 2014, 'Innovative decadal/near-term climate data products to improve near-term climate prediction investigated and developed'),
(328, 86, '4.2.1 2014 (5)', 2014, 'Assessment toolkits being refined and starting to be used in target regions; engagement with key users initiated to build capacity in use of tools and data.'),
(329, 86, '4.2.1 2015 (5)', 2015, 'Assessment toolkitsutilized in target regions by national decision-makers and others; engagement with key users to build capacity in use of tools and data.');
INSERT INTO `milestones` (`id`, `output_id`, `code`, `year`, `description`) VALUES
(330, 87, '4.2.2 2014', 2014, 'Selected approaches to information provision for making decisions evaluated at different scales in selected sites and countries in target regions that engage socially- and gender differentiated target groups as key stakeholders in the process.'),
(331, 87, '4.2.2 2015', 2015, 'Collation and dissemination of information and tools for prioritizing adaptation and mitigation actions at national/sub-national scales that engage socially- and gender-differentiated target groups in target regions'),
(332, 88, '4.3.1 2014', 2014, 'Report on new technologies and international policies to support their development for socially inclusive and gender-responsive adaptation and mitigation.'),
(333, 89, '4.3.2 2014', 2014, 'National and regional studies complementary to the global technology policy study.'),
(334, 90, '4.3.3 2014', 2014, 'Activities held at NARS, and international organizations to build capacity to utilize the modeling tools developed under milestone 4.3.1 2013.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestone_reports`
--

CREATE TABLE IF NOT EXISTS `milestone_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `milestone_id` int(11) NOT NULL,
  `milestone_status_id` int(11) DEFAULT NULL,
  `tl_description` text,
  `rpl_description` text,
  PRIMARY KEY (`id`),
  KEY `mr_milestone_fk` (`milestone_id`),
  KEY `mr_milestone_status_fk` (`milestone_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `milestone_status`
--

CREATE TABLE IF NOT EXISTS `milestone_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `milestone_status`
--

INSERT INTO `milestone_status` (`id`, `status`) VALUES
(1, 'Complete'),
(2, 'Partially complete'),
(3, 'Incomplete');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objectives`
--

CREATE TABLE IF NOT EXISTS `objectives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  `outcome_description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `theme_fk` (`theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=37 ;

--
-- Volcado de datos para la tabla `objectives`
--

INSERT INTO `objectives` (`id`, `theme_id`, `code`, `description`, `outcome_description`) VALUES
(1, 1, '1', 'Analyze and design processes to support adaptation of farming systems in the face of future uncertainties of climate in space and time', 'Agricultural and food security strategies that are adapted towards predicted conditions of climate change promoted and communicated by the key development and funding agencies (national and international), civil society organizations and private sector in at least 20 countries'),
(2, 1, '2', 'Develop breeding strategies for addressing abiotic and biotic stresses induced by future climatic conditions, variability and extremes, including novel climates', 'Strategies for addressing abiotic and biotic stresses induced by future climate change, variability and extremes, including novel climates mainstreamed among the majority of  the international research agencies who engage with CCAFS, and by national agencies in at least 12 countries'),
(3, 1, '3', 'Integrate adaptation strategies for agricultural and food systems into policy and institutional frameworks', 'Improved adaptation policies from local to international level supporting farming communities, rural institutions and food system actors adapted to future climate conditions in at least 20 countries.'),
(4, 2, '1', 'Identify and test innovations that enable rural communities to better manage climate-related risk and build more resilient livelihoods', 'Systematic technical and policy support by development agencies for farm- to community-level agricultural risk management strategies and actions that buffer against climate shocks and enhance livelihood resilience in at least 20 countries'),
(5, 2, '2', 'Identify and test tools and strategies to use advance information to better manage climate risk through food delivery, trade and crisis response', 'Better climate-informed management by key international, regional and national agencies of food crisis response, post-crisis recovery, and food trade and delivery in at least 12 countries'),
(6, 2, '3', 'Support risk management through enhanced prediction of climate impacts on agriculture, and enhanced climate information and services', 'Enhanced uptake and use of improved climate information products and services, and of information about agricultural production and biological threats, by resource-poor farmers, particularly vulnerable groups and women, in at least 12 countries'),
(7, 3, '1', 'Inform decision makers about the impacts of alternative agricultural development pathways', 'Enhanced knowledge and tools about agricultural development pathways that lead to better decisions for climate mitigation, poverty alleviation, food security and environmental health, used by national agencies in at least 20 countries'),
(8, 3, '2', 'Identify institutional arrangements and incentives that enable smallholder farmers and common-pool resource users to reduce GHGs and improve livelihoods', 'Improved knowledge about incentives and institutional arrangements for mitigation practices by resource-poor smallholders (including farmers’ organizations), project developers and policy makers in at least 10 countries'),
(9, 3, '3', 'Test and identify desirable on-farm practices and their landscape-level implications', 'Key agencies dealing with climate mitigation in at least 10 countries promoting technically and economically feasible agricultural mitigation practices that have co-benefits for resource-poor farmers, particularly vulnerable groups and women'),
(10, 4, '1', 'Explore and jointly apply approaches and methods that enhance knowledge to action linkages with a wide range of partners at local, regional and global levels', 'Appropriate adaptation and mitigation strategies mainstreamed into national policies in at least 20 countries, in the development plans of at least five economic areas (e.g. ECOWAS, EAC, South Asia) covering each of the target regions, and in the key global processes related to food security and climate change'),
(11, 4, '2', 'Assemble data and tools for analysis and planning', 'Improved frameworks, databases and methods for planning responses to climate change used by national agencies in at least 20 countries and by at least 10 key international and regional agencies'),
(12, 4, '3', 'Refine frameworks for policy analysis', 'New knowledge on how alternate policy and program options impact agriculture and food security under climate change incorporated into strategy development by national agencies in at least 20 countries and by at least 10 key international and regional agenciesat least 10 key international and regional agencies'),
(13, 5, '1', 'Analyze and design processes to support adaptation of farming systems in the face of future uncertainties of climate in space and time', 'Agricultural and food security strategies that are adapted towards predicted conditions of climate change promoted and communicated by the key development and funding agencies (national and international), civil society organizations and private sector in at least 20 countries'),
(14, 5, '2', 'Develop breeding strategies for addressing abiotic and biotic stresses induced by future climatic conditions, variability and extremes, including novel climates', 'Develop breeding strategies for addressing abiotic and biotic stresses induced by future climatic conditions, variability and extremes, including novel climates'),
(15, 5, '3', 'Integrate adaptation strategies for agricultural and food systems into policy and institutional frameworks', 'Improved adaptation policies from local to international level supporting farming communities, rural institutions and food system actors adapted to future climate conditions in at least 20 countries.'),
(16, 6, '1', 'Identify and test innovations that enable rural communities to better manage climate-related risk and build more resilient livelihoods', 'Systematic technical and policy support strengthened for farm- to community-level agricultural risk management strategies and actions that buffer against climate shocks and enhance livelihood resilience in at least 20 countries'),
(17, 6, '2', 'Identify and test tools and strategies to use advance information to better manage climate risk through food delivery, trade and crisis response', 'Identify and test tools and strategies to use advance information to better manage climate risk through food delivery, trade and crisis response'),
(18, 6, '3', 'Objective 2.3 Support risk management through enhanced prediction of climate impacts on agriculture, and enhanced climate information and services', 'Enhanced uptake and use of improved climate information products and services, and of information about agricultural production and biological threats, by resource-poor farmers, particularly vulnerable groups and women, in at least 12 countries'),
(19, 7, '1', 'Inform decision  makers about the impacts of alternative agricultural development pathways ', 'Analysis of agricultural development pathways and trade-offs'),
(20, 7, '2', 'Identify institutional arrangements and incentives that enable smallholder farmers and common-pool resource users to reduce GHGs and improve livelihoods ', 'Improved knowledge about incentives and institutional arrangements for mitigation practices by resource-poor smallholders (including farmers’ organizations), project developers, and policy makers in at least 10 countries'),
(21, 7, '3', 'Test and identify desirable on-farm practices and their landscape-level implications', 'Key agencies dealing with climate mitigation in at least 10 countries promoting technically and economically feasible agricultural mitigation practices that have co-benefits for resource-poor farmers, particularly vulnerable groups and women'),
(22, 8, '1', 'Explore and jointly apply approaches and methods that enhance knowledge to action linkages with a wide range of partners at local, regional and global levels', 'Appropriate adaptation and mitigation strategies mainstreamed into national policies in at least 20 countries, in the development plans of at least five economic areas (e.g. ECOWAS, EAC, South Asia) covering each of the target regions, and in the key global processes related to food security and climate change'),
(23, 8, '2', 'Assemble data and tools for analysis and planning', 'Improved frameworks, databases and methods for planning responses to climate change used by national agencies in at least 20 countries and by at least 10 key international and regional agencies'),
(24, 8, '3', 'Refine frameworks for policy analysis', 'New knowledge on how alternate policy and program options impact agriculture and food security under climate change incorporated into strategy development by national agencies in at least 20 countries and by at least 10 key international and regional agencies.'),
(25, 9, '1', 'Analyze and design processes to support adaptation of farming systems in the face of future uncertainties of climate in space and time9', 'Agricultural and food security strategies that are adapted towards predicted conditions of climate change promoted and communicated by the key development and funding agencies (national and international), civil society organizations and private sector in at least 20 countries'),
(26, 9, '2', 'Develop breeding strategies for addressing abiotic and biotic stresses induced by future climatic conditions, variability and extremes, including novel climates', 'Strategies for addressing abiotic and biotic stresses induced by future climate change, variability and extremes, including novel climates mainstreamed among the majority of  the international research agencies who engage with CCAFS, and by national agencies in at least 12 countries'),
(27, 9, '3', 'Integrate adaptation strategies for agricultural and food systems into policy and institutional frameworks', 'Improved adaptation policies from local to international level supporting farming communities, rural institutions and food system actors adapted to future climate conditions in at least 20 countries. '),
(28, 10, '1', 'Identify and test innovations that enable rural communities to better manage climate-related risk and build more resilient livelihoods', 'Systematic technical and policy support strengthened for farm- to community-level agricultural risk management strategies and actions that buffer against climate shocks and enhance livelihood resilience in at least 20 countries'),
(29, 10, '2', 'Identify and test tools and strategies to use advance information to better manage climate risk through food delivery, trade and crisis response', 'Better climate-informed management by key international, regional and national agencies of food crisis management, and food trade and delivery in at least 12 countries'),
(30, 10, '3', 'Support risk management through enhanced prediction of climate impacts on agriculture, and enhanced climate information and services', 'Enhanced uptake and use of improved climate information products and services, and of information about agricultural production and biological threats, by resource-poor farmers, particularly vulnerable groups and women, in at least 12 countries'),
(31, 11, '1', 'Inform decision  makers about the impacts of alternative agricultural development pathways', 'Enhanced knowledge and tools about agricultural development pathways that lead to better decisions for climate mitigation, poverty alleviation, food security, and environmental health, used by national agencies in at least 20 countries'),
(32, 11, '2', 'Identify institutional arrangements and incentives that enable smallholder farmers and common-pool resource users to reduce GHGs and improve livelihoods ', 'Improved knowledge about incentives and institutional arrangements for mitigation practices by resource-poor smallholders (including farmers’ organizations), project developers, and policy makers in at least 10 countries'),
(33, 11, '3', 'Test and identify desirable on-farm practices and their landscape-level implications', 'Key agencies dealing with climate mitigation in at least 10 countries promoting technically and economically feasible agricultural mitigation practices that have co-benefits for resource-poor farmers, particularly vulnerable groups and women'),
(34, 12, '1', 'Explore and jointly apply approaches and methods that enhance knowledge to action linkages with a wide range of partners at local, regional and global levels', 'Appropriate adaptation and mitigation strategies mainstreamed into national policies in at least 20 countries, in the development plans of at least five economic areas (e.g. ECOWAS, EAC, South Asia) covering each of the target regions, and in the key global processes related to food security and climate change'),
(35, 12, '2', 'Assemble data and tools for analysis and planning', 'Improved frameworks, databases and methods for planning responses to climate change used by national agencies in at least 20 countries and by at least 10 key international and regional agencies'),
(36, 12, '3', 'Refine frameworks for policy analysis', 'New knowledge on how alternate policy and program options impact agriculture and food security under climate change incorporated into strategy development by national agencies in at least 20 countries and by at least 10 key international and regional agencies.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `open_access`
--

CREATE TABLE IF NOT EXISTS `open_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `open_access`
--

INSERT INTO `open_access` (`id`, `name`) VALUES
(1, 'Gold'),
(2, 'Green'),
(3, 'Limited');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `other_sites`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcomes`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcome_indicators`
--

CREATE TABLE IF NOT EXISTS `outcome_indicators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL,
  `description` text NOT NULL,
  `theme_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_theme_id` (`theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outcome_indicator_reports`
--

CREATE TABLE IF NOT EXISTS `outcome_indicator_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outcome_indicator_id` int(11) NOT NULL,
  `achievements` text,
  `evidence` text,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_outcome_indicator_id` (`outcome_indicator_id`),
  KEY `FK_activity_leader_id` (`activity_leader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `outputs`
--

CREATE TABLE IF NOT EXISTS `outputs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `objective_id` int(11) NOT NULL,
  `code` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `objective_fk` (`objective_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=91 ;

--
-- Volcado de datos para la tabla `outputs`
--

INSERT INTO `outputs` (`id`, `objective_id`, `code`, `description`) VALUES
(1, 1, '1', 'Development of farming systems and production technologies adapted to climate change conditions in time and space through design of tools for improving crops, livestock, agronomic and natural resource management practices '),
(2, 1, '2', 'Building of regional and national capacities to produce and communicate socially inclusive  adaptation and mitigation strategies for progressive climate change at the national level (e.g. through NAPAs)'),
(3, 1, '3', 'New knowledge, guidelines and access to germplasm are provided for using genetic and species diversity to enhance adaptation, productivity and resilience to changing climate with benefits for socially marginal groups.'),
(4, 2, '1', 'Understanding and evaluating the response of different varieties/crops to climate change in time and space, and generating comprehensive strategies for crop improvement through a combination of modeling, expert consultation and stakeholder dialogue'),
(5, 2, '2', 'Breeding strategies disseminated to key national agencies and research partners'),
(6, 3, '1', 'Improved institutional arrangements and socially differentiated adaptation planning approaches at the local level to enable farming system adaptationstrategies for crop improvement through a combination of modeling, expert consultation and stakeholder dialogue'),
(7, 3, '2', 'Public and private sector policies and strategies at the national level to enable farming communities and the food system to adapt to predicted future conditions'),
(8, 3, '3', 'Policies to enable access to and use of  genetic resources for climate change adaptation research, and diffusion of adapted germplasm strategies for crop improvement through a combination of modeling, expert consultation and stakeholder dialogue'),
(9, 4, '1', 'Synthesized knowledge and evidence on innovative risk management strategies that foster resilient rural livelihoods and sustain a food secure environment'),
(10, 4, '2', 'Analytical framework and tools to target and evaluate risk management innovations for resilient rural livelihoods and improved food security'),
(11, 4, '3', 'Development; and demonstration of the feasibility, acceptability and impacts; of innovative risk management strategies and actions for socially-differentiated rural communities'),
(12, 5, '1', 'Enhanced knowledge, tools and evidence to support improved management of the food system (e.g., food delivery, trade, crisis response, post-crisis recovery) in the face of climate fluctuations'),
(13, 6, '1', 'Improved, value-added climate information products, knowledge, tools, methods; and platforms for monitoring and predicting impacts of climate fluctuations on agricultural production and biological threats; to support management of agricultural and food security risk'),
(14, 6, '2', 'Synthesized knowledge and evidence on institutional arrangements and communication processes for enhancing climate services for agriculture and food security, including services that reach marginalized farmers and women'),
(15, 7, '1', 'Analysis of agricultural development pathways and trade-offs '),
(16, 7, '2', 'Enhanced tools, data and analytic capacity in regional and national policy and research organizations to analyze mitigation sectors and agricultural development options'),
(17, 8, '1', 'Evidence, analysis and trials to support institutional designs, policy and finance that will deliver benefits to poor farmers and women, and reduce GHG emissions'),
(18, 8, '2', 'Improved capacity to increase the uptake and improve the design of incentives mechanisms and institutional arrangements to deliver benefits to poor farmers and women'),
(19, 9, '1', 'Analysis of mitigation biophysical and socioeconomic feasibility for different agricultural practices and regions, and impacts on emissions, livelihoods and food security '),
(20, 9, '2', 'Methods developed and validated for GHG monitoring and accounting at farm and landscape level to contribute to compliance and voluntary market standards'),
(21, 9, '3', 'Enhanced capacity for the use and development of monitoring and accounting methods and assessing feasibility and impacts in regional and national research institutions'),
(22, 10, '1', 'Future economic development scenarios taking climate change into account, and vulnerability maps and analyses incorporating a changing climate and food security issues shared with decision-makers at national, regional and global levels and informing regional economic development and national food security plans and policies'),
(23, 10, '2', 'Evidence on, testing and communication of, successful strategies, approaches, policies, and investments contributing to improved science-informed climate change-agricultural development-food security policies and decision making '),
(24, 10, '3', 'Analyses providing evidence of the benefits of, strategies for, and enhanced regional capacity developed in, gender and pro-poor climate change research approaches that will increase the likelihood that CCAFS-related research will benefit women and other vulnerable as well as socially differentiated groups'),
(25, 10, '4', 'Strengthening capacities to effectively engage in global policy processes and mainstreaming risk, adaptation and mitigation strategies into national policies, agricultural development plans, and key regional and global processes related to agriculture and rural development, food security and climate change '),
(26, 11, '1', 'Integrated assessment framework, toolkits and databases to assess climate change impacts on agricultural systems and their supporting natural resources'),
(27, 11, '2', 'Socially-differentiated decision aids and information developed and communicated for different stakeholders'),
(28, 12, '1', 'Climate change impacts assessed at global and regional levels on agricultural systems (socially and gender differentiated producers and consumers, and their natural resources), national/regional economies, and international transactions and potential of international and regional policy changes to enhance adaption and support agricultural greenhouse gas emissions mitigation'),
(29, 12, '2', 'Analyses of the likely effects of specific adaptation and mitigation options, national policies (natural resource, trade, macroeconomic, international agreements) including gender/livelihood groups, and communicated to key local, national and regional agencies and stakeholders'),
(30, 12, '3', 'Capacity built at CGIAR, NARS, and international organizations to perform global and regional analyses of the effects of policy changes using tools developed in output 4.3.1'),
(31, 13, '1', 'Development of farming systems and production technologies adapted to climate change conditions in time and space through design of tools for improving crops, livestock, agronomic and natural resource management practices'),
(32, 13, '2', 'Building of regional and national capacities to produce and communicate socially inclusive  adaptation and mitigation strategies for progressive climate change at the national level (e.g. through NAPAs)'),
(33, 13, '3', 'New knowledge, guidelines and access to germplasm are provided for using genetic and species diversity to enhance adaptation, productivity and resilience to changing climate with benefits for socially marginal groups.'),
(34, 14, '1', 'Understanding and evaluating the response of different varieties/crops to climate change in time and space, and generating comprehensive strategies for crop improvement through a combination of modeling, expert consultation and stakeholder dialogue'),
(35, 14, '2', 'Breeding strategies disseminated to key national agencies and research partners'),
(36, 15, '1', 'Improved institutional arrangements and socially differentiated adaptation planning approaches at the local level to enable farming system adaptation'),
(37, 15, '2', 'Public and private sector policies and strategies at the national level to enable farming communities and the food system to adapt to predicted future conditions'),
(38, 15, '3', 'Policies to enable access to and use of  genetic resources for climate change adaptation research, and diffusion of adapted germplasm  '),
(39, 16, '1', 'Synthesized knowledge and evidence on innovative risk management strategies that foster resilient rural livelihoods and sustain a food secure environment'),
(40, 16, '2', 'Analytical framework and tools to target and evaluate risk management innovations for resilient rural livelihoods and improved food security'),
(41, 16, '3', 'Development; and demonstration of the feasibility, acceptability and impacts; of innovative risk management strategies and actions for socially-differentiated rural communities,'),
(42, 17, '1', 'Enhanced knowledge, tools and evidence to support improved management of the food system (e.g., food delivery, trade, crisis response, post-crisis recovery) in the face of climate fluctuations'),
(43, 18, '1', 'Improved, value-added climate information products, knowledge, tools, methods; and platforms for monitoring and predicting impacts of climate fluctuations on agricultural production and biological threats; to support management of agricultural and food security risk'),
(44, 18, '2', 'Synthesized knowledge and evidence on institutional arrangements and communication processes for enhancing climate services for agriculture and food security, including services that reach marginalized farmers and women'),
(45, 19, '1', 'Analysis of agricultural development pathways and trade-offs '),
(46, 19, '2', 'Enhanced tools, data, and analytic capacity in regional and national policy and research organizations to analyze mitigation sectors and agricultural development options'),
(47, 20, '1', 'Evidence, analysis, and trials to support institutional designs, policy, and finance that will deliver benefits to poor farmers and women, and reduce GHG emissions'),
(48, 20, '2', 'Improved capacity to increase the uptake and improve the design of incentives, mechanisms, and institutional arrangements to deliver benefits to poor farmers and women'),
(49, 21, '1', 'Analysis of mitigation biophysical and socioeconomic feasibility for different agricultural practices and regions, and impacts on emissions, livelihoods, and food security'),
(50, 21, '2', 'Methods developed and validated for GHG monitoring and accounting at farm and landscape level to contribute to compliance and voluntary market standards'),
(51, 21, '3', 'Enhanced capacity for the use and development of monitoring and accounting methods and assessing feasibility and impacts in regional and national research institutions'),
(52, 22, '1', 'Future economic development scenarios taking climate change into account, and vulnerability maps and analyses incorporating a changing climate and food security issues shared with decision-makers at national, regional and global levels and informing regional economic development and national food security plans and policies'),
(53, 22, '2', 'Evidence on, testing and communication of, successful strategies, approaches, policies, and investments contributing to improved science-informed climate change-agricultural development-food security policies and decision making'),
(54, 22, '3', 'Analyses providing evidence of the benefits of, strategies for, and enhanced regional capacity developed in, gender and pro-poor climate change research approaches that will increase the likelihood that CCAFS-related research will benefit women and other vulnerable as well as socially differentiated groups '),
(55, 22, '4', 'Strengthening capacities to effectively engage in global policy processes and mainstreaming risk, adaptation and mitigation strategies into national policies, agricultural development plans, and key regional and global processes related to agriculture and rural development, food security and climate change'),
(56, 23, '1', 'Integrated assessment framework, toolkits and databases to assess climate change impacts on agricultural systems and their supporting natural resources'),
(57, 23, '2', 'Socially-differentiated decision aids and information developed and communicated for different stakeholders'),
(58, 24, '1', 'Climate change impacts assessed at global and regional levels on agricultural systems (socially and gender differentiated producers and consumers, and their natural resources), national/regional economies, and international transactions and potential of international and regional policy changes to enhance adaption and support agricultural greenhouse gas emissions mitigation.'),
(59, 24, '2', 'Analyses of the likely effects of specific adaptation and mitigation options, national policies (natural resource, trade, macroeconomic, international agreements) including gender/livelihood groups, and communicated to key local, national and regional agencies and stakeholders.'),
(60, 24, '3', 'Capacity built at CGIAR, NARS, and international organizations to perform global and regional analyses of the effects of policy changes using tools developed in output 4.3.1.'),
(61, 25, '1', 'Development of farming systems and production technologies adapted to climate change conditions in time and space through design of tools for improving crops, livestock, agronomic and natural resource management practices '),
(62, 25, '2', 'Building of regional and national capacities to produce and communicate socially inclusive  adaptation and mitigation strategies for progressive climate change at the national level (e.g. through NAPAs)'),
(63, 25, '3', 'New knowledge, guidelines and access to germplasm are provided for using genetic and species diversity to enhance adaptation, productivity and resilience to changing climate with benefits for socially marginal groups.'),
(64, 26, '1', 'Understanding and evaluating the response of different varieties/crops to climate change in time and space, and generating comprehensive strategies for crop improvement through a combination of modeling, expert consultation and stakeholder dialogue'),
(65, 26, '2', 'Breeding strategies disseminated to key national agencies and research partners'),
(66, 27, '1', 'Improved institutional arrangements and socially differentiated adaptation planning approaches at the local level to enable farming system adaptation'),
(67, 27, '2', 'Public and private sector policies and strategies at the national level to enable farming communities and the food system to adapt to predicted future conditions'),
(68, 27, '3', 'Policies to enable access to and use of  genetic resources for climate change adaptation research, and diffusion of adapted germplasm '),
(69, 28, '1', 'Synthesized knowledge and evidence on innovative risk management strategies that foster resilient rural livelihoods and sustain a food secure environment'),
(70, 28, '2', 'Analytical framework and tools to target and evaluate risk management innovations for resilient rural livelihoods and improved food security'),
(71, 28, '3', 'Development; and demonstration of the feasibility, acceptability and impacts; of innovative risk management strategies and actions for socially-differentiated rural communities.'),
(72, 29, '1', 'Enhanced knowledge, tools and evidence to support improved management of the food system (e.g., food delivery, trade, crisis response, post-crisis recovery) in the face of climate fluctuations.'),
(73, 30, '1', 'Improved, value-added climate information products, knowledge, tools, methods; and platforms for monitoring and predicting impacts of climate fluctuations on agricultural production and biological threats; to support management of agricultural and food security risk'),
(74, 30, '2', 'Synthesized knowledge and evidence on institutional arrangements and communication processes for enhancing climate services for agriculture and food security, including services that reach marginalized farmers and women'),
(75, 31, '1', 'Analysis of agricultural development pathways and trade-offs'),
(76, 31, '2', 'Enhanced tools, data, and analytic capacity in regional and national policy and research organizations to analyse mitigation sectors and agricultural development options'),
(77, 32, '1', 'Evidence, analysis, and trials to support institutional designs, policy, and finance that will deliver benefits to poor farmers and women, and reduce GHG emissions'),
(78, 32, '2', 'Improved capacity to increase the uptake and improve the design of incentives, mechanisms, and institutional arrangements to deliver benefits to poor farmers and women'),
(79, 33, '1', 'Analysis of mitigation biophysical and socioeconomic feasibility for different agricultural practices and regions, and impacts on emissions, livelihoods, and food security'),
(80, 33, '2', 'Methods developed and validated for GHG monitoring and accounting at farm and landscape level to contribute to compliance and voluntary market standards'),
(81, 33, '3', 'Enhanced capacity for the use and development of monitoring and accounting methods and assessing feasibility and impacts in regional and national research institutions'),
(82, 34, '1', 'Future economic development scenarios taking climate change into account, and vulnerability maps and analyses incorporating a changing climate and food security issues shared with decision-makers at national, regional and global levels and informing regional economic development and national food security plans and policies'),
(83, 34, '2', 'Evidence on, testing and communication of, successful strategies, approaches, policies, and investments contributing to improved science-informed climate change-agricultural development-food security policies and decision making'),
(84, 34, '3', 'Analyses providing evidence of the benefits of, strategies for, and enhanced regional capacity developed in, gender and pro-poor climate change research approaches that will increase the likelihood that CCAFS-related research will benefit women and other vulnerable as well as socially differentiated groups '),
(85, 34, '4', 'Strengthening capacities to effectively engage in global policy processes and mainstreaming risk, adaptation and mitigation strategies into national policies, agricultural development plans, and key regional and global processes related to agriculture and rural development, food security and climate change  '),
(86, 35, '1', 'Integrated assessment framework, toolkits and databases to assess climate change impacts on agricultural systems and their supporting natural resources'),
(87, 35, '2', 'Socially-differentiated decision aids and information developed and communicated for different stakeholders'),
(88, 36, '1', 'Climate change impacts assessed at global and regional levels on agricultural systems (socially and gender differentiated producers and consumers, and their natural resources), national/regional economies, and international transactions and potential of international and regional policy changes to enhance adaption and support agricultural greenhouse gas emissions mitigation.'),
(89, 36, '2', 'Analyses of the likely effects of specific adaptation and mitigation options, national policies (natural resource, trade, macroeconomic, international agreements) including gender/livelihood groups, and communicated to key local, national and regional agencies and stakeholders.'),
(90, 36, '3', 'Capacity built at CGIAR, NARS, and international organizations to perform global and regional analyses of the effects of policy changes using tools developed in output 4.3.1.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `output_summaries`
--

CREATE TABLE IF NOT EXISTS `output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `os_output_fk` (`output_id`),
  KEY `os_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partners`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partner_roles`
--

CREATE TABLE IF NOT EXISTS `partner_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `partner_roles`
--

INSERT INTO `partner_roles` (`id`, `name`) VALUES
(1, 'Co-research'),
(2, 'Scaling up'),
(3, 'Scaling out'),
(4, 'Capacity building'),
(5, 'Policy linkages'),
(6, 'Operational support');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partner_types`
--

CREATE TABLE IF NOT EXISTS `partner_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acronym` varchar(20) NOT NULL,
  `name` text NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `partner_types`
--

INSERT INTO `partner_types` (`id`, `acronym`, `name`, `description`) VALUES
(1, 'AI', 'Academic Institution', 'Educational institutions : universities, colleges, academies and other institutions of learning.'),
(2, 'ARI', 'Advanced Research Institution', 'international organizations conducting advanced research ("typically in either an industrialized or more advanced developing nation (and contrasted with NARES); and organized to conduct a diversity of research programs deemed by merit review to be of the highest international quality in personnel, infrastructure, and research output."). ESSP is mostly part of ARIs.'),
(3, 'CG', 'CGIAR Center', 'All CGIAR centers.'),
(4, 'CRP', 'Challenge Research Program', 'CGIAR CRP.'),
(5, 'Donors', 'Donors', 'Can include Non-governmental organizations established as  nonprofit corporations or as  charitable trusts, with the  principal purpose of making grants to unrelated organizations, institutions, or individuals for scientific, educational, cultural, religious, or other charitable purposes.'),
(6, 'End_users', 'End users', 'End-users organizations (community/local level).'),
(7, 'GO', 'Government office/department', 'Government offices and departments.'),
(8, 'NARES', 'National agricultural research and extension services', 'National agricultural research (and extension) system.'),
(9, 'NGO_DO', 'Non-governmental organization/Development organization', 'Self-explanatory, including civil society organizations (CSO) and development agencies.'),
(10, 'PRI', 'Private Research Institution', 'Privately owned corporations/companies.'),
(11, 'RO', 'Regional Organization', 'Organizations whose membership consists of several countries within a geographical region; "these are regional and within the CGIAR context they are often facilitating/networking organizations for agricultural research activities.'),
(12, 'Research_Network', 'Research network', 'Non CG consortium research networks.'),
(18, 'Other', 'Other', 'Other');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publications`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_themes`
--

CREATE TABLE IF NOT EXISTS `publication_themes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `publication_themes`
--

INSERT INTO `publication_themes` (`id`, `code`, `name`) VALUES
(1, '1', 'Adaptation to Progressive Climate Change'),
(2, '2', 'Adaptation through Managing Climate Risk'),
(3, '3', 'Pro-poor Climate Change Mitigation'),
(4, '4.1', 'Linking Knowledge to Action'),
(5, '4.2', 'Data and Tools for Analysis and Planning'),
(6, '4.3', 'Policies and Institutions');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_themes_reporting`
--

CREATE TABLE IF NOT EXISTS `publication_themes_reporting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publication_id` int(11) NOT NULL,
  `publication_theme_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_publication_publication_themes_reporting_id` (`publication_id`),
  KEY `FK_publication_theme_publication_themes_reporting_id` (`publication_theme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publication_types`
--

CREATE TABLE IF NOT EXISTS `publication_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `publication_types`
--

INSERT INTO `publication_types` (`id`, `name`) VALUES
(1, 'Journal papers'),
(2, 'Books'),
(3, 'Book chapters'),
(4, 'Policy briefs'),
(5, 'Working papers'),
(6, 'Conference proceedings'),
(7, 'Other');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `regions`
--

CREATE TABLE IF NOT EXISTS `regions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `regions`
--

INSERT INTO `regions` (`id`, `name`, `description`) VALUES
(1, 'East Africa (EA)', NULL),
(2, 'West Africa (WA)', NULL),
(3, 'South Asia (SAs)', NULL),
(4, 'Latin America (LAM)', NULL),
(5, 'South East Asia (SEA)', NULL),
(6, 'Other', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `region_locations`
--

CREATE TABLE IF NOT EXISTS `region_locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `details` text,
  PRIMARY KEY (`id`),
  KEY `region_id` (`region_id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resources`
--

CREATE TABLE IF NOT EXISTS `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `activity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `res_activity_fk` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rpl_synthesis_reports`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `submissions`
--

CREATE TABLE IF NOT EXISTS `submissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_leader_id` int(11) NOT NULL,
  `logframe_id` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `section` enum('Planning','Reporting') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_leader_fk_idx` (`activity_leader_id`),
  KEY `logframe_id_fk_idx` (`logframe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `themes`
--

CREATE TABLE IF NOT EXISTS `themes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` text NOT NULL,
  `description` text,
  `logframe_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `logframe_fk` (`logframe_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Volcado de datos para la tabla `themes`
--

INSERT INTO `themes` (`id`, `code`, `description`, `logframe_id`) VALUES
(1, '1', 'Adaptation to Progressive Climate Change', 2),
(2, '2', 'Adaptation through Managing Climate Risk', 2),
(3, '3', 'Pro-Poor Climate Change Mitigation', 2),
(4, '4', 'Integration for Decision Making', 2),
(5, '1', 'Adaptation to Progressive Climate Change', 3),
(6, '2', 'Adaptation through Managing Climate Risk', 3),
(7, '3', 'Pro-Poor Climate Change Mitigation', 3),
(8, '4', 'Integration for Decision Making', 3),
(9, '1', 'Adaptation to Progressive Climate Change', 4),
(10, '2', 'Adaptation through Managing Climate Risk', 4),
(11, '3', 'Pro-Poor Climate Change Mitigation', 4),
(12, '4', 'Integration for Decision Making', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tl_output_summaries`
--

CREATE TABLE IF NOT EXISTS `tl_output_summaries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `output_id` int(11) NOT NULL,
  `activity_leader_id` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tlos_output_fk` (`output_id`),
  KEY `tlos_activity_leader_fk` (`activity_leader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=221 ;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `activity_leader_id`, `role`, `last_login`) VALUES
(220, 'Test user (My pass is 123)', 'test@test.org', '202cb962ac59075b964b07152d234b70', 3, 'CP', '0000-00-00 00:00:00');

