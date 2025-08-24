#!/usr/bin/env sh
MVNW_REPOURL="https://repo.maven.apache.org/maven2"
JAVA_EXE="java"
WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
  mkdir -p .mvn/wrapper
  curl -L -o "$WRAPPER_JAR" "$WRAPPER_URL"
fi
exec "$JAVA_EXE" -jar "$WRAPPER_JAR" "$@"
