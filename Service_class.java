import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.sql.*;

// javac *.java -- daca ai probleme cu clasele le recreezi practic
// DRIVER LOADING:
// javac -cp .:postgresql-42.6.0.jar *.java
// java -cp .:postgresql-42.6.0.jar CalisthenicsApp

public class Service_class extends Exercise {

    private final DatabaseService dbService = DatabaseService.getInstance();

    public void addInput(String input){
        exercises.add(input);
    }

    public void putInput(String Input, String value){
        exercise_ranked.put(Input, value);
    }

    public Map<String, ExercisePlan> sortMap(HashMap<String, ExercisePlan> weeklyPlan){
        List<String> dayOrder = Arrays.asList("MON", "TUE", "WEN", "THU", "FRY", "SAT", "SUN");
        Map<String, ExercisePlan> sortedWeeklyPlan = new TreeMap<>(Comparator.comparingInt(dayOrder::indexOf));
        sortedWeeklyPlan.putAll(weeklyPlan);
        return sortedWeeklyPlan;
    }

    public void outPlan(HashMap<String, ExercisePlan> weeklyPlan, Map<String, ExercisePlan> sortedWeeklyPlan){
        for (Map.Entry<String, ExercisePlan> entry : sortedWeeklyPlan.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public boolean doesTableExist(Connection connection, String tableName) throws SQLException {
        String checkTableSQL = "SELECT EXISTS (" +
                "SELECT FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_name = ?" +
                ")";
        try (PreparedStatement pstmt = connection.prepareStatement(checkTableSQL)) {
            pstmt.setString(1, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        }
        return false;
    }

    public void storePlan(String athleteName, HashMap<String, ExercisePlan> weeklyPlan) {
        
        String lower_case_AthleteName = athleteName.toLowerCase().replaceAll("[^a-z]", "");
        String tableName = lower_case_AthleteName + "_training_plan";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id SERIAL PRIMARY KEY, " +
                "day VARCHAR(10) NOT NULL, " +
                "static_exercise VARCHAR(100) NOT NULL, " +
                "dynamic_exercise VARCHAR(100) NOT NULL, " +
                "sets_reps_exercise VARCHAR(100) NOT NULL" +
                ")";

        String insertSQL = "INSERT INTO " + tableName + " (day, static_exercise, dynamic_exercise, sets_reps_exercise) " + "VALUES (?, ?, ?, ?)";

        try {
            dbService.executeUpdate(createTableSQL, insert -> {});


            for (Map.Entry<String, ExercisePlan> entry : weeklyPlan.entrySet()) {
                String day = entry.getKey();
                ExercisePlan plan = entry.getValue();


                dbService.executeUpdate(insertSQL, insert -> {
                    try {
                        insert.setString(1, day);
                        insert.setString(2, plan.getStaticExercise());
                        insert.setString(3, plan.getDynamicExercise());
                        insert.setString(4, plan.getSetsAndReps());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
                

            System.out.println("Training plan stored successfully for athlete: " + athleteName);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletePlan(String athleteName) {
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";

        String tableName = athleteName.toLowerCase().replaceAll("[^a-z]", "") + "_training_plan";
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            statement.execute(dropTableSQL);
            System.out.println("Training program for athlete '" + athleteName + "' deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting training program: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showExistingPlan(String athleteName) {
        String tableName = athleteName.toLowerCase().replaceAll("[^a-z]", "") + "_training_plan";
        String selectSQL = "SELECT day, static_exercise, dynamic_exercise, sets_reps_exercise " + "FROM " + tableName;

        try {
            HashMap<String, ExercisePlan> weeklyPlan = dbService.executeQuery(selectSQL, null, rs -> {
                HashMap<String, ExercisePlan> map = new HashMap<>();
                try {
                    while (rs.next()) {
                        String day = rs.getString("day");
                        String staticEx = rs.getString("static_exercise");
                        String dynamicEx = rs.getString("dynamic_exercise");
                        String setsReps = rs.getString("sets_reps_exercise");
                        map.put(day, new ExercisePlan(staticEx, dynamicEx, setsReps));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return map;
            });

            Map<String, ExercisePlan> sortedPlan = sortMap(weeklyPlan);
            outPlan(weeklyPlan, sortedPlan);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        Training_program_tracker_csv.getInstance().logAction(athleteName + "_show_program");
    }

    public void showExistingPlanForDay(String athleteName, String day) {
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";

        String tableName = athleteName.toLowerCase().replaceAll("[^a-z]", "") + "_training_plan";
        String selectSQL = "SELECT day, static_exercise, dynamic_exercise, sets_reps_exercise " +
                        "FROM " + tableName + " WHERE day = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            
            // Set the parameter value for the day
            statement.setString(1, day);
            
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("No training plan found for " + day);
                    return;
                }

                while (rs.next()) {
                    String staticEx = rs.getString("static_exercise");
                    String dynamicEx = rs.getString("dynamic_exercise");
                    String setsReps = rs.getString("sets_reps_exercise");

                    ExercisePlan plan = new ExercisePlan(staticEx, dynamicEx, setsReps);
                    System.out.println(day + " -> " + plan.toString());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching training plan for day " + day + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean updateTrainingDay(String athleteName, String day, String staticEx, 
                               String dynamicEx, String setsReps) {
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";

        String tableName = athleteName.toLowerCase().replaceAll("[^a-z]", "") + "_training_plan";
        String updateSQL = "UPDATE " + tableName + " SET static_exercise = ?, " +
                        "dynamic_exercise = ?, sets_reps_exercise = ? WHERE day = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            
            statement.setString(1, staticEx);
            statement.setString(2, dynamicEx);
            statement.setString(3, setsReps);
            statement.setString(4, day);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating training plan for day " + day + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    }

    @Override
    public HashMap<String, String> getExerciseRanked() {
        return exercise_ranked;
    }

}
