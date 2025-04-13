import java.util.ArrayList;
import java.util.HashMap;

public class Dynamic_exercise extends Exercise{
    
    private Service_class x;

    public Dynamic_exercise(){
        x = new Service_class();
        x.addInput("180");
        x.addInput("Front Roll");
        x.addInput("Dragon 360");
        x.addInput("360");
        x.addInput("Baby Giant");
        x.addInput("Giant");
        x.addInput("Geinger");
        x.addInput("540");
        x.addInput("Front Flip Regrab");
        x.addInput("720");
        x.addInput("Giant 360");

        for(int i = 0; i < 11; i++){
            if(i <= 1){x.putInput(x.getExercise(i), "beginner");}
            else if(i <= 4){x.putInput(x.getExercise(i), "intermidiate");}
            else if(i <= 7){x.putInput(x.getExercise(i), "advanced");}
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



