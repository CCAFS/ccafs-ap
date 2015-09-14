----------------------------------------------------------------------------
--    Improving the schemas of permission in the database
----------------------------------------------------------------------------

-- Delete duplicated row
DELETE FROM `permissions` WHERE `id`='59';

-- Change the Flagship program leader and Regional program leaders by the Management liaison role
UPDATE `roles` SET `name`='Management Liaison', `acronym`='ML' WHERE `id`='2';

UPDATE `user_roles` SET role_id = 2
WHERE role_id = 3;

DELETE FROM `roles` WHERE `id`='3';

-- UPDATE the permissions 

DELETE FROM `permissions` WHERE `id`='26';
DELETE FROM `permissions` WHERE `id`='27';
DELETE FROM `permissions` WHERE `id`='28';
DELETE FROM `permissions` WHERE `id`='29';
DELETE FROM `permissions` WHERE `id`='30';
DELETE FROM `permissions` WHERE `id`='31';
DELETE FROM `permissions` WHERE `id`='32';
DELETE FROM `permissions` WHERE `id`='33';
DELETE FROM `permissions` WHERE `id`='34';
DELETE FROM `permissions` WHERE `id`='37';
DELETE FROM `permissions` WHERE `id`='38';
DELETE FROM `permissions` WHERE `id`='39';
DELETE FROM `permissions` WHERE `id`='40';
DELETE FROM `permissions` WHERE `id`='41';
DELETE FROM `permissions` WHERE `id`='42';
DELETE FROM `permissions` WHERE `id`='43';
DELETE FROM `permissions` WHERE `id`='45';
DELETE FROM `permissions` WHERE `id`='46';
DELETE FROM `permissions` WHERE `id`='48';
DELETE FROM `permissions` WHERE `id`='50';
DELETE FROM `permissions` WHERE `id`='54';
DELETE FROM `permissions` WHERE `id`='55';
DELETE FROM `permissions` WHERE `id`='60';
DELETE FROM `permissions` WHERE `id`='61';

-- Change all the permissions assigned to the project coordinator
DELETE FROM role_permissions WHERE role_id = 7;

INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:projectList:submitButton:*' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:description:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (7, (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:update' ));

-- Update all the permissions assigned to the project coordinator
DELETE FROM role_permissions WHERE role_id = 9;

INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:description:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:partners:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:locations:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:budget:update' ));
INSERT INTO role_permissions (role_id, permission_id) VALUES (9, (SELECT id FROM permissions WHERE permission = 'planning:projects:budgetByMog:update' ));
