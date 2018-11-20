package aufgabe5;


public class SteppeHerdAnimal extends FitAnimal {

    private int sprintVelocity;

    public int getSprintVelocity(){
        return this.sprintVelocity;
    }
    public void calculateFitness() {
        this.changeFintess(this.sprintVelocity);
    }

    public SteppeHerdAnimal(int fitness, boolean hierarchical, int sprintVelocity) {
        super(fitness, hierarchical);
        this.sprintVelocity = sprintVelocity;
    }

    @Override
    public String toString () {
        String h;
        if (this.hierarchical()) h = "is hierarchical";
        else h = "is not hierarchical";

        return "<" + " Fitness is: " + this.getFitness() + " | Sprint velocity is: " + this.sprintVelocity + " | " + h + " >" ;
    }

}
