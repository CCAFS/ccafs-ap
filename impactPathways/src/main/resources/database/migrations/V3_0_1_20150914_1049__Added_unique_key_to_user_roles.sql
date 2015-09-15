----------------------------------------------------------------------------
--    Adding unique key to the table user roles
----------------------------------------------------------------------------

-- Deleting duplicated records from the table user roles
DELETE FROM user_roles 
WHERE id IN (
  SELECT t.id FROM (SELECT id FROM user_roles
  GROUP BY user_id, role_id
  HAVING COUNT(1) > 1
  ) as t 
);

-- Applying the unique key
ALTER TABLE `user_roles` ADD UNIQUE INDEX `UK_user_roles` (`user_id` ASC, `role_id` ASC) ;
