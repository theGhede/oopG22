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

	private int key;

	public void influencing() {
		this.analysing();
		this.key = 0;
		for (int i = 0; i < wishes.length; i++) {
			this.key = i;
			this.people.getWishList().getWishList().stream().map(p -> {
				if (p.getWishes().get(key) != null && p.getWishes().get(key) != 0) {
					int n = (int) (Math.random() * 2 * p.getWishes().get(key) * p.getOwner().getSusceptibility());
					if (!this.strategy) {
						if (p.getOwner().getSusceptibility() < 0.75) {
							p.getWishes().put(key, n);
						}
					} else {
						p.getWishes().put(key, n);
					}
				}
				if (!this.focused) {
					if (p.getWishes().get(key) == null && p.getWishes().get(key) == 0) {
						int m = (int) (Math.random() * 2 * p.getOwner().getSusceptibility());
						p.getWishes().put(key, m);
					}
				}
				return p;
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
	public void analysing() {
		// TODO: compare this.wishes to WishList after Christmas and make up rules for
		// future strategy
		this.matches = 0;
		this.desire = 0;
		this.strongDesire = false;
		for (int i = 0; i < this.wishes.length; i++) {
			int product = this.wishes[i];
			this.people.getWishList().getWishList().stream().map(p -> {
				long count = p.getTopFive().entrySet().stream().filter(wish -> wish.getKey().equals(product)).count();
				p.getWishes().entrySet().stream().map(wish -> {
					if (wish.getKey().equals(product))
						this.desire += wish.getValue();
					return wish;
				});
				this.matches += (int) count;
				return p;
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
