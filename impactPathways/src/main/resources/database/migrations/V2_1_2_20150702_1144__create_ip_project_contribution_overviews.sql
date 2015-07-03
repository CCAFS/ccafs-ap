 
-- ************************************************************************************************************
-- *********** Create table project_contribution_overviews
-- ************************************************************************************************************

-- This table will be used to save the annual contribution in the outcome.

DROP TABLE IF EXISTS ip_project_contribution_overviews;
CREATE TABLE ip_project_contribution_overviews (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  project_contribution_id BIGINT(20) NOT NULL,
  year INT NOT NULL,
  anual_contribution TEXT NULL,
  gender_contribution TEXT NULL,
  PRIMARY KEY (id),
  INDEX FK_project_contribution_idx (project_contribution_id ASC),
  
  CONSTRAINT FK_project_contribution
    FOREIGN KEY (project_contribution_id)
    REFERENCES ip_project_contributions (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)