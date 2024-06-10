
# Spring App

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Backend](#backend)
- [Frontend](#frontend)
- [Testing](#testing)
- [Package](#package)
- [CI/CD](#cicd)
- [Deployment](#deployment)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This is a full-stack application that enables you to add, remove, or modify customers. It is a Maven project with Java and Spring Boot for the backend and React and JavaScript for the frontend. An alternative frontend implementation using Angular and TypeScript is also available.

## Features

- Add, remove, and modify customer records
- User-friendly interface with React and JavaScript
- Backend API with Spring Boot
- Dockerized application for easy deployment
- Continuous Integration and Deployment with GitHub Actions
- PostgreSQL database integration
- Testcontainers for integration tests
- Jib for container image building
- Deployment on AWS Elastic Beanstalk

## Technologies Used

- **Backend:** Java, Spring Boot, PostgreSQL
- **Frontend:** React, JavaScript (alternative: Angular, TypeScript)
- **Testing:** Spring Boot Testing, Testcontainers, Mockito
- **Packaging:** Docker, Jib
- **CI/CD:** GitHub Actions
- **Deployment:** AWS Elastic Beanstalk

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/ddcsoftdev/spring-app.git
   ```

2. Navigate to the project directory:
   ```sh
   cd spring-app
   ```

3. Build the project:
   ```sh
   mvn clean install
   ```

4. Run the backend:
   ```sh
   cd backend
   mvn spring-boot:run
   ```

5. Run the frontend:
   ```sh
   cd frontend
   npm install
   npm start
   ```

## Usage

To use the application, navigate to `http://localhost:5173` in your web browser. You can add, remove, and modify customer records using the interface.

## Backend

The backend is built with Java and Spring Boot. It uses a PostgreSQL database running in a Docker container. The backend exposes a REST API for managing customer records.

## Frontend

The frontend is built with React and JavaScript, providing a user-friendly interface for interacting with customer records. There is also an alternative version available using Angular and TypeScript.

## Testing

### Tools and Frameworks

- **Spring Boot Testing:** Utilized for unit and integration tests within the backend.
- **Testcontainers:** Provides lightweight, disposable instances of databases, message brokers, and other services for automated testing.
- **Mockito:** A Java-based mocking framework used for unit testing of components.

### Running Tests

Run the tests with the following command:
```sh
mvn test
```

## Package

### Tools

- **Docker:** Used for containerizing the application to ensure it runs consistently across different environments.
- **Jib:** A tool that builds optimized Docker and OCI images for Java applications without requiring a Docker daemon.

### Building Docker Image

To build the Docker image, run:
```sh
mvn compile jib:dockerBuild
```

## CI/CD

The project uses GitHub Actions for Continuous Integration and Continuous Deployment. Automated testing and deployment workflows are triggered on pull requests and merges to the main branch.

## Deployment

### Platform

- **AWS Free Tier:** Hosting platform offering a free tier with sufficient resources for small applications.

### Service

- **Elastic Beanstalk:** An easy-to-use service for deploying and scaling web applications and services. It hosts both the application and the PostgreSQL database containers.

### Deployment Steps

1. Configure AWS Elastic Beanstalk environment
2. Deploy Dockerized application and database
3. Monitor and scale the application as needed

## Media

[Watch Demo](https://drive.google.com/file/d/1BOsiEDTh6VLDI1AaAUWCxuWY7rcxniCX/view?usp=sharing)

## Contributing

We welcome contributions! To contribute, follow these steps:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature/your-feature`).
6. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
