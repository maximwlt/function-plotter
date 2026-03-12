FROM eclipse-temurin:24-jdk-alpine

RUN apk add --no-cache socat bash

WORKDIR /app

COPY lvp-0.5.4.jar .
COPY funktionsplotter.java .
COPY Turtle.java .
COPY start.sh .

RUN chmod +x start.sh

CMD ["./start.sh"]

#CMD socat TCP-LISTEN:50001,bind=0.0.0.0,fork TCP:127.0.0.1:50001 & java -jar lvp-0.5.4.jar --log --watch=src.funktionsplotter.java
# ENTRYPOINT ["java", "-jar", "lvp-0.5.4.jar", "--log", "--watch=src.funktionsplotter.java"]


