-- -----------------------------------------------------------------------------
--    Migrated bilateral activities as projects. 
-- -----------------------------------------------------------------------------

-- Adding temporal column for to save the activity id.
 ALTER TABLE projects ADD activity_temporal  BIGINT(20) DEFAULT 0 AFTER modification_justification;
 
-- Adding temporal column for to save the activity id.
 ALTER TABLE project_partners ADD activity_temporal  BIGINT(20) DEFAULT 0 AFTER modification_justification;
  
-- Adding temporal column for to save the activity id.
 ALTER TABLE activities ADD last_activity_id_temporal  BIGINT(20) DEFAULT 0 AFTER modification_justification;
 
-- Entering bilateral activites as bilteral projects. 
INSERT INTO projects (title, summary, start_date, end_date, type,  liaison_institution_id, liaison_user_id,  created_by, 
 active_since, modified_by, modification_justification, is_global, activity_temporal)
SELECT act.title, act.description, act.startDate, act.endDate, "BILATERAL", 
	  
      -- Select liaison institutions
      (SELECT CASE (pp.partner_id)
	   WHEN 45 THEN 21
       WHEN 46 THEN 13
       WHEN 49 THEN 12
       WHEN 66 THEN 22
       WHEN 67 THEN 16
       WHEN 88 THEN 18
       WHEN 89 THEN 20
       WHEN 115 THEN 14
       ELSE 15
       END),   
       
	  -- Select liaison users, This is the contact point's liaison's user_id 
	   (SELECT CASE (pp.partner_id)
	   WHEN 45 THEN 30
       WHEN 46 THEN 21
       WHEN 49 THEN 20
       WHEN 66 THEN 31
       WHEN 67 THEN 25
       WHEN 88 THEN 34
       WHEN 89 THEN 29
       WHEN 115 THEN 24
       ELSE 24
       END)
       , 
       -- Select craeted by, This is the contact point's user's id  
	   (SELECT CASE (pp.partner_id)
	   WHEN 45 THEN 81
       WHEN 46 THEN 66
       WHEN 49 THEN 61
       WHEN 66 THEN 82
       WHEN 67 THEN 67
       WHEN 88 THEN 83
       WHEN 89 THEN 52
       WHEN 115 THEN 119
       ELSE 88
       END)
       , act.active_since, act.modified_by, act.modification_justification, act.is_global, act.id 
       
FROM   activities act INNER JOIN project_partners pp ON act.project_partner_id = pp.id
	   INNER JOIN institutions i ON pp.partner_id = i.id
WHERE  (act.title LIKE "%bilateral%" AND i.institution_type_id = 3) OR act.id = 164;

-- Entering project partners of bilateral projects. 
INSERT INTO project_partners (project_id, partner_id, user_id, partner_type, activity_partner, responsabilities,  created_by, modified_by, 
 modification_justification, activity_temporal)
SELECT p.id, pp.partner_id, pp.user_id, "PL", 0,  CONCAT("Activity 2014-", act.id), p.created_by, p.modified_by, p.modification_justification, p.activity_temporal
FROM projects p INNER JOIN activities act ON p.activity_temporal = act.id
INNER JOIN project_partners pp ON act.project_partner_id = pp.id;

-- Exception in activities # 164 
-- Entering CIAT as project leader and Peter laderach as user_id of activity 
INSERT INTO project_partners (project_id, partner_id, user_id, partner_type, activity_partner, responsabilities, created_by, modified_by, 
 modification_justification, activity_temporal)
SELECT p.id, 46, 66, "PL", 0,  CONCAT("Activity 2014-", act.id), p.created_by, p.modified_by, p.modification_justification, act.id
FROM  activities act INNER JOIN projects p ON p.activity_temporal = act.id WHERE act.id = 164;

-- Entering IRI as project coordinator 
INSERT INTO project_partners (project_id, partner_id, user_id, partner_type, activity_partner, responsabilities,  created_by, modified_by, 
 modification_justification, activity_temporal)
SELECT p.id, 299, 29, "PC", 0,  CONCAT("Activity 2014-", act.id), p.created_by, p.modified_by, p.modification_justification, act.id
FROM  activities act INNER JOIN projects p ON p.activity_temporal = act.id WHERE act.id = 164;

-- Duplicating activites with new project id. 
 INSERT INTO activities (project_id,  title, description, startDate, endDate, is_global, expected_research_outputs,expected_gender_contribution,
 outcome, gender_percentage, project_partner_id, created_by, is_active,  modified_by, modification_justification, last_activity_id_temporal)
SELECT pp.project_id, act.title, act.description, act.startDate, act.endDate, act.is_global, act.expected_research_outputs, act.expected_gender_contribution,
act.outcome, act.gender_percentage, pp.id, act.created_by, act.is_active, act.modified_by, act.modification_justification, act.id
FROM activities act INNER JOIN project_partners pp ON act.id = pp.activity_temporal;

-- Entering Deliverables of new activities
INSERT INTO deliverables(project_id, activity_id, title, type_id, type_other, year, is_active, active_since, created_by, modified_by, modification_justification)
SELECT d.project_id, act.id, d.title, d.type_id, d.type_other, d.year, d.is_active, d.active_since, d.created_by, d.modified_by, d.modification_justification
FROM activities act INNER JOIN deliverables d ON act.last_activity_id_temporal = d.activity_id;

-- Entering activity_cross_cutting_themes of new activities
INSERT INTO activity_cross_cutting_themes( activity_id, theme_id, is_active, active_since, created_by, modified_by, modification_justification)
SELECT act.id, d.theme_id, d.is_active, d.active_since, d.created_by, d.modified_by, d.modification_justification
FROM activities act INNER JOIN activity_cross_cutting_themes d ON act.last_activity_id_temporal = d.activity_id;

-- Entering activity_partners of new activities
INSERT INTO activity_partners( institution_id, activity_id, contact_name, contact_email, user_id, contribution,  is_active, active_since, created_by, modified_by, modification_justification)
SELECT d.institution_id, act.id, d.contact_name, d.contact_email, d.user_id, d.contribution, d.is_active, d.active_since, d.created_by, d.modified_by, d.modification_justification
FROM activities act INNER JOIN activity_partners d ON act.last_activity_id_temporal = d.activity_id;

-- Entering ip_activity_contributions of new activities
INSERT INTO ip_activity_contributions( activity_id, mog_id, midOutcome_id, is_active, active_since, created_by, modified_by, modification_justification)
SELECT act.id, d.mog_id, d.midOutcome_id, d.is_active, d.active_since, d.created_by, d.modified_by, d.modification_justification
FROM activities act INNER JOIN ip_activity_contributions d ON act.last_activity_id_temporal = d.activity_id;

-- Entering ip_activity_indicators of new activities
INSERT INTO ip_activity_indicators( description, target, activity_id, parent_id, is_active, active_since, created_by, modified_by, modification_justification)
SELECT d.description, d.target, act.id, d.parent_id, d.is_active, d.active_since, d.created_by, d.modified_by, d.modification_justification
FROM activities act INNER JOIN ip_activity_indicators d ON act.last_activity_id_temporal = d.activity_id;

-- Deleting temporal columns
 ALTER TABLE projects DROP COLUMN activity_temporal;
 ALTER TABLE project_partners DROP COLUMN  activity_temporal;
 ALTER TABLE activities DROP COLUMN  last_activity_id_temporal;