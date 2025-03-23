Database setup:

    - pull the postgres version 17 image
        'docker pull postgres:17'

    - create a postgres container 
        'docker run -d --name listtick -p 5432:5432 -e POSTGRES_PASSWORD=admin postgres:17'

    - check if you can see the postgres container
        'docker ps'

    - connect to the container 
        'docker exec -it postgres psql -U postgres'

    - create the project databases
        'CREATE DATABASE account'
        'CREATE DATABASE bucket_list'
        'CREATE DATABASE note'
        'CREATE DATABASE notification'
        'CREATE DATABASE shopping_list'
        'CREATE DATABASE task'

    - configure the InteliJ database plugin connections for every database