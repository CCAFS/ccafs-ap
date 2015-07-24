ALTER TABLE `projects` 
  ADD COLUMN `workplan_name` VARCHAR(255) NULL  AFTER `requires_workplan_upload` , 
  ADD COLUMN `bilateral_contract_name` VARCHAR(255) NULL  AFTER `workplan_name` ;
