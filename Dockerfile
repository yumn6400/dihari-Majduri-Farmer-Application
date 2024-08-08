FROM openjdk:17-jdk
ADD target/dihari-majduri.jar dihari-majduri.jar
ENTRYPOINT ["java","-jar","/dihari-majduri.jar"]