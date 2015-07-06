-- -----------------------------------------------------------------------------
--    Updating type projects when projects are bilateral in title. 
-- -----------------------------------------------------------------------------

-- Updating column type of projects table.
UPDATE projects
SET type= "BILATERAL"
WHERE title LIKE "%Bilateral%";
