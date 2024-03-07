# car-dealer-work-orders

para iniciar la prueba se debe ejecutar en un terminal
- /docker-compose up
- ./mvnw spring-boot:run -f pom.xm

Para realizar las pruebas el collection de postman esta en la carpeta principal web-store en:
 - /PostmanCollection

La Idea del proyecto es Logear con Usuario, previamente un admin debe crear los OrderType ('Mantenimiento',
'Pintura', 'Reparación'). Seguidamente se crea el Cliente, Vehículo, La orden de trabajo, a la cual se
va asociar dicho cliente y vehículo y un orderType, posteriormente con el endpoint de WorkOrderItem 
se le da una descripcción

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
