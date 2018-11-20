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

@SuppressWarnings("hiding")
public class SocialGroup<FitAnimal> implements Iterable<FitAnimal> {

	private Node<FitAnimal> head, tail;

	public Node<FitAnimal> getHead() {
		return this.head;
	}
	
	public void setHead(Node<?> head) {
		this.head = (Node<FitAnimal>) head;
	}

	public Node<FitAnimal> getTail() {
		return this.tail;
	}

	@SuppressWarnings("unchecked")
	public void setTail(Node<?> tail) {
		this.tail = (Node<FitAnimal>) tail;
	}

	@Override
	public Iterator<FitAnimal> iterator() {
		return new SocialGroupIterator<FitAnimal>(this);
	}

	public void add(FitAnimal a) {
		Node<FitAnimal> node = new Node<>(a, null);
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
		for (Node <FitAnimal> current : this) {
			if (!current.getHierarchical) return false;
		}

		return true;
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

		private Node<FitAnimal> current;

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

		// TODO setHead / setTail

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
}
