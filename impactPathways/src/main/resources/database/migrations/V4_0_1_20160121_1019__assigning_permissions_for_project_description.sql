-- Assigning roles to ML.
INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:*');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'ML'),
permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:*');

-- Assigning roles to CP.
INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:*');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'CP'),
permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:*');

-- Assigning roles to PL.
INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:bilateralContract');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:summary');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:summary');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:title');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:update');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:workplan');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:annualReportDonor');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:status');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:statusDescription');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PL'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:update');

-- Assigning permissions to PC.
INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:bilateralContract');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:summary');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:summary');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:title');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:update');

INSERT INTO role_permissions 
SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'),
permission_id = (SELECT id FROM permissions WHERE permission = 'planning:projects:description:workplan');

INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:annualReportDonor');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:status');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:statusDescription');
INSERT INTO role_permissions SET role_id = (SELECT id FROM roles WHERE acronym = 'PC'), permission_id = (SELECT id FROM permissions WHERE permission = 'reporting:projects:description:update');


