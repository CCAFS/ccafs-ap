ALTER TABLE `ip_indicators` DROP FOREIGN KEY `FK_indicators_program_elements` ;
ALTER TABLE `ip_indicators` CHANGE COLUMN `program_element_id` `program_element_id` BIGINT(20) NULL  , 
  ADD CONSTRAINT `FK_indicators_program_elements` FOREIGN KEY (`program_element_id` )
  REFERENCES `ip_program_elements` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE;
