FROM openjdk:17
ADD target/DockerPostService.jar DockerPostService.jar
EXPOSE 3010
ENTRYPOINT ["java","-jar","DockerPostService.jar"]