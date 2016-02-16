ALTER TABLE `project_highligths`
ADD COLUMN `objectives`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL after results  ,
ADD COLUMN `publisher`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL  after results ,
ADD COLUMN  `is_global`  tinyint(1) NOT NULL after results;


ALTER TABLE $[database]_history.`project_highligths`
ADD COLUMN `objectives`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL after results  ,
ADD COLUMN `publisher`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL  after results ,
ADD COLUMN  `is_global`  tinyint(1) NOT NULL after results;
