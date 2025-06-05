import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract class Exercise {
  protected ArrayList<String> exercises;
  protected HashMap<String, String> exercise_ranked;

  protected Exercise(){
    exercises = new ArrayList<>();
    exercise_ranked = new HashMap<>();
  }

  public abstract List<String> getAllExercisesFromDB();

  public abstract void addExerciseToDB(String name, String skillLevel);

  public abstract void updateExerciseName(int id, String newName);

  public abstract void deleteExercise(int id);

  public void showAllExercisesWithIds() {
      String sql = "SELECT id, name, skill_level FROM " + getTableName();
      try {
          DatabaseService.getInstance().executeQuery(sql, null, rs -> {
              System.out.println("\nID | Name | Skill Level");
              try {
                  while (rs.next()) {
                      System.out.printf("%d | %s | %s\n", rs.getInt("id"), rs.getString("name"), rs.getString("skill_level"));
                  }
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
              return null;
          });
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }
  private String getTableName() {
      if (this instanceof Static_exercise) return "static_exercise";
      if (this instanceof Dynamic_exercise) return "dynamic_exercise";
      return "sets_and_reps";
  }




  // Getter
  public abstract HashMap<String, String> getExerciseRanked();


  public abstract String getExercise(int index);

}
