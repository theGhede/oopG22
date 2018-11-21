package aufgabe5;

public class Node<T extends FitAnimal> {
	private T current;
	private Node<T> next;
	private Node<T> previous;
	private Node<T> nextNext;

	public Node(T current, Node<T> next) {
		this.current = current;
		this.next = next;
		this.nextNext = next.getNext();
	}

	public void setCurrent(T current) {
		this.current = current;
	}

	public T getCurrent() {
		return this.current;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getNext() {
		return this.next;
	}

	public void setPrevious(Node<T> prev) {
		this.previous = prev;
	}

	public Node<T> getPrevious() {
		return this.previous;
	}

	public void setNextNext(Node<T> nextNext) {
		this.nextNext = nextNext;
	}

	public Node<T> getNextNext() {
		return this.nextNext;
	}
}
