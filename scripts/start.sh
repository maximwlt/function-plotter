#!/bin/bash

socat TCP-LISTEN:50001,bind=0.0.0.0,fork TCP:127.0.0.1:50002 &
exec java -jar lvp-0.5.4.jar --log --watch=funktionsplotter.java 50002