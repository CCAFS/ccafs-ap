----------------------------------------------------------------------------
--    Setting permissions to summaries section in the database
----------------------------------------------------------------------------

INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'summaries:*', 'Can update all the summaries section contents');
INSERT INTO `permissions` (`id`, `permission`, `description`) VALUES (NULL, 'summaries:board:*', 'Can use all the functions in the summaries board');
