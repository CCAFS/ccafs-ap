-- -- Deleting permissions
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%:projects:budget:%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:budget:*', 'Can update everything on project budgets in planning round');
UPDATE `permissions` SET `description`='Can update the W3/Bilateral budget in the project budget section in planning round', `permission` = 'planning:projects:budget:annualBilateral' WHERE `permission` ='planning:projects:budget:annualBilateral:update';
UPDATE `permissions` SET `description`='Can update the W1/W2 budget in the project budget section in planning round', `permission` = 'planning:projects:budget:annualW1w2' WHERE `permission`='planning:projects:budget:annualW1w2:update';
UPDATE `permissions` SET `description`='Can update the planning project budget section in planning round' WHERE `permission`='planning:projects:budget:update';

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:budget:*', 'Can update everything on project budgets in reporting round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:budget:update', 'Can update the planning project budget section in reporting round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:budget:annualBilateral', 'Can update the W3/Bilateral budget in the project budget section in reporting round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:budget:annualW1w2:update', 'Can update the W1/W2 budget in the project budget section in reporting round');

-- -- Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:annualBilateral');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:annualBilateral');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:annualBilateral');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:annualBilateral');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:annualW1w2');

