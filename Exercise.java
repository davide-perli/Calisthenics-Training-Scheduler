import java.util.ArrayList;
import java.util.HashMap;

abstract class Exercise {
  protected ArrayList<String> exercises;
  protected HashMap<String, String> exercise_ranked;

  protected Exercise(){
    exercises = new ArrayList<>();
    exercise_ranked = new HashMap<>();
  }

  // Getter
  public HashMap<String, String> getExerciseRanked() {
    return exercise_ranked;
  }

  public void showExercises() {
    System.out.println(getClass().getSimpleName() + " exercises: " + exercise_ranked);
  }
 
}
