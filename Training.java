import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Training {
    Static_exercise static_exercise = new Static_exercise();
    Dynamic_exercise dynamic_exercise = new Dynamic_exercise();
    Sets_and_reps sets_and_reps = new Sets_and_reps();
    Training_program training_program = new Training_program();
    Athlete athlete;

    Scanner myObj = new Scanner(System.in);

    private String[] days = {"MON", "TUE", "WEN", "TUE", "FRY", "SAT", "SUN"};
    private String[] level = {"beginner", "intermidiate", "advanced", "expert"};
    protected int option;
    protected int contor;
    protected String static_option;

    public Training(Athlete athlete) {
        this.athlete = athlete;
        String lev_val = level[athlete.getLevel() - 1];
        System.out.println("For each day of the week you'll choose exercises for " + lev_val + " level");
        for (String i : days) {
            System.out.println("For " + i + " choose your exercises\nChoose a static exercise: ");

            training_program.addDay(i);
            
            contor = 0;
            List<String> filteredExercises = new ArrayList<>();
            for (Map.Entry<String, String> entry : static_exercise.getExerciseRanked().entrySet()) {       
                if (entry.getValue().equals(lev_val)) {
                    contor ++;
                    filteredExercises.add(entry.getKey());
                    System.out.println(contor + ". " + entry.getKey());
                }
            }

            while (true) {
                System.out.print("\nOption: ");
                option = myObj.nextInt();
                
                if (option>= 1 && option <= contor) {
                    break;
                } else {
                    System.out.println("Invalid option! Please enter a valid option.");
                }
            }
            static_option = filteredExercises.get(option - 1);
            System.out.println("You selected: " + static_option + "\n\nChoose a dynamic exercise: ");

            filteredExercises.clear();
            contor = 0;
            for (Map.Entry<String, String> entry : dynamic_exercise.getExerciseRanked().entrySet()) {       
                if (entry.getValue().equals(lev_val)) {
                    contor ++;
                    filteredExercises.add(entry.getKey());
                    System.out.println(contor + ". " + entry.getKey());
                }
            }

            while (true) {
                System.out.print("\nOption: ");
                option = myObj.nextInt();
                
                if (option>= 1 && option <= contor) {
                    break;
                } else {
                    System.out.println("Invalid option! Please enter a valid option.");
                }
            }
            static_option = filteredExercises.get(option - 1);
            System.out.println("You selected: " + static_option + "\n");

        }
    }
}
