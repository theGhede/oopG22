package aufgabe5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/* - toString */

public class SocialGroup<T> implements Iterable<T> {

	private Node<T> head, tail;

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

	public void add(T t) {
		if (head == null) {
			addTail(t);
		}
		FitAnimal a = (FitAnimal) t;
		int tailFitness = ((FitAnimal) this.tail.getCurrent()).getFitness();
		for (T i : this) {
			FitAnimal other = (FitAnimal) i;
			if (head == null) {
				addTail(t);
			} else if (a.getFitness() >= other.getFitness() && i == this.head.getCurrent()) {
				addHead(t);
				// TODO
			} else if (a.getFitness() >= other.getFitness() && a.getFitness() < 0) {
				insert(t);
			} else if (other.getFitness() == tailFitness) {
				addTail(t);
			}

		}
	}

	public void insert(T a) {

	}

	public void addHead(T a) {

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
			if (!current.getHierarchical) return false;
		}
		return true;
	}

	// TODO
	public SocialGroup<T> sort() {
		return null;
	}

	public SocialGroup<T> alpha() {
		SocialGroup<T> potentialAlphas = new SocialGroup<T>();
		if (hierarchical()) {
			for (T current : this) {
				if (current.getAlpha) {
					potentialAlphas.add(current);
				}
			}
			return potentialAlphas;
		}
		return null;
	}

	public class SocialGroupIterator<T> implements Iterator<T> {

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
