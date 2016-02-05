-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:synthesis:crpIndicators:*', 'Can update everything on CRP Indicatoris in reporting round.');


-- -- Assigning permissions
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:crpIndicators:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:crpIndicators:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:synthesis:crpIndicators:*');

