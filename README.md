# FishMarketWebAPP-Java-SpringBoot  
Final exam project of FTN Informatika .NET web development course rewritten in Java  
Made in IntelliJ IDEA

Web application with elementary functionalities for fish markets and the fish they sell, implemented using the following working frameworks, tools and techniques:

SERVER:  
• Spring Boot  
• Spring Security  
• JWT Token  
• Implementation of Mapper, Repository pattern, Service and Dependency Injection concept   
• Persistence of data in a relational database on a local MySQL Server (initial values located in: ~/sql/initial_values.sql)
  
TEST:  
• Unit testing of controller actions using the MockMVC framework
  
CLIENT (Single Page Application):  
• JavaScript  
• Fetch API  
• HTML  
• CSS  
• Bootstrap  

Initial login accounts:  
• User: bojan ; Password: bojan  
• User: admin ; Password: admin   

Steps to run an application:  
1. go to ~/src/main/resources/application.properties
2. Edit spring.datasource.*  
3. Run spring boot application  
4. Execute initial values   
5. Run client (index.html)  
