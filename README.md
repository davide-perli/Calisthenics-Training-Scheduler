# Scope of Project

**This project aims to help calisthenics athletes create and manage a training program suited to their needs, preferences, and skill level.**

# Requirements

**Openjdk version "21.0.6" (compatibility with older versions is untested). Verify with:**
**```java -version```**
**PostgreSQL JDBC version 42.6.0 (ensure PostgreSQL is installed and running along with pgAdmin 4 in order to view created tables)**

# Compilation

**First in the terminal run ```javac *.java``` to compile the classes**
**Second run ```javac -cp .:postgresql-42.6.0.jar *.java``` to load JDBC drivers(Unix/macOS)**
**On Windows run ```javac -cp .;postgresql-42.6.0.jar *.java```**
**Lastly run ```java -cp .:postgresql-42.6.0.jar CalisthenicsApp``` to run the application(Unix/macOS)**
**On Windows run ```java -cp .;postgresql-42.6.0.jar CalisthenicsApp```**

## First stage requirements:
1. System definition
- [x] Create a list based on the chosen theme of at least **10 actions/queries** that can be done within the system and a list of at least **8 object types**.
2. Implementation
- [x] Implement a Java application based on those defined in the first point.
- [x] The application will contain:
    - [x] Simple classes with **private / protected attributes and access methods**
    - [x] At least **2 different collections** capable of managing the previously defined objects (e.g., List, Set, Map, etc.), of which at least one must be **sorted**
    - [x] Use **inheritance** to create additional classes and use them within collections
    - [x] At least **one service class** to expose system operations
    - [x] A **Main class** from which calls to services are made

# Second Stage requirements:

### Database Persistence Implementation

1. Relational Database Integration:
- [x] Implement persistence using a relational database (MySQL, PostgreSQL, Oracle, etc.)
- [x] Use JDBC for database connectivity
- [] Create appropriate database tables that map to your domain classes

2. CRUD Operations:
- [] Develop services that provide CRUD functionality for at least 4 of your domain classes and each service should implement:
  - Create (insert) operations
  - Read (select) operations
  - Update operations
  - Delete operations

3. Generic Singleton Services:
- [] Implement generic singleton services for database operations:
  - A service for writing to the database
  - A service for reading from the database

  I created a class singleton class for the database connection + a generic method to get the singleton instance, a generic method for executing a query which doesn't return results (INSERT, UPDATE, DELETE) and just prepares the statement and executes it and a generic method for executing a query which returns a result(SELECT)

### Audit Service Implementation

1. CSV Logging:
- [] Create a service that logs each executed action to a CSV file and the service should be singleton and thread-safe so I created a singleton class and for each create/view/update/delete operation I log the user + type of operation and the timestamp

2. File Structure:
- [] The CSV file should contain the following columns:
  - `action_name`: Name/description of the executed action
  - `timestamp`: When the action occurred (use ISO 8601 format)

## Actions/Queries:
 1. Create a user.
 2. Set a training program for the week.
 3. For each day of the week, create a training program.
 4. For the skill level selected for the day, the user can only see the corresponding exercises (e.g., for beginner static skills, the user will only see beginner static skills from which to choose).
 5. Choose a skill level for static skills for the user.
 6. Choose a static exercise.
 7. Choose a skill level for dynamic skills for the user.
 8. Choose a dynamic exercise.
 9. Choose sets and reps to do (no skill question here since the static one reflects it, and you can mostly choose almost any to train regardless).
10. At the end, view the training program for the week along with user information.
11. Options for wrong inputs (the user can re-enter input if it's not valid). 

## Implementation:
- Collections used: ArrayList, List, Map, HashMap, TreeMap.
- Sorted collection: Map (to sort the training program in the order of the weekdays).
- Created an abstract class `Exercise` which is inherited by `Static_Exercise`, `Dynamic_Exercise`, and `Sets_and_Reps` (since they are all exercises) and a `Service_Class` (for add, put, show operations, and sorting the Map).
- Service class: `Service_Class` is used to add the input to the ArrayList, which contains the exercises, and to the HashMap, which contains the exercises ranked by the skill level needed to do them. It also implements the sorting function for the Map mentioned earlier and displays the final training program, as well as implementing 2 getters from the abstract class to retrieve the ArrayList and HashMap created.
- Main class: `CalisthenicsApp` where the user can perform the actions/queries to create a training program.