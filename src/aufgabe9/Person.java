package aufgabe9;

@MadeBy(lastModification = "19.12.2018")
public class Person {

	private WishMap wishes;
	// susceptibility is a value between 0.5 and 1 with 1 being easiest to influence
	private double susceptibility;

	public Person() {
		this.wishes = new WishMap(this);
		this.susceptibility = Math.min(1, Math.random() * 0.5 + 0.5);
	}

	public WishMap getWishes() {
		return this.wishes;
	}

	public double getSusceptibility() {
		return this.susceptibility;
	}

	public boolean mortality() {
		return Math.random() <= 0.15;
	}

	public void yearEnd() {
		this.wishes.yearEnd();

	}
}
