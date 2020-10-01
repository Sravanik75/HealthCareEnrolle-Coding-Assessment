# CodingChallenge

# Enrolee registration service for Healthcare apps

This service handles enrolee registration and enrolee management services. That means adding/updating/removing of enrolees and adding/updating/removing of enrolee dependents.

Project uses SpringBoot and Mongo DB

# Swagger URL

http://<host>:<port>/swagger-ui.html

# Building project

To build project just execute: ./gradlew clean build

#Building a docker image
./gradlew bootBuildImage --imageName=sravani/healthcare-enrolee-management

# Running application

To run application in docker : docker run -p <port>:8080 <imagename>
