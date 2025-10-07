FROM maven:3.9.9-amazoncorretto-17-al2023 AS build

LABEL maintainer="Logic Flare <logicflare3@gmail.com>"

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2023-jdk

WORKDIR /app

COPY --from=build /app/target/rest.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "rest.jar"]
