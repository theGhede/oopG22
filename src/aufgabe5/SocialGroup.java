package aufgabe5;

import java.util.Iterator;
import java.util.function.Predicate;

/*– iterator
– add
– hierarchical
– alpha
– move mit einem Parameter vom Typ SocialGroup und einem vom Typ java.util.function.Predicate
– compareAll
- toString */

@SuppressWarnings("hiding")
public class SocialGroup<FitAnimal> implements Iterable<FitAnimal> {

	private Node<FitAnimal> head, tail;

	public Node<FitAnimal> getHead() {
		return this.head;
	}

	public Node<FitAnimal> getTail() {
		return this.tail;
	}

	@Override
	public Iterator<FitAnimal> iterator() {
		return new SocialGroupIterator<FitAnimal>(this);
	}

	public void add(FitAnimal a) {
		Node<FitAnimal> node = new Node<FitAnimal>(a, null);
		if (head == null) {
			this.head = this.tail = node;
		} else {
			this.tail.setNext(node);
			tail = node;
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

	public Iterator<FitAnimal> alpha() {
		return null;

	}

	public class SocialGroupIterator<FitAnimal> implements Iterator<FitAnimal> {

		private SocialGroup<FitAnimal>.Node<FitAnimal> current;

		public SocialGroupIterator(SocialGroup<FitAnimal> group) {
			current = group.getHead();
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public FitAnimal next() {
			if (!this.hasNext()) {
				return null;
			}
			FitAnimal animal = current.getCurrent();
			current = current.getNext();
			return animal;
		}
		
		// TODO
		public void remove(FitAnimal a) {



		}
	}

	public class Node<FitAnimal> {
		private FitAnimal current;
		private Node<FitAnimal> next;

		public Node(FitAnimal current, Node<FitAnimal> next) {
			this.current = current;
			this.next = next;
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
	}
}
