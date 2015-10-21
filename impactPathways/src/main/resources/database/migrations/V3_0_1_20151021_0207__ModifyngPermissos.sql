----------------------------------------------------------------------------
--    Removing 'gender' and 'stories' columns from project_outcomes table
--    keeping the history log.
----------------------------------------------------------------------------

delete 
from role_permissions where id IN (2,4);

insert into role_permissions (role_id,permission_id) 
select 2 as role_id ,id 
from permissions where id in (5,24,53,25,51,44,49,47,8,20,22,21,23,19,62,6)
union
select 6 as role_id,id from permissions where id in (5,24,53,25,51,44,49,47,8,20,22,21,23,19,62,6)
;

