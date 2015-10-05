------------------------------------------------
-- Updating some deliverable categories
------------------------------------------------

UPDATE `deliverables` SET `type_id`='14' WHERE `id`='14';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='19';
UPDATE `deliverables` SET `type_id`='20' WHERE `id`='169';
UPDATE `deliverables` SET `type_id`='20' WHERE `id`='191';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='198';
UPDATE `deliverables` SET `type_id`='10' WHERE `id`='241';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='281';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='315';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='316';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='317';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='320';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='324';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='330';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='359';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='360';
UPDATE `deliverables` SET `type_id`='16' WHERE `id`='406';
UPDATE `deliverables` SET `type_id`='16' WHERE `id`='412';
UPDATE `deliverables` SET `type_id`='12' WHERE `id`='467';
UPDATE `deliverables` SET `type_id`='34' WHERE `id`='523';
UPDATE `deliverables` SET `type_id`='10' WHERE `id`='534';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='539';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='644';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='676';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='702';
UPDATE `deliverables` SET `type_id`='39' WHERE `id`='709';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='718';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='719';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='720';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='721';
UPDATE `deliverables` SET `type_id`='37' WHERE `id`='742';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='897';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='927';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='1033';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='1044';
UPDATE `deliverables` SET `type_id`='10' WHERE `id`='1056';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='1067';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='1095';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1137';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1138';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1139';
UPDATE `deliverables` SET `type_id`='34' WHERE `id`='1143';
UPDATE `deliverables` SET `type_id`='15' WHERE `id`='1144';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1152';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='1181';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='1192';
UPDATE `deliverables` SET `type_id`='39' WHERE `id`='1257';
UPDATE `deliverables` SET `type_id`='20' WHERE `id`='1289';
UPDATE `deliverables` SET `type_id`='20' WHERE `id`='1290';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='1293';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1313';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1314';
UPDATE `deliverables` SET `type_id`='11' WHERE `id`='1315';

-- Updating names
UPDATE `deliverable_types` SET `name`='Other' WHERE `id`='38';
UPDATE `deliverable_types` SET `name`='Platforms' WHERE `id`='34';

-- adding some information into deliverables description
UPDATE `deliverable_types` SET `description`='Primary data / raw data (unprocessed data).' WHERE `id`='10';
UPDATE `deliverable_types` SET `description`='Dataset is a collection of data.' WHERE `id`='11';
UPDATE `deliverable_types` SET `description`='Database is an organized collection of data.' WHERE `id`='12';
UPDATE `deliverable_types` SET `description`='Workshop report, consultant’s report, project reports, student thesis, portfolios, etc.' WHERE `id`='14';
UPDATE `deliverable_types` SET `description`='Policy brief, info note, etc.' WHERE `id`='15';
UPDATE `deliverable_types` SET `description`='Working paper.' WHERE `id`='16';
UPDATE `deliverable_types` SET `description`='Conference proceeding, conference paper.' WHERE `id`='17';
UPDATE `deliverable_types` SET `description`='Seminar paper' WHERE `id`='18';
UPDATE `deliverable_types` SET `description`='Discussion paper.' WHERE `id`='19';
UPDATE `deliverable_types` SET `description`='Booklets and training manuals for extension agents, etc.' WHERE `id`='20';
UPDATE `deliverable_types` SET `description`='Article in a magazin.' WHERE `id`='22';
UPDATE `deliverable_types` SET `description`='Case study, outcome case, etc.' WHERE `id`='30';
UPDATE `deliverable_types` SET `description`='Peer-reviewed journal article from scientific journal.' WHERE `id`='21';
UPDATE `deliverable_types` SET `description`='Peer-reviewed book.' WHERE `id`='23';
UPDATE `deliverable_types` SET `description`='Peer-reviewed book chapter.' WHERE `id`='24';
UPDATE `deliverable_types` SET `description`='Radio, TV, newspapers, newsletters, etc.' WHERE `id`='26';
UPDATE `deliverable_types` SET `description`='Web site, blog, wiki, linkedin group, facebook, yammer, etc.' WHERE `id`='27';
UPDATE `deliverable_types` SET `description`='Poster.' WHERE `id`='28';
UPDATE `deliverable_types` SET `description`='Presentation.' WHERE `id`='29';
UPDATE `deliverable_types` SET `description`='Audio.' WHERE `id`='31';
UPDATE `deliverable_types` SET `description`='Video.' WHERE `id`='32';
UPDATE `deliverable_types` SET `description`='Photo, Picture, etc.' WHERE `id`='33';
UPDATE `deliverable_types` SET `description`='Data Portals for dissemination.' WHERE `id`='34';
UPDATE `deliverable_types` SET `description`='Search engine, game, etc.' WHERE `id`='36';
UPDATE `deliverable_types` SET `description`='Website, blog, etc.' WHERE `id`='41';
UPDATE `deliverable_types` SET `description`='Algorithms' WHERE `id`='38';

-- deleting some unuseful deliverables
DELETE FROM `deliverables` WHERE `id`='1313';
DELETE FROM `deliverables` WHERE `id`='1314';
DELETE FROM `deliverables` WHERE `id`='1315';

