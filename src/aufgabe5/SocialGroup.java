package aufgabe5;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/*– iterator
– add
– hierarchical
– alpha
– move mit einem Parameter vom Typ SocialGroup und einem vom Typ java.util.function.Predicate
– compareAll
- toString */

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

	public void add(T a) {
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

	// TODO
	public boolean hierarchical() {
		return false;
	}

	// TODO
	public SocialGroup<T> sort() {
		return null;
	}

	// TODO
	public Iterator<T> alpha() {
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

		// TODO setHead / setTail

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
