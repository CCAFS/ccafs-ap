----------------------------------------------------------------------------
-- Removing the regional Major Output Groups
----------------------------------------------------------------------------

DELETE ie.* FROM `ip_elements` ie 
INNER JOIN ip_programs ip ON ie.ip_program_id = ip.id 
WHERE `element_type_id` = 4 AND ip.type_id = 5;