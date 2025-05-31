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
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";


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
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC driver loaded successfully");

            try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {

                System.out.println("Connected to PostgreSQL database successfully!");

                statement.execute(createTableSQL);
                System.out.println("Table '" + tableName + "' created or verified successfully!");

                try (PreparedStatement insert = connection.prepareStatement(insertSQL)) {
                    for (Map.Entry<String, ExercisePlan> entry : weeklyPlan.entrySet()) {
                        String day = entry.getKey();
                        ExercisePlan plan = entry.getValue();


                        insert.setString(1, day);
                        insert.setString(2, plan.getStaticExercise());
                        insert.setString(3, plan.getDynamicExercise());
                        insert.setString(4, plan.getSetsAndReps());

                        insert.executeUpdate();
                    }
                }

                System.out.println("Training plan stored successfully for athlete: " + athleteName);

            }
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found: " + e.getMessage());
            e.printStackTrace();
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
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";

        String tableName = athleteName.toLowerCase().replaceAll("[^a-z]", "") + "_training_plan";
        String selectSQL = "SELECT day, static_exercise, dynamic_exercise, sets_reps_exercise " +
                        "FROM " + tableName;

        HashMap<String, ExercisePlan> weeklyPlan = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSQL)) {

            while (rs.next()) {
                String day = rs.getString("day");
                String staticEx = rs.getString("static_exercise");
                String dynamicEx = rs.getString("dynamic_exercise");
                String setsReps = rs.getString("sets_reps_exercise");

                ExercisePlan plan = new ExercisePlan(staticEx, dynamicEx, setsReps);
                weeklyPlan.put(day, plan);
            }

            Map<String, ExercisePlan> sortedPlan = sortMap(weeklyPlan);
            outPlan(weeklyPlan, sortedPlan);

        } catch (SQLException e) {
            System.err.println("Error fetching training program: " + e.getMessage());
            e.printStackTrace();
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
