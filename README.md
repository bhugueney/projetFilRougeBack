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

For more information refer to [Using Docker and Docker-Compose][TODO], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

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

## Build

**Download**
- Clone the project to your machine
```
git clone https://github.com/pf1fhg/projetFilRougeBack.git
```

**Build**

- Build the project
```
mvn clean package
```

To be completed by [Hugues]()

## Development server

Server will be launched on 'http://localhost:8095/'. 
It will accept all requests coming from local client (http://localhost:4200). This origin can be change in class CorsFilter.java

To launch the application you can use on of those methods

### Running from an IDE

You can run a Spring Boot application from your IDE as a simple Java application. However, you first need to import your project. Import steps vary depending on your IDE and build system. Most IDEs can import Maven projects directly. 
For example, Eclipse users can select 
>    Import…​ → Existing Maven Projects from the File menu.


### Using the Maven Plugin
The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. Applications run in an exploded form, as they do in your IDE. The following example shows a typical Maven command to run a Spring Boot application:

    $ mvn spring-boot:run
    

### Running as a Packaged Application
If you use the Spring Boot Maven or Gradle plugins to create an executable jar, you can run your application using java -jar, as shown in the following example:

    $ java -jar target/FilRouge-0.0.1-SNAPSHOT.jar

## Further help

Feel free to join us on Slack or by email

[Gabriel Wisniewski](gabriel.wisniewski@gmail.com)

[Frederik Pujada]()

[Hugues au frais]()
