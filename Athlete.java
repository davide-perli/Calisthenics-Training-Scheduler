public class Athlete {
    private String name;
    private int static_level;
    private int dynamic_level;

    public void setName(String newName){
        this.name = newName;
    }

    public void setStatic_level(int newLevel){
        this.static_level = newLevel;
    }

    public void setDynamic_level(int newLevel){
        this.dynamic_level = newLevel;
    }
    
    public String getName(){
        return name;
    }

    public int getStatic_level(){
        return static_level;
    }

    public int getDynamic_level(){
        return dynamic_level;
    }
}
