-- -----------------------------------------------------------------------------
--        Fixing critical errors on table users
-- -----------------------------------------------------------------------------

UPDATE users old
INNER JOIN users new ON old.id = (new.id + 3)
SET old.first_name = new.first_name, old.last_name = new.last_name
WHERE 
  old.id > 317 AND old.id < 668;
  
UPDATE `users` SET `first_name`='Sandra', `last_name`='Russo' WHERE `email`='srusso@ufic.ufl.edu';
UPDATE `users` SET `first_name`='Arega', `last_name`='Alene' WHERE `email`='A.ALENE@CGIAR.ORG';
UPDATE `users` SET `first_name`='Anousith', `last_name`='Keophoxay' WHERE `email`='A.Keophoxay@cgiar.org';
