-- -----------------------------------------------------------------------------
--        Migrated Activity Locations as Project Locations
-- -----------------------------------------------------------------------------

-- Adding the column project_id in table activity_locations.
ALTER TABLE activity_locations ADD project_id  bigint(20)  NOT NULL AFTER id;

-- Entering data of project_id in table activity_locations
UPDATE activity_locations actlo INNER JOIN activities act ON act.id = actlo.activity_id  
SET actlo.project_id = act.project_id;


-- Foreign Key between activities and activity_locations with project_id.
ALTER TABLE activity_locations ADD CONSTRAINT  FK_project_locations_activities_project_id FOREIGN KEY (project_id) REFERENCES projects(id);

-- Rename table activity_locations to project_locations.
ALTER TABLE activity_locations  RENAME TO project_locations;