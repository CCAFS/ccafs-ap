-- Fixing permissions on activities
UPDATE `permissions` SET `permission`='planning:projects:activities:update' WHERE `permission`='planning:projects:activities:udpate';
UPDATE `permissions` SET `permission`='reporting:projects:activities:update' WHERE `permission`='reporting:projects:activities:udpate';
