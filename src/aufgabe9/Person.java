package aufgabe9;

public class Person {

	private WishMap wishes;
	// susceptibility is a value between 0.5 and 1 with 1 being easiest to influence
	private double susceptibility;

	public Person() {
		this.wishes = new WishMap(this);
		this.susceptibility = Math.min(1, Math.random() + 0.5);
	}

	public WishMap getWishes() {
		return this.wishes;
	}

	public double getSusceptibility() {
		return this.susceptibility;
	}
	
	public boolean mortality() {
		return Math.random() <= 0.2;
	}
	
	public Person yearEnd() {
		this.wishes.yearEnd();
		return this;
	}
}
