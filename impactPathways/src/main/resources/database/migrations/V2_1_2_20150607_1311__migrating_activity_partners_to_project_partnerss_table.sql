-- Adding new column in project_partners table to differenciate those records that are going to be migrated from 
-- activity_partners table.
ALTER TABLE `project_partners` 
ADD COLUMN `activity_partner` TINYINT(1) NOT NULL DEFAULT 0 AFTER `partner_type`;

-- Adding new column in activity_partners to reference the users table.
ALTER TABLE `activity_partners` 
ADD COLUMN `user_id` BIGINT(20) NULL AFTER `contact_email`;

-- Deleting information from a specific project partner that was filled incorrectly (****).
UPDATE `activity_partners` SET `contact_name`='', `contact_email`='', `contribution`='' WHERE `id`='4704';

-- Deleting information from partners that were filled as "tbc".
UPDATE `activity_partners` SET `contact_name`='', `contact_email`='' WHERE contact_email = 'tbc';

-- Manual Checking and fixing partners that were filled incorrectly
UPDATE `activity_partners` SET `contact_name`='Arsenio Balisacan', `contact_email`='AMBalisacan@neda.gov.ph' WHERE `id`='3839';
UPDATE `activity_partners` SET `contact_name`='Arlene Inocencio', `contact_email`='arlene.inocencio@dlsu.edu.ph' WHERE `id`='3840';
UPDATE `activity_partners` SET `contact_name`='Charles Kariuki', `contact_email`='charles.kariuki@kalro.org' WHERE `id`='1390';
UPDATE `activity_partners` SET `contact_email`='chmaikut@gmail.com' WHERE `id`='3407';
UPDATE `activity_partners` SET `contact_email`='chmaikut@gmail.com' WHERE `id`='3532';
UPDATE `activity_partners` SET `contact_email`='chmaikut@gmail.com' WHERE `id`='4700';
UPDATE `activity_partners` SET `contact_name`='Cesar Sotomayor', `contact_email`='csotomayor@minagri.gob' WHERE `id`='4467';
UPDATE `activity_partners` SET `contact_email`='dksharma@cssri.ernet.in' WHERE `id`='4554';
UPDATE `activity_partners` SET `contact_name`='Felino P. Lansigan', `contact_email`='fplansigan@yahoo.com' WHERE `id`='3843';
UPDATE `activity_partners` SET `contact_email`='majimenezcr@gmail.com' WHERE `id`='3946';
UPDATE `activity_partners` SET `contact_name`='Merceditas A. Sombilla', `contact_email`='MASombilla@neda.gov.ph' WHERE `id`='2437';
UPDATE `activity_partners` SET `contact_email`='mchacon@mag.go.cr' WHERE `id`='4511';
UPDATE `activity_partners` SET `contact_email`='mchacon@mag.go.cr' WHERE `id`='4517';
UPDATE `activity_partners` SET `contact_email`='mchacon@mag.go.cr' WHERE `id`='4686';
UPDATE `activity_partners` SET `contact_name`='Majah-Leah Ravago', `contact_email`='mvravago@econ.upd.edu.ph' WHERE `id`='3841';
UPDATE `activity_partners` SET `contact_name`='', `contact_email`='' WHERE `id`='4403';
UPDATE `activity_partners` SET `contact_email`='obedt@nfa.org.ug' WHERE `id`='2368';
UPDATE `activity_partners` SET `contact_name`='', `contact_email`='', `user_id`='115' WHERE `id`='3314';
UPDATE `activity_partners` SET `contact_name`='', `contact_email`='' WHERE `id`='4331';
UPDATE `activity_partners` SET `contact_name`='Segfredo Serrano' WHERE `id`='3208';
UPDATE `activity_partners` SET `contact_name`='Segfredo Serrano' WHERE `id`='3844';
UPDATE `activity_partners` SET `contact_name`='Manuel Gerochi' WHERE `id`='3290';
UPDATE `activity_partners` SET `contact_name`='Manuel Gerochi' WHERE `id`='3845';
UPDATE `activity_partners` SET `contact_email`='yanda@ira.udsm.ac.tz' WHERE `id`='3538';
UPDATE `activity_partners` SET `contact_email`='satraore@ecowas.int' WHERE `id`='87';

