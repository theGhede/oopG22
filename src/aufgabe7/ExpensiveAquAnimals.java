package aufgabe7;

public class ExpensiveAquAnimals extends Animals {

	public ExpensiveAquAnimals(int size) {
		super(size);
	}

	@Override
	public void putInContainer(Vivarium container) {
		container.housesExpensiveAquAnimals(this);
		if (container.getInhabitant() != null && container.getInhabitant().equals(this))
			this.setContainer(container);
	}

	@Override
	public void putInSecondChoice(Vivarium container) {
	}

	@Override
	public String toString() {
		String s = "<Type: expensive aquatic animal, Size: " + this.getSize();
		if (this.getContainer() != null && !this.getShortString()) {
			this.getContainer().setShortString(true);
			s += ", Housed in:" + this.getContainer().toString();
		}
		s += ">";
		this.setShortString(false);
		return s;
	}
}
