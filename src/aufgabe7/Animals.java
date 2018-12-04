package aufgabe7;

public abstract class Animals {

	private Vivarium container;
	private int size;
	private boolean owned;
	// variable for shortened toString() if called by toString() of a Vivarium
	private boolean shortString;

	// precondition - size ist ein sinnvoller Wert, der das Volumen eines Tieres
	// beschreibt (size > 0)
	public Animals(int size) {
		this.size = size;
		this.container = null;
		this.setOwned(false);
		this.shortString = false;
	}

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

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public boolean getShortString() {
		return shortString;
	}

	public void setShortString(boolean shortString) {
		this.shortString = shortString;
	}

	public abstract void putInContainer(Vivarium container);

	public abstract void putInSecondChoice(Vivarium container);

	public abstract String toString();
}
