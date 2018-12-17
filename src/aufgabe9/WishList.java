package aufgabe9;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Map.Entry.*;
import static java.util.stream.Collectors.*;

public class WishList {

	private Population people;
	private List<WishMap> wishList;

	public WishList(Population people) {
		this.people = people;
	}

	public List<WishMap> getWishList() {
		return this.wishList;
	}

	public void setWishList() {
		this.people.getPeople().stream().forEach(e -> this.wishList.add(e.getWishes()));
	}

	private Stream<Person> topFive() {
		return this.people.getPeople().stream().map(person -> {
			Map<Integer, Integer> sorted = person.getWishes().getWishes().entrySet().stream().sorted(comparingByValue())
					.collect(toMap(Entry::getKey, Entry::getValue, (elem1, elem2) -> elem2, LinkedHashMap::new));
			person.getWishes().getTopFive().clear();
			@SuppressWarnings("unchecked")
			Entry<Integer, Integer>[] entry = (Entry<Integer, Integer>[]) sorted.entrySet().toArray()[sorted.size()
					- 1];
			int c = Math.max(4, entry.length - 1);
			while (c <= 0) {
				person.getWishes().getTopFive().put(entry[c].getKey(), entry[c].getValue());
				c--;
			}
			return person;
		});
	}

	private double avgValue(int key) {
		double avg = 0;
		avg = this.wishList.stream().collect(Collectors.summingDouble(wishMap -> wishMap.avgValue(key)));
		return avg / this.wishList.size();
	}

	public double totalAvgValue() {
		double total = 0;
		for (int i = 0; i < 20; i++) {
			total += this.avgValue(i);
		}
		// TODO: alternative
		this.wishList.stream().forEach(wishMap -> {
			double t = wishMap.getWishes().entrySet().stream().collect(Collectors.averagingInt(Entry::getValue));
		});
		return total;
	}

	public void yearEnd() {
		this.topFive();
		// topFive wishes per person are being fulfilled
		this.wishList.stream().forEach(wishMap -> {
			for (int i = 0; i < 20; i++) {
				wishMap.getTopFive().get(i);
				if (wishMap.getTopFive().get(i) != null) {
					wishMap.getWishes().put(i, 0);
				}
			}
		});
	}
}
