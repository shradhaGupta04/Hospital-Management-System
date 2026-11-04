# Hospital Management System 🏥

## About the Project

Hospital Management System is a console-based Java application that uses JDBC and MySQL to manage basic hospital operations.

## Features

- Add patient details
- View registered patients
- View doctors and their specializations
- Book appointments for patients
- Check whether a doctor already has an appointment on the selected date
- Store and retrieve information using MySQL

## Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- SQL
- Object-Oriented Programming (OOP)

## Project Structure

- `HospitalManagementSystem.java` — Main menu and appointment management
- `Patient.java` — Add, view, and find patients
- `Doctors.java` — View doctors and find doctors by ID
- `database.sql` — Database and table creation queries (to be added)

## Database Setup

1. Install MySQL Server.
2. Create a database named `hospital`.
3. Create the `patients`, `doctors`, and `appointments` tables.
4. Configure the database connection in the application.
5. Add MySQL Connector/J to the project's classpath.

## How to Run

1. Install the JDK and MySQL Server.
2. Set up the database and tables.
3. Configure the JDBC driver and database connection.
4. Open the project in your Java IDE.
5. Run `HospitalManagementSystem.java`.

## Future Improvements

- Add patient search and update functionality.
- Validate appointment dates.
- Prevent conflicting appointments.
- Add a graphical user interface.
- Improve database configuration and error handling.

## Author

Shradha Gupta  
B.Tech Information Technology  
Haldia Institute of Technology
