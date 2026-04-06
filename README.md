# Library Management System

A Spring Boot application for managing a library’s core workflows, including books, users, loans, subscriptions, and related catalog data.

## Features

- Book and genre management
- User and subscription management
- Book loan tracking
- REST-style web API
- Validation, persistence, and security support
- Mail support for application notifications

## Tech Stack

- Java 25
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate / JPA
- MySQL
- Lombok
- Jakarta EE APIs
- Maven

## Project Structure

The application follows a layered architecture:

- `controller` — web/API endpoints
- `services` — business logic
- `repo` — data access
- `domain` / `model` — core entities and data objects
- `mapper` — object mapping helpers
- `payload` — request/response DTOs
- `exception` — application-specific error handling
- `configuration` — app configuration

## Prerequisites

- Java 25
- Maven
- MySQL database


