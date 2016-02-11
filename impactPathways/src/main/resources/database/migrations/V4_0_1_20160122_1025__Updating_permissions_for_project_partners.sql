-- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:partners%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:partners:*', 'Can update everything on the partners section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:partners:coordinator', 'Can update the project coordinator (PC) in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:partners:leader', 'Can update the project leader (PL) in planning round.');
UPDATE `permissions` SET `description`='Can update some content in project partners section in planning round.' WHERE `id`='19';
UPDATE `permissions` SET `permission`='planning:projects:partners:ppa', `description`='Can update the PPA partners in planning round.' WHERE `id`='64';

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:partners:*', 'Can update everything on the partners section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:partners:coordinator', 'Can update the project coordinator (PC) in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:partners:leader', 'Can update the project leader (PL) in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:partners:ppa', 'Can udpate the PPA partners in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:partners:update', 'Can update some content in project partners section in reporting round.');

-- Assingning roles to the partners permissions.
-- -- ML
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:leader');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:leader');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:update');

-- -- CP
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:leader');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:leader');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:update');

-- -- PL
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:coordinator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:update');

-- -- PC
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:update');

-- -- Finance
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:ppa');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:ppa');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'Finance'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:partners:update');
