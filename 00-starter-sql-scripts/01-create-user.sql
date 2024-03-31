-- Drop user first if they exist
DROP USER if exists 'Priya'@'%' ;

-- Now create user with prop privileges
CREATE USER 'Priya'@'%' IDENTIFIED BY 'cogo';

GRANT ALL PRIVILEGES ON * . * TO 'Priya'@'%';