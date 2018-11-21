package aufgabe5;

public class Ostrich extends SteppeHerdAnimal {

	/*
	 * Note: Since Ostriches are known for kicking, not grabbing we assume power is
	 * a representation of an ostriches potent kick which is measured in N/cm^2 not
	 * Newton. Kicks can measure up to 138.3 bar or 1383 N/cm^2
	 */
	private int power;

	public int power() {
		return power;
	}

	public Ostrich(int fitness, boolean hierarchical, int sprintVelocity, int power) {
		super(fitness, hierarchical, sprintVelocity);
		this.setHierarchical(false);
		this.power = power;
	}

	@Override
	public String toString() {
		String h;
		if (this.hierarchical())
			h = "is hierarchical";
		else
			h = "is not hierarchical";

		return "<" + " Fitness is: " + this.getFitness() + " | Power is: " + this.power + " | Sprint velocity is: "
				+ this.getSprintVelocity() + " | " + h + " >";
	}

}
