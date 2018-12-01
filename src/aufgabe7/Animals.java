package aufgabe7;

public class Animals {

	public Animals(int size) {
		this.size = size;
		this.container = null;
	}

	private Vivarium container;
	private int size;

	public Vivarium getContainer() {
		return container;
	}

	public void setContainer(Vivarium container) {
		this.container = container;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	// TODO:
	public String toString() {
		String s = "";
		return s;
	}
}
