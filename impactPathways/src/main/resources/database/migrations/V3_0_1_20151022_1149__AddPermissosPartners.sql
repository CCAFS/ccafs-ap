----------------------------------------------------------------------------


--    ADD actually permissions for ML y CU and add newest 

-------------------------

INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:partner:leader:update', 'Can update the planning project partners leader');

INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:partner:cordinator:update', 'Can update the planning project partners cordinator');


insert into role_permissions (role_id,permission_id) 
select distinct  rol.id,pe.id
from roles rol , permissions pe 
where rol.id in (2,10,4)
and permission in ('planning:projects:partner:cordinator:update','planning:projects:partner:leader:update')
UNION
select distinct  rol.id,pe.id
from roles rol , permissions pe 
where rol.id in (7)
and permission in ('planning:projects:partner:cordinator:update');