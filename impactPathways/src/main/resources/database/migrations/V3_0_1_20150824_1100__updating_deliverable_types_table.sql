------------------------------------------------
-- Updating categories
------------------------------------------------

-- Data and information outputs, including datasets, databases and models
UPDATE `deliverable_types` SET `name`='Models' WHERE `id`='37';

-- Reports, Reference Materials and Other Papers
UPDATE `deliverable_types` SET `name`='Research report' WHERE `id`='14';
UPDATE `deliverable_types` SET `name`='Reference material' WHERE `id`='20';
UPDATE `deliverable_types` SET `name`='Other non-peer reviewed articles' WHERE `id`='22';
UPDATE `deliverable_types` SET `parent_id`='2' WHERE `id`='30';


-- Communication Products and Multimedia
UPDATE `deliverable_types` SET `name`='Articles for media or news' WHERE `id`='26';
UPDATE `deliverable_types` SET `name`='Social media outputs' WHERE `id`='27';


-- Tools and Computer Software
UPDATE `deliverable_types` SET `name`='Maps' WHERE `id`='35';
UPDATE `deliverable_types` SET `name`='Tools' WHERE `id`='36';

-- Case Study as category
DELETE FROM `deliverable_types` WHERE `id`='5';