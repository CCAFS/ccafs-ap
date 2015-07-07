-- Adding new column to differenciate the type of partner (responsible or other).
ALTER TABLE `deliverable_partnerships` 
ADD COLUMN `type` ENUM('Resp', 'Other') NOT NULL DEFAULT 'Other' AFTER `user_id`;

-- renaming column
ALTER TABLE `deliverable_partnerships` 
CHANGE COLUMN `type` `partner_type` ENUM('Resp','Other') NOT NULL DEFAULT 'Other' ;
