
# AOP Notice Board System

A web-based notice board for Adeseun Ogundoyin Polytechnic (AOP), allowing admins to publish notices and students to view and search them, built with Jakarta EE, JSP, and PostgreSQL.

## Tech Stack
- Java 17
- Jakarta EE (`jakarta.servlet-api` 6.0.0) + JSP + JSTL 3.0
- PostgreSQL (JDBC driver 42.7.4)
- Apache Tomcat 10.1
- Maven (packaging: `war`)

## Prerequisites
- [JDK 17](https://adoptium.net/) or later
- [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/) (Eclipse EE)
- [Apache Tomcat 10.1](https://tomcat.apache.org/download-10.cgi)
- [PostgreSQL](https://www.postgresql.org/download/) (local, or a cloud instance such as [Neon](https://neon.tech))
- Git

## Project Structure
notice-board/ └── src/main/java/com/noticeboard/ ├── dao/ ├── model/ ├── servlet/ # admin/, student/, notice-details, search-notices, mark-notice-read └── util/ # DBConnection (DB connection handling)

## 1. Clone the Repository
```bash
git clone https://github.com/giangel/notice-board.git
2. Import into Eclipse
1.	Open Eclipse.
2.	File -> Import -> Maven -> Existing Maven Projects.
3.	Browse to the cloned notice-board folder -> Finish.
4.	Let Eclipse resolve the Maven dependencies.
3. Set Up PostgreSQL
1.	Install PostgreSQL and make sure the server is running.
2.	Create the database: 
3.	CREATE DATABASE noticeboard_db;
4.	Run your schema/table creation script against noticeboard_db.
4. Configure Database Credentials
The app falls back to local dev defaults but reads these environment variables if present:
Variable	Default (dev fallback)
DB_URL	jdbc:postgresql://localhost:5432/noticeboard_db
DB_USERNAME	postgres
DB_PASSWORD	(your local DB password)
Set these under Run/Debug Configurations -> Environment in Eclipse, or as system environment variables. For a cloud database like Neon, set DB_URL to the connection string Neon gives you (include ?sslmode=require).
5. Add Tomcat to Eclipse
1.	Servers tab -> right-click -> New -> Server.
2.	Choose Apache -> Tomcat v10.1 Server, point it to your Tomcat installation, click Finish.
6. Deploy and Run
1.	Right-click the project -> Run As -> Run on Server.
2.	Select the Tomcat v10.1 server -> Finish.
7. Access the App
The WAR deploys with the finalName ROOT, so the app runs at the server root:
http://localhost:8080/login.jsp
Building Manually
mvn clean package
Deploy the resulting WAR from target/ into Tomcat's webapps/ folder (rename to ROOT.war to serve from the root context).
License
This project was built for academic purposes.
