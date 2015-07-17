-- -----------------------------------------------------------------------------
--        Moving is_global field in projects
-- -----------------------------------------------------------------------------

ALTER TABLE `linked_core_projects` RENAME TO  `project_cofinancing_linkages` ;
