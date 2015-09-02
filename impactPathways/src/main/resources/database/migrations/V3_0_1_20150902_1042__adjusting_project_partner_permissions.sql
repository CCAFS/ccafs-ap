----------------------------------------------------------------------------
-- Adjusting the permissions used in the project partner section
----------------------------------------------------------------------------

DELETE FROM `permissions` WHERE `permissions`.`permission` = 'planning:projects:partnerLead:update';
DELETE FROM `permissions` WHERE `permissions`.`permission` = 'planning:projects:ppaPartners:update';

INSERT INTO `permissions` (permission, description) VALUES ('planning:projects:partners:leader:update', 'Can update the project leader of a project');
INSERT INTO `permissions` (permission, description) VALUES ('planning:projects:partners:ppa:update', 'Can update the PPA partners of a project');
