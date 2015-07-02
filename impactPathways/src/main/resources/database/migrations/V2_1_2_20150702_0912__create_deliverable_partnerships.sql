 
-- ************************************************************************************************************
-- *********** Create table deliverable partnership
-- ************************************************************************************************************

-- This table will be used to save the contributions made by the project partners to each deliverable

DROP TABLE IF EXISTS deliverable_partnerships;
CREATE TABLE deliverable_partnerships(
  id BIGINT(20) NOT NULL,
  deliverable_id BIGINT(20) NOT NULL,
  project_partner_id BIGINT(20) NOT NULL,
  user_id BIGINT(20) NOT NULL,
  PRIMARY KEY (id),
  INDEX `deliverable_project_partner_idx` (`project_partner_id` ASC),
  INDEX `deliverable_partnership_user_idx` (`user_id` ASC),
  INDEX `deliverable_partnership_deliverable_idx` (`deliverable_id` ASC),
  
  CONSTRAINT `FK_deliverable_partnership_project_partner`
    FOREIGN KEY (`project_partner_id`)
    REFERENCES `project_partners` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    
  CONSTRAINT `FK_deliverable_partnership_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    
  CONSTRAINT `FK_deliverable_partnership_deliverable`
    FOREIGN KEY (`deliverable_id`)
    REFERENCES `deliverables` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    