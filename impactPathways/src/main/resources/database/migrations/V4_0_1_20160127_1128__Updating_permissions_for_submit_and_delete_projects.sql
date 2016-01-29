-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%submit%";

DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%:projects:manage:%";

-- Updating permissions
DELETE FROM `permissions` WHERE `id`='6';	
UPDATE `permissions` SET `permission`='planning:projects:manage:submitProject', `description`='Can use the \"Submit project\" button in any section in planning round.' WHERE `id`='95';
UPDATE `permissions` SET `permission`='reporting:projects:manage:submitProject', `description`='Can use the \"Submit project\" button in any section in reporting round.' WHERE `id`='100';
UPDATE `permissions` SET `permission`='planning:projects:manage:deleteProject', `description`='Can use the \"Delete project\" button in any section in planning round.' WHERE `id`='7';
UPDATE `permissions` SET `permission`='reporting:projects:manage:deleteProject', `description`='Can use the \"Delete project\" button in any section in reporting round.' WHERE `id`='99';


-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:manage:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:manage:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:manage:submitProject');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:manage:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:manage:submitProject');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:manage:submitProject');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:manage:deleteProject');

