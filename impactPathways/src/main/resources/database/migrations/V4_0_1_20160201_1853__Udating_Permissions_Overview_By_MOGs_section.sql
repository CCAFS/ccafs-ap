-- CCAFS Outcomes

-- -- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:outputs%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outputs:update', 'Can make changes in the overview by mogs section in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outputs:expectedAnnualContribution', 'Can update the expected annual contribution in overview by mogs section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outputs:socialInclusionDimmension', 'Can update the expected gender contribution in overview by mogs section in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outputs:briefSummary', 'Can update the actual contribution in overview by mogs section in the planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outputs:summaryGender', 'Can update the actual gender contribution in the overview by mogs section in the planning round.');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:update', 'Can make changes in the overview by mogs section in the reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:expectedAnnualContribution', 'Can update the expected annual contribution in overview by mogs section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:socialInclusionDimmension', 'Can update the expected gender contribution in overview by mogs section in the reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:briefSummary', 'Can update the actual contribution in overview by mogs section in the reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:summaryGender', 'Can update the actual gender contribution in the overview by mogs section in the reporting round.');



-- --Assigning permissions.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:expectedAnnualContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:expectedAnnualContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:expectedAnnualContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:expectedAnnualContribution');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:socialInclusionDimmension');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:socialInclusionDimmension');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:socialInclusionDimmension');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:socialInclusionDimmension');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:briefSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:briefSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:briefSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:briefSummary');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:summaryGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:summaryGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:summaryGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:summaryGender');