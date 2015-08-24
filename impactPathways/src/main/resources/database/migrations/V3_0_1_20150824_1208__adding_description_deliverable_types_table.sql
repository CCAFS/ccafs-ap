-- Adding description field to deliverable_types table

ALTER TABLE `deliverable_types` 
ADD COLUMN `description` TEXT NULL DEFAULT NULL COMMENT '' AFTER `parent_id`;

-- adding some information into deliverables description
UPDATE `deliverable_types` SET `description`='dataset' WHERE `id`='11';
UPDATE `deliverable_types` SET `description`='i.e. Crop models' WHERE `id`='37';
UPDATE `deliverable_types` SET `description`='workshop report, consultant’s report, project reports, student thesis, etc.' WHERE `id`='14';
UPDATE `deliverable_types` SET `description`='policy brief, info note, etc' WHERE `id`='15';
UPDATE `deliverable_types` SET `description`='working paper' WHERE `id`='16';
UPDATE `deliverable_types` SET `description`='conference proceeding, conference paper' WHERE `id`='17';
UPDATE `deliverable_types` SET `description`='seminar paper' WHERE `id`='18';
UPDATE `deliverable_types` SET `description`='discussion paper' WHERE `id`='19';
UPDATE `deliverable_types` SET `description`='booklets and training manuals for extension agents, etc.' WHERE `id`='20';
UPDATE `deliverable_types` SET `description`='peer-reviewed journal article from scientific journal' WHERE `id`='21';
UPDATE `deliverable_types` SET `description`='article in a magazin' WHERE `id`='22';
UPDATE `deliverable_types` SET `description`='peer-reviewed book' WHERE `id`='23';
UPDATE `deliverable_types` SET `description`='peer-reviewed book chapter' WHERE `id`='24';
UPDATE `deliverable_types` SET `description`='radio, TV, newspapers, newsletters, etc.' WHERE `id`='26';
UPDATE `deliverable_types` SET `description`='web site, blog, wiki, linkedin group, facebook, yammer, etc.' WHERE `id`='27';
UPDATE `deliverable_types` SET `description`='case study, outcome case, etc.' WHERE `id`='30';
UPDATE `deliverable_types` SET `description`='CCAFS Sites Atlas, cropland, etc.' WHERE `id`='35';
UPDATE `deliverable_types` SET `description`='search engine, game, etc.' WHERE `id`='36';
UPDATE `deliverable_types` SET `description`='website, blog, etc.' WHERE `id`='41';