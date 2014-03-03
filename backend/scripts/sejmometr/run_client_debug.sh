#!/bin/bash

MAIN_PATH=/home/tomasz/workspace/sejmAPI/sejm-ngram/backend
SEJMOMETR_CLIENT=$MAIN_PATH/sejmometr-client
SEJMOMETR_JAR=$SEJMOMETR_CLIENT/target/sejmometr-client-1.0-SNAPSHOT-jar-with-dependencies.jar

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROPERTIES_FILE_LOCATION=$SCRIPT_DIR/common.properties

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005 -Dprop.file.loc=$PROPERTIES_FILE_LOCATION -jar $SEJMOMETR_JAR