import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            System.out.println("\nWelcome back " + user.getName() + "!\n\nChoose what you want to do:\n1. Create a new training program\n2. View existing training program\n3. Modify you training program\n4. Delete training program\n");
            int option;
            while (true) {
                System.out.print("Option: ");
                option = myObj.nextInt();
                
                if (option >= 1 && option <= 4) {
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
            } else if (option == 3) {
                System.out.println("\nYou chose to modify your training program.\n");
                service.showExistingPlan(user.getName());

                String[] days = {"MON", "TUE", "WEN", "THU", "FRY", "SAT", "SUN"};
                System.out.println("\nSelect a day to modify:");
                for(int i=0; i<days.length; i++) {
                    System.out.println((i+1) + ". " + days[i]);
                }
                
                int dayChoice;
                while(true) {
                    System.out.print("\nEnter day number (1-7): ");
                    dayChoice = myObj.nextInt();
                    if(dayChoice >=1 && dayChoice <=7) break;
                    System.out.println("Invalid choice! Please enter 1-7.");
                    myObj.nextLine();
                }
                
                String selectedDay = days[dayChoice-1];
                System.out.println("\nYou chose to modify exercises for " + selectedDay + ".\n");
                service.showExistingPlanForDay(user.getName(), selectedDay);

                String[] levels = {"beginner", "intermidiate", "advanced", "expert"};
                Static_exercise staticExerciseObj = new Static_exercise();
                Dynamic_exercise dynamicExerciseObj = new Dynamic_exercise();
                Sets_and_reps setsAndRepsObj = new Sets_and_reps();

                System.out.println("\nPick static skill level:\n1.Beginner\n2.Intermidiate\n3.Advanced\n4.Expert\n");
                int staticSkillOption;
                while (true) {
                    System.out.print("\nOption (1-4): ");
                    staticSkillOption = myObj.nextInt();
                    if (staticSkillOption >= 1 && staticSkillOption <= 4) {
                        break;
                    } else {
                        System.out.println("Invalid option! Please enter a number between 1 and 4.");
                    }
                }
                String statLevelVal = levels[staticSkillOption - 1];
                System.out.println("\nChoose static exercise:");
                int counter = 0;
                List<String> filteredExercises = new ArrayList<>();
                for (Map.Entry<String, String> entry : staticExerciseObj.getExerciseRanked().entrySet()) {
                    if (entry.getValue().equals(statLevelVal)) {
                        counter++;
                        filteredExercises.add(entry.getKey());
                        System.out.printf("%2d. %s%n", counter, entry.getKey());
                    }
                }
                int staticExerciseOption;
                String staticExercise = null;
                while (true) {
                    System.out.print("\nOption (1-" + counter + "): ");
                    staticExerciseOption = myObj.nextInt();
                    if (staticExerciseOption >= 1 && staticExerciseOption <= counter) {
                        staticExercise = filteredExercises.get(staticExerciseOption - 1);
                        System.out.println("You selected: " + staticExercise);
                        break;
                    } else {
                        System.out.println("Invalid option! Please enter a valid option.");
                    }
                }

                System.out.println("\nPick dynamic skill level:\n1.Beginner\n2.Intermidiate\n3.Advanced\n4.Expert\n");
                int dynamicSkillOption;
                while (true) {
                    System.out.print("\nOption (1-4): ");
                    dynamicSkillOption = myObj.nextInt();
                    if (dynamicSkillOption >= 1 && dynamicSkillOption <= 4) {
                        break;
                    } else {
                        System.out.println("Invalid option! Please enter a number between 1 and 4.");
                    }
                }
                String dynLevelVal = levels[dynamicSkillOption - 1];
                System.out.println("\nChoose dynamic exercise:");
                counter = 0;
                filteredExercises.clear();
                for (Map.Entry<String, String> entry : dynamicExerciseObj.getExerciseRanked().entrySet()) {
                    if (entry.getValue().equals(dynLevelVal)) {
                        counter++;
                        filteredExercises.add(entry.getKey());
                        System.out.printf("%2d. %s%n", counter, entry.getKey());
                    }
                }
                int dynamicExerciseOption;
                String dynamicExercise = null;
                while (true) {
                    System.out.print("\nOption (1-" + counter + "): ");
                    dynamicExerciseOption = myObj.nextInt();
                    if (dynamicExerciseOption >= 1 && dynamicExerciseOption <= counter) {
                        dynamicExercise = filteredExercises.get(dynamicExerciseOption - 1);
                        System.out.println("You selected: " + dynamicExercise);
                        break;
                    } else {
                        System.out.println("Invalid option! Please enter a valid option.");
                    }
                }

                System.out.println("\nChoose sets and reps exercise:");
                counter = 0;
                List<String> allExercises = new ArrayList<>();
                for (Map.Entry<String, String> entry : setsAndRepsObj.getExerciseRanked().entrySet()) {
                    counter++;
                    allExercises.add(entry.getKey());
                    System.out.printf("%2d. %s%n", counter, entry.getKey());
                }
                int setsRepsOption;
                String setsRepsExercise = null;
                while (true) {
                    System.out.print("\nOption (1-" + counter + "): ");
                    setsRepsOption = myObj.nextInt();
                    if (setsRepsOption >= 1 && setsRepsOption <= counter) {
                        setsRepsExercise = allExercises.get(setsRepsOption - 1);
                        System.out.println("You selected: " + setsRepsExercise);
                        break;
                    } else {
                        System.out.println("Invalid option! Please enter a valid option.");
                    }
                }

                boolean success = service.updateTrainingDay(
                    user.getName(),
                    selectedDay,
                    staticExercise,
                    dynamicExercise,
                    setsRepsExercise
                );

                if (success) {
                    System.out.println("\nTraining program updated successfully!");
                    System.out.println("\nUpdated plan for " + selectedDay + ":");
                    service.showExistingPlanForDay(user.getName(), selectedDay);
                } else {
                    System.out.println("\nFailed to update training program. Please try again.");
                }
            } else {
                System.out.println("\nAre you sure you want to delete your training program?(YES or NO)\nWARNING: This action cannot be undone!\n");
                myObj.nextLine();
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