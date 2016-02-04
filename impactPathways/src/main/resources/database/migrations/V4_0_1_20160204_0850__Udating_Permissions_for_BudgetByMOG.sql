-- -- Deleting permissions
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%:projects:budgetByMOG:%";

-- -- Inserting new permissions.
UPDATE `permissions` SET `description`='Can update everything in project budget by mog section in planning round.', `permission` = 'planning:projects:budgetByMog:*' WHERE `permission` ='planning:projects:budgetByMog:update';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:budgetByMog:*', 'Can update everything in project budget by mog section in reporting round.');

-- -- Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:*');



