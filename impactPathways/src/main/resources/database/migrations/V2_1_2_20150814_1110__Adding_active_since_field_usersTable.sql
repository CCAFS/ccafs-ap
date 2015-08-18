-- -----------------------------------------------------------------------------
--        Adding active_since field to users.
-- -----------------------------------------------------------------------------

ALTER TABLE `users` 
ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '' AFTER `is_ccafs_user`;


