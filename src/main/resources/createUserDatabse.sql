CREATE USER pokedex_user@localhost IDENTIFIED BY 'test123'; --->Opretter bruger med kode i mellem ''

SELECT USER, host FROM mysql.user;---> Syntax til at se hvilke bruger der findes på databasen

GRANT SELECT, insert, UPDATE, DELETE ---> Syntax til at tildele rettigheder for brugeren
    ON pokedex.*
    TO pokedex_user@localhost;

SHOW GRANTS FOR pokedex_user@localhost; ----> Syntax til at få vist hvilke rettigheder en bruger har