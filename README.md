# Summary
This is a fullstack application that enables you to add, remove or modify customers. This is a Maven project with Java and Springboot for the Backend and React and JavaScript
for the Frontend.

# Backend
The Backend is handled with Java and Springboot. The database is a postgres Docker imager that is connected to the application.

# Frontend
Frontend has been done with React and JavaScript. There is also a version with Angular an Typescript

# Testing
Testing is done with Springboot, Testcointainer and Mockito.

# Package
Packaging is done with Docker and Jib

# CI/CD
It relies on Github Actions to run all the tests and deployment automatically when doing a pull request in Github

# Deployment
It uses AWS Free tier for deployment, using and Elastic Beanstalk cointainer to sping up the needed enviroment (application container + postgres container)
