import java.util.ArrayList;
import java.util.HashMap;

abstract class Exercise {
  protected ArrayList<String> exercises;
  protected HashMap<String, String> x;

  protected Exercise(){
    exercises = new ArrayList<>();
    x = new HashMap<>();
  }

  // Getter
  public abstract HashMap<String, String> getExerciseRanked();


  public abstract String getExercise(int index);

}
