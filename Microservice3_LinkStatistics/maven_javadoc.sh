#!/bin/bash

clear

./mvnw clean javadoc:javadoc -Dshow=private -DadditionalJOption=-Xdoclint:none

echo


