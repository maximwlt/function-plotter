FROM eclipse-temurin:24-jdk-alpine

RUN apk add --no-cache socat bash

WORKDIR /app

COPY src/lvp-0.5.4.jar .
COPY src/funktionsplotter.java .
COPY src/Turtle.java .
COPY scripts/start.sh .

RUN chmod +x start.sh

CMD ["./start.sh"]



