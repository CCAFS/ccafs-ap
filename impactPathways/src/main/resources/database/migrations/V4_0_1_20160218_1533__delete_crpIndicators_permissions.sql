-- -- Delete RPL CRP Indicators permission

DELETE FROM `role_permissions` WHERE role_id = (select id from roles where acronym = 'RPL') and permission_id = (select id from permissions where permission = 'reporting:synthesis:crpIndicators:*');