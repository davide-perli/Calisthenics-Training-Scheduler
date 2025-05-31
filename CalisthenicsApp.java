import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class CalisthenicsApp {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String name_input;
        boolean alreadyExists = false;
        Service_class service = new Service_class();
        System.out.println("\nWelcome to my calisthenics app where you can create your personalized training program!\n");
        while (true) {
            System.out.println("Enter your name: ");
            name_input = myObj.nextLine();

            if (!name_input.matches("[a-zA-Z]+")) {
                System.out.println("Invalid name! Please enter only letters!\n");
                continue;
            }

            String safeName = name_input.toLowerCase().replaceAll("[^a-z]", "");
            String tableName = safeName + "_training_plan";

            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/calisthenics_training_program", "postgres", "dlmvm")) {
                if (service.doesTableExist(connection, tableName)) {
                    alreadyExists = true;
                    break;
                } else {
                    break; 
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        Athlete user = new Athlete();
        user.setName(name_input);

        if (alreadyExists) {
            System.out.println("\nWelcome back " + user.getName() + "!\n\nChoose what you want to do:\n1. Create a new training program\n2. View existing training program\n3. Delete training program\n");
            int option;
            while (true) {
                System.out.print("Option: ");
                option = myObj.nextInt();
                
                if (option >= 1 && option <= 3) {
                    break;
                } else {
                    System.out.println("Invalid option! Please enter a number between 1 and 3 as your option!\n");
                }
            }

            if (option == 1) {
                System.out.println("\nYou chose to create a new training program.\n");
                Training training = new Training(user);
                training.putInDatabase(training.getWeeklyPlan());
                System.out.println("Athlete " + user.getName() + " training program: ");
                training.showPlan(training.getWeeklyPlan());
            } else if (option == 2) {
                System.out.println("\nYou chose to view your existing training program.\n");
                service.showExistingPlan(user.getName());
            } else {
                System.out.println("\nAre you sure you want to delete your training program?(YES or NO)\nWARNING: This action cannot be undone!\n");
                myObj.nextLine(); // Consume the newline character
                String confirmation = myObj.nextLine().trim().toUpperCase();
                while (!confirmation.equals("YES") && !confirmation.equals("NO") && !confirmation.equals("Y") && !confirmation.equals("N")) {
                    System.out.println("Invalid input! Please type 'YES' or 'NO'.");
                    confirmation = myObj.nextLine().trim().toUpperCase();
                }
                
                if (confirmation.equals("NO") || confirmation.equals("N")) {
                    System.out.println("\nYou chose not to delete your training program.\n");
                } else if (confirmation.equals("YES") || confirmation.equals("Y")) {
                    System.out.println("\nYou chose to delete your training program.\n");
                    service.deletePlan(user.getName());
                }
            }
        } else {
        
            System.out.println("\n\nWelcome " + user.getName()+ "!\nCreate you training program for your skill level:\n");

            Training training = new Training(user);
            training.putInDatabase(training.getWeeklyPlan());
            System.out.println("Athlete " + user.getName() + " training program: ");
            training.showPlan(training.getWeeklyPlan());
        }

        myObj.close();
    }
}