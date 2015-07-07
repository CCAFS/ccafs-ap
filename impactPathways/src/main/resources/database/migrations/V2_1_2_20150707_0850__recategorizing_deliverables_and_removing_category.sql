-- -----------------------------------------------------------------------------
--        Recategorizing 9 deliverables
-- -----------------------------------------------------------------------------

UPDATE `deliverables` SET `type_id`='19' WHERE `id`='34';
UPDATE `deliverables` SET `type_id`='22' WHERE `id`='108';
UPDATE `deliverables` SET `type_id`='14' WHERE `id`='322';
UPDATE `deliverables` SET `type_id`='27' WHERE `id`='710';
UPDATE `deliverables` SET `type_id`='15' WHERE `id`='937';
UPDATE `deliverables` SET `type_id`='38' WHERE `id`='950';
UPDATE `deliverables` SET `type_id`='20' WHERE `id`='957';
UPDATE `deliverables` SET `type_id`='36' WHERE `id`='987';
UPDATE `deliverables` SET `type_id`='15' WHERE `id`='1262';

-- Removing category "Other" in publications.
DELETE FROM `deliverable_types` WHERE `id`='25';