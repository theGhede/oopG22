package aufgabe7;

public class ExpensiveTerrAnimals extends Animals {

	public ExpensiveTerrAnimals(int size) {
		super(size);
	}

	@Override
	public void putInContainer(Vivarium container) {
		container.housesExpensiveTerrAnimals(this);
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public void putInSecondChoice(Vivarium container) {
	}

	@Override
	public String toString() {
		String s = "<Type: expensive terrestrial animal, Size: " + this.getSize();
		if (this.getContainer() != null && !this.getShortString()) {
			this.getContainer().setShortString(true);
			s += ", Housed in:" + this.getContainer().toString();
		}
		s += ">";
		this.setShortString(false);
		return s;
	}
}
