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
		int tIndex = 0;
		int nextIndex;
		for (T t : this) {
			if (head == null) {
				addTail(a);
			} else if (t == this.tail.getCurrent() && a.getFitness() <= this.tail.getCurrent().getFitness()) {
				addTail(a);
			} else if (a.getFitness() >= t.getFitness() && t == this.head.getCurrent()) {
				addHead(a);
			} else if (a.getFitness() < t.getFitness()) {
				nextIndex = tIndex + 1;
				for (T next : this) {
					if (a.getFitness() >= next.getFitness() && nextIndex == 0) {
						insert(a, t, next);
					}
					nextIndex--;
				}
			}
			tIndex++;
		}
	}

	public void insert(T a, T prev, T next) {
		Node<T> node = new Node<>(a, null);
		// TODO adding pointers for node
		// node.setNext(???);
		// node.setPrevious(???);
		
		// correcting pointers pointing towards node
		node.getNext().setPrevious(node);
		node.getPrevious().setNext(node);
	}

	public void addHead(T a) {
		// new head node [null -> a -> oldHead]
		Node<T> node = new Node<>(a, head);
		// change head
		this.head = node;
		// adjust previous of the old head to new head
		this.head.getNext().setPrevious(this.head);
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
	public void print() {
		System.out.println("Animals within this " + this.getClass().getSimpleName() + " :");
		for (T t : this) {
			System.out.println(t.toString());
		}
	}

	public void printString() {
		System.out.println("Animals within this " + this.getClass().getSimpleName() + " :");
		String s = "";
		for (T t : this) {
			s += t.toString() + "  ";
		}
		System.out.println(s);
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

	public void move(SocialGroup<T> source, Predicate<T> predicate) {
		// result should be this remaining a 'clean' group of either only
		// SteppeHerdAnimals or Starlings
		boolean steppe = false;
		// is this group already a mixed group?
		boolean mixed = false;
		for (T t : this) {
			if (predicate.test(t) && !mixed) {
				steppe = true;
			} else if (!predicate.test(t) && !mixed) {
				steppe = false;
			}
			if (predicate.test(t) && !steppe) {
				mixed = true;
			}
			if (!predicate.test(t) && steppe) {
				mixed = true;
			}
		}
		// this contains only FitAnimals which aren't SteppeHerdAnimals and Starlings
		if (!steppe && !mixed) {
			for (T animal : source) {
				if (!predicate.test(animal)) {
					this.add(animal);
					source.iterator().remove();
				}
			}
		}
		// this contains only SteppeHerdAnimals
		if (steppe && !mixed) {
			for (T animal : source) {
				if (predicate.test(animal)) {
					this.add(animal);
					source.iterator().remove();
				}
			}
		}
	}

	@SuppressWarnings("hiding")
	public class TypePredicates<T> {
		/*
		 * NOTE: SonarLint linter recommends turning this into a lambda function & after
		 * doing so it recommends using methods so it is hard to be certain which is the
		 * preferred option, but since test() was explicitly mentioned this seemed like
		 * it
		 */
		public Predicate<T> typePredicate = new Predicate<T>() {
			public boolean test(T t) {
				return (SteppeHerdAnimal.class.isInstance(t));
			}
		};
	}

	@SuppressWarnings("hiding")
	public class SocialGroupIterator<T extends FitAnimal> implements Iterator<T> {

		private Node<T> current;

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
