#!/bin/bash

clear

./mvnw clean javadoc:javadoc -Dshop=private -DadditionalJOption=-Xdoclint:none

echo


