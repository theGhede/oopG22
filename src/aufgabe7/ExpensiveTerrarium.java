package aufgabe7;

public class ExpensiveTerrarium extends Vivarium {

	public ExpensiveTerrarium(int length, int width, int height) {
		super(length, width, height);
	}

	@Override
	public void housesCheapTerrAnimals(CheapTerrAnimals cheapTerrAnimals) {
	}

	@Override
	public void housesCheapAquAnimals(CheapAquAnimals cheapAquAnimals) {
	}

	@Override
	public void housesExpensiveTerrAnimals(ExpensiveTerrAnimals expensiveTerrAnimals) {
		this.setInhabitant(expensiveTerrAnimals);
	}

	@Override
	public void housesExpensiveAquAnimals(ExpensiveAquAnimals expensiveAquAnimals) {
	}

	@Override
	public void housesCheapTerrSecondChoice(CheapTerrAnimals cheapTerrAnimals) {
		this.setInhabitant(cheapTerrAnimals);
	}

	@Override
	public void housesCheapAquSecondChoice(CheapAquAnimals cheapAquAnimals) {
	}

	@Override
	// toString shortened for cheap animals / expensive container combination
	public String toString() {
		String s = "[Type: expensive terrarium, Size: " + this.volume();
		if (this.getInhabitant() != null && !this.getShortString()) {
			this.getInhabitant().setShortString(true);
			s += ", Houses: " + this.getInhabitant().toString();
		}
		s += "]";
		this.setShortString(false);
		return s;
	}
}
