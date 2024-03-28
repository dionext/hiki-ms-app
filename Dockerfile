#FROM amazoncorretto:21.0.2
#FROM amazoncorretto:21.0.2-alpine
FROM bellsoft/liberica-openjdk-alpine-musl:21.0.2
COPY ./target/hiki-ms-app-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","hiki-ms-app-0.0.1-SNAPSHOT.jar"]