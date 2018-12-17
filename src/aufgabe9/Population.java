package aufgabe9;

import java.util.ArrayList;	
import java.util.List;

public class Population {

	private List<Person> people;
	private WishList wishList;

	public Population() {
		this.people = new ArrayList<>();
		int census = (int) (Math.random() * 4000) + 10000;
		for (int i = 0; i < census; i++) {
			this.people.add(new Person());
		}
		wishList = new WishList(this);
	}

	public List<Person> getPeople() {
		return this.people;
	}
	
	public WishList getWishList() {
		return this.wishList;
	}

	private void sweetChristmas() {
		wishList.yearEnd();
	}

	public void yearEnd() {
		this.sweetChristmas();
		this.people.removeIf(person -> person.deathRate());
		for (int i = 0; i < (int) (this.people.size() * Math.random() * 0.2); i++) {
			this.people.add(new Person());
		}
		this.people.stream().map(e -> e.yearEnd());
	}
}
