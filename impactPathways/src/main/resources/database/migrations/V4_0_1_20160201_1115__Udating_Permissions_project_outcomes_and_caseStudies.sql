-- Outcome Case Studies
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:caseStudies:*', 'Can update the Outcomes Case Studies section in the planning round.');

-- Project Outcomes

-- -- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:outcomes%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outcomes:update', 'Can update project outcomes in planning round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outcomes:statement', 'Can update project statement in project outcomes in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outcomes:annualProgress', 'Can update annual progress in project outcomes in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outcomes:communicationEngagement', 'Can update communication engagement in project outcomes in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:outcomes:uploadSummary', 'Can upload a summary file in project outcomes in planning round.');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outcomes:update', 'Can update project outcomes in reporting round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outcomes:statement', 'Can update project statement in project outcomes in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outcomes:annualProgress', 'Can update annual progress in project outcomes in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outcomes:communicationEngagement', 'Can update communication engagement in project outcomes in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outcomes:uploadSummary', 'Can upload a summary file in project outcomes in reporting round.');

-- --Assigning permissions.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:statement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:statement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:statement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outcomes:statement');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:annualProgress');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:annualProgress');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:annualProgress');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:annualProgress');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:communicationEngagement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:communicationEngagement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:communicationEngagement');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:communicationEngagement');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:uploadSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:uploadSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:uploadSummary');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outcomes:uploadSummary');
