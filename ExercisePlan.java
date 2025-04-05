// Helper Class
public class ExercisePlan {
    private String staticExercise;
    private String dynamicExercise;
    private String setsAndReps;

    public ExercisePlan(String staticExercise, String dynamicExercise, String setsAndReps) {
        this.staticExercise = staticExercise;
        this.dynamicExercise = dynamicExercise;
        this.setsAndReps = setsAndReps;
    }

    public String getStaticExercise() {
        return staticExercise;
    }

    public String getDynamicExercise() {
        return dynamicExercise;
    }

    public String getSetsAndReps() {
        return setsAndReps;
    }

    @Override
    public String toString() {
        return "[Static: " + staticExercise + ", Dynamic: " + dynamicExercise + ", Sets&Reps: " + setsAndReps + "]";
    }
}
