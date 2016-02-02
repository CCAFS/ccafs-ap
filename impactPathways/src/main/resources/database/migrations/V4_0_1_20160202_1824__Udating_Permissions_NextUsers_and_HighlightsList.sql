-- -- Deleting permissions
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:nextUsers:%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:nextUsers:*', 'Can update the Next users section in the planning round.');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');

-- -- Deleting permissions
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:highlights:%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:highlights:*', 'Can update the Project Highlights List section in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:highlights:update', 'Can make changes in the Project Highlights List section in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:highlights:addHighlight', 'Can add new Project Highlights in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:highlights:removeHighlight', 'Can remove Project Highlights in the planning round.');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:highlights:update', 'Can make changes in the Project Highlights List section in the reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:highlights:addHighlight', 'Can add new Project Highlights in the reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:highlights:removeHighlight', 'Can remove Project Highlights in the reporting round.');


INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:addHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:addHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:addHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:addHighlight');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:removeHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:removeHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:removeHighlight');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:removeHighlight');