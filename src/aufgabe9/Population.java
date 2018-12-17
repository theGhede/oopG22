package aufgabe9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Population {

	private List<Person> people;
	private WishList wishList;

	public Population() {
		this.people = new ArrayList<>();
		int census = (int) (Math.random() * 4000) + 10000;
		for (int i = 0; i < census; i++) {
			this.people.add(new Person());
		}
		this.wishList = new WishList(this);
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

	public int census() {
		return this.people.size();
	}

	// average amount of wishes per person
	public double avgWishes() {
		int[] avg = { 0 };
		this.people.stream().forEach(person -> {
			avg[0] += person.getWishes().countWishes();
		});
		return (double) avg[0] / this.people.size();
	}

	// average desire of wishes of all people
	// including negative values, which are easily handled by averages
	public double avgDesires() {
		List<Double> avgList = new ArrayList<>();
		this.people.stream().forEach(person -> avgList.add(person.getWishes().avgDesire()));
		return avgList.stream().collect(Collectors.averagingDouble(d -> d));
	}

	// average desires of positive wishes of all people
	public double avgPositives() {
		List<Integer> avgList = new ArrayList<>();
		this.people.stream().forEach(person -> person.getWishes().getWishes().entrySet().stream()
				.filter(entry -> entry.getValue() > 0).forEach(entry -> avgList.add(entry.getValue())));
		return avgList.stream().collect(Collectors.averagingInt(i -> i));
	}

	public void yearEnd() {
		this.sweetChristmas();
		this.people.removeIf(Person::mortality);
		for (int i = 0; i < (int) (this.people.size() * Math.random() * 0.2); i++) {
			this.people.add(new Person());
		}
		this.people.stream().forEach(Person::yearEnd);
	}
}
