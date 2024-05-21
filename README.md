# Risk Assessment

## Description

Risk Assessment is a Spring Boot application for managing user registrations and confirmations. It provides REST endpoints for registering users and confirming registrations via email.

## Controllers

### RegistrationController

Responsible for handling user registration requests.

- **Endpoint**: `/register`
- **Method**: POST
- **Produces**: application/json

### ConfirmController

Handles confirmation of user registrations.

- **Endpoint**: `/confirm/{uid}`
- **Method**: PUT

## Service

### EmailService

Provides functionality for sending emails.

## Author

Nikk (dominik@minesort.de)
