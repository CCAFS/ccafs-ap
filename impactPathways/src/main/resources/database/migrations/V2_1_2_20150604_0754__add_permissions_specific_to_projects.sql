-- -----------------------------------------------------------------------------
-- These are permission specific to projects.
-- -----------------------------------------------------------------------------

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES 
(NULL, 'project:projectInfo:read', 'Can read the project''s information section'), 
(NULL, 'project:projectInfo:update', 'Can update the project''s information section'), 
(NULL, 'project:projectPartners:read', 'Can read the project partners section'), 
(NULL, 'project:projectPartners:update', 'Can update the project partners section'), 
(NULL, 'project:projectLocations:read', 'Can read the project location section'), 
(NULL, 'project:projectLocations:update', 'Can update the project location section'), 
(NULL, 'project:projectOutcomes:read', 'Can read the project outcomes section'), 
(NULL, 'project:projectOutcomes:update', 'Can update the project outcomes section'), 
(NULL, 'project:projectOutputs:read', 'Can read the project outputs section'), 
(NULL, 'project:projectOutputs:update', 'Can update the project outputs section'), 
(NULL, 'project:projectActivitiesList:read', 'Can read the project activities list section '), 
(NULL, 'project:projectActivitiesList:update', 'Can update the project activities list section '), 
(NULL, 'project:projectActivities:read', 'Can read the project activity section'), 
(NULL, 'project:projectActivities:update', 'Can update the project activity section'), 
(NULL, 'project:projectBudget:read', 'Can read the project budget section '), 
(NULL, 'project:projectBudget:update', 'Can update the project budget section ');

INSERT INTO `roles` (`id`, `name`, `acronym`) VALUES (NULL, 'Project contributos', 'PC');

INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectInfo:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectInfo:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectPartners:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectPartners:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectLocations:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectLocations:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectOutcomes:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectOutcomes:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectOutputs:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectOutputs:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectActivitiesList:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectActivitiesList:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectActivities:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectActivities:update' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectBudget:read' AND r.acronym='PC';
INSERT INTO `role_permissions` (`permission_id`, `role_id`) SELECT p.id, r.id FROM permissions p, roles r WHERE p.permission = 'project:projectBudget:update' AND r.acronym='PC';
