# sample-java-spring-data-neo4j

This application was created for testing Spring Data Neo4j and Neo4j OGM lib.

# Run application and build an executable JAR

You can run the application from the command line with Gradle. Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources, and run that. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

You can run the application using ./gradlew bootRun. Or you can build the JAR file using ./gradlew build. Then you can run the JAR file:

java -jar build/libs/neo4jrest-0.0.1-SNAPSHOT.jar

# Deploy WAR file to Tomcat

For Spring Boot WAR deployment, you need to do three steps:

1. Extends SpringBootServletInitializer
2. Marked the embedded servlet container as provided.
3. Update packaging to war

# Swagger
For testing api methods with Swagger - run application and open http://localhost:8080/swagger-ui.html
