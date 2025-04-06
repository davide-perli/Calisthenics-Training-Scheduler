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

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    @Override
    public String toString() {
        return "|Static: " + padRight(staticExercise, 19) +
               "|  |Dynamic: " + padRight(dynamicExercise, 17) +
               "|  |Sets&Reps: " + padRight(setsAndReps, 38) + "|";
    }
}
