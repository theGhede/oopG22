package aufgabe9;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Aufgabe9.MadeBy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import static java.util.stream.Collectors.*;

@MadeBy(lastModification = "19.12.2018")
public class WishList {

	private Population people;
	private List<WishMap> wishList;

	@MadeBy
	public WishList(Population people) {
		this.people = people;
		this.wishList = new ArrayList<>();
	}

	public List<WishMap> getWishList() {
		return this.wishList;
	}

	public void setWishList() {
		this.people.getPeople().stream().forEach(person -> this.wishList.add(person.getWishes()));
	}

	@MadeBy
	private void topFive() {
		this.people.getPeople().stream().forEach(person -> {
			Map<Integer, Integer> sorted = person.getWishes().getWishes().entrySet().stream()
					.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					.collect(toMap(Entry::getKey, Entry::getValue, (elem1, elem2) -> elem2, LinkedHashMap::new));
			person.getWishes().getTopFive().clear();
			/*
			 * Note:
			 * 
			 * Had some really interesting experience with LinkedHashMap toArray() here:
			 * Firstly the entry, keys and values are forced to be Object[] and a cast to
			 * get them to what they actually are resolves in an error. Then checking
			 * getClass() of the array entries reveals that the class of these Objects is in
			 * fact Entry and Integer respectively, as one would expect. Lastly casting the
			 * Object (which really is an Integer) right before it's used seems perfectly
			 * fine and safe in contrast to the issues that came with the attempt to
			 * generate arrays of the proper type in the first place. What seemed to have
			 * happened was java was unsure about sorted.keySet()/.values() types before
			 * toArray() and told me that I can't be sure either but afterwards says
			 * "yes, of course these are Integers".
			 */

			sorted.entrySet().stream().limit(5).forEach(entry -> {
				person.getWishes().getTopFive().put(entry.getKey(), entry.getValue());
			});
		});
	}

	// Note: this is different from the other methods producing statistical data as
	// it is required by Organization.analyzing()
	@MadeBy
	public double avgValue() {
		double[] t = { 0 };
		this.wishList.stream().forEach(wishMap -> t[0] += wishMap.getWishes().entrySet().stream()
				.collect(Collectors.averagingInt(Entry::getValue)));
		t[0] = t[0] / this.wishList.size();
		return t[0];
	}

	@MadeBy
	public void yearEnd() {
		/*
		 * Note:
		 * 
		 * Using aspectJ to manage these calls would require the methods to be static,
		 * this is not going to happen despite us hoping to use it for cases such as
		 * this and not just some console output
		 */
		this.setWishList();
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
