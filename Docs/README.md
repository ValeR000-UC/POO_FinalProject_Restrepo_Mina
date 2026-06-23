# POO_FinalProject_Restrepo_Mina

# Movie Rental System

Internal movie rental management system developed in Java using Object-Oriented Programming principles, layered architecture, data persistence through serialization, and a recommendation component based on rental history.

---

## Team Members

* Valery Restrepo — 66395
* Sebastian Mina— 62640

----------------------------------------------------------------------------------------------------------------------

## Project Description

Movie Rental System is an internal-use application designed for employees or operators of a video rental store to manage the complete rental process
The system allows registering and managing movies, customers, genres and rentals while preserving application data between executions through object serialization.

The project was developed following Object-Oriented Programming principles such as:

* **Abstraction** → domain entities represent real business concepts.
* **Encapsulation** → each class manages and protects its own state.
* **Inheritance** → applied through the UI abstraction layer.
* **Polymorphism** → implemented through interchangeable UI behavior.

The application follows a layered architecture to separate responsibilities and improve maintainability.

---

## MAIN FEATURES:

### Movie Management

* Register movies
* Search movies by:

  * ID
  * Name
  * Genre

* List:

  * All movies
  * Available movies
  * Rented movies
* Delete movies (only if not rented)

### Customer Management

* Register customers
* Search customers

* List:

  * Active customers
  * Inactive customers
* Delete customers

### Rental Management

* Create rentals
* Return rented movies
* Search rentals by ID

* List:

  * Active rentals
  * Inactive rentals

* Automatic rental cost calculation based on:

  * Daily rental price
  * Rental duration
  * Number of rented movies

### System Configuration

* Consult current rental price
* Update rental price
* Consult current user information

### Recommendation System

The system includes a recommendation component implemented without external libraries.

The recommendation algorithm:

1. Reads all rentals registered in the system.
2. Counts how many times each movie genre appears.
3. Identifies the most rented genre.
4. Recommends available movies from that genre.

This functionality is integrated into the application data and exposed through the user interface.

---

## Technologies

* Java 17 (LTS)
* Object-Oriented Programming
* Java Collections (`ArrayList`)
* Serialization (`ObjectInputStream`, `ObjectOutputStream`)
* CSV Import/Export
* Console User Interface

---

## Project Structure

src/
│
├── ui/
│   ├── MainMenu.java
│   ├── Ui.java
│   ├── UiConsole.java
│   └── Main.java
│
├── domain/
│   ├── Store.java
│   ├── Movie.java
│   ├── Rental.java
│   ├── Customer.java
│   ├── Genre.java
│   └── User.java
│
└── data/
    ├── DataManager.java
    └── CSVManager.java
----------------------------------------------------------------------------------------------------------------------

### Package Responsibilities:

#### ui/

Contains the user interaction layer implemented using console menus.

#### domain/

Contains business entities and system logic.

#### data/

Responsible for persistence, serialization and CSV data exchange.

---

## Persistence

The project implements two persistence mechanisms.

### Main Persistence (Required)

Implemented through serialization

Classes:

* `DataManager`
* `ObjectOutputStream`
* `ObjectInputStream`

Stores and restores the complete `Store` object preserving the application state.

Generated file: store.dat

---

### CSV Import / Export (Optional)

Implemented to allow data interchange and initial loading.

Classes:

* `CSVManager`

Supported entities:

* Movies
* Customers
* Genres
* Rentals

Generated files: movies.csv, customers.csv, genres.csv, rentals.csv
---

## Design Decisions

* The application manages data in memory using **ArrayList**.
* `Store` acts as the central object that contains all system collections.
* Rentals preserve business information independently from future rental price changes.
* The architecture separates business logic, persistence and user interaction.

---
## Requirements
* Java 17 or Java 21 (LTS)
* VS Code, IntelliJ IDEA or Eclipse
---

## Example Input / Output

### Input

```
Movie ID: MV001
Customer ID: C001
Rental Days: 5
```
### Output

```
Rental created successfully.
Total rental cost: 25000
```
---
## Response Codes

| Code | Meaning                          |
|------|----------------------------------|
| 100  | Successful operation             |
| 200  | Element not found                |
| 201  | Element not available            |
| 202  | Invalid data                     |
| 203  | Empty list                       |
| 300  | Operation not permitted          |
| 301  | Duplicate record                 |
| 302  | Movie cannot be rented           |
| 303  | Movie cannot be returned         |
| 304  | Object cannot be deleted         |
| 305  | Registration failed              |

## UML Diagram

Project class diagram: "UML Diagram.png"
---
## Notes

* No databases were used.
* No external libraries or frameworks were used.
* All source code and comments were written in English.
* The project compiles and runs from console and IDE environments.
