----------------------------------------------------------------------------
-- Adjusting the permissions used in the project partner section
----------------------------------------------------------------------------

DELETE FROM `permissions` WHERE `permissions`.`permission` = 'project:partnerLead:update';
DELETE FROM `permissions` WHERE `permissions`.`permission` = 'project:ppaPartners:update';

INSERT INTO `permissions` (permission, description) VALUES ('project:partners:leader:update', 'Can update the project leader of a project');
INSERT INTO `permissions` (permission, description) VALUES ('project:partners:ppa:update', 'Can update the PPA partners of a project');

-- Project leader
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 7, (SELECT id FROM permissions WHERE permission = 'project:partners:update'));
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 7, (SELECT id FROM permissions WHERE permission = 'project:partners:leader:update'));
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 7, (SELECT id FROM permissions WHERE permission = 'project:partners:ppa:update'));

-- Project coordinator
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 9, (SELECT id FROM permissions WHERE permission = 'project:partners:update'));
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 9, (SELECT id FROM permissions WHERE permission = 'project:partners:leader:update'));
INSERT INTO `role_permissions` (role_id, permission_id) VALUES( 9, (SELECT id FROM permissions WHERE permission = 'project:partners:ppa:update'));

UPDATE `roles` SET `name` = 'Project coordinator' WHERE `roles`.`id` = 9;