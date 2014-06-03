--
-- 01.01.0000
--


-- Creating the table to log all the changes maked into the DB

CREATE TABLE IF NOT EXISTS `schema_changes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `change_id` varchar(10) NOT NULL,
  `date_applied` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`change_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creating the schema change variable (sc) needed to identify the changes
SET @sc_id='';

-- testing with a fake change
SET @sc_id:='1.1.0001';

INSERT IGNORE INTO `schema_changes` (`change_id`) SELECT @sc_id;

-- iF
IF (ROW_COUNT() != 0)
THEN 
-- Aqui va el cambio a la bd
  