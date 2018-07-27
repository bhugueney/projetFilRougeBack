# ProjetFilRougeBack

This project was generated with [Spring Boot](https://github.com/spring-projects/spring-boot) version 2.0.2.

It uses a postgres database. Properties of this database are stored in application.properties file.

2 files will be used for populate/update this database : aliment.csv and glycemique.csv in folder resources/data/csv.
They will be loaded automatically during application start.

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Development server

java -jar target/????.jar

Server is launch on 'http://localhost:8095/'. 
It will accept all requests coming from local client (http://localhost:4200). This origin can be change in class CorsFilter.java


## Build

To be completed by [Hugues]()


## Further help

Feel free to join us on Slack or by email
[Gabriel Wisniewski](gabriel.wisniewski@gmail.com)
[Frederik Pujada]()
[Hugues au frais]()
