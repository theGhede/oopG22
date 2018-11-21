package aufgabe5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class SocialGroup<T extends FitAnimal> implements Iterable<T> {

	private Node<T> head;
	private Node<T> tail;

	public Node<T> getHead() {
		return this.head;
	}

	/*
	 * Note: If the parameter type is Node<T> like we wanted, this would ask to be
	 * cast from Node<T> to Node<T> for some reason @line 209 but still would work
	 * fine with head = null; further more casting the argument where the method is
	 * called to Node<T> (as was the other suggestion of the IDE) does nothing at
	 * all and still is considered an error.
	 * 
	 * But while the IDE asks for a suppressed warning, through assertions this is
	 * not a problem - since we have no Nodes of any type but <T> & the method is
	 * never called with any type that cannot be resolved.
	 */
	@SuppressWarnings("unchecked")
	public void setHead(Node<?> head) {
		this.head = (Node<T>) head;
	}

	public Node<T> getTail() {
		return this.tail;
	}

	@SuppressWarnings("unchecked")
	public void setTail(Node<?> tail) {
		this.tail = (Node<T>) tail;
	}

	@Override
	public Iterator<T> iterator() {
		return new SocialGroupIterator<>(this);
	}

	public void add(T a) {
		if (this.head == null) {
			this.addTail(a);
		} else if (a.getFitness() <= this.tail.getCurrent().getFitness()) {
			addTail(a);
		} else if (a.getFitness() >= this.head.getCurrent().getFitness()) {
			addHead(a);
		} else {
			insert(a, head);
		}
	}

	public void insert(T a, Node<T> current) {
		Node<T> node = new Node<>(a, null);
		if (a.getFitness() < current.getCurrent().getFitness() && a.getFitness() <=  current.getNext().getCurrent().getFitness()) {
			insert(a, current.getNext());
		}
		if (a.getFitness() >= current.getNext().getCurrent().getFitness()) {
			node.setNext(current.getNext());
			current.setNext(node);
			node.setPrevious(current);
			current.getNext().setPrevious(node);
		}

	}

	public void addHead(T a) {
		// new head node [null -> a -> oldHead]
		Node<T> node = new Node<>(a, head);
		node.setPrevious(null);
		// adjust previous of the old head to new head
		this.head.setPrevious(node);
		// change head
		this.head = node;
	}

	public void addTail(T a) {
		Node<T> node = new Node<>(a, null);
		if (head == null) {
			this.head = this.tail = node;
			this.head.setPrevious(null);
		} else {
			this.tail.setPrevious(this.tail);
			this.tail.setNext(node);
			this.tail = node;
		}
	}

	public void compareAll() {
		SocialGroup<T> save = new SocialGroup<>();
		int change;
		for (T i : this) {
			for (T j : this) {
				if (i.fitter(j) == 0) {
					double winner = Math.random();
					if (winner >= 0.5 ) {
						change = (int) (j.getFitness() * 0.1);
						i.changeFintess(i.getFitness() + change);
						j.changeFintess(j.getFitness() - change);
						
					} else if (winner < 0.5) {
						change = (int) (j.getFitness() * 0.1);
						i.changeFintess(i.getFitness() - change);
						j.changeFintess(j.getFitness() + change);
					}
				}
			}
		}
		Iterator<T> itr = new SocialGroupIterator<>(this);
		while (itr.hasNext()) {
			save.add(itr.next());
			itr.remove();
		}
		for (T t : save) {
			this.add(t);
		}
	}

	public boolean hierarchical() {
		for (T current : this) {
			if (!current.hierarchical())
				return false;
		}
		return true;
	}

	// options for printing:
	// - one animal in each line
	// - all animals in one String
	// Note: Generics are not accessible at run time without trickery so the println
	// will only know it's a SocialGroup, but has already forgotten which kind
	public void print() {
		System.out.println("Animals within this " + this.getClass().getSimpleName() + " :");
		for (T t : this) {
			System.out.println(t.toString());
		}
		System.out.println("\n");
	}

	public void printString() {
		System.out.println("Animals within this " + this.getClass().getSimpleName() + " :");
		String s = "";
		for (T t : this) {
			s += t.toString() + "  ";
		}
		System.out.println(s + "\n");
	}

	public SocialGroup<T> alpha() {
		SocialGroup<T> potentialAlphas = new SocialGroup<>();
		if (hierarchical()) {
			for (T current : this) {
				if (current.mayBeAlpha()) {
					potentialAlphas.add(current);
				}
			}
			return potentialAlphas;
		}
		return null;
	}

	/*
	 * Note: While the description found in Context.SocialGroup.move of which
	 * animals should be moved was very unclear we reduced the method to a point
	 * where it only moves animals as described by "Welche Aufgabe zu lösen ist 2."
	 */
	// TODO: FIX: this moves animals between two groups of any type, as long as
	// source and this have the same type
	public void move(SocialGroup<T> source, Predicate<FitAnimal> predicate) {
		Iterator<T> itr = new SocialGroupIterator<>(source);
		while (itr.hasNext()) {
			T animal = itr.next();
			if (predicate.test(animal)) {
				this.add(animal);
				itr.remove();
			}
		}
	}

	// cast is checked by assertions - Zebra is in fact valid for T
	@SuppressWarnings("unchecked")
	public void moveZebras(SocialGroup<Zebra> source, Predicate<FitAnimal> predicate) {
		Iterator<Zebra> itr = new SocialGroupIterator<>(source);
		while (itr.hasNext()) {
			Zebra animal = itr.next();
			if (predicate.test(animal)) {
				this.add((T) animal);
				itr.remove();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void moveOstriches(SocialGroup<Ostrich> source, Predicate<FitAnimal> predicate) {
		Iterator<Ostrich> itr = new SocialGroupIterator<>(source);
		while (itr.hasNext()) {
			Ostrich animal = itr.next();
			if (predicate.test(animal)) {
				this.add((T) animal);
				itr.remove();
			}
		}
	}

	public static class TypePredicates {
		/*
		 * NOTE: SonarLint linter recommends turning this into a lambda function & after
		 * doing so it recommends using methods so it is hard to be certain which is the
		 * preferred option, but since test() was explicitly mentioned this seemed like
		 * it
		 * 
		 * Predicate<T> is not possible - must be static and <T> requires non-static
		 * method
		 */
		public static final Predicate<FitAnimal> typePredicate = new Predicate<FitAnimal>() {
			public boolean test(FitAnimal t) {
				if (Zebra.class.isInstance(t)) {
					// above average when it comes to stripes (assuming a normal distribution)
					return ((Zebra) t).getStriped() > 0.5;
				}
				if (Ostrich.class.isInstance(t)) {
					// roughly the 75th percentile in terms of power
					return ((Ostrich) t).power() > 1000;
				}
				return false;
			}
		};
	}

	@SuppressWarnings("hiding")
	public class SocialGroupIterator<T extends FitAnimal> implements Iterator<T> {

		private Node<T> current;

		public Node<T> getCurrent() {
			return this.current;
		}

		public SocialGroupIterator(SocialGroup<T> group) {
			this.current = group.getHead();
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			T animal = current.getCurrent();
			this.current = this.current.getNext();
			return animal;
		}

		@Override
		public void remove() {
			if (this.hasNext()) {
				if (this.current.getPrevious() == null && this.current.getNext() != null) { // head
					this.current.getNext().setPrevious(null);
					this.current.setNext(null);
					setHead(this.current.getNext());
				} else if (this.current.getNext() == null && this.current.getPrevious() != null) { // tail
					this.current.getPrevious().setNext(null);
					setTail(this.current.getPrevious());
				} else if (this.current.getNext() == null && this.current.getPrevious() == null) { // only element
					setHead(null);
					setTail(null);
				} else { // else
					Node<T> prevHelp = this.current.getPrevious();
					this.current.getNext().setPrevious(this.current.getPrevious());
					prevHelp.setNext(this.current.getNext());
				}
			}
		}
	}
}
