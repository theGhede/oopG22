package aufgabe6;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class Pack implements Iterable {

	private Node head;
	private Node tail;
	private Node beforeTail;
	// while not final this cannot be changed from outside this class without a
	// setter
	private String name;

	public String getName() {
		return this.name;
	}

	public Node getHead() {
		return this.head;
	}

	public Node getTail() {
		return this.tail;
	}

	public Pack(String name) {
		this.name = name;
	}

	@Override
	public Iterator iterator() {
		return new PackIterator(this);
	}

	public void add(Object o) {
		if (Animal.class.isInstance(o)) {
			Node node = new Node(o, null);
			if (head == null) {
				this.head = this.tail = node;
			} else {
				this.beforeTail = this.tail;
				this.tail.setNext(node);
				this.tail = node;
			}
		}
	}

	public void print() {
		if (this.head != null)
			System.out.println("Animals in this Pack:");
		if (this.head == null)
			System.out.println("This pack is empty");
		for (Object o : this) {
			System.out.println(o.toString());
		}
		System.out.println("\n");
	}

	public int size() {
		int size = 0;
		for (@SuppressWarnings("unused")
		Object o : this) {
			size++;
		}
		return size;
	}

	public void avgAge() {
		double age = 0;
		for (Object o : this) {
			// age = today - time of birth relative to 1.1.2000
			age += this.getTime() - ((Animal) o).getBirthday();
		}
		if (this.size() != 0)
			System.out.println(
					"Average age of animals (in days) in this Pack is: " + (int) (age / (this.size() * 60 * 24)));
		age = 0;
		int i = 0;
		for (Object o : this) {
			if (!((Animal) o).isFemale()) {
				age += this.getTime() - ((Animal) o).getBirthday();
				i++;
			}
		}
		if (i != 0)
			System.out.println("Average age of males (in days) in this Pack is: " + (int) (age / (i * 60 * 24)));
		age = 0;
		i = 0;
		for (Object o : this) {
			i++;
			if (((Animal) o).isFemale()) {
				age += this.getTime() - ((Animal) o).getBirthday();
				i++;
			}
		}
		// if there are no females present age = 0 and i = 0 and 0/0 is 0 in java with
		// no exception or safety required
		if (i != 0)
			System.out
					.println("Average age of females (in days) in this Pack is: " + (int) (age / (i * 60 * 24)) + "\n");
	}

	public void avgChildren() {
		double children = 0;
		int i = 0;
		for (Object o : this) {
			if (((Animal) o).isFemale()) {
				children += ((Animal) o).getChildren();
				i++;
			}
		}
		// if i = 0 this pack has no females
		if (i != 0)
			System.out.println("Average amount of children per female: " + (children / i) + "\n");
		else
			System.out.println("Pack has no females \n");
	}

	public void avgAdrenaline() {
		double adrenaline = 0;
		int i = 0;
		for (Object o : this) {
			i++;
			if (!((Animal) o).getRank())
				adrenaline += ((Animal) o).getAdrenaline();
		}
		if (i != 0)
			System.out.println(
					"Average adrenaline per beta: " + (double) (Math.round(adrenaline * 10000 / i)) / 10000 + "\n");
	}

	public void avgCortisol() {
		double cortisol = 0;
		for (Object o : this) {
			cortisol += ((Animal) o).getCortisol();
		}
		if (this.size() != 0)
			System.out.println(
					"Average cortisol per animal: " + (double) (Math.round(cortisol * 10000 / this.size())) / 10000);
		cortisol = 0;
		int i = 0;
		for (Object o : this) {
			if (((Animal) o).getRank()) {
				cortisol += ((Animal) o).getCortisol();
				i++;
			}
		}
		if (i != 0)
			System.out.println("Average cortisol per alpha: " + (double) (Math.round(cortisol * 10000 / i)) / 10000);
		cortisol = 0;
		i = 0;
		for (Object o : this) {
			if (!((Animal) o).getRank()) {
				cortisol += ((Animal) o).getCortisol();
				i++;
			}
		}
		if (i != 0)
			System.out.println(
					"Average cortisol per beta: " + (double) (Math.round(cortisol * 10000 / i)) / 10000 + "\n");
	}

	public void avgTimeAsAlpha() {
		double time = 0;
		int i = 0;
		for (Object o : this) {
			if (((Animal) o).getRank()) {
				time += ((Animal) o).getTimeAsAlpha();
				i++;
			}
		}
		if (i != 0)
			System.out.println("Average time as alpha: " + (time / i + "\n"));
		else
			System.out.println("Pack has no alpha \n");
	}

	public void allStats() {
		this.avgAge();
		this.avgChildren();
		this.avgCortisol();
		this.avgAdrenaline();
		this.avgTimeAsAlpha();
	}

	// calculates time lapsed since 1.1.2000
	public int getTime() {
		Date today = new Date();
		// 1.1.1970 is javas baseline and we need to adjust this to 1.1.2000 and convert
		// milliseconds to minutes
		// after the calculation we no longer need to use long
		return (int) (today.getTime() / 60000 - 30 * 525600);
	}

	// 3 cases: remove head, remove tail, remove other than head or tail
	public void removeByID(int id) {
		boolean hasID = false;
		Iterator itr = new PackIterator(this);
		// index is the index of element to be removed, counting from 0
		int index = 0;
		Object needsNewTail = null;
		for (Object a : this) {
			if (((Animal) a).getID() == id)
				hasID = true;
			needsNewTail = a;
		}
		if (this.head.getCurrent().equals(this.tail.getCurrent())) {
			this.head = this.tail = null;
		}
		// removing head
		else if (((Animal) this.head.getCurrent()).getID() == id && this.head.getNext() != null) {
			this.head = this.head.getNext();
		} else if (hasID) {
			for (Object o : this) {
				if (((Animal) o).getID() == id)
					break;
				index++;
			}
			while (itr.hasNext()) {
				itr.next();
				index--;
				if (index == 0) {
					itr.remove();
				}
			}
		}
		if (needsNewTail != null && ((Animal) needsNewTail).getID() == id && this.head != null && this.tail != null
				&& !this.head.getCurrent().equals(this.tail.getCurrent())) {
			this.tail = this.beforeTail;
			index = 0;
			for (@SuppressWarnings("unused")
			Object t : this) {
				index++;
			}
			// set new second to last element in case tail will be removed more than once
			for (Object s : this) {
				index--;
				if (index == 1) {
					Node newBeforeTail = new Node(s, this.tail);
					this.beforeTail = newBeforeTail;
				}
			}
		}
	}

	public class PackIterator implements Iterator {

		private Node current;
		private Node previous;

		public Node getCurrent() {
			return this.current;
		}

		public PackIterator(Pack pack) {
			this.current = pack.getHead();
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public Object next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			this.previous = this.current;
			Object ret = this.current.getCurrent();
			this.current = this.current.getNext();
			return ret;
		}

		@Override
		public void remove() {
			if (!this.hasNext()) {
				this.current = this.previous;
				this.previous.setNext(null);
			}
			if (this.hasNext()) {
				this.previous.setNext(this.current.getNext());
				this.current.setNext(null);
			}
		}
	}
}
