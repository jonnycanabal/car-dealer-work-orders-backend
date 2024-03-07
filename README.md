# car-dealer-work-orders

para iniciar la prueba se debe ejecutar en un terminal
- /docker-compose up
- ./mvnw spring-boot:run -f pom.xm

Para realizar las pruebas el collection de postman esta en la carpeta principal web-store en:
 - /PostmanCollection

Usuario y contraseña por defecto:
 - username: "Admin"
 - password: "12345"

En el postman collection se debe realizar el login primero, está en la carpeta:
 - Login/login
 - endpoint: "localhost:8080/login"

Recordar copiar el token de autenticación generado con JWT Token y
pegarlo en las solicitudes correspondientes en donde dice:
 - Authorization
 - Type
 - Bearer Token
 - toke: "Pegar el token"
