package aufgabe5;

@SuppressWarnings("hiding")
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
