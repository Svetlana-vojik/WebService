FROM openjdk:19
ARG JAR_FILE
COPY target/SpringBootProject-0.0.1-SNAPSHOT.jar /SpringBootProject-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringBootProject-0.0.1-SNAPSHOT.jar","-Djava.security.egd=file:/dev/./urandom","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]