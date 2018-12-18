package aufgabe9;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Organization {

	public Organization(Population people) {
		this.people = people;
		this.strategy = true;
		this.focused = false;
		// organization wants to sell up to 7 products each
		this.wishes = new int[(int) (Math.random() * 4 + 3)];
		for (int i = 0; i < this.wishes.length; i++) {
			this.wishes[i] = -1;
		}
		this.portfolio();
		Arrays.sort(wishes);
	}

	// organizations want to sell all and any product; these are all positive and
	// there is no priority
	private int[] wishes;
	// needs a reference to the Population to gather data
	private Population people;
	// advertising strategies targeting different types of people depending on their
	// susceptibility - false means ignoring highly resistant individuals
	private boolean strategy;
	// focusing on only increasing desire, not creating new wishes
	private boolean focused;

	private void portfolio() {
		for (int i = 0; i < this.wishes.length; i++) {
			int wish = (int) (Math.random() * 20);
			for (int j = 0; j < this.wishes.length; j++) {
				if (this.wishes[j] == wish) {
					wish = -1;
				}
			}
			if (wish != -1)
				this.wishes[i] = wish;
		}
		// Note: repeat helps limiting the recursion to once per check instead of once
		// per wish that is still -1
		boolean repeat = false;
		for (int i = 0; i < this.wishes.length; i++) {
			if (wishes[i] == -1)
				repeat = true;
		}
		if (repeat)
			this.portfolio();
	}

	// how many percent of the companies wishes are represented in the topWishes of
	// all people
	public double representedWishes() {
		this.matches = 0;
		this.people.getPeople().stream().forEach(person -> {
			for (int i = 0; i < this.wishes.length; i++) {
				int product = this.wishes[i];
				// since we're looking for the type count will be either 0 or 1
				long count = person.getWishes().getTopFive().entrySet().stream().filter(p -> p.getKey() == product)
						.count();
				this.matches += (int) count;
			}
		});
		return (double) Math.round((double) this.matches / Test.population.census() * 10000) / 10000;
	}

	private double targetSusceptability() {
		return this.people.getPeople().stream().collect(Collectors.averagingDouble(Person::getSusceptibility));
	}

	public void influencing() {
		this.analyzing();
		/*
		 * Note: Another workaround for
		 * "Local variable i defined in an enclosing scope must be final or effectively final"
		 * is using an array (since the value inside the array changes, not the array
		 * itself)
		 */
		int[] key = { 0 };
		double t = this.targetSusceptability();
		for (int i = 0; i < wishes.length; i++) {
			key[0] = i;
			this.people.getWishList().getWishList().stream().forEach(wishMap -> {
				if (wishMap.getWishes().get(key[0]) != null && wishMap.getWishes().get(key[0]) != 0) {
					int n = (int) (Math.random() * 2 * wishMap.getWishes().get(key[0])
							* wishMap.getOwner().getSusceptibility());
					if (this.strategy && wishMap.getOwner().getSusceptibility() < t) {
						wishMap.getWishes().put(key[0], n);
					}
					if (!this.strategy) {
						wishMap.getWishes().put(key[0], n);
					}
					// not focused on only influencing current wishes but trying to create new ones
					// has a random chance to fail (if desire() returns 0)
					if (!this.focused
							&& (wishMap.getWishes().get(key[0]) == null || wishMap.getWishes().get(key[0]) == 0)) {
						int m = (int) (WishMap.desire() * wishMap.getOwner().getSusceptibility());
						wishMap.getWishes().put(key[0], m);
					}
				}
			});
		}
	}

	private int matches;
	private double desire;
	private boolean strongDesire;

	/*
	 * checking for total overlap between own products and all topFive lists and
	 * looking for strongly desired wishes
	 */
	private void analyzing() {
		// compare this.wishes to WishList after Christmas and make up rules for future
		// strategy
		this.matches = 0;
		this.desire = 0;
		this.strongDesire = false;
		double[] tmp1 = { 0 };
		double[] tmp2 = { 0 };
		for (int i = 0; i < this.wishes.length; i++) {
			int product = this.wishes[i];
			this.people.getWishList().getWishList().stream().forEach(wishMap -> {
				long count = wishMap.getTopFive().entrySet().stream()
						.filter(wish -> wish.getKey().equals(product) && wish.getValue() > 0).count();
				wishMap.getWishes().entrySet().stream().forEach(wish -> {
					if (wish.getKey().equals(product))
						tmp1[0] += wish.getValue();
				});
				tmp1[0] = tmp1[0] / wishMap.getWishes().size();
				tmp2[0] += tmp1[0];
				this.matches += (int) count;
			});
			this.desire += tmp2[0];
		}
		// desire for this organizations products is above average
		this.desire = this.desire / (this.people.getPeople().size() * this.wishes.length);
		if (this.desire >= this.people.getWishList().avgValue())
			this.strongDesire = true;

		if (this.strongDesire) {
			this.focused = true;
		} else
			this.focused = false;

		if (this.matches > this.wishes.length * this.people.getPeople().size()) {
			this.strategy = true;
		} else
			this.strategy = false;
	}
}
