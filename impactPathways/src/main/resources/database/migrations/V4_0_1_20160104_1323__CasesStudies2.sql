ALTER TABLE `cases_studies`
ADD COLUMN `outputUsed`  text NULL AFTER `references`,
ADD COLUMN `year`  int NULL AFTER `outputUsed`,
ADD COLUMN `researchPartners`  text NULL AFTER `outputUsed`,
ADD COLUMN `explainIndicatorRelation`  text NULL AFTER `researchPartners`;

