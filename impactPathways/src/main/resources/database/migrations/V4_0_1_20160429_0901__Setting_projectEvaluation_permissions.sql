-- -- Creating new roles
INSERT INTO `roles` (name, acronym) VALUES ('External Evaluator','EE');
INSERT INTO `roles` (name, acronym) VALUES ('Program Director','PD');
 
-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:evaluation:*', 'Can update project Evaluation');


-- -- Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CU'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'EE'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PD'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:evaluation:*');

