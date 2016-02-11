-- CCAFS Outcomes

-- -- Deleting permissions refered to project partners.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:otherContributions%";

-- -- Inserting new permissions.
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:update', 'Can make changes in the other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:contribution', 'Can update how is contributing in other contributuions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:otherContributionIndicator', 'Can update the indicator in other contributions section un planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:otherContributionDescription', 'Can update the description of the contribution to the indicator in other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:otherContributionTarget', 'Can update the target in other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:additionalContribution', 'Can update the contribuition to another center activity in other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:natureCollaboration', 'Can update the nature collaboration in other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:explainAchieved', 'Can update the ahieved outcome in other contributions section in planning round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:otherContributions:addCRPContribution', 'Can add new CRP in other contributions section in planning round.');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:update', 'Can make changes in the other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:contribution', 'Can update how is contributing in other contributuions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:otherContributionIndicator', 'Can update the indicator in other contributions section un reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:otherContributionDescription', 'Can update the description of the contribution to the indicator in other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:otherContributionTarget', 'Can update the target in other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:additionalContribution', 'Can update the contribuition to another center activity in other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:natureCollaboration', 'Can update the nature collaboration in other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:explainAchieved', 'Can update the ahieved outcome in other contributions section in reporting round.');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:otherContributions:addCRPContribution', 'Can add new CRP in other contributions section in reporting round.');

-- --Assigning permissions.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:contribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:contribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:contribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:contribution');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:natureCollaboration');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:natureCollaboration');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:natureCollaboration');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:natureCollaboration');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:addCRPContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:addCRPContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:addCRPContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:otherContributions:addCRPContribution');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:update');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:update');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionIndicator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionIndicator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionIndicator');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionIndicator');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionDescription');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionDescription');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionDescription');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionDescription');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionTarget');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionTarget');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionTarget');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:otherContributionTarget');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:additionalContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:additionalContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:additionalContribution');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:additionalContribution');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:explainAchieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:explainAchieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:explainAchieved');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:otherContributions:explainAchieved');
