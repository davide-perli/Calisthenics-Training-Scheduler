import java.util.HashSet;

public class Training_program {
    protected HashSet<String> weekdays = new HashSet<>();

    public void addDay(String day){
        weekdays.add(day);
    }
    
    public HashSet<String> getWeekdays(){
        return weekdays;
    }
}
