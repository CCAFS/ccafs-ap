----------------------------------------------------------------------------
-- Addding the unique key to the table project_partner_persons
----------------------------------------------------------------------------

ALTER TABLE `project_partner_persons` 
ADD UNIQUE INDEX `UK_project_partner_persons` (`project_partner_id` ASC, `user_id` ASC) ;
