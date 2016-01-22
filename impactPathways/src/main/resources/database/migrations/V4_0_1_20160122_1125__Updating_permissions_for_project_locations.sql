-- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:locations%";

-- Updating permissions
UPDATE `ccafs_pr`.`permissions` SET `permission`='planning:projects:locations:*', `description`='Can update everything in project locations in planning round.' WHERE `id`='20';

INSERT INTO `ccafs_pr`.`permissions` (`permission`, `description`) VALUES ('reporting:projects:locations:*', 'Can update everything in project locations in reporting round.');

-- Assingning roles to the lcoation permissions.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:*');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:locations:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:locations:*');
