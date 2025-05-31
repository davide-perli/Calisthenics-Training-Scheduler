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

    public void storePlan(String athleteName, HashMap<String, ExercisePlan> weeklyPlan) {
        String url = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
        String user = "postgres";
        String password = "dlmvm";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS training_plan (" +
                "id SERIAL PRIMARY KEY, " +
                "athlete_name VARCHAR(50) NOT NULL, " +
                "day VARCHAR(10) NOT NULL, " +
                "static_exercise VARCHAR(100) NOT NULL, " +
                "dynamic_exercise VARCHAR(100) NOT NULL, " +
                "sets_reps_exercise VARCHAR(100) NOT NULL" +
                ")";

        String insertSQL = "INSERT INTO training_plan (athlete_name, day, static_exercise, dynamic_exercise, sets_reps_exercise) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC driver loaded successfully");

            try (Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()) {

                System.out.println("Connected to PostgreSQL database successfully!");

                statement.execute(createTableSQL);
                System.out.println("Table 'training_plan' created/verified successfully!");

                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    for (Map.Entry<String, ExercisePlan> entry : weeklyPlan.entrySet()) {
                        String day = entry.getKey();
                        ExercisePlan plan = entry.getValue();

                        pstmt.setString(1, athleteName);
                        pstmt.setString(2, day);
                        pstmt.setString(3, plan.getStaticExercise());
                        pstmt.setString(4, plan.getDynamicExercise());
                        pstmt.setString(5, plan.getSetsAndReps());

                        pstmt.executeUpdate();
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



    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    }

    @Override
    public HashMap<String, String> getExerciseRanked() {
        return exercise_ranked;
    }

}
