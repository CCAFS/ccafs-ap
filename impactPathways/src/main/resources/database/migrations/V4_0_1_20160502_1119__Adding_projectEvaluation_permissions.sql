-- -- Removing "*" permission for everyone
DELETE FROM `role_permissions` where permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projectsEvaluation:*', 'Can update Evaluation projects section');

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:update', 'Can update project Evaluation');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CU'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'EE'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:update');

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessPL', 'Can view Project Leaders evaluations');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessPL');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessPL');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CU'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessPL');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessPL');


-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessRPL', 'Can view Regional Program evaluations');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessRPL');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessRPL');



-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessFPL', 'Can view Flagship Program evaluations');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessFPL');



-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessEE', 'Can view External evaluator evaluations');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessEE');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessEE');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CU'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessEE');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessEE');


-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessCU', 'Can view Coordination Unit evaluations');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessCU');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:accessCU');


-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:accessPD', 'Can view Program Director evaluations');






