package aufgabe6;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class AllPacks implements Iterable {

	private Node head;
	private Node tail;
	private Node beforeTail;
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

	public AllPacks(String name) {
		this.name = name;
	}

	@Override
	public Iterator iterator() {
		return new AllPacksIterator(this);
	}

	public void add(Object o) {
		if (Pack.class.isInstance(o)) {
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
		if (this.head == null)
			System.out.println("This pack of packs is empty");
		for (Object o : this) {
			System.out.println("Pack '" + ((Pack) o).getName() + "':");
			((Pack) o).print();
		}
	}

	public void removeByName(String name) {
		boolean hasName = false;
		Iterator itr = new AllPacksIterator(this);
		int index = 0;
		Object needsNewTail = null;
		for (Object a : this) {
			if (((Pack) a).getName().equals(name))
				hasName = true;
			needsNewTail = a;
		}
		if (this.head != null && this.tail != null && this.head.getCurrent().equals(this.tail.getCurrent())
				&& ((Pack) this.head.getCurrent()).getName().equals(name)) {
			this.head = this.tail = null;
		} else if (((Pack) this.head.getCurrent()).getName().equals(name) && this.head.getNext() != null) {
			this.head = this.head.getNext();
		} else if (hasName) {
			for (Object o : this) {
				if (((Pack) o).getName().equals(name))
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
		if (needsNewTail != null && ((Pack) needsNewTail).getName().equals(name) && this.head != null
				&& this.tail != null && !this.head.getCurrent().equals(this.tail.getCurrent())) {
			this.tail = this.beforeTail;
			index = 0;
			for (@SuppressWarnings("unused")
			Object t : this) {
				index++;
			}
			for (Object s : this) {
				index--;
				if (index == 1) {
					Node newBeforeTail = new Node(s, this.tail);
					this.beforeTail = newBeforeTail;
					
				}
			}
		}
	}

	public class AllPacksIterator implements Iterator {

		private Node current;
		private Node previous;

		public Node getCurrent() {
			return this.current;
		}

		public AllPacksIterator(AllPacks packs) {
			this.current = packs.getHead();
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
			Object ret = current.getCurrent();
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
