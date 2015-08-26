-- ----------------------------------------------------------------
-- Rename project_partners table and delete all the indexes and 
-- foreign keys
-- ----------------------------------------------------------------

RENAME TABLE project_partners TO pp_old;

ALTER TABLE `pp_old` 
  DROP FOREIGN KEY `FK_project_partners_user_id`, 
  DROP FOREIGN KEY `fk_project_partners_users_modified__by`, 
  DROP FOREIGN KEY `fk_project_partners_users_created_by`, 
  DROP FOREIGN KEY `FK_project_partners_projects` , 
  DROP FOREIGN KEY `FK_project_partners_institutions` ;

ALTER TABLE `pp_old` 
  DROP INDEX `fk_project_partners_users_modified__by`, 
  DROP INDEX `fk_project_partners_users_created_by`, 
  DROP INDEX `FK_project_partners_users_idx`, 
  DROP INDEX `FK_project_partners_institutions_idx`, 
  DROP INDEX `FK_project_partners_projects_idx` ;

DROP TRIGGER IF EXISTS `after_project_partners_insert`;
DROP TRIGGER IF EXISTS `after_project_partners_update`;

ALTER TABLE `project_partner_contributions` DROP FOREIGN KEY `FK_project_partners_contributions_project_partner` ;
ALTER TABLE `activities` DROP FOREIGN KEY `FK_activities_project_partner_id` ;
ALTER TABLE `deliverable_partnerships` DROP FOREIGN KEY `FK_deliverable_partnerships_project_partners_partner_id` ;

-- ----------------------------------------------------------------
--  Add new table project_partners
-- ----------------------------------------------------------------

CREATE TABLE `project_partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) NOT NULL COMMENT 'Foreign key to projects table',
  `institution_id` bigint(20) NOT NULL COMMENT 'Foreign key to institutions table',
  `is_leader` tinyint(1) NOT NULL DEFAULT '0',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_project_partners` (`project_id` ASC, `institution_id` ASC),
  KEY `FK_project_partners_projects_idx` (`project_id`),
  KEY `FK_project_partners_institutions_idx` (`institution_id`),
  KEY `fk_project_partners_users_created_by_idx` (`created_by`),
  KEY `fk_project_partners_users_modified_by_idx` (`modified_by`),  
  CONSTRAINT `FK_project_partners_institutions` FOREIGN KEY (`institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_partners_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_partners_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_partners_users_modified__by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------------------------------------------
-- Populate table project_partners
-- ----------------------------------------------------------------

INSERT IGNORE INTO project_partners (project_id, institution_id, is_leader, is_active, created_by, modified_by, modification_justification)
SELECT project_id, partner_id, IF(partner_type = "PL", 1, 0), is_active,  created_by, modified_by, modification_justification
FROM pp_old;

-- ----------------------------------------------------------------
-- Add new column to the old table of project partners to keep 
-- a reference of the corresponding ID in the new table.
-- ----------------------------------------------------------------

ALTER TABLE `pp_old` ADD COLUMN `new_id` BIGINT NULL  AFTER `modification_justification` ;

UPDATE pp_old po 
SET new_id = (SELECT id FROM project_partners pp WHERE pp.project_id = po.project_id AND pp.institution_id = po.partner_id );

-- ----------------------------------------------------------------
-- Create project_partner_persons table
-- ----------------------------------------------------------------

CREATE  TABLE `project_partner_persons` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `project_partner_id` BIGINT NOT NULL ,
  `user_id` BIGINT NOT NULL ,
  `contact_type` ENUM('PL', 'PC', 'CP') NOT NULL ,
  `responsibilities` TEXT NULL ,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`) ,
  INDEX `FK_project_partner_persons_project_partners_idx` (`project_partner_id` ASC) ,
  INDEX `FK_project_partner_persons_users_idx` (`user_id` ASC) ,
  KEY `fk_project_partners_users_created_by_idx` (`created_by`),
  KEY `fk_project_partners_users_modified_by_idx` (`modified_by`),  
  CONSTRAINT `FK_project_partner_persons_project_partners` FOREIGN KEY (`project_partner_id` )
    REFERENCES `project_partners` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_partner_persons_users` FOREIGN KEY (`user_id` )
    REFERENCES `users` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE,   
  CONSTRAINT `fk_project_partner_persons_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_project_partner_persons_users_modified__by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8;

