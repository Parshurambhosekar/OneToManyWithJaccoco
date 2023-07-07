
FROM openjdk:11

COPY target/onetomany_docker_app.jar /usr/app/

WORKDIR /usr/app

EXPOSE 8085

ENTRYPOINT ["java","-jar","onetomany_docker_app.jar"]

