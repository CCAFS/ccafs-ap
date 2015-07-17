
--
-- Platform permissions
--

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES
(1, '*', 'Full privileges to all the platform'),
(2, 'planning:read', 'Can see all the planning contents'),
(3, 'planning:*', 'Can update all the planning contents'),
(4, 'planning:projectList:read', 'Can read the projects list in the planning section'),
(5, 'planning:projectList:*', 'Can use all the functions in the planning projects list'),
(6, 'planning:projectList:coreProjectButton:*', 'Can use the "add core project" button in the planning section'),
(7, 'planning:projectList:bilateralProjectButton:*', 'Can use the "add bilateral project" button in the planning section'),
(8, 'planning:projectList:submitButton:*', 'Can use the "Submit" button in the planning section'),
(9, 'planning:projectList:deleteProjectButton:*', 'Can use the "Delete project" button in the planning section'),
(10, 'planning:projectInfo:read', 'Can read the planning project information section'),
(11, 'planning:projectInfo:update', 'Can read the planning project information section'),
(12, 'planning:projectInfo:*', 'Can read the planning project information section'),
(13, 'planning:projectInfo:managementLiaison:update', 'Can update the management liaison of a project in the planning section'),
(14, 'planning:projectInfo:startDate:update', 'Can update the start date of a project in the planning section'),
(15, 'planning:projectInfo:workplan:*', 'Can upload a project work plan for a project in the planning section'),
(16, 'planning:projectInfo:bilateralContract:*', 'Can upload a bilateral contract proposal for a project in the planning section'),
(17, 'planning:projectInfo:flagships:*', 'Can update the flagships linked to a project in the planning section'),
(18, 'planning:projectInfo:regions:update', 'Can update the end date of a project in the planning section'),
(19, 'planning:projectInfo:endDate:update', 'Can update the end date of a project in the planning section'),
(20, 'planning:projectPartners:read', 'Can read the planning project partners section'),
(21, 'planning:projectPartners:update', 'Can update the planning project partners section'),
(22, 'planning:projectLocations:read', 'Can read the planning project locations section'),
(23, 'planning:projectLocations:update', 'Can update the planning project locations section'),
(24, 'planning:projectOutcomes:read', 'Can read the planning project outcomes section'),
(25, 'planning:projectOutcomes:update', 'Can update the planning project outcomes section'),
(26, 'planning:projectOutputs:read', 'Can read the planning project outputs section'),
(27, 'planning:projectOutputs:update', 'Can update the planning project outputs section'),
(28, 'planning:projectActivitiesList:read', 'Can read the planning project activities list section'),
(29, 'planning:projectActivitiesList:update', 'Can update the planning project activities list section'),
(30, 'planning:projectActivities:read', 'Can read the planning project activities section'),
(31, 'planning:projectActivities:update', 'Can update the planning project activities section'),
(32, 'planning:projectBudget:read', 'Can read the planning project budget section'),
(33, 'planning:projectBudget:update', 'Can update the planning project budget section');

--
-- Assign permission to each role
--

INSERT INTO `role_permissions` (`id`, `role_id`, `permission_id`) VALUES
(10, 1, 1),
(11, 2, 3),
(12, 4, 7),
(13, 4, 11),
(14, 4, 21),
(15, 4, 23),
(16, 4, 25),
(17, 4, 27),
(18, 4, 28),
(19, 4, 31),
(20, 4, 33),
(21, 3, 3),
(22, 5, 2),
(23, 6, 3),
(24, 8, 2),
(25, 7, 2),
(26, 7, 6),
(27, 7, 8),
(28, 7, 9),
(29, 7, 11),
(30, 7, 21),
(31, 7, 23),
(32, 7, 25),
(33, 7, 27),
(34, 7, 29),
(35, 7, 31),
(36, 7, 33);
