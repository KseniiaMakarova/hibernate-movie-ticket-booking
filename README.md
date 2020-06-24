## MOVIE TICKETS ONLINE BOOKING

![Header Image](src/main/resources/cinema.gif)

---

[Project purpose](#project-purpose)

[Project structure](#project-structure)

[Implementation details](#implementation-details)

[Launch guide](#launch-guide)

[Author](#author)

---
## Project purpose

The project comprises the **basic back-end functionality** of a movie tickets online booking **REST-ful web application**.
It makes use of **Hibernate, Spring and Spring Security frameworks**.

---

The following entities are used: Movie, CinemaHall, MovieSession, Ticket, User, Role, ShoppingCart, Order.

**Register and login endpoints** are available for anyone.

When authenticated, users with both ADMIN and USER roles are able to:

    - get all Movies and CinemaHalls
    - get all available MovieSessions

Users with ADMIN role only are able to:

    - add a new Movie, CinemaHall or MovieSession
    - get a User by email

Users with USER role only are able to:

    - add a particular MovieSession Ticket to their ShoppingCart
    - view their ShoppingCart
    - complete an Order
    - get their Order history

---
## Project structure

The project uses the **MVC architectural pattern**. Project structure is the following:

- Entity classes, **DTOs** and **DTOMappers**
- **DAO layers**, containing basic CRUD-operations
- **Service layers**, containing business logic of the application
- **Controllers**, implementing client-server communication logic
- **Spring configuration classes**

---
## Implementation details

Some other project features were implemented using:
- **JPA Criteria API** - for DB queries
- **BCryptPasswordEncoder** - for user password encryption
- **Jackson API** - for working with JSON
- custom SecurityConfigurerAdapter and GenericFilterBean - for **JWT user authentication**
- both **custom** and Hibernate built-in annotations - for **DTO fields validation**
- custom **ResponseEntityExceptionHandler implementation**
- Log4j2 library
- Maven Checkstyle Plugin, Travis CI and **SonarCloud Continuous Code Quality Tool**

---
## Launch guide

To run this project you will need to install:

- [JDK 11 or higher](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Apache Tomcat](https://tomcat.apache.org/download-90.cgi)
- [MySQL RDBMS](https://dev.mysql.com/downloads/installer)
- [Postman (recommended)](https://www.postman.com/downloads)

Here are the steps for you to follow:

- Add this project to your IDE as **Maven project**.
- If necessary, configure Java SDK 11 in Project Structure settings.
- Add new Tomcat Server configuration and select **war-exploded artifact** to deploy. Set **application context** parameter to "/".
- Change **path to your log file** in _src/main/resources/log4j2.properties_ on line 5. You may also want to change the 'filePattern' parameter on line 18.
- Execute the following line in MySQL RDBMS in order to **create the schema**: 

      CREATE SCHEMA `ticket_booking` DEFAULT CHARACTER SET utf8;
- Enter your **own username and password** in _src\main\resources\db.properties_ class on lines 3-4.
- Run the project via Tomcat configuration.
- To access endpoints, use **Postman** or any alternative tool capable of making POST queries and altering request headers.

A user with ADMIN role will be registered automatically with an email "admin@gmail.com" and a password "1234".

All subsequent users you will register will have the USER role.

After a successful login, you will receive the JWT token which you should pass in the Authentication header in any subsequent request if you wish to remain authenticated as a current user.

---
## Author

[Kseniia Makarova](https://github.com/KseniiaMakarova)
