FROM maven:3.8.4-openjdk-17 as build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /app/target/TestPanProject-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app.jar"]
