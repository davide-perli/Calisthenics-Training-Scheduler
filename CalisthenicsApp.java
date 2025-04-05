import java.util.Scanner;

public class CalisthenicsApp {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        Exercise staticExercises = new Static_exercise();
        Exercise dynamicExercises = new Dynamic_exercise();

        String name_input;
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

        System.out.println("\nChose your skill level:\n1.Beginner\n2.Intermidiate\n3.Advanced\n4.Expert\n");

        int skill_option;
        while (true) {
            skill_option = myObj.nextInt();
            
            if (skill_option >= 1 && skill_option <= 4) {
                break;
            } else {
                System.out.println("Invalid option! Please enter a number between 1 and 4.");
            }
        }

        user.setLevel(skill_option);

        System.out.println("Create you training program for your skill:\n");

        Training training = new Training(user);
        

        //staticExercises.showExercises();
        //dynamicExercises.showExercises();

        myObj.close();
    }
}
