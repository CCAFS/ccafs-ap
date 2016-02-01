-- CCAFS Outcomes

-- -- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:ccafsOutcomes%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:update', 'Can make changes in the ccafs outcomes section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:target', 'Can update the target value in ccafs outcomes section in plannnig round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:achieved', 'Can update the target achieved in ccafs outcomes section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:description', 'Can update the expected annual contribution in ccafs outcomes section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:narrativeTargets', 'Can update the annual contribution achieved in ccafs outcomes section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:gender', 'Can update the expected gender contribution in ccafs outcomes section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:ccafsOutcomes:narrativeGender', 'Can update the gender contribution achieved in ccafs outcomes section in planning round.');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:update', 'Can make changes in the ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:target', 'Can update the target value in ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:achieved', 'Can update the target achieved in ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:description', 'Can update the expected annual contribution in ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:narrativeTargets', 'Can update the annual contribution achieved in ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:gender', 'Can update the expected gender contribution in ccafs outcomes section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:ccafsOutcomes:narrativeGender', 'Can update the gender contribution achieved in ccafs outcomes section in reporting round.');


-- --Assigning permissions.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:target');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:target');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:target');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:target');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:description');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:description');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:description');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:description');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:gender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:gender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:gender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:ccafsOutcomes:gender');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:achieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:achieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:achieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:achieved');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeTargets');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeTargets');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeTargets');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeTargets');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeGender');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:ccafsOutcomes:narrativeGender');
