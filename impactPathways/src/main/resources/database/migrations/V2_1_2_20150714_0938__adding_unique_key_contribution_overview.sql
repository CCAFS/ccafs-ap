-- -----------------------------------------------------------------------------------
-- Adding a unique key in the table ip_project_contribution_overviews
-- -----------------------------------------------------------------------------------
ALTER TABLE `ip_project_contribution_overviews` 
ADD UNIQUE INDEX `UK_ip_project_contribution_overviews` (`project_id` ASC, `output_id` ASC, `year` ASC) ;
