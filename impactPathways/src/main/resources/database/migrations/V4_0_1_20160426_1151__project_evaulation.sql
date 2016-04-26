CREATE TABLE `project_evaluation` (
`id`  bigint NOT NULL AUTO_INCREMENT ,
`project_id`  bigint NOT NULL ,
`type_evaluation`  varchar(100) NOT NULL ,
`user_id`  bigint NOT NULL ,
`is_submited`  bit NOT NULL ,
`ranking_outputs`  double NOT NULL ,
`ranking_outcomes`  double NOT NULL ,
`ranking_parternship_comunnication`  double NOT NULL ,
`ranking_response_team`  double NOT NULL ,
`ranking_quality`  double NOT NULL ,
`communication_products`  text NOT NULL ,
`project_highlights`  text NOT NULL ,
`outcome_case_studies`  text NOT NULL ,
`general_comments`  text NOT NULL ,
`recommendations`  text NOT NULL ,
`any_action_requeried`  text NOT NULL ,
`total_score`  double NOT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
)
;

