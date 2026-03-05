# build image: docker build . -t cmiethling/accounts:s4
# create+run container: docker run -d -p 8080:8080 cmiethling/accounts:s4
# push MS to docker hub (github of docker): docker image push docker.io/cmiethling/accounts:s4

# Start with base image containing Java runtime; search in dockerhub
FROM eclipse-temurin:21-jdk

#Info around who maintains the image
LABEL maintainer="eazybytes.com"

# copy from accounts/target to docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# when container is generated from docker image (=docker is run) then run this commands
# java -jar target/accounts-0.0.1-SNAPSHOT.jar # execute app
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]