FROM debian:buster-slim

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl

ADD target/Brown_Fields-1.1-jar-with-dependencies.jar /srv/Brown_fields-1.1.jar

WORKDIR /srv
EXPOSE 5050
CMD ["java", "-jar", "echo-Brown_fields-1.1.jar"]