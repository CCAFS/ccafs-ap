-- Updating liaison institutions ids.
ALTER TABLE `crp_indicator_reports` 
CHANGE COLUMN `liaison_institution_id` `liaison_institution_id` BIGINT(20) NULL;

UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'AfricaRice') WHERE acronym = 'AfricaRice';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'BI') WHERE acronym = 'Bioversity';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'CIAT') WHERE acronym = 'CIAT';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'CIFOR') WHERE acronym = 'CIFOR';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'CIMMYT') WHERE acronym = 'CIMMYT';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'CIP') WHERE acronym = 'CIP';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'ICARDA') WHERE acronym = 'ICARDA';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'ICRAF') WHERE acronym = 'ICRAF';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'ICRISAT') WHERE acronym = 'ICRISAT';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'IFPRI') WHERE acronym = 'IFPRI';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'IITA') WHERE acronym = 'AfricaRice';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'ILRI') WHERE acronym = 'ILRI';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'IRRI') WHERE acronym = 'IRRI';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'IWMI') WHERE acronym = 'IWMI';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'WorldFish') WHERE acronym = 'WorldFish';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'RP EA') WHERE acronym = 'RPL EA';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'RP LAM') WHERE acronym = 'RPL LAM';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'RP SAs') WHERE acronym = 'RPL SAs';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'RP SEA') WHERE acronym = 'RPL SEA';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'RP WA') WHERE acronym = 'RPL WA';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'F1') WHERE acronym = 'Theme 1';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'F2') WHERE acronym = 'Theme 2';
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'F3') WHERE acronym = 'Theme 3';
UPDATE crp_indicator_reports SET liaison_institution_id = NULL WHERE acronym = 'Theme 4.1';
UPDATE crp_indicator_reports SET liaison_institution_id = NULL WHERE acronym = 'Theme 4.2';
UPDATE crp_indicator_reports SET liaison_institution_id = NULL WHERE acronym = 'Theme 4.3';

-- Minimizing acronyms for themes
UPDATE crp_indicator_reports SET acronym = 'T4.1' WHERE acronym = 'Theme 4.1';
UPDATE crp_indicator_reports SET acronym = 'T4.2' WHERE acronym = 'Theme 4.2';
UPDATE crp_indicator_reports SET acronym = 'T4.3' WHERE acronym = 'Theme 4.3';

-- inserting concatenated values values as FP4. 
SET group_concat_max_len =  4294967295;
INSERT INTO crp_indicator_reports (target, next_target, actual, support_links, deviation, indicator_id, year, acronym) 
(
SELECT GROUP_CONCAT(if(crp.target='', null, concat(crp.acronym, ': ', crp.target)) SEPARATOR ', ') as target,
GROUP_CONCAT(if(crp.next_target = '', null, concat(crp.acronym, ': ', crp.next_target)) SEPARATOR ', ') as next_target, 
GROUP_CONCAT(if(crp.actual = '', null, concat(crp.acronym, ': ', crp.actual)) SEPARATOR ', ') as actual,
GROUP_CONCAT(if(crp.support_links = '', null, concat(crp.acronym, ': ', crp.support_links)) SEPARATOR ', ') as support_links,
GROUP_CONCAT(if(crp.deviation = '', null, concat(crp.acronym, ': ', crp.deviation)) SEPARATOR ', ') as deviation,
crp.indicator_id, 
crp.year, 
'FP4' as acronym
FROM crp_indicator_reports crp
WHERE acronym LIKE "%4.%"
GROUP BY indicator_id, year
);
SET group_concat_max_len =  2024;

-- Deleting old themes as they were concatenated.
DELETE FROM crp_indicator_reports WHERE acronym = 'T4.1';
DELETE FROM crp_indicator_reports WHERE acronym = 'T4.2';
DELETE FROM crp_indicator_reports WHERE acronym = 'T4.3';

-- Updating liaison_institution_id
UPDATE crp_indicator_reports SET liaison_institution_id = (SELECT id FROM liaison_institutions WHERE acronym = 'F4') WHERE acronym = 'FP4';

-- Deleting column acronym as it is not needed any more.
ALTER TABLE `crp_indicator_reports` 
DROP COLUMN `acronym`;

-- Adding foreign key to liaison_institution_id column.
ALTER TABLE `crp_indicator_reports` 
ADD INDEX `FK_crp_indicator_reports_liaison_institution_id_idx` (`liaison_institution_id` ASC);
ALTER TABLE `crp_indicator_reports` 
ADD CONSTRAINT `FK_crp_indicator_reports_liaison_institution_id`
  FOREIGN KEY (`liaison_institution_id`)
  REFERENCES `liaison_institutions` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;




