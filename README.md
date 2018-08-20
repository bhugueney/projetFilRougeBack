# ProjetFilRougeBack

This project was generated with [Spring Boot](https://github.com/spring-projects/spring-boot) version 2.0.2.

It uses a postgres database. Properties of this database are stored in application.properties file.

## Datas set automatic import
Two files will be used for populate/update this database : aliment.csv and glycemique.csv.
These files will be automatically imported at application startup from the folder [external_datas/import](external_datas/import). 
After successful import these 2 two files will be renamed with timestamp and moved in [external_datas/imported](external_datas/imported).

Import and imported folders locations is by default in the same directory as the app jar, but this can be changed if desired by values specified in application.properties file. 

An example of aliment.csv and glycemique.csv can be found in [external_datas/source/csv](external_datas/source/csv).




## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

    docker-compose -f src/main/docker/postgresql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/postgresql.yml down

For more information refer to [Using Docker and Docker-Compose](https://docs.docker.com/compose/), this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

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
mvn install
```

This can be done from eclispe :

>    right ckick on project → run as → Maven clean

>    right ckick on project → run as → Maven install


## Development server

Server will be launched on 'http://localhost:8095/'. 

By default it will accept all requests coming from local client (http://localhost:4200). 
This origin can be change via the property client.url in application.properties file.


## Launching the Application

To launch the application you can use one of those methods

### Running from an IDE

You can run a Spring Boot application from your IDE as a simple Java application. However, you first need to import your project. Import steps vary depending on your IDE and build system. Most IDEs can import Maven projects directly. 
For example, Eclipse users can select 
>    Import…​ → Existing Maven Projects from the File menu.


### Using the Maven Plugin
The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. Applications run in an exploded form, as they do in your IDE. The following example shows a typical Maven command to run a Spring Boot application:

    $ mvn spring-boot:run
    

### Running as a Packaged Application
If you use the Spring Boot Maven or Gradle plugins to create an executable jar, you can run your application using java -jar pathfilename-to-jar-file.

In this case don't forget to provide external_datas folder (see above in [Datas set automatic import](#datas-set-automatic-import) section)

Example of command line to start the application from target folder :

    $ java -jar target/FilRouge-0.0.1-SNAPSHOT.jar

if you want to provide a your own configuration file (to overrides default config) vou can use this command line (in this example you must have your  application.properties file in the current folder, if you prefer you can replace . by your path for the folder config) : 

    $ java -jar target/FilRouge-0.0.1-SNAPSHOT.jar  -Dspring.config.location=.
 


## Technical documentation (Java-doc generated)
You can find documentation regarding this server [here](./documentation/index.html) or in documentation repository of this  project.


## Further help

Feel free to join us on Slack or by email

[Gabriel Wisniewski](gabriel.wisniewski@gmail.com)

[Frederik Pareja](frederic.pareja.pf1@gmail.com)

[Hugues Poumeyrol](hugues.poumeyrol.pf1@gmail.com)
