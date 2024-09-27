# Track2Bsky

## Description
This project is a Spring Boot application that provides a REST API for integrating a user's Spotify and Bluesky accounts. The primary functionality of this backend is to update the user's Bluesky profile description with the song currently playing on Spotify. It features secure authentication using JWT tokens, automatic password encoding, and comprehensive API documentation via Swagger (OpenAPI). The project also serves as a robust template for future applications utilizing Java 21.

## Technologies Used
- Java 21
- Spring Boot
- Gradle
- Swagger (OpenAPI)

## Project Setup

### Prerequisites
- JDK 11 or higher (Recommended: JDK 21)
- Gradle 6.0 or higher

### Cloning the Repository

```
git clone https://github.com/joicepassos/core-application.git
cd core-application
```

### Building the Project
After navigating to the project directory, you can build the project using the following command:
```
./gradlew build
```

## Running the Application
To start the application, use the following command:

```
./gradlew bootRun
```

This will start the application on the default port 8080. You can check the API documentation by accessing the Swagger UI at:

```
http://localhost:8080/swagger-ui/
```

## Security

The application uses JWT-based authentication. To access protected endpoints, you must include the JWT token in the request header:
```
Authorization: Bearer <your-jwt-token>
```




