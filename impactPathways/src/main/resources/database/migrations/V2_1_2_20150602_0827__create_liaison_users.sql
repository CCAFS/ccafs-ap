-- -----------------------------------------------------------------------------
-- Create table to store what insitutions can be management liaison
-- -----------------------------------------------------------------------------

CREATE  TABLE IF NOT EXISTS `liaison_institutions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `institution_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `acronym` VARCHAR(255) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_liaison_institutions_institutions_idx` (`institution_id` ASC) ,
  CONSTRAINT `FK_liaison_institutions_institutions` 
    FOREIGN KEY (`institution_id` )
    REFERENCES `institutions` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;

INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('697', 'Coordinating Unit', 'CU');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'Flagship One', 'FP1');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'Flagship Two', 'FP2');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('1053', 'Flagship Three', 'FP3');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'Flagship Four', 'FP4');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'East Africa Region', 'RP EA');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'Latin America Region', 'RP LAM');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'South Asia Region', 'RP SAs');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'South East Asia Region', 'RP SEA');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'West Africa Region', 'RP WA');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('52', 'Africa Rice Center', 'AfricaRice');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('49', 'Bioversity International', 'BI');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('46', 'Centro Internacional de Agricultura Tropical', 'CIAT');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('115', 'Center for International Forestry Research', 'CIFOR');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('50', 'International Maize and Wheat Improvement Center', 'CIMMYT');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('67', 'Centro Internacional de la Papa', 'CIP');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('51', 'International Center for Agricultural Research in the Dry Areas', 'ICARDA');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('88', 'World Agroforestry Centre', 'ICRAF');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('103', 'International Crops Research Institute for the Semi-Arid Tropics', 'ICRISAT');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('89', 'International Food Policy Research Institute', 'IFPRI');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('45', 'International Institute of Tropical Agriculture', 'IITA');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('66', 'International Livestock Research Institute', 'ILRI');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('5', 'International Rice Research Institute', 'IRRI');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('172', 'International Water Management Institute', 'IWMI');
INSERT INTO `liaison_institutions` (`institution_id`, `name`, `acronym`) VALUES ('99', 'WorldFish Center', 'WorldFish');