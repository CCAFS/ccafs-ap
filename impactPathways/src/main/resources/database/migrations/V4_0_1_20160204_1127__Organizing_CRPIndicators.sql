-- Updating liaison institutions ids.
ALTER TABLE `crp_indicator_reports` 
CHANGE COLUMN `liaison_institution_id` `liaison_institution_id` INT(11) NULL ;

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
