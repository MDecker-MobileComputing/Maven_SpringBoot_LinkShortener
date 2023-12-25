#!/bin/bash

clear

rm logdatei_instanz2.log 2> /dev/null

./mvnw clean spring-boot:run -Dspring-boot.run.profiles=instanz2


