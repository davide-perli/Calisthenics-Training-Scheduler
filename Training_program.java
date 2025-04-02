import java.util.HashSet;

public class Training_program {
    protected HashSet<String> weekdays;

    protected Training_program(){
        weekdays = new HashSet<>();
        weekdays.add("MON");
        weekdays.add("TUE");
        weekdays.add("WEN");
        weekdays.add("THU");
        weekdays.add("FRY");
        weekdays.add("SAT");
        weekdays.add("SUN");
    }
    
    public HashSet<String> getWeekdays(){
        return weekdays;
    }
}
