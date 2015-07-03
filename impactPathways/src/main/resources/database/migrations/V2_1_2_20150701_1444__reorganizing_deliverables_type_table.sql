------------------------------------------------
-- Updating categories
------------------------------------------------

-- Updating names
UPDATE `deliverable_types` SET `name`='Data and information outputs, including datasets, databases and models' WHERE `id`='1';
UPDATE `deliverable_types` SET `name`='Peer reviewed Publications' WHERE `id`='3';
UPDATE `deliverable_types` SET `name`='Communication Products and Multimedia' WHERE `id`='4';
UPDATE `deliverable_types` SET `name`='Tools and Computer Software' WHERE `id`='7';
UPDATE `deliverable_types` SET `name`='Workshops' WHERE `id`='8';

-- Data and information outputs, including datasets, databases and models
UPDATE `deliverable_types` SET `name`='Models (i.e. Crop models)', `parent_id`='1' WHERE `id`='37';

-- Reports, Reference Materials and Other Papers
UPDATE `deliverable_types` SET `name`='Research report (i.e. workshop report, consultant’s report, project reports, student thesis, etc.)' WHERE `id`='14';
UPDATE `deliverable_types` SET `name`='Working paper' WHERE `id`='16';
UPDATE `deliverable_types` SET `parent_id`='2' WHERE `id`='22';

-- Peer reviewed Publications
-- TODO - To remove "Other" [id=25].

-- Communication Products and Multimedia
UPDATE `deliverable_types` SET `name`='Articles for media or news (radio, TV, newspapers, newsletters, etc.)' WHERE `id`='26';
UPDATE `deliverable_types` SET `parent_id`='4' WHERE `id`='31';
UPDATE `deliverable_types` SET `parent_id`='4' WHERE `id`='32';
UPDATE `deliverable_types` SET `parent_id`='4' WHERE `id`='33';

-- Case Study
--  Keep same.

-- Multimedia
DELETE FROM `deliverable_types` WHERE `id`='6';

-- Tools and Computer Software
UPDATE `deliverable_types` SET `name`='Other (please specify)' WHERE `id`='38';
INSERT INTO `deliverable_types` (`name`, `parent_id`, `timeline`) VALUES ('Website', '7', '0');


-- Workshops
--  Keep same.

-- Capacity
UPDATE `deliverable_types` SET `parent_id`='9' WHERE `id`='40';

----------------------------------------------------------------------
-- Updating deliverables table so user can define other category
----------------------------------------------------------------------

ALTER TABLE `deliverables` 
ADD COLUMN `type_other` TEXT NULL COMMENT 'Other type defined by the user.' AFTER `type_id`;

