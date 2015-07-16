-- -----------------------------------------------------------------------------------
-- Column user_id in deliverable_partnerships table is not required.
-- -----------------------------------------------------------------------------------

ALTER TABLE `deliverable_partnerships` 
DROP FOREIGN KEY `FK_deliverable_partnerships_users_user_id`;
ALTER TABLE `deliverable_partnerships` 
CHANGE COLUMN `user_id` `user_id` BIGINT(20) NULL ;
ALTER TABLE `deliverable_partnerships` 
ADD CONSTRAINT `FK_deliverable_partnerships_users_user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

-- Deleting triggers
DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_insert` $$
DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_update` $$
DELIMITER ;

DROP TABLE IF EXISTS $[database]_history.deliverable_partnerships;