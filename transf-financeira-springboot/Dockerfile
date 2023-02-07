FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ./target/transf-financeira-springboot.jar transf-financeira-springboot.jar
ENTRYPOINT ["java", "-jar", "transf-financeira-springboot.jar"]