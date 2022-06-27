FROM debian:buster-slim

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl

ADD target/echo-server-0.0.0-SNAPSHOT-jar-with-dependencies.jar /srv/echo-server-0.0.0.jar

WORKDIR /srv
EXPOSE 9000
CMD ["java", "-jar", "echo-server-0.0.0.jar"]