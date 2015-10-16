--------------------------------------------
-- Modifying Project submit permission from projectList to projects level. 
--------------------------------------------

UPDATE `permissions` SET `permission`='planning:projects:submitButton:*' WHERE `permission`='planning:projectList:submitButton:*';