-- -----------------------------------------------------------------------------
-- Creation of the triggers that will insert the history records in the
-- corresponding tables
-- 
-- -----------------------------------------------------------------------------


-- -----------------------------------------------------------------------------
--                  Create triggers for table activities
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_activities_update;
DELIMITER $$
CREATE TRIGGER after_activities_update 
  AFTER UPDATE ON activities 
  FOR EACH ROW BEGIN

	  -- Insert the new record
    INSERT INTO $[database]_history.activities 
    (`record_id`, `project_id`, `title`, `description`, `startDate`, `endDate`, `is_global`, `expected_leader_id`, 
      `leader_id`, `expected_research_outputs`, `expected_gender_contribution`, `outcome`, `gender_percentage`, 
      `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`title`, OLD.`description`, OLD.`startDate`, OLD.`endDate`, OLD.`is_global`, OLD.`expected_leader_id`, 
    OLD.`leader_id`, OLD.`expected_research_outputs`, OLD.`expected_gender_contribution`, OLD.`outcome`, OLD.`gender_percentage`, 
    OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table activities
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table activity_budgets
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_activity_budgets_update;
DELIMITER $$
CREATE TRIGGER after_activity_budgets_update 
  AFTER UPDATE ON activity_budgets 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.activity_budgets 
    (`record_id`, `activity_id`, `budget_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
    `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`activity_id`, OLD.`budget_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
    OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table activity_budgets
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table activity_cross_cutting_themes
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_activity_cross_cutting_themes_update;
DELIMITER $$
CREATE TRIGGER after_activity_cross_cutting_themes_update 
  AFTER UPDATE ON activity_cross_cutting_themes 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.activity_cross_cutting_themes 
    (`record_id`, `activity_id`, `theme_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
    `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`activity_id`, OLD.`theme_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
    OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table activity_cross_cutting_themes
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table activity_locations
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_activity_locations_update;
DELIMITER $$
CREATE TRIGGER after_activity_locations_update 
  AFTER UPDATE ON activity_locations 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.activity_locations 
    (`record_id`, `activity_id`, `loc_element_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
    `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`activity_id`, OLD.`loc_element_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
     OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table activity_locations
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table activity_partners
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_activity_partners_update;
DELIMITER $$
CREATE TRIGGER after_activity_partners_update 
  AFTER UPDATE ON activity_partners 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.activity_partners 
    (`record_id`, `institution_id`, `activity_id`, `contact_name`, `contact_email`, `contribution`, 
    `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`institution_id`, OLD.`activity_id`, OLD.`contact_name`, OLD.`contact_email`, OLD.`contribution`, 
     OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table activity_partners
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table deliverables
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_deliverables_update;
DELIMITER $$
CREATE TRIGGER after_deliverables_update 
  AFTER UPDATE ON deliverables 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.deliverables 
    (`record_id`, `activity_id`, `title`, `type_id`, `year`, `is_active`, `active_since`, `active_until`, `modified_by`, 
     `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`activity_id`, OLD.`title`, OLD.`type_id`, OLD.`year`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
     OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table deliverables
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_activity_contributions
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_activity_contributions_update;
DELIMITER $$
CREATE TRIGGER after_ip_activity_contributions_update 
  AFTER UPDATE ON ip_activity_contributions 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_activity_contributions 
    (`record_id`, `activity_id`, `mog_id`, `midOutcome_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
    `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`activity_id`, OLD.`mog_id`, OLD.`midOutcome_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
     OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_activity_contributions
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_activity_indicators
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_activity_indicators_update;
DELIMITER $$
CREATE TRIGGER after_ip_activity_indicators_update 
  AFTER UPDATE ON ip_activity_indicators 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_activity_indicators 
    (`record_id`, `description`, `target`, `activity_id`, `parent_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
     `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`description`, OLD.`target`, OLD.`activity_id`, OLD.`parent_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'UPDATE');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_activity_indicators
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_deliverable_contributions
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_deliverable_contributions_update;
DELIMITER $$
CREATE TRIGGER after_ip_deliverable_contributions_update 
  AFTER UPDATE ON ip_deliverable_contributions 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_deliverable_contributions 
    (`record_id`, `project_contribution_id`, `deliverable_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
     `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_contribution_id`, OLD.`deliverable_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
     OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_deliverable_contributions
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_elements
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_elements_update;
DELIMITER $$
CREATE TRIGGER after_ip_elements_update 
  AFTER UPDATE ON ip_elements 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_elements 
    (`record_id`, `description`, `element_type_id`, `ip_program_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
     `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`description`, OLD.`element_type_id`, OLD.`ip_program_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_elements
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_indicators
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_indicators_update;
DELIMITER $$
CREATE TRIGGER after_ip_indicators_update 
  AFTER UPDATE ON ip_indicators 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_indicators 
    (`record_id`, `description`, `target`, `ip_element_id`, `program_element_id`, 
     `parent_id`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`description`, OLD.`target`, OLD.`ip_element_id`, OLD.`program_element_id`, 
     OLD.`parent_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'insert');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_indicators
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table projects
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_projects_update;
DELIMITER $$
CREATE TRIGGER after_projects_update 
  AFTER UPDATE ON projects 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.projects 
    (`record_id`, `title`, `summary`, `start_date`, `end_date`, `leader_responsabilities`, 
     `expected_project_leader_id`, `project_leader_id`, `program_creator_id`, `project_owner_id`, 
     `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`title`, OLD.`summary`, OLD.`start_date`, OLD.`end_date`, OLD.`leader_responsabilities`, 
     OLD.`expected_project_leader_id`, OLD.`project_leader_id`, OLD.`program_creator_id`, OLD.`project_owner_id`, 
     OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table projects
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_other_contributions
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_other_contributions_update;
DELIMITER $$
CREATE TRIGGER after_ip_other_contributions_update 
  AFTER UPDATE ON ip_other_contributions 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_other_contributions 
    (`record_id`, `project_id`, `contribution`, `additional_contribution`, `is_active`, `active_since`, `active_until`, 
     `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`contribution`, OLD.`additional_contribution`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_other_contributions
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_project_contributions
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_project_contributions_update;
DELIMITER $$
CREATE TRIGGER after_ip_project_contributions_update 
  AFTER UPDATE ON ip_project_contributions 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_project_contributions 
    (`record_id`, `project_id`, `mog_id`, `midOutcome_id`, `is_active`, `active_since`, `active_until`, 
     `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`mog_id`, OLD.`midOutcome_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_project_contributions
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table ip_project_indicators
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_ip_project_indicators_update;
DELIMITER $$
CREATE TRIGGER after_ip_project_indicators_update 
  AFTER UPDATE ON ip_project_indicators 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.ip_project_indicators 
    (`record_id`, `description`, `target`, `year`, `project_id`, `parent_id`, `outcome_id`, 
     `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`description`, OLD.`target`, OLD.`year`, OLD.`project_id`, OLD.`parent_id`, OLD.`outcome_id`, 
     OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table ip_project_indicators
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table next_users
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_next_users_update;
DELIMITER $$
CREATE TRIGGER after_next_users_update 
  AFTER UPDATE ON next_users 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.next_users 
    (`record_id`, `deliverable_id`, `user`, `expected_changes`, `strategies`, `is_active`, 
     `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`deliverable_id`, OLD.`user`, OLD.`expected_changes`, OLD.`strategies`, OLD.`is_active`, 
     NOW(), OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table next_users
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
--                  Create triggers for table project_budgets
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_project_budgets_update;
DELIMITER $$
CREATE TRIGGER after_project_budgets_update 
  AFTER UPDATE ON project_budgets 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.project_budgets 
    (`record_id`, `project_id`, `budget_id`, `is_active`, `active_since`, `active_until`, `modified_by`, 
     `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`budget_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, 
     OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table project_budgets
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table project_outcomes
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_project_outcomes_update;
DELIMITER $$
CREATE TRIGGER after_project_outcomes_update 
  AFTER UPDATE ON project_outcomes 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.project_outcomes 
    (`record_id`, `year`, `statement`, `stories`, `project_id`, `is_active`, `active_since`, `active_until`, 
    `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`year`, OLD.`statement`, OLD.`stories`, OLD.`project_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table project_outcomes
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table project_focuses
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_project_focuses_update;
DELIMITER $$
CREATE TRIGGER after_project_focuses_update 
  AFTER UPDATE ON project_focuses 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.project_focuses 
    (`record_id`, `project_id`, `program_id`, `is_active`, `active_since`, `active_until`, 
     `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`program_id`, OLD.`is_active`, OLD.`active_since`,  NOW(), 
     OLD.`modified_by`, OLD.`modification_justification`, 'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table project_focuses
-- -----------------------------------------------------------------------------

-- -----------------------------------------------------------------------------
--                  Create triggers for table project_partners
-- -----------------------------------------------------------------------------

-- !! Update
DROP TRIGGER IF EXISTS after_project_partners_update;
DELIMITER $$
CREATE TRIGGER after_project_partners_update 
  AFTER UPDATE ON project_partners 
  FOR EACH ROW BEGIN

    -- Insert the new record
    INSERT INTO $[database]_history.project_partners 
    (`record_id`, `project_id`, `partner_id`, `contact_name`, `contact_email`, 
     `responsabilities`, `is_active`, `active_since`, `active_until`, `modified_by`, `modification_justification`, `action`)
    VALUES 
    (OLD.`id`, OLD.`project_id`, OLD.`partner_id`, OLD.`contact_name`, OLD.`contact_email`, 
     OLD.`responsabilities`, OLD.`is_active`, OLD.`active_since`,  NOW(), OLD.`modified_by`, OLD.`modification_justification`, 
     'update');

  END$$
DELIMITER ;

-- -----------------------------------------------------------------------------
--                  End of Create triggers for table project_partners
-- -----------------------------------------------------------------------------
