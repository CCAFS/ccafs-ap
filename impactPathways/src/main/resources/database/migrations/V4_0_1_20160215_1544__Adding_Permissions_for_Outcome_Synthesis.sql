-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:outcomeSynthesis:*', 'Can edit everything on Outcome Synthesis in reporting round.');


-- -- Update Outcome Synthesis section
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:outcomeSynthesis:update', 'Can edit on Outcome Synthesis in reporting round.');
--  Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:outcomeSynthesis:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:outcomeSynthesis:update');

-- -- Update Regional Synthesis
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:outcomeSynthesis:rplSynthesis', 'Can edit on Regional Synthesis in reporting round.');
--  Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:outcomeSynthesis:rplSynthesis');

-- -- Update Flagship Synthesis
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:outcomeSynthesis:fplSynthesis', 'Can edit on Flagship Synthesis in reporting round.');
--  Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:outcomeSynthesis:fplSynthesis');