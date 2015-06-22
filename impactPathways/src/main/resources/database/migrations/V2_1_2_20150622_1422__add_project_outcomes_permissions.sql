-- -----------------------------------------------------------------------------
--        Add new permission to the new section into Project Outcomes
-- -----------------------------------------------------------------------------

UPDATE `permissions` SET `permission` = 'planning:projects:otherContributions:update' WHERE `permissions`.`id` = 22;
UPDATE `permissions` SET `permission` = 'project:otherContributions:update' WHERE `permissions`.`id` = 40;

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'planning:projects:ccafsOutcomes:update', 'Can update the planning project CCAFS outcomes section');
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'project:ccafsOutcomes:update', 'Can update the project CCAFS outcomes section of some specific project');