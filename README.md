1. Database setup:

- execute setup-db.sh script
<<<<<<< Updated upstream
=======
- after the database is set up, execute db_config.sql script
>>>>>>> Stashed changes

2. Keycloak setup:

keycloak url -> http://localhost:8090/
user -> admin
password -> admin

on the left top corner, click 'Create realm' button
choose a path to the realm-export.json file 

<<<<<<< Updated upstream
=======
go to realm settings, select events tab, add custom-registration-listener to the event listeners
save the changes

>>>>>>> Stashed changes

3. OpenAPI documentation:

api documentation -> http://localhost:8080/api-docs
swagger ui -> http://localhost:8080/swagger-ui/index.html