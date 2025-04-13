import java.util.ArrayList;
import java.util.HashMap;

public class Sets_and_reps extends Exercise {

    private Service_class x;

    public Sets_and_reps(){
        x = new Service_class();
        x.putInput("Dips(pronated, supinated, neutral)", "beginner");
        x.putInput("Pull-Ups(pronated, supinated, neutral)", "beginner");
        x.putInput("Push-Ups(pronated, supinated, neutral)", "beginner");
        x.putInput("Russian Dips", "beginner");
        x.putInput("Crunches", "beginner");
        x.putInput("Shoulder Taps(for abdomen)", "beginner");
        x.putInput("V-Sits", "beginner");
        x.putInput("Squats", "beginner");
        x.putInput("One Leg Assisted Squats", "beginner");
        x.putInput("Bulgarian Squats", "beginner");
        x.putInput("Forearm Push-Ups", "intermidiate");
        x.putInput("One Arm Push-Ups", "intermidiate");
        x.putInput("One Arm Pull-Ups", "advanced");
        x.putInput("Tricep Push-Ups", "advanced");

        this.exercises = new ArrayList<>(x.exercises);
    }
    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    } 
    
    @Override
    public HashMap<String, String> getExerciseRanked() {
        return x.getExerciseRanked();
    }
}
