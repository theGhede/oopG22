package aufgabe6;

public class Node {
	private Object current;
	private Node next;

	public Node(Object current, Node next) {
		this.current = current;
		this.next = next;
	}

	public void setCurrent(Object current) {
		this.current = current;
	}

	public Object getCurrent() {
		return this.current;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getNext() {
		return this.next;
	}
}
