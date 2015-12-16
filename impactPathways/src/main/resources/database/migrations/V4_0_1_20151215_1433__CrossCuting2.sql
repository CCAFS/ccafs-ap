ALTER TABLE `cross_cutting_contribution`
ADD COLUMN `is_active`  tinyint(1) NOT NULL DEFAULT 1 AFTER `category`,
ADD COLUMN `active_since`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `is_active`,
ADD COLUMN `created_by`  bigint(20) NOT NULL AFTER `active_since`,
ADD COLUMN `modified_by`  bigint(20) NOT NULL AFTER `created_by`,
ADD COLUMN `modification_justification`  text CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL AFTER `modified_by`,
ADD COLUMN `file`  varchar(200) NULL AFTER `category`;


