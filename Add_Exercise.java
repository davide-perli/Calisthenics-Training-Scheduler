import java.sql.SQLException;

public class Add_Exercise {
    private final DatabaseService db = DatabaseService.getInstance();

    public void addStaticExercises() {
        String createSQL = ""
          + "CREATE TABLE IF NOT EXISTS static_exercise ("
          + "  id          SERIAL PRIMARY KEY, "
          + "  name        VARCHAR(100) NOT NULL, "
          + "  skill_level VARCHAR(20)  NOT NULL"
          + ")";
        try {
            db.executeUpdate(createSQL, pstmt -> {});
        } catch (SQLException e) {
            System.err.println("Could not create static_exercise table: " + e.getMessage());
            return;
        }

        String[][] staticExercises = {
            {"L-Sit", "beginner"},
            {"Handstand", "beginner"},
            {"Pull-Over", "beginner"},
            {"Muscle-Up", "beginner"},
            {"Handstand Push-Up", "intermidiate"},
            {"Back Lever", "intermidiate"},
            {"Front Lever", "intermidiate"},
            {"Dragon Press", "intermidiate"},
            {"Back Lever Pull-Up", "advanced"},
            {"Front Lever Pull-Up", "advanced"},
            {"Front Lever Press", "advanced"},
            {"Planche", "advanced"},
            {"Hefesto", "advanced"},
            {"Planche Push-Up", "advanced"},
            {"Planche Press", "advanced"},
            {"Front Lever Touch", "advanced"},
            {"Victorian", "advanced"},
            {"One Arm Front Lever", "advanced"},
            {"Back Lever Hefesto", "advanced"},
            {"Front SAT", "expert"},
            {"Back SAT", "expert"},
            {"Maltese", "expert"},
            {"Maltese Press", "expert"},
            {"Entrada de Angel", "expert"}
        };

        String insertSQL = "INSERT INTO static_exercise (name, skill_level) VALUES (?, ?)";
        for (String[] ex : staticExercises) {
            try {
                db.executeUpdate(insertSQL, pstmt -> {
                    try {
                        pstmt.setString(1, ex[0]);
                        pstmt.setString(2, ex[1]);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                System.out.println("Failed to insert static exercise: " + ex[0] + "  (" + e.getMessage() + ")");
            }
        }
    }

    public void addDynamicExercises() {
        String createSQL = ""
          + "CREATE TABLE IF NOT EXISTS dynamic_exercise ("
          + "  id          SERIAL PRIMARY KEY, "
          + "  name        VARCHAR(100) NOT NULL, "
          + "  skill_level VARCHAR(20)  NOT NULL"
          + ")";
        try {
            db.executeUpdate(createSQL, pstmt -> {});
        } catch (SQLException e) {
            System.err.println("Could not create dynamic_exercise table: " + e.getMessage());
            return;
        }

        String[][] dynamicExercises = {
            {"180", "beginner"},
            {"Front Roll", "beginner"},
            {"Dragon 360", "intermidiate"},
            {"360", "intermidiate"},
            {"Baby Giant", "intermidiate"},
            {"Giant", "advanced"},
            {"Geinger", "advanced"},
            {"540", "advanced"},
            {"Front Flip Regrab", "expert"},
            {"720", "expert"},
            {"Giant 360", "expert"}
        };

        String insertSQL = "INSERT INTO dynamic_exercise (name, skill_level) VALUES (?, ?)";
        for (String[] ex : dynamicExercises) {
            try {
                db.executeUpdate(insertSQL, pstmt -> {
                    try {
                        pstmt.setString(1, ex[0]);
                        pstmt.setString(2, ex[1]);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                System.out.println("Failed to insert dynamic exercise: " + ex[0] + "  (" + e.getMessage() + ")");
            }
        }
    }

    public void addSetsAndReps() {
        String createSQL = ""
          + "CREATE TABLE IF NOT EXISTS sets_and_reps ("
          + "  id          SERIAL PRIMARY KEY, "
          + "  name        VARCHAR(100) NOT NULL, "
          + "  skill_level VARCHAR(20)  NOT NULL"
          + ")";
        try {
            db.executeUpdate(createSQL, pstmt -> {});
        } catch (SQLException e) {
            System.err.println("Could not create sets_and_reps table: " + e.getMessage());
            return;
        }

        String[][] setsAndReps = {
            {"Dips(pronated, supinated, neutral)", "beginner"},
            {"Pull-Ups(pronated, supinated, neutral)", "beginner"},
            {"Push-Ups(pronated, supinated, neutral)", "beginner"},
            {"Russian Dips", "beginner"},
            {"Crunches", "beginner"},
            {"Shoulder Taps(for abdomen)", "beginner"},
            {"V-Sits", "beginner"},
            {"Squats", "beginner"},
            {"One Leg Assisted Squats", "beginner"},
            {"Bulgarian Squats", "beginner"},
            {"Forearm Push-Ups", "intermidiate"},
            {"One Arm Push-Ups", "intermidiate"},
            {"One Arm Pull-Ups", "advanced"},
            {"Tricep Push-Ups", "advanced"}
        };

        String insertSQL = "INSERT INTO sets_and_reps (name, skill_level) VALUES (?, ?)";
        for (String[] ex : setsAndReps) {
            try {
                db.executeUpdate(insertSQL, pstmt -> {
                    try {
                        pstmt.setString(1, ex[0]);
                        pstmt.setString(2, ex[1]);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                System.out.println("Failed to insert sets and reps exercise: " + ex[0] + "  (" + e.getMessage() + ")");
            }
        }
    }

    public void seedAll() {
        addStaticExercises();
        addDynamicExercises();
        addSetsAndReps();
        System.out.println("All exercises seeded to the database.");
    }
}
