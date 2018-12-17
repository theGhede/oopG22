package aufgabe9;

import java.util.Arrays;

public class Organization {

	public Organization(Population people) {
		this.people = people;
		this.strategy = true;
		this.focused = false;
		// organization wants to sell up to 7 products each
		this.wishes = new int[(int) (Math.random() * 7)];
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

	public int[] getWishes() {
		return this.wishes;
	}

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
		this.people.getPeople().stream().forEach(e -> {
			for (int i = 0; i < this.wishes.length; i++) {
				int product = this.wishes[i];
				// since we're looking for the type count will be either 0 or 1
				long count = e.getWishes().getTopFive().entrySet().stream().filter(p -> p.getKey() == product).count();
				this.matches += (int) count;
			}
		});
		return (double) this.matches / Math.min(1, this.wishes.length);
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
		for (int i = 0; i < wishes.length; i++) {
			key[0] = i;
			this.people.getWishList().getWishList().stream().forEach(wishMap -> {
				if (wishMap.getWishes().get(key) != null && wishMap.getWishes().get(key[0]) != 0) {
					int n = (int) (Math.random() * 2 * wishMap.getWishes().get(key[0])
							* wishMap.getOwner().getSusceptibility());
					if (this.strategy && wishMap.getOwner().getSusceptibility() < 0.75) {
						wishMap.getWishes().put(key[0], n);
					}
					if (!this.strategy) {
						wishMap.getWishes().put(key[0], n);
					}
					if (!this.focused) {
						int m = (int) (Math.random() * 2 * wishMap.getOwner().getSusceptibility());
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
		for (int i = 0; i < this.wishes.length; i++) {
			int product = this.wishes[i];
			this.people.getWishList().getWishList().stream().forEach(wishMap -> {
				long count = wishMap.getTopFive().entrySet().stream().filter(wish -> wish.getKey().equals(product))
						.count();
				wishMap.getWishes().entrySet().stream().forEach(wish -> {
					if (wish.getKey().equals(product))
						this.desire += wish.getValue();
				});
				this.matches += (int) count;
			});
			// desire for this organizations products is above average
			if (this.desire >= this.people.getWishList().totalAvgValue())
				this.strongDesire = true;
		}

		if (this.strongDesire) {
			this.focused = true;
		} else
			this.focused = false;
		if (this.matches > (int) (this.wishes.length * 0.6 * this.people.getPeople().size() * 0.6)) {
			this.strategy = true;
		} else
			this.strategy = false;
	}
}
