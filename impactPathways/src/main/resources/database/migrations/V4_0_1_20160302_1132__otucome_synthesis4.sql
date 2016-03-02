/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : ccafspr_ip

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-03-02 11:30:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `expecteds_value_synthesis`
-- ----------------------------
DROP TABLE IF EXISTS `expecteds_value_synthesis`;
CREATE TABLE `expecteds_value_synthesis` (
`indicador`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Flaghip`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EA`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`WA`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`SA`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`SEA`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LAM`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`Global`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of expecteds_value_synthesis
-- ----------------------------
BEGIN;
INSERT INTO `expecteds_value_synthesis` VALUES ('13', '1', '1', '2', '1', '0', '1', '1'), ('14', '1', '0', '1', '0', '0', '1', '0'), ('36', '2', '1', '2', '1', '0', '0', '0'), ('37', '2', '0', '0', '0', '0', '0', '1'), ('38', '3', '0', '0', '0', '0', '1', '0'), ('39', '3', '0', '0', '0', '0', '0', '0.5'), ('50', '4', '1', '1', '1', '0', '1', '0'), ('52', '4', '0', '0', '0', '0', '0', '0');
COMMIT;

-- ----------------------------
-- Table structure for `outcome_synthesis`
-- ----------------------------
DROP TABLE IF EXISTS `outcome_synthesis`;
CREATE TABLE `outcome_synthesis` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`ip_progam_id`  int(11) NOT NULL ,
`year`  int(11) NOT NULL ,
`mid_outcome_id`  int(11) NOT NULL ,
`indicador_id`  int(11) NOT NULL ,
`achieved`  double(11,2) NULL DEFAULT NULL ,
`synthesis_anual`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`synthesis_gender`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`discrepancy`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`achieved_expected`  double(11,2) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=58

;

-- ----------------------------
-- Records of outcome_synthesis
-- ----------------------------
BEGIN;
INSERT INTO `outcome_synthesis` VALUES ('19', '6', '2015', '66', '13', null, '', '', '', '1.00'), ('20', '6', '2015', '67', '14', null, '', '', '', '0.00'), ('21', '6', '2015', '68', '36', null, '', '', '', '1.00'), ('22', '6', '2015', '69', '38', null, '', '', '', '0.00'), ('23', '6', '2015', '70', '50', null, '', '', '', '1.00'), ('24', '6', '2015', '71', '52', null, '', '', '', '0.00'), ('25', '2', '2015', '44', '36', null, '', '', null, '0.00'), ('26', '2', '2015', '45', '37', null, '', '', null, '1.00'), ('27', '5', '2015', '108', '13', null, '', '', '', '1.00'), ('28', '5', '2015', '108', '14', null, '', '', '', '1.00'), ('29', '5', '2015', '115', '36', null, '', '', '', '0.00'), ('30', '5', '2015', '115', '37', null, '', '', '', '0.00'), ('31', '5', '2015', '121', '38', null, '', '', '', '1.00'), ('32', '5', '2015', '121', '39', null, '', '', '', '0.00'), ('33', '5', '2015', '126', '50', null, '', '', '', '1.00'), ('34', '5', '2015', '126', '52', null, '', '', '', '0.00'), ('35', '8', '2015', '143', '13', null, '', '', '', '1.00'), ('36', '8', '2015', '148', '36', null, '', '', '', '1.00'), ('37', '8', '2015', '149', '38', null, '', '', '', '0.00'), ('38', '8', '2015', '150', '50', null, '', '', '', '1.00'), ('39', '1', '2015', '13', '13', null, '', '', null, '1.00'), ('40', '1', '2015', '14', '14', null, '', '', null, '0.00'), ('41', '3', '2015', '38', '38', null, '', '', null, '0.00'), ('42', '3', '2015', '39', '39', null, '', '', null, '0.50'), ('43', '9', '2015', '87', '13', null, '', '', '', '0.00'), ('44', '9', '2015', '87', '14', null, '', '', '', '0.00'), ('45', '9', '2015', '88', '14', null, '', '', '', '0.00'), ('46', '9', '2015', '89', '36', null, '', '', '', '0.00'), ('47', '9', '2015', '89', '37', null, '', '', '', '0.00'), ('48', '9', '2015', '90', '38', null, '', '', '', '0.00'), ('49', '9', '2015', '90', '39', null, '', '', '', '0.00'), ('50', '9', '2015', '91', '50', null, '', '', '', '0.00'), ('51', '9', '2015', '91', '52', null, '', '', '', '0.00'), ('52', '7', '2015', '53', '13', null, '', '', '', '2.00'), ('53', '7', '2015', '53', '14', null, '', '', '', '1.00'), ('54', '7', '2015', '54', '36', null, '', '', '', '2.00'), ('55', '7', '2015', '55', '50', null, '', '', '', '1.00'), ('56', '4', '2015', '24', '50', null, '', '', null, '0.00'), ('57', '4', '2015', '25', '52', null, '', '', null, '0.00');
COMMIT;

-- ----------------------------
-- Auto increment value for `outcome_synthesis`
-- ----------------------------
ALTER TABLE `outcome_synthesis` AUTO_INCREMENT=58;





update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id
 set outc.achieved_expected=val.LAM  where outc.ip_progam_id=5;



update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id
 set outc.achieved_expected=val.EA  where outc.ip_progam_id=6;


update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id
 set outc.achieved_expected=val.WA  where outc.ip_progam_id=7;



update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id
 set outc.achieved_expected=val.SA where outc.ip_progam_id=8;

update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id
 set outc.achieved_expected=val.SEA  where outc.ip_progam_id=9;




update outcome_synthesis outc  INNER JOIN expecteds_value_synthesis val on val.indicador=outc.indicador_id and outc.ip_progam_id=val.Flaghip
 set outc.achieved_expected=val.`Global`  ;

DROP TABLE IF EXISTS `expecteds_value_synthesis`;