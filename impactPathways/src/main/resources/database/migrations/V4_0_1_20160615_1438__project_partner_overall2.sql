delete from project_partner_overall;

insert INTO project_partner_overall (project_partner_id,year,overall)
select id,2015,case  when overall is null then '' else overall end 
from project_partners
where is_active=1;

ALTER TABLE `project_partners`
DROP COLUMN `overall`;

