-- Inserting User Roles for FPLs:
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('o.bonilla@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('a.jarvis@CGIAR.ORG'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('jhansen@iri.columbia.edu'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('kcoffey@iri.columbia.edu'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('p.henderson@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('Lini.wollenberg@uvm.edu'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('meryl.richards@uvm.edu'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('jwhite19@uvm.edu'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('w.foerch@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('p.thornton@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('l.cramer@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'FPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('i.vasileiou@cgiar.org'));

-- Inserting User Roles for RPLs:
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('j.kinyangi@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('M.Radeny@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('d.m.baron@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('a.m.loboguerrero@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('P.K.Aggarwal@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('A.Khatri-Chhetri@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('l.sebastian@irri.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('y.bui@irri.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('R.Zougmore@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('S.Partey@cgiar.org'));
INSERT INTO user_roles SET role_id = (SELECT id FROM roles WHERE acronym = 'RPL'), user_id = (SELECT id FROM users WHERE LOWER(email) = LOWER('a.s.Moussa@cgiar.org'));
