import java.util.Scanner;

public class CalisthenicsApp {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String name_input;
        System.out.println("\nWelcome to my caslisthenics app where you can create your personalized training program!\n");
        while (true) {
            System.out.println("Enter your name: ");
            name_input = myObj.nextLine();
            
            if (name_input.matches("[a-zA-Z]+")) {
                break;
            } else {
                System.out.println("Invalid name! Please enter only letters.");
            }
        }

        Athlete user = new Athlete();
        user.setName(name_input);

        System.out.println("\n\nWelcome " + user.getName()+ "!\nCreate you training program for your skill level:\n");

        Training training = new Training(user);
        System.out.println("Athlete " + user.getName() + " training program: ");
        training.showPlan(training.getWeeklyPlan());

        //staticExercises.showExercises();
        //dynamicExercises.showExercises();

        myObj.close();
    }
}