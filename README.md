# Scope of Project

**This project aims to help calisthenics athletes create and manage a training program suited to their needs, preferences, and skill level.**

# Requirements

* **OpenJDK 21.0.6** (compatibility with older versions is untested). Verify with:

  ```bash
  java -version
  ```
* **PostgreSQL**
* **PostgreSQL JDBC driver 42.6.0** (included as `postgresql-42.6.0.jar`)
* **pgAdmin 4**

---

## Database Setup

To create the three required exercise tables, run the application and log in as `admin`. Then choose **Option 4. Add default exercises**. This automatically executes the necessary `CREATE TABLE IF NOT EXISTS …` commands and populates each table. Exit the admin menu by choosing **Option 5**, and rerun the application as a normal user.
(More information about `admin` check at the admin sectionS)

---

## Compilation

1. Compile all `.java` files:

   ```bash
   javac *.java
   ```

2. Include the JDBC driver on the classpath:

   * **Linux(Unix)/macOS:**

     ```bash
     javac -cp .:postgresql-42.6.0.jar *.java
     ```
   * **Windows:**

     ```bash
     javac -cp .;postgresql-42.6.0.jar *.java
     ```

3. Run the application:

   * **Linux(Unix)/macOS:**

     ```bash
     java -cp .:postgresql-42.6.0.jar CalisthenicsApp
     ```
   * **Windows:**

     ```bash
     java -cp .;postgresql-42.6.0.jar CalisthenicsApp
     ```

---

# First Stage Requirements (Completed)

1. **System definition**

   * Defined at least 10 actions/queries and 8 object types

# Actions / Queries

1. **Create a user.**
2. **Set a training program for the week.**
3. **For each day of the week, create a training program entry.**
4. **Filter by skill level** when choosing static or dynamic exercises.
5. **Choose a static skill level**, then pick a static exercise from that level.
6. **Choose a dynamic skill level**, then pick a dynamic exercise.
7. **Choose a sets‐and‐reps exercise** (no further filtering).
8. **View the training program** for the week, showing each day’s exercises.
9. **Modify** yesterday’s plan by swapping in new exercises.
10. **Delete** the entire training program.
11. **Manage exercise tables** (view/add/update/delete static/dynamic/sets\_and\_reps), if logged in as `admin`.
12. **Seed default exercises** into the three exercise tables (if logged in as `admin`).
13. **Handle invalid input** by re-prompting the user.

2. **Implementation**

   * Private/protected attributes with getters/setters
   * Used at least two collections—`ArrayList`, `HashMap`, `TreeMap`—one of which is sorted
   * Inheritance: `Exercise` → (`Static_exercise`, `Dynamic_exercise`, `Sets_and_reps`) and `Service_class` extends `Exercise`
   * Service class (`Service_class`) exposes operations
   * `CalisthenicsApp` is the main class that invokes services

* **Collections used**:

  * `ArrayList<String>` for in-memory lists of exercise names
  * `HashMap<String,String>` for name→skill\_level mapping (ranking)
  * `TreeMap<String,ExercisePlan>` for sorting the weekly training plan by day index

* **Inheritance hierarchy**:

  ```
  Exercise (abstract)
  ├─ Static_exercise
  ├─ Dynamic_exercise
  └─ Sets_and_reps
  ```

---

# Second Stage Requirements (Completed)

## 1. Relational Database Integration

* **Persistence** is implemented via PostgreSQL + JDBC
* **Tables** are created either manually or via `Add_Exercise` code
* **DatabaseService** acts as a generic singleton for executing queries

**DatabaseService (singleton)**

  * Manages a single JDBC connection pool (or connection)
  * Provides:

    * `executeUpdate(String sql, SQLConsumer<PreparedStatement> paramSetter)` for INSERT/UPDATE/DELETE/DDL
    * `executeQuery(String sql, SQLConsumer<PreparedStatement> paramSetter, ResultSetHandler<T> handler)` for SELECT
  * Ensures thread-safe, reusable database operations

