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

public class SocialGroup<FitAnimal> implements Iterable<FitAnimal> {

	private Node<FitAnimal> head, tail;

	public Node<FitAnimal> getHead() {
		return this.head;
	}

	public void setHead(SocialGroup<FitAnimal>.Node<FitAnimal> node) {
		this.head = node;
	}

	public Node<FitAnimal> getTail() {
		return this.tail;
	}

	public void setTail(Node<FitAnimal> tail) {
		this.tail = tail;
	}

	@Override
	public Iterator<FitAnimal> iterator() {
		return new SocialGroupIterator<FitAnimal>(this);
	}

	public void add(FitAnimal a) {
		Node<FitAnimal> node = new Node<FitAnimal>(a, null);
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
	public void move(SocialGroup<FitAnimal> source, Predicate<FitAnimal> p) {

	}

	// TODO
	public void compareAll() {

	}

	// TODO
	public boolean hierarchical() {
		return false;

	}

	// TODO
	public SocialGroup<FitAnimal> sort() {
		return null;
	}

	// TODO
	public Iterator<FitAnimal> alpha() {
		return null;

	}

	public class SocialGroupIterator<FitAnimal> implements Iterator<FitAnimal> {

		private SocialGroup<FitAnimal>.Node<FitAnimal> current;

		public SocialGroupIterator(SocialGroup<FitAnimal> group) {
			this.current = group.getHead();
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public FitAnimal next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			FitAnimal animal = current.getCurrent();
			this.current = this.current.getNext();
			return animal;
		}

		public void remove(Node<FitAnimal> a) {
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

	public class Node<FitAnimal> {
		private FitAnimal current;
		private Node<FitAnimal> next;
		private Node<FitAnimal> previous;
		private Node<FitAnimal> nextNext;

		public Node(FitAnimal current, Node<FitAnimal> next) {
			this.current = current;
			this.next = next;
			this.nextNext = next.getNext();
		}

		public void setCurrent(FitAnimal current) {
			this.current = current;
		}

		public FitAnimal getCurrent() {
			return this.current;
		}

		public void setNext(Node<FitAnimal> next) {
			this.next = next;
		}

		public Node<FitAnimal> getNext() {
			return this.next;
		}

		public void setPrevious(Node<FitAnimal> prev) {
			this.previous = prev;
		}

		public Node<FitAnimal> getPrevious() {
			return this.previous;
		}

		public void setNextNext(Node<FitAnimal> nextNext) {
			this.nextNext = nextNext;
		}

		public Node<FitAnimal> getNextNext() {
			return this.nextNext;
		}
	}
}
