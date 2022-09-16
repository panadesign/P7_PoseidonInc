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
CREATE TABLE USER_ACCOUNT
(
id tinyint(4) NOT NULL AUTO_INCREMENT,
username VARCHAR(125),
password VARCHAR(125),
fullname VARCHAR(125),
role VARCHAR(125),
PRIMARY KEY (Id)
)
CREATE TABLE BidList (
id tinyint(4) NOT NULL AUTO_INCREMENT,
account VARCHAR(30) NOT NULL,
type VARCHAR(30) NOT NULL,
bidQuantity DOUBLE,
askQuantity DOUBLE,
bid DOUBLE ,
ask DOUBLE,
benchmark VARCHAR(125),
bidListDate TIMESTAMP,
commentary VARCHAR(125),
security VARCHAR(125),
status VARCHAR(10),
trader VARCHAR(125),
book VARCHAR(125),
creationName VARCHAR(125),
creationDate TIMESTAMP ,
revisionName VARCHAR(125),
revisionDate TIMESTAMP ,
dealName VARCHAR(125),
dealType VARCHAR(125),
sourceListId VARCHAR(125),
side VARCHAR(125),

PRIMARY KEY (BidListId)
)

CREATE TABLE Trade (
id tinyint(4) NOT NULL AUTO_INCREMENT,
account VARCHAR(30) NOT NULL,
type VARCHAR(30) NOT NULL,
buyQuantity DOUBLE,
sellQuantity DOUBLE,
buyPrice DOUBLE ,
sellPrice DOUBLE,
tradeDate TIMESTAMP,
security VARCHAR(125),
status VARCHAR(10),
trader VARCHAR(125),
benchmark VARCHAR(125),
book VARCHAR(125),
creationName VARCHAR(125),
creationDate TIMESTAMP ,
revisionName VARCHAR(125),
revisionDate TIMESTAMP ,
dealName VARCHAR(125),
dealType VARCHAR(125),
sourceListId VARCHAR(125),
side VARCHAR(125),

PRIMARY KEY (TradeId)
)

CREATE TABLE CurvePoint (
id tinyint(4) NOT NULL AUTO_INCREMENT,
CurveId tinyint,
asOfDate TIMESTAMP,
term DOUBLE ,
curveValue DOUBLE ,
creationDate TIMESTAMP ,

PRIMARY KEY (Id)
)

CREATE TABLE Rating (
id tinyint(4) NOT NULL AUTO_INCREMENT,
moodysRating VARCHAR(125),
sandPRating VARCHAR(125),
fitchRating VARCHAR(125),
orderNumber tinyint,

PRIMARY KEY (Id)
)

CREATE TABLE RuleName (
id tinyint(4) NOT NULL AUTO_INCREMENT,
name VARCHAR(125),
description VARCHAR(125),
json VARCHAR(125),
template VARCHAR(512),
sqlStr VARCHAR(125),
sqlPart VARCHAR(125),

PRIMARY KEY (Id)
)

insert into userAccount(fullname, username, password, role) values("Administrator", "admin", "$2y$10$IBP5qFgrzcoHfKN2X6thmulbZ2jZDNi29PkVC2k4dG5Pl6nzsGFbC", "ADMIN")
