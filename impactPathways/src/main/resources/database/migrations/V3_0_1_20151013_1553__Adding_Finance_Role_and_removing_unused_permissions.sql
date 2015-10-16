--------------------------------------------
-- Adding Finance role and permissions
--------------------------------------------

-- Adding Finance person role
INSERT INTO `roles` (`id`, `name`, `acronym`) VALUES (NULL, 'Finance person', 'FP');

-- Adding permissions to FP role
INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) 
VALUES (NULL, '10', '25'), (NULL, '10', '51'), (NULL, '10', '19');

-- Deleting unused permissions
DELETE FROM `permissions` WHERE `id` = 58;
DELETE FROM `permissions` WHERE `id` = 57;

--------------------------------------------
-- Updating Flagships acronym names
--------------------------------------------

UPDATE `liaison_institutions` SET `acronym` = 'F1' WHERE `liaison_institutions`.`acronym` = 'FP1';
UPDATE `liaison_institutions` SET `acronym` = 'F2' WHERE `liaison_institutions`.`acronym` = 'FP2';
UPDATE `liaison_institutions` SET `acronym` = 'F3' WHERE `liaison_institutions`.`acronym` = 'FP3';
UPDATE `liaison_institutions` SET `acronym` = 'F4' WHERE `liaison_institutions`.`acronym` = 'FP4';