-- -----------------------------------------------------------------------------
--                         Adding Permission to add co-funded projects
-- -----------------------------------------------------------------------------

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'planning:projects:addCoFundedProject:*', 'Can use all the functions adding a Co-Funded Project');