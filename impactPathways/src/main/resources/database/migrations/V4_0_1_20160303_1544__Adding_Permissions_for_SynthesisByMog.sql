-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:synthesisByMog:*', 'Can edit everything on Synthesis by MOG in reporting round.');


-- -- Update Outcome Synthesis section
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:synthesisByMog:update', 'Can edit on Synthesis by MOG in reporting round.');
--  Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:synthesisByMog:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:synthesisByMog:update');
