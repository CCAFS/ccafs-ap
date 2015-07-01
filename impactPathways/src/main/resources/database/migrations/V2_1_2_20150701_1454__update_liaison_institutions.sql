 
-- ************************************************************************************************************
-- *********** Update liaison institutions
-- ************************************************************************************************************

-- Update institution id FP2 to Columbia University
UPDATE liaison_institutions SET institution_id='100' WHERE id='3';

-- Update institution id FP4 to IFPRI
UPDATE liaison_institutions SET institution_id='89' WHERE id='5';

-- Update institution id EA to ILRI
UPDATE liaison_institutions SET institution_id='66' WHERE id='6';

-- Update institution id South Asia Region to IRRI
UPDATE liaison_institutions SET institution_id='172' WHERE id='8';

-- Update institution id South Asia Region to IWMI
UPDATE liaison_institutions SET institution_id='5' WHERE id='9';
