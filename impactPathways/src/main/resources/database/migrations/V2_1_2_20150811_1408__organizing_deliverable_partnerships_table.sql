-- -----------------------------------------------------------------------------
--        Deleting unused columns in the deliverable_partnerships table.
-- -----------------------------------------------------------------------------

-- Deleting triggers
DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_insert` $$
DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `after_deliverable_partnerships_update` $$
DELIMITER ;

DROP TABLE deliverable_partnerships;

CREATE TABLE `deliverable_partnerships` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deliverable_id` bigint(20) NOT NULL,
  `partner_id` bigint(20) NOT NULL,
  `partner_type` enum('Resp','Other') NOT NULL DEFAULT 'Other',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `active_since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(20) NOT NULL,
  `modified_by` bigint(20) NOT NULL,
  `modification_justification` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deliverable_partnership_deliverable_idx` (`deliverable_id`),
  KEY `FK_deliverable_partnerships_users_created_by_idx` (`created_by`),
  KEY `FK_deliverable_partnerships_users_modified_by_idx` (`modified_by`),
  KEY `FK_deliverable_partnerships_projectPartners_partner_id_idx` (`partner_id`),
  CONSTRAINT `FK_deliverable_partnerships_deliverable` FOREIGN KEY (`deliverable_id`) REFERENCES `deliverables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_project_partners_partner_id` FOREIGN KEY (`partner_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_users_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_deliverable_partnerships_users_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



