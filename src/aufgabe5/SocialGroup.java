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

	// TODO: add insert()
	public void add(T a) {
		if (this.head == null) {
			this.addTail(a);
		} else if (a.fitter(this.tail.getCurrent()) <= 0) {
			addTail(a);
		} else if (a.fitter(this.head.getCurrent()) >= 0) {
			addHead(a);
		} else {
			insert(a, head);
		}
	}

	public void insert(T a, Node<T> current) {
		Node<T> node = new Node<>(a, null);
		// checks nodes in recursion
		if (a.fitter(current.getCurrent()) == -1 && a.fitter(current.getNext().getCurrent()) == -1) {
			insert(a, current.getNext());
		}
		if (a.fitter(current.getNext().getCurrent()) >= 0) {
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

	// TODO
	public void compareAll() {

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
		for (T animal : source) {
			if (predicate.test(animal)) {
				this.add(animal);
				// TODO: remove the Node which animal belongs to from source
				source.iterator();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void moveZebras(SocialGroup<Zebra> source, Predicate<FitAnimal> predicate) {
		for (Zebra animal : source) {
			if (predicate.test(animal)) {
				this.add((T) animal);
				// TODO: remove the Node which animal belongs to from source
				source.iterator();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void moveOstriches(SocialGroup<Ostrich> source, Predicate<FitAnimal> predicate) {
		for (Ostrich animal : source) {
			if (predicate.test(animal)) {
				this.add((T) animal);
				// TODO: remove the Node which animal belongs to from source
				source.iterator();
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

		public void remove(Node<T> a) {
			if (a.getPrevious() == null && a.getNext() != null) {
				a.getNext().setPrevious(null);
				a.setNext(null);
				setHead(a.getNext());
			} else if (a.getNext() == null && a.getPrevious() != null) {
				a.getPrevious().setNext(null);
				setTail(a.getPrevious());
			} else if (a.getNext() == null && a.getPrevious() == null) {
				setHead(null);
				setTail(null);
			} else {
				a.getNext().setPrevious(a.getPrevious());
				a.getPrevious().setNext(a.getNext());
			}
		}
	}
}
