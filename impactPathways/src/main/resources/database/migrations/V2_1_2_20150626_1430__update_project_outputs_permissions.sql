-- -----------------------------------------------------------------------------
--        Update permission name for project outputs section menu
-- -----------------------------------------------------------------------------

UPDATE `permissions` SET `permission` = 'planning:projects:deliverablesList:update' WHERE `permissions`.`permission` = 'planning:projects:deliverables:update';
UPDATE `permissions` SET `permission` = 'project:deliverablesList:update' WHERE `permissions`.`permission` = 'project:deliverables:update';

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'project:deliverable:update', 'Can update the project deliverable section of some specific project');
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'planning:projects:deliverable:update', 'Can update the planning project deliverable section');