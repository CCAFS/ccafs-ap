-----------------------------------------------------------------------
--      Updating the permissions that are not a project level.
-----------------------------------------------------------------------
UPDATE `permissions` SET `permission`='planning:projectList:*' WHERE `id`='3';
UPDATE `permissions` SET `permission`='planning:projectList:coreProjectButton:*' WHERE `id`='4';
UPDATE `permissions` SET `permission`='planning:projectList:addCoFundedProject:*' WHERE `id`='56';
UPDATE `permissions` SET `permission`='planning:projectList:bilateralProjectButton:*' WHERE `id`='5';
UPDATE `permissions` SET `permission`='planning:projectList:submitButton:*' WHERE `id`='6';
UPDATE `permissions` SET `permission`='planning:projectList:deleteProjectButton:*' WHERE `id`='7';
