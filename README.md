
# Spring App

![Logo](logo.png)

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

This is a fullstack application that enables you to add, remove, or modify customers. It is a Maven project with Java and Spring Boot for the backend and React and JavaScript for the frontend.

## Features

- Add, remove, and modify customer records
- User-friendly interface with React
- Backend API with Spring Boot
- Dockerized application for easy deployment
- Continuous Integration and Deployment with GitHub Actions

## Technologies Used

- Java
- Spring Boot
- React
- JavaScript
- PostgreSQL
- Docker
- Jib
- Testcontainers
- Mockito
- GitHub Actions
- AWS Elastic Beanstalk

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

To use the application, navigate to `http://localhost:3000` in your web browser. You can add, remove, and modify customer records using the interface.

## Backend

The backend is built with Java and Spring Boot. It uses a PostgreSQL database running in a Docker container. The backend exposes a REST API for managing customer records.

## Frontend

The frontend is built with React and JavaScript. It provides a user-friendly interface for interacting with the customer records. There is also a version available with Angular and TypeScript.

## Testing

Testing is done with Spring Boot, Testcontainers, and Mockito. Run the tests with the following command:
```sh
mvn test
```

## Package

Packaging is done with Docker and Jib. To build the Docker image, run:
```sh
mvn compile jib:dockerBuild
```

## CI/CD

The project uses GitHub Actions for Continuous Integration and Continuous Deployment. Tests and deployments are automatically triggered on pull requests.

## Deployment

The application is deployed on AWS Free Tier using Elastic Beanstalk. It uses an Elastic Beanstalk container to spin up the needed environment, which includes the application container and a PostgreSQL container.

## Screenshots

![Screenshot 1](https://user-images.githubusercontent.com/ddcsoftdev/spring-app/screenshots/screenshot-2024-01-30-13-29-17.png)
![Screenshot 2](https://user-images.githubusercontent.com/ddcsoftdev/spring-app/screenshots/screenshot-2024-01-30-13-30-33.png)

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
