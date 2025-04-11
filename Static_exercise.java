public class Static_exercise extends Exercise {
    public Static_exercise(){
        super();
        exercises.add("L-Sit");
        exercises.add("Handstand");
        exercises.add("Pull-Over");
        exercises.add("Muscle-Up");
        exercises.add("Handstand Push-Up");
        exercises.add("Back Lever");
        exercises.add("Front Lever");
        exercises.add("Dragon Press");
        exercises.add("Back Lever Pull-Up");
        exercises.add("Front Lever Pull-Up");
        exercises.add("Front Lever Press");
        exercises.add("Planche");
        exercises.add("Hefesto");
        exercises.add("Planche Push-Up");
        exercises.add("Planche Press");
        exercises.add("Front Lever Touch");
        exercises.add("Victorian");
        exercises.add("One Arm Front Lever");
        exercises.add("Back Lever Hefesto");
        exercises.add("Front SAT");
        exercises.add("Back SAT");
        exercises.add("Maltese");
        exercises.add("Maltese Press");
        exercises.add("Entrada de Angel");

        for(int i = 0; i < 24; i++){
            if(i < 4){exercise_ranked.put(exercises.get(i), "beginner");}
            else if(i <= 7){exercise_ranked.put(exercises.get(i), "intermidiate");}
            else if(i <= 18){exercise_ranked.put(exercises.get(i), "advanced");}
            else{exercise_ranked.put(exercises.get(i), "expert");}
        }

    }

}
