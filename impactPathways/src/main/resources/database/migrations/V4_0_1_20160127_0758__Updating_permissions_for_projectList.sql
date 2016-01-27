-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%:projectList:%";

-- Updating permissions
UPDATE `permissions` SET `permission`='planning:projectsList:*', `description`='Can use all the functions in the projects list section in planning round.' WHERE `id`='3';
UPDATE `permissions` SET `permission`='planning:projectsList:addCoreProject', `description`='Can use the \"add core project\" button in the projects list section in planning round.' WHERE `id`='4';
UPDATE `permissions` SET `permission`='planning:projectsList:addBilateralProject', `description`='Can use the \"add bilateral project\" button in the projects list section in planning round.' WHERE `id`='5';
DELETE FROM `permissions` WHERE `id`='56';
UPDATE `permissions` SET `permission`='planning:projectsList:deleteProject', `description`='Can use the \"Delete project\" button in the projects list section in planning round.' WHERE `id`='7';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projectsList:submitProject', 'Can use the \"Submit project\" button in the projects list section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsList:*', 'Can use all the functions in the projects list section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsList:addBilateralProject', 'Can use the \"add bilateral project\" button in the projects list section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsList:addCoreProject', 'Can use the \"add core project\" button in the projects list section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsList:deleteProject', 'Can use the \"Delete project\" button in the projects list section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsList:submitProject', 'Can use the \"Submit project\" button in the projects list section in reporting round.');


-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projectsList:addBilateralProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projectsList:addBilateralProject');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projectsList:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projectsList:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projectsList:submitProject');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projectsList:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projectsList:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projectsList:submitProject');