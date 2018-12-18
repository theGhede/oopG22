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

	public int census() {
		return this.people.size();
	}

	// average amount of wishes per person
	public double avgWishes() {
		int[] avg = { 0 };
		this.people.stream().forEach(person -> avg[0] += person.getWishes().countWishes());
		return (double) Math.round((double) avg[0] / this.people.size() * 100) / 100;
	}

	// average desire of wishes of all people
	// including negative values, which are easily handled by averages
	public double avgDesires() {
		List<Double> avgList = new ArrayList<>();
		this.people.stream().forEach(person -> avgList.add(person.getWishes().avgDesire()));
		return (double) Math.round(avgList.stream().collect(Collectors.averagingDouble(d -> d)) * 100) / 100;
	}

	public double avgNegatives() {
		double[] c = { 0 };
		this.people.stream().forEach(person -> c[0] += person.getWishes().getWishes().entrySet().stream()
				.filter(entry -> entry.getValue() < 0).count());
		return (double) Math.round(c[0] / this.people.size() * 100) / 100;
	}

	// average desires of positive wishes of all people
	public double avgPositives() {
		List<Integer> avgList = new ArrayList<>();
		this.people.stream().forEach(person -> person.getWishes().getWishes().entrySet().stream()
				.filter(entry -> entry.getValue() > 0).forEach(entry -> avgList.add(entry.getValue())));
		return (double) Math.round(avgList.stream().collect(Collectors.averagingInt(i -> i)) * 100) / 100;
	}

	public void yearEnd() {
		wishList.yearEnd();
		this.populationChanges();
	}

	private void populationChanges() {
		int yearEndCount = this.people.size();
		// population changes lead to a statistically growing population
		this.people.removeIf(Person::mortality);
		for (int i = 0; i < (int) (yearEndCount * Math.max(0.17, Math.random())); i++) {
			this.people.add(new Person());
		}
		this.people.stream().forEach(Person::yearEnd);
	}
}
