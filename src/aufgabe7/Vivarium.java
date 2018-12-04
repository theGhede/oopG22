package aufgabe7;

public abstract class Vivarium {

	private int length;
	private int width;
	private int height;
	private boolean intact;
	private Animals inhabitant;
	// variable for shortened toString() if called by toString() of a Vivarium
	private boolean shortString;

	public Vivarium(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
		this.intact = true;
		this.inhabitant = null;
		this.shortString = false;
	}

	public int volume() {
		int vol = -1;
		vol = this.length * this.width * this.height;
		return vol;
	}

	public boolean isFree() {
		return this.inhabitant != null;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isIntact() {
		return intact;
	}

	public void setIntact(boolean intact) {
		this.intact = intact;
	}

	public Animals getInhabitant() {
		return inhabitant;
	}

	public void setInhabitant(Animals inhabitant) {
		this.inhabitant = inhabitant;
	}

	public boolean getShortString() {
		return shortString;
	}

	public void setShortString(boolean shortString) {
		this.shortString = shortString;
	}

	public abstract void housesCheapTerrAnimals(CheapTerrAnimals cheapTerrAnimals);

	public abstract void housesCheapAquAnimals(CheapAquAnimals cheapAquAnimals);

	public abstract void housesExpensiveTerrAnimals(ExpensiveTerrAnimals expensiveTerrAnimals);

	public abstract void housesExpensiveAquAnimals(ExpensiveAquAnimals expensiveAquAnimals);
	
	public abstract void housesCheapTerrSecondChoice(CheapTerrAnimals cheapTerrAnimals);

	public abstract void housesCheapAquSecondChoice(CheapAquAnimals cheapAquAnimals);

	public abstract String toString();
}
