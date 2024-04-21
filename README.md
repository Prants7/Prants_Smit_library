## How to run
* Application is expecting a postgres database on "localhost:5432" with default "postgres" database name, and 
"public" schema, username: "postgres" and password: "postgres"

* Personally used "docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres" 
docker command to generate database

* To be careful as the app is running liquibase for migration and is configured to run against the most basic
postgres database with most common username and password combination, so there is a slight chance of getting some
unwanted new tables in your general postgres instance

* if you got a database set up (ideally docker image) then just run main method in "Application" class

* good luck, there is no situation that can't be improved by some luck



