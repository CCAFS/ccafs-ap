-- -----------------------------------------------------------------------------
--       Adding new permissions to be used in the project budget section 
-- -----------------------------------------------------------------------------

INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:budget:annualW1w2:update', 'Can update the W1/W2 budget in the planning project budget section');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('planning:projects:budget:annualBilateral:update', 'Can update the W3/Bilateral budget in the planning project budget section');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('project:budget:annualW1w2:update', 'Can update the W1/W2 budget in the project budget section of some specific project');
INSERT INTO `permissions` (`permission`, `description`) VALUES ('projects:budget:annualBilateral:update', 'Can update the W3/Bilateral budget in the project budget section of some specific project');
