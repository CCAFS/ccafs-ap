-- -----------------------------------------------------------------------------
--        Inserting new permissions for project outputs section menu
-- -----------------------------------------------------------------------------

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'project:deliverables:update', 'Can update the project deliverables section of some specific project');
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'planning:projects:deliverables:update', 'Can update the planning project deliverables section');