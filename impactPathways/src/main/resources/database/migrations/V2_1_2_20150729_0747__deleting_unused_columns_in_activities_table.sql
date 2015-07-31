-- Deleting columns that are not being used on this new P&R version.
ALTER TABLE `activities` 
DROP COLUMN `gender_percentage`,
DROP COLUMN `outcome`,
DROP COLUMN `expected_gender_contribution`,
DROP COLUMN `expected_research_outputs`;
