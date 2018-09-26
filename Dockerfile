# Start with a base image containing Java runtime

FROM openjdk:8
ADD /target/member-service-1.0.0.jar member-service.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "member-service.jar"]
