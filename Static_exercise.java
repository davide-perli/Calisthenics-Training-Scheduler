import java.util.ArrayList;
import java.util.HashMap;

public class Static_exercise extends Exercise {
    private Service_class x;
    public Static_exercise(){
        x = new Service_class();
        x.addInput("L-Sit");
        x.addInput("Handstand");
        x.addInput("Pull-Over");
        x.addInput("Muscle-Up");
        x.addInput("Handstand Push-Up");
        x.addInput("Back Lever");
        x.addInput("Front Lever");
        x.addInput("Dragon Press");
        x.addInput("Back Lever Pull-Up");
        x.addInput("Front Lever Pull-Up");
        x.addInput("Front Lever Press");
        x.addInput("Planche");
        x.addInput("Hefesto");
        x.addInput("Planche Push-Up");
        x.addInput("Planche Press");
        x.addInput("Front Lever Touch");
        x.addInput("Victorian");
        x.addInput("One Arm Front Lever");
        x.addInput("Back Lever Hefesto");
        x.addInput("Front SAT");
        x.addInput("Back SAT");
        x.addInput("Maltese");
        x.addInput("Maltese Press");
        x.addInput("Entrada de Angel");

        for(int i = 0; i < 24; i++){
            if(i < 4){x.putInput(x.getExercise(i), "beginner");}
            else if(i <= 7){x.putInput(x.getExercise(i), "intermidiate");}
            else if(i <= 18){x.putInput(x.getExercise(i), "advanced");}
            else{x.putInput(x.getExercise(i), "expert");}
        }

        this.exercises = new ArrayList<>(x.exercises);

    }

    @Override
    public String getExercise(int index) {
        return exercises.get(index);
    }

    @Override
    public HashMap<String, String> getExerciseRanked() {
        return x.getExerciseRanked();
    }

}
