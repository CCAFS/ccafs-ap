-- -----------------------------------------------------------------------------
-- Create table to store what insitutions can be management liaison
-- -----------------------------------------------------------------------------

CREATE  TABLE IF NOT EXISTS `liaison_users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `user_id` BIGINT(20) NOT NULL COMMENT 'This field is a link to the table users',
  `institution_id` BIGINT(20) NOT NULL COMMENT 'This field is a link to the table liaison_institutions' ,
  PRIMARY KEY (`id`) ,
  INDEX `FK_liaison_users_users__idx` (`user_id` ASC) ,
  INDEX `FK_liaison_users_institutions__idx` (`institution_id` ASC) ,
  CONSTRAINT `FK_liaison_users__users`
    FOREIGN KEY (`user_id` )
    REFERENCES `users` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_liaison_users__liaison_institutions`
    FOREIGN KEY (`institution_id` )
    REFERENCES `liaison_institutions` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
DEFAULT CHARACTER SET = utf8;

INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('122', '1');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('132', '1');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('28', '2');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('17', '2');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('29', '3');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('30', '3');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('31', '4');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('32', '4');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('7', '5');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('5', '5');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('24', '6');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('25', '6');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('19', '7');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('18', '7');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('20', '8');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('21', '8');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('26', '9');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('193', '9');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('22', '10');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('61', '12');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('66', '13');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('69', '13');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('119', '14');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('88', '15');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('67', '16');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('84', '19');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('162', '19');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('51', '20');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('52', '20');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('81', '21');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('82', '22');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('108', '23');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('275', '24');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('83', '18');
INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('101', '25');
