ALTER TABLE `cases_studies`
CHANGE COLUMN `references` `references_case`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL AFTER `evidenceOutcome`;