## 2. CRUD Operations

* **Service\_class**

  * **Extends `Exercise`**, so it must override all four abstract CRUD methods from `Exercise` (these throw `UnsupportedOperationException`, since actual CRUD is done in the three subclasses)
  * Manages training-program tables (one table per athlete):

    1. `doesTableExist(Connection, String tableName)`
    2. `storePlan(String athleteName, HashMap<String,ExercisePlan> weeklyPlan)`
    3. `showExistingPlan(String athleteName)`
    4. `showExistingPlanForDay(String athleteName, String day)`
    5. `updateTrainingDay(String athleteName, String day, String staticEx, String dynamicEx, String setsReps)`
    6. `deletePlan(String athleteName)`
  * Also holds in-memory lists/maps via inherited `exercises` and `exercise_ranked`, used by subclasses to populate their maps

* **Add\_Exercise**

  * Populates default exercise data into `static_exercise`, `dynamic_exercise`, and `sets_and_reps`
  * Before inserting, it runs `CREATE TABLE IF NOT EXISTS ...` for each table to ensure they exist
  * Methods:

    * `addStaticExercises()`
    * `addDynamicExercises()`
    * `addSetsAndReps()`
    * `seedAll()` calls the three above in order

* **Static\_exercise, Dynamic\_exercise, Sets\_and\_reps** each implement:

  * **Create**: `addExerciseToDB(String name, String skillLevel)`
  * **Read**: `getAllExercisesFromDB()`
  * **Update**: `updateExerciseName(int id, String newName)`
  * **Delete**: `deleteExercise(int id)`

## 4. Audit Service

* **Training\_program\_tracker\_csv** logs every create/view/update/delete action of a training program to a CSV file
* Each row: `action_name, timestamp` in ISO-8601, thread-safe

---

### Main Class

* **CalisthenicsApp**

  1. Prompts user for a name and sanitizes it (letters only)
  2. Checks `athleteName_training_plan` table existence

     * If `admin`, shows an **Admin menu**
     * If not admin and table exists:

       1. Create new training program (calls `Training.putInDatabase(...)`)
       2. View existing program (`Service_class.showExistingPlan(...)`)
       3. Modify existing program (`Service_class.updateTrainingDay(...)`)
       4. Delete program (`Service_class.deletePlan(...)`)
     * If not admin and table does not exist:

       1. Immediately create a new training program (calls `Training.putInDatabase(...)`)
  3. All console input is via a single `Scanner(System.in)` passed to `manageExercises(...)`

* **Training**

  * Builds a weekly plan (`HashMap<String,ExercisePlan>`) for a given athlete
  * Provides `putInDatabase(...)` to store that plan in the athlete’s table (creating it first if necessary)
  * Provides `showPlan(...)` to print the plan to console in weekday order (using a `TreeMap` sorted by day index)

#### Admin Menu

When a user logs in as `admin`, they see:

```
ADMIN MENU
What do you want to manage?
1. Static exercises
2. Dynamic exercises
3. Sets & Reps exercises
4. Add default exercises
5. Exit
```

* **Option 1**
  Invokes `manageExercises(new Static_exercise(), "static", myObj)` to perform CRUD on `static_exercise`
* **Option 2**
  Invokes `manageExercises(new Dynamic_exercise(), "dynamic", myObj)` to perform CRUD on `dynamic_exercise`
* **Option 3**
  Invokes `manageExercises(new Sets_and_reps(), "sets_and_reps", myObj)` to perform CRUD on `sets_and_reps`
* **Option 4**
  Invokes `Add_Exercise.seedAll()` which:

  1. Creates tables `static_exercise`, `dynamic_exercise`, `sets_and_reps` if they don’t exist
  2. Inserts default exercise rows into each
* **Option 5**
  Exits the admin menu (and thus the application in admin mode)

---
