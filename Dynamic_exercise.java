public class Dynamic_exercise extends Exercise{
    public Dynamic_exercise(){
        super();
        exercises.add("180");
        exercises.add("Front Roll");
        exercises.add("Dragon 360");
        exercises.add("360");
        exercises.add("Baby Giant");
        exercises.add("Giant");
        exercises.add("Geinger");
        exercises.add("540");
        exercises.add("Front Flip Regrab");
        exercises.add("720");
        exercises.add("Giant 360");

        for(int i = 0; i < 11; i++){
            if(i <= 1){exercise_ranked.put(exercises.get(i), "beginner");}
            else if(i <= 4){exercise_ranked.put(exercises.get(i), "intermidiate");}
            else if(i <= 7){exercise_ranked.put(exercises.get(i), "advanced");}
            else{exercise_ranked.put(exercises.get(i), "expert");}
        }
    }
}