UPDATE `activity_partners` SET `contact_name`='Ashish Mondal' WHERE `id`='3449';
UPDATE `activity_partners` SET `contact_name`='Ashish Mondal' WHERE `id`='1954';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4714';
UPDATE `activity_partners` SET `contact_email`='bharvey@idrc.ca' WHERE `id`='4163';
UPDATE `activity_partners` SET `contact_email`='bounthong@live.com.au' WHERE `id`='4744';
UPDATE `activity_partners` SET `contact_name`='Anousith Keophoxay' WHERE `id`='2037';
UPDATE `activity_partners` SET `contact_name`='Anousith Keophoxay' WHERE `id`='4267';
UPDATE `activity_partners` SET `contact_name`='Anousith Keophoxay' WHERE `id`='4462';
UPDATE `activity_partners` SET `contact_email`='daoudad91@yahoo.fr' WHERE `id`='4345';
UPDATE `activity_partners` SET `contact_name`='Jiban Krishna Biswas' WHERE `id`='4099';
UPDATE `activity_partners` SET `contact_name`='Jiban Krishna Biswas' WHERE `id`='4119';
UPDATE `activity_partners` SET `contact_name`='Md. Abbas Ali' WHERE `id`='4120';
UPDATE `activity_partners` SET `contact_name`='Md. Khaled Sultan' WHERE `id`='4100';
UPDATE `activity_partners` SET `contact_name`='Rovi Gopal' WHERE `id`='4101';
UPDATE `activity_partners` SET `contact_name`='Douglas Beare' WHERE `id`='2507';
UPDATE `activity_partners` SET `contact_name`='Dr. K.V. Prabhu' WHERE `id`='1953';
UPDATE `activity_partners` SET `contact_name`='Dr. Prakash Tyagi' WHERE `id`='1955';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4716';
UPDATE `activity_partners` SET `contact_email`='MacleanEgesa.Mangeni@heifer.org' WHERE `id`='2500';
UPDATE `activity_partners` SET `contact_name`='Elizabeth Msoka' WHERE `id`='4218';
UPDATE `activity_partners` SET `contact_name`='Elizabeth Msoka' WHERE `id`='4398';
UPDATE `activity_partners` SET `contact_email`='gwamukoya@comesa.int' WHERE `id`='3009';
UPDATE `activity_partners` SET `contact_email`='gwamukoya@comesa.int' WHERE `id`='3616';
UPDATE `activity_partners` SET `contact_email`='James.Butler@csiro.au' WHERE `id`='4160';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4734';
UPDATE `activity_partners` SET `contact_email`='pjjoseph@aicofindia.com' WHERE `id`='4678';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4719';
UPDATE `activity_partners` SET `contact_email`='m.vanzonneveld@cgiar.org', `user_id`='57' WHERE `id`='3454';
UPDATE `activity_partners` SET `contact_email`='manuel.jimenez@iita.int' WHERE `id`='3946';
UPDATE `activity_partners` SET `user_id`='32' WHERE `id`='4599';
UPDATE `activity_partners` SET `contact_email`='nanghsenghom@gmail.com' WHERE `id`='2160';
UPDATE `activity_partners` SET `contact_email`='nvivas@gmail.com' WHERE `id`='4512';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4722';
UPDATE `activity_partners` SET `contact_email`='ondiaye70@gmail.com' WHERE `id`='3270';
UPDATE `activity_partners` SET `contact_name`='Palikone Thalongsengchanh' WHERE `id`='4264';
UPDATE `activity_partners` SET `contact_email`='p.vanasten@cgiar.org', `user_id`='81' WHERE `id`='2726';
UPDATE `activity_partners` SET `contact_email`='p.vanasten@cgiar.org', `user_id`='81' WHERE `id`='4293';
UPDATE `activity_partners` SET `contact_email`='r.kapoor@heifer.org' WHERE `id`='4348';
UPDATE `activity_partners` SET `contact_email`='r.wassmann@irri.org', `user_id`='108' WHERE `id`='3323';
UPDATE `activity_partners` SET `contact_email`='r.wassmann@irri.org', `user_id`='108' WHERE `id`='3315';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4720';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4723';
UPDATE `activity_partners` SET `contact_email`='S.Traore@agrhymet.ne' WHERE `id`='1431';
UPDATE `activity_partners` SET `contact_email`='S.Traore@agrhymet.ne' WHERE `id`='4727';
UPDATE `activity_partners` SET `contact_email`='wsolano@catie.ac.cr' WHERE `id`='4487';
UPDATE `activity_partners` SET `contact_name`='' WHERE `id`='4725';

-- Identifying which users already exist in the users table and reference them into the user_id column in
-- activity_partners. 
UPDATE activity_partners ap
SET ap.user_id =
CASE -- START CASE
  WHEN (SELECT COUNT(u.id) FROM users u WHERE u.email = ap.contact_email) = 1
  THEN (SELECT u.id FROM users u WHERE u.email = ap.contact_email)
  ELSE NULL
END -- END CASE
WHERE ap.user_id IS NULL; 


-- Creating inactive users in the users table.
INSERT INTO users(first_name, email, password, is_ccafs_user, created_by, is_active)
SELECT ap.contact_name, ap.contact_email, " ", 0, 1, 0 
FROM activity_partners ap 
WHERE ap.user_id IS NULL 
AND ap.contact_email != ""
GROUP BY ap.contact_email;

-- Assigning guest role for those new users.
INSERT INTO user_roles (user_id, role_id)
SELECT id, 8 FROM users WHERE last_name IS NULL;

-- Now that new users were added into the database, we need to identify again which users already exist in the users 
-- table and reference them again into the user_id column in activity_partners. 
UPDATE activity_partners ap
SET ap.user_id =
CASE -- START CASE
  WHEN (SELECT COUNT(u.id) FROM users u WHERE u.email = ap.contact_email) = 1
  THEN (SELECT u.id FROM users u WHERE u.email = ap.contact_email)
  ELSE NULL
END -- END CASE
WHERE ap.user_id IS NULL; 

-- Now is time to migrate the information to project level
INSERT INTO project_partners 
(project_id, partner_id, user_id, partner_type, activity_partner, responsabilities, is_active, active_since,
created_by, modified_by, modification_justification)
SELECT p.id, ap.institution_id, ap.user_id, 'PP', 1,  CONCAT("Activity 2014-", a.id , " *Partner*. ")  , ap.is_active, ap.active_since,
ap.created_by, ap.modified_by, ''
FROM activity_partners ap
INNER JOIN activities a ON a.id = ap.activity_id
INNER JOIN projects p ON p.id = a.project_id

-- After this insertion process, we have some project partners where their institutions are repeteated on the same 
-- project. A process to fix that problems will be required.

