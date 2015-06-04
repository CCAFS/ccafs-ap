-- -----------------------------------------------------------------------------
-- These are permission specific to projects.
-- -----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE `role_permissions`;
TRUNCATE `permissions`;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES 
(NULL, '*', 'Full privileges on all the platform'), 
(NULL, 'planning:*', 'Can update all the planning section contents'), 
(NULL, 'planning:projects:projectList:*', 'Can use all the functions in the planning projects list'), 
(NULL, 'planning:projects:projectList:coreProjectButton:*', 'Can use the "add core project" button in the planning projects list section'), 
(NULL, 'planning:projects:projectList:bilateralProjectButton:*', 'Can use the "add bilateral project" button in the planning projects list section'), 
(NULL, 'planning:projects:projectList:submitButton:*', 'Can use the "Submit" button in the planning section'), 
(NULL, 'planning:projects:projectList:deleteProjectButton:*', 'Can use the "Delete project" button in the planning section'), 
(NULL, 'planning:projects:description:*', 'Can update the planning project description information section'), 
(NULL, 'planning:projects:description:update', 'Can update the planning project information section'), 
(NULL, 'planning:projects:description:managementLiaison:*', 'Can update the management liaison of a project in the planning section'), 
(NULL, 'planning:projects:description:startDate:*', 'Can update the start date of a project in the planning section'), 
(NULL, 'planning:projects:description::endDate:*', 'Can update the end date of a project in the planning section'), 
(NULL, 'planning:projects:description:workplan:*', 'Can upload a project work plan for a project in the planning section'), 
(NULL, 'planning:projects:description:bilateralContract:*', 'Can upload a bilateral contract proposal for a project in the planning section'), 
(NULL, 'planning:projects:description:flagships:*', 'Can update the flagships linked to a project in the planning section'), 
(NULL, 'planning:projects:description:regions:*', 'Can update the end date of a project in the planning section'), 
(NULL, 'planning:projects:partnerLead:update', 'Can update the planning project Lead partner section'), 
(NULL, 'planning:projects:ppaPartners:update', 'Can update the planning project PPA partners section'), 
(NULL, 'planning:projects:partners:update', 'Can update the planning project partners section'), 
(NULL, 'planning:projects:locations:update', 'Can update the planning project locations section'), 
(NULL, 'planning:projects:outcomes:update', 'Can update the planning project outcomes section'), 
(NULL, 'planning:projects:ipOtherContributions:update', 'Can update the planning project ip other contributions section'), 
(NULL, 'planning:projects:outputs:update', 'Can update the planning project outputs section'), 
(NULL, 'planning:projects:activities:update', 'Can update the planning project activities list section'), 
(NULL, 'planning:projects:budget:update', 'Can update the planning project budget section'), 
(NULL, 'project:description:*', 'Can change all the project description information of some specific project'), 
(NULL, 'project:description:update', 'Can update the project description information of some specific project'), 
(NULL, 'project:description:managementLiaison:*', 'Can update the management liaison of some specific project'), 
(NULL, 'project:description:startDate:*', 'Can update the start date of some specific project'), 
(NULL, 'project:description::endDate:*', 'Can update the end date of some specific project'), 
(NULL, 'project:description:workplan:*', 'Can upload a project work plan of some specific project'), 
(NULL, 'project:description:bilateralContract:*', 'Can upload a bilateral contract proposal of some specific project'), 
(NULL, 'project:description:flagships:*', 'Can update the flagships linked to a some specific project'), 
(NULL, 'project:description:regions:*', 'Can update the end date of some specific project'), 
(NULL, 'project:partnerLead:update', 'Can update the Lead partner section of some specific project'), 
(NULL, 'project:ppaPartners:update', 'Can update the project PPA partners section of some specific project'), 
(NULL, 'project:partners:update', 'Can update the project partners section of some specific project'), 
(NULL, 'project:locations:update', 'Can update the project locations section of some specific project'), 
(NULL, 'project:outcomes:update', 'Can update the project outcomes section of some specific project'), 
(NULL, 'project:ipOtherContributions:update', 'Can update the project ip other contributions section of some specific project'),
(NULL, 'project:outputs:update', 'Can update the project outputs section of some specific project'), 
(NULL, 'project:activities:update', 'Can update the project activities list section of some specific project'), 
(NULL, 'project:budget:update', 'Can update the project budget section of some specific project');


INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='Admin' AND p.permission = '*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='FPL' AND p.permission = 'planning:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='RPL' AND p.permission = 'planning:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CU' AND p.permission = 'planning:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:projectList:bilateralProjectButton:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:projectList:submitButton:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:projectList:deleteProjectButton:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:description:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:description:bilateralContract:*';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:partnerLead:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:ppaPartners:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:partners:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:locations:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:outcomes:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:ipOtherContributions:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:outputs:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:activities:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='CP' AND p.permission = 'planning:projects:budget:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:description:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:partnerLead:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:ppaPartners:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:locations:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:outcomes:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:ipOtherContributions:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:outputs:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:activities:update';
INSERT INTO `role_permissions` (`role_id`, `permission_id`) SELECT r.id, p.id FROM permissions p, roles r WHERE r.acronym='PL' AND p.permission = 'project:budget:update';
