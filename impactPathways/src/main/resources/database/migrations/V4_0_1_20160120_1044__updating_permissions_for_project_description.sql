-- Updating names for some roles.
UPDATE `roles` SET `acronym`='FPL' WHERE `id`='12';
UPDATE `roles` SET `acronym`='Finance' WHERE `id`='10';
UPDATE `roles` SET `acronym`='RPL' WHERE `id`='11';

-- Deleting all permissions refered to "Project Description" section in planning and reporting
DELETE rp FROM role_permissions rp
INNER JOIN permissions p ON rp.permission_id = p.id
INNER JOIN roles r ON r.id = rp.role_id
WHERE p.permission like "%projects:description:%";

-- Updating and adding new permissions in planning section.
UPDATE `permissions` SET `permission`='planning:projects:description:startDate' WHERE `id`='11';
UPDATE `permissions` SET `permission`='planning:projects:description:endDate' WHERE `id`='12';
UPDATE `permissions` SET `permission`='planning:projects:description:managementLiaison' WHERE `id`='10';
UPDATE `permissions` SET `permission`='planning:projects:description:workplan', `description`='Can update the project status in reporting section' WHERE `id`='13';
UPDATE `permissions` SET `permission`='planning:projects:description:bilateralContract' WHERE `id`='14';
UPDATE `permissions` SET `permission`='planning:projects:description:flagships' WHERE `id`='15';
UPDATE `permissions` SET `permission`='planning:projects:description:regions' WHERE `id`='16';
UPDATE `permissions` SET `description`='Can update the planning project information section' WHERE `id`='9';
UPDATE `permissions` SET `description`='Can update the whole planning project description information section' WHERE `id`='8';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:description:summary', 'Can update the project summary in planning section');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:description:title', 'Can update the project title in planning section');
UPDATE `permissions` SET `description`='Can update only the fields about the project status in reporting section.' WHERE `id`='71';
UPDATE `permissions` SET `description`='Can update all the reporting section in project description' WHERE `id`='70';

-- Adding new permissions in reporting section.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:annualReportDonor', 'Can upload the report to the donor (bilateral project) in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:bilateralContract', 'Can update the bilateral contract in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:endDate', 'Can update the field end date in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:flagships', 'Can update the flagships selections in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:managementLiaison', 'Can update the ML in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:regions', 'Can update the regions selections in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:startDate', 'Can update the field start date in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:statusDescription', 'Can update the project status in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:summary', 'Can update the project summary in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:title', 'Can update the project title in reporting round');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:update', 'Can update the project description section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:description:workplan', 'Can upload the workplan (ccafs projects) in reporting round.');


