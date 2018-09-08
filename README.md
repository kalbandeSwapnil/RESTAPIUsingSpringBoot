# RESTAPIUsingSpringBoot
REST API implementation for User Management Operation like Create, Update , Read, etc 

I have used Hibernate using JPA, springboot MVC framework to build this project.

All APIs will accpet and produce application/ json only  and configured with basic authentication.

# REST APIs are :
1. To get all users 
   GET http://localhost:8080/springrestapp/users/getAll?page=1&pageSize=600
   
2. To add user :
   POST http://localhost:8080/springrestapp/users/addUser
   Request Body :
     {
            "username": "test12",
            "firstName": "test12",
            "lastName": "test12"
    } 
3. To Delete User using id:
   DELETE http://localhost:8080/springrestapp/users/deleteUser?id=1
4. To search user :
   GET http://localhost:8080/springrestapp/users/search

5. To update user:  
   POST http://localhost:8080/springrestapp/users/updateUser
     Request Body :
     {
            "id": 12    
            "username": "test12",
            "firstName": "test12",
            "lastName": "test12"
    } 
