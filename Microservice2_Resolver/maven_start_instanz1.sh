#!/bin/bash

clear

rm logdatei_instanz1.log 2> /dev/null

./mvnw clean spring-boot:run -Dspring-boot.run.profiles=instanz1
