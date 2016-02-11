-----------------------------------
-- New permissions for reporting --
-----------------------------------

-- Project description
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:*', 'Can update all the reporting section in project description.');
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES ('2', (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:*'));
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES ('4', (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:*'));

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:status', 'Can update only the fields about the project status.');
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES ('7', (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:status'));
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES ('9', (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:status'));
