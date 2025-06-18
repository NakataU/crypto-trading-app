# Crypto Simulation Application

This is a Spring Boot RESTful backend application that exposes APIs to be consumed by an Angular frontend. The project follows a modular architecture and demonstrates full-stack development with clearly separated layers: the Entity Layer maps domain models to database tables using JPA; the Service Layer handles business logic and data processing; the Controller Layer exposes RESTful endpoints for the Angular frontend; and the Repository Layer manages data persistence using Spring Data JPA. The frontend, developed in Angular, communicates with the backend over HTTP.

## Core Functionalities

**Buy Crypto** – Simulate buying cryptocurrencies using your free balance.

**Sell Crypto** – Sell your crypto holdings and convert them to free balance.

**Reset Profile** – Reset your user profile and start with a clean portfolio.

**View Transactions** – Review your entire trading history.

**View Assets** – Check your current crypto holdings and their values.

**Dual Balance System:**
- **Free Balance:** Represents funds available for new trades.
- **Assets Balance:** Reflects the value of currently held cryptocurrencies.


## Installation
To run this application locally, you must have Java 22, Angular CLI 18+, and a configured PostgreSQL database. The backend is divided into two modules: entities, which contains the JPA entity definitions, and services, which contains business logic, REST controllers, and the application startup class. To launch the backend, simply start the application from the `StartUp.java` class located in the services module.

For the frontend, navigate to `Front End/trading-front-end` and start the Angular development server using the command `ng serve`. Make sure the backend is running and properly connected to your PostgreSQL database.

This application also includes Swagger integration for API documentation and testing. Once the backend is running, you can access the Swagger UI at: http://localhost:8080/swagger-ui.html
