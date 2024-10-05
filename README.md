# Spring Batch Demo Project

This project is a demo application built with Spring Boot and Spring Batch that allows users to upload a CSV or Excel file containing a large dataset of customers. The application processes the uploaded data and saves it into a `Customer` table in a MySQL database.

## Features

- Upload CSV or Excel files containing customer data.
- Parse and save customer information into the database.
- Utilizes Spring Batch for efficient data processing.

## Dependencies

The project uses the following dependencies:

- **Spring Boot Starters:**
    - `spring-boot-starter-batch` for batch processing support.
    - `spring-boot-starter-data-jpa` for database interaction with JPA.
    - `spring-boot-starter-web` for creating RESTful APIs.
  
- **Lombok:**
    - `lombok` for reducing boilerplate code in Java.

- **MySQL Connector:**
    - `mysql-connector-j` for connecting to a MySQL database.

- **Apache POI:**
    - `poi` for handling `.xls` files (HSSF).
    - `poi-ooxml` for handling `.xlsx` files (XSSF).

- **OpenCSV:**
    - `opencsv` for reading and writing CSV files.

- **Jackson:**
    - `jackson-dataformat-csv` for processing CSV data.

- **Testing:**
    - `spring-boot-starter-test` for unit and integration testing.
    - `spring-batch-test` for testing Spring Batch components.
    - `junit-platform-launcher` for running JUnit tests.

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- MySQL Database

### Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <project-directory>
