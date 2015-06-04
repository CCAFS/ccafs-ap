-- Removing one contact name and letting only the first one.
-- Before: Julie Ojango & John Goopy - J.Ojango@cgiar.org; j.goopy@cgiar.org
UPDATE `project_partners` SET `contact_name`='Julie Ojango', `contact_email`='J.Ojango@cgiar.org' WHERE `id`='286';

-- Creating inactive users
INSERT INTO users(first_name, email, password, is_ccafs_user, created_by, is_active)
SELECT pp.contact_name, pp.contact_email, " ", 0, 1, 0 FROM project_partners pp WHERE pp.user_id IS NULL AND pp.contact_email != "";

-- Fixing first names and last names
UPDATE `users` SET `first_name`='V Padmma', `last_name`='Kumar' WHERE `email`='v.padmakumar@cgiar.org';
UPDATE `users` SET `first_name`='J K', `last_name`='Lada' WHERE `email`='j.k.ladha@irri.org';
UPDATE `users` SET `first_name`='Ronnie', `last_name`='Vernooy' WHERE `email`='r.vernooy@cgiar.org';
UPDATE `users` SET `first_name`='Christopher', `last_name`='Wheatley' WHERE `email`='c.wheatley@cgiar.org';
UPDATE `users` SET `first_name`='Steve', `last_name`='Staal' WHERE `email`='s.staal@cgiar.org';
UPDATE `users` SET `first_name`='Reiner', `last_name`='Wassman' WHERE `email`='r.wassman@cgiar.org';
UPDATE `users` SET `first_name`='Jenny', `last_name`='Ordoñez' WHERE `email`='j.ordonez@cgiar.org';
UPDATE `users` SET `first_name`='Guillaume', `last_name`='Lacombe' WHERE `email`='G.Lacombe@cgiar.org';
UPDATE `users` SET `first_name`='Julie', `last_name`='Ojango' WHERE `email`='J.Ojango@cgiar.org';
UPDATE `users` SET `first_name`='Elliot', `last_name`='Vhurumuku' WHERE `email`='elliot.vhurumuku@wfp.org';
UPDATE `users` SET `first_name`='Lini', `last_name`='Wollenberg' WHERE `email`='ewollenb@uvm.edu';

-- Fixing error for Wassmann.
UPDATE `project_partners` SET `contact_email`='r.wassmann@irri.org', `user_id`='108' WHERE `id`='60';
DELETE FROM `users` WHERE `email`='r.wassman@cgiar.org';

-- Fixing error for Lini.
UPDATE `project_partners` SET `contact_email`='lini.wollenberg@uvm.edu', `user_id`='31' WHERE `id`='486';
DELETE FROM `users` WHERE `email`='ewollenb@uvm.edu';

-- Applying Guest role for those users.
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'v.padmakumar@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'j.k.ladha@irri.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'r.vernooy@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'c.wheatley@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 's.staal@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'j.ordonez@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'G.Lacombe@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'J.Ojango@cgiar.org';
INSERT INTO user_roles(user_id, role_id) SELECT u.id, 8 FROM users u WHERE email = 'elliot.vhurumuku@wfp.org';

-- Updating project_partners references to users table
UPDATE project_partners pp
INNER JOIN users u ON u.email = pp.contact_email
SET pp.user_id = u.id
WHERE pp.user_id IS NULL AND pp.contact_email != "";

-- Adding deatilas for Sandra Russo
UPDATE `project_partners` SET `contact_email`='srusso@ufic.ufl.edu' WHERE `id`='494';
INSERT INTO `users` (`first_name`, `last_name`, `email`, `password`, `is_ccafs_user`, `created_by`, `is_active`) 
VALUES ('Sandra', 'Russo', 'srusso@ufic.ufl.edu', ' ', '0', '1', '0');
INSERT INTO `user_roles` (`user_id`, `role_id`) SELECT u.id, 8 FROM users u WHERE u.email = 'srusso@ufic.ufl.edu';
UPDATE project_partners pp INNER JOIN users u ON u.email = pp.contact_email SET pp.contact_email=u.email,
pp.user_id=u.id WHERE u.email='srusso@ufic.ufl.edu';

