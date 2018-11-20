package aufgabe5;

/*
– hierarchical
– mayBeAlpha
- power
- toString
 */
public class Ostrich extends SteppeHerdAnimal {

    private int power;

    public int power(){ return power; }

    public Ostrich(int fitness, boolean hierarchical, int sprintVelocity, int power) {
        super(fitness, hierarchical, sprintVelocity);
        this.power = power;
    }
    //this.setHierarchical(false);

    @Override
    public String toString () {
        String h;
        if (this.hierarchical()) h = "is hierarchical";
        else h = "is not hierarchical";

        return "<" + " Fitness is: " + this.getFitness() + " | Power is: " + this.power + " | Sprint velocity is: " + this.getSprintVelocity() + " | " + h + " >" ;
    }

}
