package aufgabe9;

import java.util.Map;	
import java.util.Map.Entry;
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
		this.people.getPeople().stream().map(e -> this.wishList.add(e.getWishes()));
	}

	public List<WishMap> getWishList() {
		return this.wishList;
	}

	public Stream<Person> topFive() {
		return this.people.getPeople().stream().map(person -> {
			Map<Integer, Integer> sorted = person.getWishes().getWishes().entrySet().stream().sorted(comparingByValue())
					.collect(toMap(elem -> elem.getKey(), elem -> elem.getValue(), (elem1, elem2) -> elem2,
							LinkedHashMap::new));
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

	private double avg;

	public double avgValue(int key) {
		this.avg = 0;
		this.wishList.stream().map(e -> this.avg += e.avgValue(key));
		return this.avg / this.wishList.size();
	}

	public double totalAvgValue() {
		double total = 0;
		for (int i = 0; i < 20; i++) {
			total += this.avgValue(i);
		}
		return total / 20;
	}

	public void yearEnd() {
		this.topFive();
		// topFive wishes per person are being fulfilled
		this.wishList.stream().map(w -> {
			for (int i = 0; i < 20; i++) {
				w.getTopFive().get(i);
				if (w.getTopFive().get(i) != null) {
					w.getWishes().put(i, 0);
				}
			}
			return w;
		});
	}
}
