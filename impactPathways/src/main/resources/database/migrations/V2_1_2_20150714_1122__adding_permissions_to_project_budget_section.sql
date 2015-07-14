-- -----------------------------------------------------------------------------------
-- Add permissions to project budget section
-- -----------------------------------------------------------------------------------

-- Adding permissions
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'project:budgetByMog:update', 'Can update the project budget by MOG section of some specific project');
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'planning:projects:budgetByMog:update', 'Can update the planning project budget by MOG section');

-- Adding permission to role PL
INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) VALUES (NULL, '7', '50');
INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) VALUES (NULL, '7', '45');
INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) VALUES (NULL, '7', '48');
INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) VALUES (NULL, '7', '46');