INSERT INTO project_partner_persons (project_partner_id, user_id, contact_type, responsibilities, active_since, created_by, modified_by, modification_justification)
SELECT new_id, user_id, CASE partner_type WHEN "PP" THEN "CP" WHEN "PPA" THEN "CP" ELSE partner_type END, 
       responsabilities, active_since, created_by, modified_by, modification_justification 
FROM pp_old pp
WHERE user_id IS NOT NULL;

-- ----------------------------------------------------------------
-- Update the references to project partners from other tables
-- ----------------------------------------------------------------

UPDATE activities a SET leader_id = (
  IFNULL(
    (
    SELECT ppp.id FROM project_partner_persons ppp 
    INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id
    INNER JOIN pp_old po ON pp.id = po.new_id  
    WHERE po.id = a.leader_id AND ppp.user_id = po.user_id AND po.user_id IS NOT NULL
    ),
    -- If the project has not a leader, assign the project leader as activity leader
    (
    SELECT ppp.id FROM project_partner_persons ppp 
    INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id 
    WHERE ppp.contact_type = 'PL' AND pp.project_id = a.project_id 
    )
  )
);

-- ----------------------------------------------------------------
-- Add new columns and foreign keys to project_partner_contributions table
-- ----------------------------------------------------------------

ALTER TABLE `project_partner_contributions` 
  ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT  FIRST , 
  ADD COLUMN `is_active` TINYINT(1) NOT NULL DEFAULT '1'  AFTER `contribution_institution_id` , 
  ADD COLUMN `active_since` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  AFTER `is_active` , 
  ADD COLUMN `created_by` BIGINT NOT NULL  AFTER `active_since` , 
  ADD COLUMN `modified_by` BIGINT NOT NULL  AFTER `created_by` , 
  ADD COLUMN `modification_justification` TEXT NOT NULL  AFTER `modified_by` , 
  ADD CONSTRAINT `FK_project_partners_contributions_project_partner` FOREIGN KEY (`project_partner_id` )
    REFERENCES `project_partners` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_project_partners_contributions_users_created_by` FOREIGN KEY (`created_by` )
    REFERENCES `users` (`id` ) ON DELETE NO ACTION ON UPDATE NO ACTION, 
  ADD CONSTRAINT `FK_project_partners_contributions_users_modified_by` FOREIGN KEY (`modified_by` )
  REFERENCES `users` (`id` ) ON DELETE NO ACTION ON UPDATE NO ACTION, 
  DROP PRIMARY KEY, ADD PRIMARY KEY (`id`), 
  ADD INDEX `FK_project_partners_contributions_users_created_by_idx` (`created_by` ASC), 
  ADD INDEX `FK_project_partners_contributions_users_modified_by_idx` (`modified_by` ASC) ;



-- ----------------------------------------------------------------
-- Add foreign key to project partners in table activities 
-- ----------------------------------------------------------------

ALTER TABLE `activities` 
  ADD CONSTRAINT `FK_activities_project_partner_persons` FOREIGN KEY (`leader_id` )
  REFERENCES `project_partner_persons` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------------------------------------------
-- Add foreign key to project partners in table deliverable_partnerships 
-- ----------------------------------------------------------------

ALTER TABLE `deliverable_partnerships` CHANGE COLUMN `partner_id` `partner_person_id` BIGINT(20) NOT NULL ;

ALTER TABLE `deliverable_partnerships` 
  ADD CONSTRAINT `FK_deliverable_partnerships_project_partner_persons` FOREIGN KEY (`partner_person_id` )
  REFERENCES `project_partner_persons` (`id` ) ON DELETE CASCADE ON UPDATE CASCADE;

  
-- ----------------------------------------------------------------
-- Drop the previous table of project_partners (pp_old)
-- ----------------------------------------------------------------
DROP TABLE pp_old;