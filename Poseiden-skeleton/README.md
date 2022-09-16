![](src/main/resources/static/img/logo.png)

# Poseidon Inc.

1. Framework: Spring Boot v2.7.0
2. Java 17
3. Thymeleaf
4. Bootstrap v.5.2.0

## HOW TO USE

**Clone the project**
<br>`git clone https://github.com/panadesign/P7_PoseidonInc.git`

**Run application**
<br>In command line, go to the root of the project and execute:
<br>**mvn spring-boot:run**

**H2** for develop : ```mvn clean package -Pdev``` to use dev profile

http://localhost:8080
<br>http://localhost:8080/h2-console

You can now connect to the application with this admin account or user account :

1. Role ADMIN(username: admin, password: AdminSpring_123)
2. Role USER(username: user, password: UserSpring_123)

### You can also use a mysql database

User you're going to add in database:
<br>username: admin
<br>password: AdminSpring_123

With this admin account you can do all you want.
<br>You can add new user account at this address:<br>http://localhost:8080/user/list or http://localhost:8080/app/secure/article-details

**Create a new mysql database with:**
<br>CREATE DATABASE poseidonInc;
<br>USE poseidonInc;

And after use this SQL script:
<br>
create table user_account
(
id tinyint(4) NOT NULL AUTO_INCREMENT,
username VARCHAR(125),
password VARCHAR(125),
fullname VARCHAR(125),
role VARCHAR(125),

PRIMARY KEY (Id)
insert into Users(fullname, username, password, role) values("Administrator", "admin", "$2y$10$IBP5qFgrzcoHfKN2X6thmulbZ2jZDNi29PkVC2k4dG5Pl6nzsGFbC", "ADMIN")
)