FROM openjdk:8-jdk-alpine
COPY target/car-dealer-work-orders-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT [ "java", "-jar", "java-app.jar" ]
