import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Service_class extends Exercise {
    public void addInput(String input){
        exercises.add(input);
    }

    public void putInput(String Input, String value){
        x.put(Input, value);
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

    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    }

    @Override
    public HashMap<String, String> getExerciseRanked() {
        return x;
    }

}
