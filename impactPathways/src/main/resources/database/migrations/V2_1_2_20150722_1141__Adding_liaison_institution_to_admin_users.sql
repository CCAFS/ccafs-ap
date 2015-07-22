-- ************************************************************************************************************
-- *********** Adding a liaison institution to the admin user and deleting unused roles.
-- ************************************************************************************************************

INSERT INTO `liaison_users` (`user_id`, `institution_id`) VALUES ('1', '13');

DELETE FROM `user_roles` WHERE `id`='2';
DELETE FROM `user_roles` WHERE `id`='3';
DELETE FROM `user_roles` WHERE `id`='4';