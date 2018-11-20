package aufgabe5;

public class Starliling extends FitAnimal{

    private int endurance;
    private boolean responsivness;

    public void calculateFitness() {
        int newFitness = this.endurance;
        if (!this.responsivness) newFitness = this.endurance / 2;
        this.changeFintess(newFitness);
    }

    public Starliling(int fitness, boolean hierarchical, int endurance, boolean responsivness) {
        super(fitness, hierarchical);
        this.endurance = endurance;
        this.responsivness = responsivness;
    }
   // this.setHierarchical(false);

    @Override
    public String toString () {
        String h;
        if (this.hierarchical()) h = "is hierarchical";
        else h = "is not hierarchical";
        String r;
        if (this.responsivness) r = "Reacts quickly";
        else r = "Doesn't react quickly";

        return "<" + " Fitness is: " + this.getFitness() + " | " + r + " | Endurance is: "+ this.endurance + " | " + h + " >" ;
    }


}
