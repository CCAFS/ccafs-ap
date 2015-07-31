-- ***********************
-- Improving flagship names.
-- ***********************
UPDATE `liaison_institutions` SET `name`='Flagship 1' WHERE `id`='2';
UPDATE `liaison_institutions` SET `name`='Flagship 2' WHERE `id`='3';
UPDATE `liaison_institutions` SET `name`='Flagship 3' WHERE `id`='4';
UPDATE `liaison_institutions` SET `name`='Flagship 4' WHERE `id`='5';

-- ***********************
-- Flagships and Regions should not be partners. Instead, they will be replaced by the institutions they represent.
-- ***********************

-- CU
UPDATE project_partners pp
SET pp.partner_id = 114
WHERE pp.partner_id = 1033;

-- FP1
UPDATE project_partners pp
SET pp.partner_id = 46
WHERE pp.partner_id = 1024;

-- FP2
UPDATE project_partners pp
SET pp.partner_id = 100
WHERE pp.partner_id = 1025;

-- FP3
UPDATE project_partners pp
SET pp.partner_id = 1053
WHERE pp.partner_id = 1026;

-- FP4
UPDATE project_partners pp
SET pp.partner_id = 89
WHERE pp.partner_id = 1027;

-- EA
UPDATE project_partners pp
SET pp.partner_id = 66
WHERE pp.partner_id = 1029;

-- LAM
UPDATE project_partners pp
SET pp.partner_id = 46
WHERE pp.partner_id = 1028;

-- SAs
UPDATE project_partners pp
SET pp.partner_id = 172
WHERE pp.partner_id = 1031;

-- SEA
UPDATE project_partners pp
SET pp.partner_id = 5
WHERE pp.partner_id = 1032;

-- WA
UPDATE project_partners pp
SET pp.partner_id = 103
WHERE pp.partner_id = 1030;

-- Before removing records in the institutions table, we need to remove the foreign key that exists 
-- in the employees table.
ALTER TABLE `employees` 
DROP FOREIGN KEY `FK_employees_institutions`;
ALTER TABLE `employees` 
DROP INDEX `FK_employees_institutions_idx` ;
  
-- deleting those institutions
DELETE FROM `institutions` WHERE `id`='1033';

DELETE FROM `institutions` WHERE `id`='1029';
DELETE FROM `institutions` WHERE `id`='1028';
DELETE FROM `institutions` WHERE `id`='1031';
DELETE FROM `institutions` WHERE `id`='1032';
DELETE FROM `institutions` WHERE `id`='1030';

DELETE FROM `institutions` WHERE `id`='1024';
DELETE FROM `institutions` WHERE `id`='1025';
DELETE FROM `institutions` WHERE `id`='1026';
DELETE FROM `institutions` WHERE `id`='1027';

-- ***********************
-- Setting all the Project Partners to PPA or PP for those projects that are being financed by CCAFS.
-- ***********************
UPDATE project_partners pp
INNER JOIN institutions i ON i.id = pp.partner_id
INNER JOIN projects p ON p.id = pp.project_id
SET pp.partner_type = "PP"
WHERE i.is_ppa = 1
AND p.type = "BILATERAL"
AND pp.partner_type <> "PL"

