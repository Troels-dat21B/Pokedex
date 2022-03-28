CREATE USER pokedex_user@localhost IDENTIFIED BY 'test123';
SELECT USER, host FROM mysql.user;

GRANT SELECT, insert, UPDATE, DELETE
    ON pokedex.*
    TO pokedex_user@localhost;
SHOW GRANTS FOR pokedex_user@localhost;