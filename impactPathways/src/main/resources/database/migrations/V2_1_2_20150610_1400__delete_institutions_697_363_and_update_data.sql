-- Update the website for the institucion Universität Kopenhagen
UPDATE institutions SET website_link ='http://www.ku.dk/' WHERE id=114;

-- Delete the trigger because this block the update sentences */
DROP TRIGGER IF EXISTS after_project_partners_update;

-- Sentences for update data with the institution_id = 697
UPDATE activity_partners
SET institution_id=114
WHERE institution_id=697;

UPDATE budgets
SET institution_id=114
WHERE institution_id=697;

UPDATE employees
SET institution_id=114
WHERE institution_id=697;

UPDATE expected_activity_leaders
SET institution_id=114
WHERE institution_id=697;

UPDATE liaison_institutions
SET institution_id=114
WHERE institution_id=697;

UPDATE project_partners
SET partner_id=114
WHERE partner_id=697;

-- Sentences for update data of the institution_id = 363
UPDATE activity_partners
SET institution_id=400
WHERE institution_id=363;

UPDATE budgets
SET institution_id=400
WHERE institution_id=363;

UPDATE employees
SET institution_id=400
WHERE institution_id=363;

UPDATE expected_activity_leaders
SET institution_id=400
WHERE institution_id=363;

UPDATE liaison_institutions
SET institution_id=400
WHERE institution_id=363;

UPDATE project_partners
SET partner_id=400
WHERE partner_id=363;

-- Delete institutions 697 and 363
DELETE FROM institutions WHERE id=697;
DELETE FROM institutions WHERE id=363;







