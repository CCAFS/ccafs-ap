-- -----------------------------------------------------------------------------
--        Adding unique index for duplicated fields into project_locations
-- -----------------------------------------------------------------------------

CREATE TABLE p_locations like project_locations;
ALTER TABLE p_locations ADD UNIQUE INDEX UK_projectID_locElementID (project_id, loc_element_id);
INSERT IGNORE INTO p_locations (SELECT * FROM project_locations);
RENAME TABLE project_locations TO drop_locations;
RENAME TABLE p_locations TO project_locations;
DROP TABLE drop_locations;

