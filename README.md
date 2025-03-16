# Faculty Scheduler

Faculty Scheduler is a JavaFX-based desktop application designed for managing faculty office hours. This project is part of the CS151 Object-Oriented Design course and is developed by Team 03 in Section 04.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Team Contributions](#team-contributions)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Build and Run Instructions](#build-and-run-instructions)

---

## Project Overview

The Faculty Scheduler application allows faculty members to manage their office hours efficiently. Users can define the semester's office hours, schedule appointments, and eventually view reports. In Code Version 0.2, the application features a modern user interface with a dynamic home page and a dedicated section for defining semester office hours.

---

## Features

- **Modern UI**: Sleek, professional design using JavaFX.
- **Dynamic Date Display**: The current date is displayed dynamically in the top bar.
- **User Welcome Page**: A home page with a personalized welcome message and user photo.
- **Navigation Sidebar**: A left sidebar with options like Home, Semester's Office Hours, Appointment, Search Schedules, and Reports.
- **Define Semester Form**: A form to set the semester, year, and available office hours (days of the week).
- **Responsive Layout**: The interface uses a ScrollPane and responsive design principles to adapt to different window sizes.

---

## Installation

1. **Clone the Repository:**
```bash
git clone https://github.com/Soham0047/CS151-SemesterProject
```
Navigate to the Project Directory:
```bash
cd faculty-scheduler/dev-03-0.2/
```

## Usage
1. **Open the Project in IntelliJ IDEA.**
2. **Set the Project SDK to OpenJDK 23 (or your chosen version) via Project Structure.**
3. **Build the Project:**
```bash
mvn clean compile
```
4. **Run the Application:**
```bash
mvn javafx:run
```

## Project Structure
```bash
faculty-scheduler/dev-03-0.2/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── s25.cs151.application
│       │               ├── Main.java
│       │               ├── HomePageController.java
│       │               └── DefineSemesterController.java
│       └── resources/
│           ├── HomePage.fxml
│           ├── DefineSemester.fxml
│           └── icons/
│               ├── dashboard.png
│               ├── schedule.png
│               ├── calendar.png
│               ├── appointment.png
│               ├── search.png
│               ├── report.png
│               └── userPhoto.png
└── target/
```
## Dependencies
1. JavaFX: Version 20
2. Maven: 3.8.1
3. JUnit: 3.8.1 (for tests)

## Build and Run Instructions
1. **Build the Project:**
```bash
mvn clean compile
```
2. **Run the Application:**
```bash
mvn javafx:run
```

