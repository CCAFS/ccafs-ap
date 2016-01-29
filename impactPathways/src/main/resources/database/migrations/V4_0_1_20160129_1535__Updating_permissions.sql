---------------------------------------------------
--              Outcomes Case Studies
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:caseStudies%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:caseStudies:*', 'Can update the Outcomes Case Studies section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:caseStudies:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:caseStudies:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:caseStudies:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:caseStudies:*');


---------------------------------------------------
--              Overview by MOGs (outputs)
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:outputs%";

-- Updating permissions
UPDATE `permissions` SET `permission`='planning:projects:outputs:*', `description`='Can update the Overview by MOGs (outputs) section in the planning round.' WHERE `id`='23';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:outputs:*', 'Can update the Overview by MOGs (outputs) section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:outputs:*');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:outputs:*');


---------------------------------------------------
--                   Deliverable 
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:deliverable%";

-- Updating permissions
UPDATE `permissions` SET `permission`='planning:projects:deliverable:*', `description`='Can update the Deliverable section in the planning round.' WHERE `id`='49';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:deliverable:*', 'Can update the Deliverable section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverable:*');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverable:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverable:*');

---------------------------------------------------
--              Deliverables List
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:deliverablesList%";

-- Updating permissions
UPDATE `permissions` SET `permission`='planning:projects:deliverablesList:*', `description`='Can update the Deliverables List section in the planning round.' WHERE `id`='47';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:deliverablesList:*', 'Can update the Deliverables List section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:deliverablesList:*');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverablesList:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:deliverablesList:*');



---------------------------------------------------
--                   Next Users 
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:nextUsers%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:nextUsers:*', 'Can update the Next users section in the reporting round.');

-- Assingning roles. 
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:nextUsers:*');


---------------------------------------------------
--            Project Highlight (Single) 
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:highlight%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:highlight:*', 'Can update the Project Highlight section in the reporting round.');

-- Assingning roles. 
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlight:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlight:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlight:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlight:*');

---------------------------------------------------
--           Project Highlights (List)
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:highlights%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:highlights:*', 'Can update the Project Highlights List section in the reporting round.');

-- Assingning roles. 
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:highlights:*');


---------------------------------------------------
--                Project Activities
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:activities%";

-- Updating permissions
UPDATE `permissions` SET `permission`='planning:projects:activities:*', `description`='Can update the activities section in the planning round.' WHERE `id`='24';
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:activities:*', 'Can update the Project Activities section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:activities:*');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:activities:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:activities:*');


---------------------------------------------------
--                Leverages
---------------------------------------------------

-- Deleting permissions refered to projects list.
DELETE rp FROM role_permissions rp 
INNER JOIN permissions p ON rp.permission_id = p.id
WHERE p.permission like "%projects:leverages%";

-- Updating permissions
INSERT INTO `permissions` (`permission`, `description`) VALUES ('reporting:projects:leverages:*', 'Can update the Leverages section in the reporting round.');

-- Assingning roles.
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:leverages:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:leverages:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:leverages:*');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:leverages:*');

