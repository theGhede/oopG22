package aufgabe5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/* - toString or print method */

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
		return new SocialGroupIterator<T>(this);
	}

	public void add(T a) {
		if (head == null) {
			addTail(a);
		}
		int tailFitness = (this.tail.getCurrent()).getFitness();
		int tIndex = 0;
		int nextIndex;
		for (T t : this) {
			if (head == null) {
				addTail(a);
			} else if (a.getFitness() >= t.getFitness() && t == this.head.getCurrent()) {
				addHead(a);
				// TODO
				// e.g. 5 > a=4 < 2
			} else if (a.getFitness() >= t.getFitness()) {
				nextIndex = tIndex + 1;
				for (T next : this) {
					if (a.getFitness() < next.getFitness() && nextIndex == 0) {
						insert(a);
					}
					nextIndex--;
				}
			} else if (t.getFitness() == tailFitness) {
				addTail(a);
			}
			tIndex++;
		}
	}

	public void insert(T a) {

	}

	public void addHead(T a) {
		Node<T> node = new Node<>(a, null);
		Node<T> oldHead = new Node<>(a, null);
		
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
	public void move(SocialGroup<T> source, Predicate<T> p) {

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
