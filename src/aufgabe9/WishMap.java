package aufgabe9;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class WishMap {

	private Map<Integer, Integer> wishes;
	private Map<Integer, Integer> topFive;
	private Person owner;

	public WishMap(Person owner) {
		this.wishes = new HashMap<>();
		// random amount of random wishes in map
		for (int type = 0; type < 20; type++) {
			this.wishes.put(type, this.desire());
		}
		this.owner = owner;
	}

	private int desire() {
		int desire;
		desire = (int) (Math.random() * 4);
		if (Math.random() <= 0.20)
			desire = desire * -1;
		return desire;
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

	// Note:
	// we wanted this to be a local variable of course but "Local variable avg
	// defined in an enclosing scope must be final or effectively final" needs some
	// working around
	private double avg;
	private int k;

	public double avgValue(int key) {
		this.avg = 0;
		this.k = 0;
		this.wishes.entrySet().stream().forEach(entry -> {
			if (entry.getKey() == key) {
				this.k++;
				this.avg += entry.getValue();
			}
		});
		return this.avg / Math.min(1, this.k);
	}

	public long countWishes() {
		return this.wishes.entrySet().stream().filter(e -> (e.getValue() != null && e.getValue() != 0)).count();
	}

	public double avgDesire() {
		List<Entry<Integer, Integer>> entryList = this.wishes.entrySet().stream()
				.filter(entry -> (entry.getValue() != null && entry.getValue() != 0)).collect(Collectors.toList());
		return entryList.stream().collect(Collectors.averagingInt(Entry::getValue));
	}

	// person is frustrated by not being granted ad wish they really wanted (because
	// they had 5 even more important wishes but wanted more)
	private void frustrated() {
		this.wishes.entrySet().stream().filter(entry -> entry.getValue() > 8).forEach(entry -> entry.setValue(0));
	}

	public void yearEnd() {
		this.frustrated();
		// randomly adjusts some of the wishes
		this.wishes.entrySet().stream().forEach(entry -> {
			if (entry.getValue() != 0 && entry.getValue() != null) {
				double r = Math.random();
				if (r <= 0.05) {
					// 5% chance to forget about a wish
					entry.setValue(0);
				} else if (r > 0.05 && r <= 0.2) {
					// 15% chance to do the advertisers work for them
					entry.setValue((int) (entry.getValue() * Math.random() * 2));
				} else if (r > 0.2 && r <= 0.25) {
					// 5% chance to radically change opinion about a wish and change the sign
					entry.setValue(-1 * entry.getValue());
				}
			}
		});
		// "adds" new wishes if low on wishes, desire randomly returns a positive or
		// negative value or 0 (in which case no new wish is added)
		long count = this.wishes.entrySet().stream().filter(e -> (e.getValue() == null && e.getValue() == 0)).count();
		if (count > 10) {
			this.wishes.entrySet().stream().forEach(entry -> {
				if (entry.getValue() == null && entry.getValue() == 0) {
					entry.setValue(this.desire());
				}
			});
		}
	}
}
