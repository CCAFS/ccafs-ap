-- Creating table that will relate the project partners with the ppa partners
CREATE TABLE `project_partner_contributions` (
  `project_partner_id` bigint(20) NOT NULL,
  `contribution_institution_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_partner_id`,`contribution_institution_id`),
  KEY `FK_project_partners_contributions_project_partner_idx` (`project_partner_id`),
  KEY `FK_project_partners_contributions_institutions_idx` (`contribution_institution_id`),
  CONSTRAINT `FK_project_partners_contributions_institutions` FOREIGN KEY (`contribution_institution_id`) REFERENCES `institutions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_project_partners_contributions_project_partner` FOREIGN KEY (`project_partner_id`) REFERENCES `project_partners` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT='2-level partners contribution with PPA partners.';