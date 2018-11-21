package aufgabe5;

public class Zebra extends SteppeHerdAnimal {
	private boolean female;
	// google says actual zebras can get to top sprint speeds of 64km/h & we only
	// want the fastest of females to be our alpha zebra
	private static final int ALPHAMINVELOCITY = 60;
	private double striped;

	public double getStriped() {
		return this.striped;
	}

	public Zebra(int fitness, boolean hierarchical, int sprintVelocity, boolean female, double striped) {
		super(fitness, hierarchical, sprintVelocity);
		this.setHierarchical(true);
		this.female = female;
		if (striped >= 0 && striped <= 1) {
			this.striped = striped;
		} else if (striped < 0) {
			this.striped = 0;
		} else if (striped > 1) {
			this.striped = 1;
		}
	}

	public double protection() {
		if (this.striped < 0)
			this.striped = 0;
		if (this.striped > 1)
			this.striped = 1;
		return this.striped;
	}

	@Override
	public boolean mayBeAlpha() {
		return (this.female && this.hierarchical() && this.getSprintVelocity() > ALPHAMINVELOCITY);
	}

	@Override
	public String toString() {
		String h;
		String gender;
		if (this.hierarchical())
			h = "is hierarchical";
		else
			h = "is not hierarchical";
		if (this.female)
			gender = "female";
		else
			gender = "male";

		return "<" + " Fitness is: " + this.getFitness() + " | Sprint velocity is: " + this.getSprintVelocity()
				+ " | Degree of protection: " + this.protection() + " | Gender:" + gender + " | " + h
				+ " | May be alpha: " + this.mayBeAlpha() + " >";
	}
}
