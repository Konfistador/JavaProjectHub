#!/bin/bash

if [ ! -d target ]; then
     mvn compile
fi
       

java -Duser.language=nl -Duser.country=NL -cp target/classes/ pub.HablasEspañol
java -Duser.language=es -Duser.country=ES -cp target/classes/ pub.HablasEspañol
