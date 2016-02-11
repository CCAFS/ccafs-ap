ALTER TABLE `ip_project_contribution_overviews`
ADD COLUMN `brief_summary`  text NULL AFTER `anual_contribution`,
ADD COLUMN `summary_gender`  text  NULL AFTER `gender_contribution`;