package aufgabe5;
 /*
* TODO
*     - Konstruktor
*
 */

public class FitAnimal {

    private int fitness;
    private boolean hierarchical;


 public int getFitness() {return this.fitness;}

 public void setHierarchical(boolean h){
     this.hierarchical = h;
 }

    public FitAnimal(int fitness, boolean hierarchical) {
        this.fitness = fitness;
        this.hierarchical = hierarchical;
    }

    public int fitter (FitAnimal compared) {
     double margin = 0.2;
     if (this.fitness >= (1 + margin) * compared.fitness) return 1;
     if (this.fitness <= (1 + margin) * compared.fitness && this.fitness >= (1 - margin) * compared.fitness) return 0;

     return -1;
 }

 public int changeFintess(int newFitness) {
     return this.fitness = Math.abs(newFitness);
 }

 public boolean hierarchical () {
     if (this.hierarchical) return true;
     return false;
 }

 public boolean mayBeAlpha () {
     if (this.hierarchical()) return true;
     return false;
 }

 @Override
 public String toString () {
     String h;
     if (this.hierarchical()) h = "is hierarchical";
        else h = "is not hierarchical";

     return "<" + " Fitness is: " + this.fitness + " | " + h + " >" ;
 }

}
