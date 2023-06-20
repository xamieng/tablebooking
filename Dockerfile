FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

#Backend
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY build/libs/reservation-1.0.jar $PROJECT_HOME/reservation.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Djava.security.egd=file:/dev/./urandom","-jar", "./reservation.jar"]

