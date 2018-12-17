package aufgabe9;

import java.util.HashMap;
import java.util.Map;

public class WishMap {

	private Map<Integer, Integer> wishes;
	private Map<Integer, Integer> topFive;
	private Person owner;

	public WishMap(Person owner) {
		this.wishes = new HashMap<>();
		// random amount of random wishes in map
		int desire;
		for (int type = 0; type < 20; type++) {
			desire = (int) (Math.random() * 4);
			if (Math.random() <= 0.15)
				desire = desire * -1;
			this.wishes.put(type, desire);
		}
		this.owner = owner;
	}

	public Map<Integer, Integer> getWishes() {
		return this.wishes;
	}

	public void setWishes(Map<Integer, Integer> wishes) {
		this.wishes = wishes;
	}

	public Map<Integer, Integer> getTopFive() {
		return this.topFive;
	}

	public Person getOwner() {
		return this.owner;
	}

	private double avg;
	private double k;

	public double avgValue(int key) {
		this.avg = 0;
		this.k = 0;
		this.wishes.entrySet().stream().map(e -> {
			if (e.getKey() == key) {
				this.k++;
				this.avg += e.getValue();
			}
			return e;
		});
		return this.avg / this.k;
	}

	public void yearEnd() {
		// "adds" new wishes if low on wishes
		long count = this.wishes.entrySet().stream().filter(e -> (e.getValue() == null || e.getValue() == 0)).count();
		if (count > this.wishes.size()) {
			this.wishes.entrySet().stream().map(e -> {
				if (e.getValue() == null || e.getValue() == 0) {
					e.setValue((int) (Math.random() * 2 + 1));
				}
				return e;
			});
		}
		// TODO: manipulate wishes randomly in map

	}
}
