CREATE TABLE IF NOT EXISTS `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `parent_id` bigint(20),
  PRIMARY KEY (`id`),
  KEY `FK_parent_id_idx` (`parent_id`),
  CONSTRAINT `FK_resources_parent_resource_id` FOREIGN KEY (`parent_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role_privileges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table resources',
  `role_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table roles',
  `permission` enum('No access','View','Edit') NOT NULL DEFAULT 'View',
  PRIMARY KEY (`id`),
  KEY `FK_role_privileges_role_index` (`role_id`),
  KEY `FK_role_privileges_resource_index` (`resource_id`),
  CONSTRAINT `FK_role_privileges_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_role_privileges_resources` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user_privileges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table resources',
  `user_id` bigint(20) NOT NULL COMMENT 'Foreign key to the table users',
  `permission` enum('No access','View','Edit') NOT NULL DEFAULT 'View',
  PRIMARY KEY (`id`),
  KEY `FK_role_privileges_role_index` (`resource_id`),
  KEY `FK_role_privileges_user_index` (`user_id`),
  CONSTRAINT `FK_role_privileges_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_role_user_privileges_resources` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
