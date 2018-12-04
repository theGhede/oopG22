package aufgabe7;

public class CheapAquAnimals extends Animals {

	public CheapAquAnimals(int size) {
		super(size);
	}

	@Override
	public void putInContainer(Vivarium container) {
		container.housesCheapAquAnimals(this);
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public void putInSecondChoice(Vivarium container) {
		container.housesCheapAquSecondChoice(this);
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public String toString() {
		String s = "<Type: cheap aquatic animal, Size: " + this.getSize();
		if (this.getContainer() != null && !this.getShortString()) {
			this.getContainer().setShortString(true);
			s += ", Housed in:" + this.getContainer().toString();
		}
		s += ">";
		this.setShortString(false);
		return s;
	}
}
