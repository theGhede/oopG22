package aufgabe7;

public class CheapAquarium extends Vivarium {

	public CheapAquarium(int length, int width, int height) {
		super(length, width, height);
	}

	@Override
	public void housesCheapTerrAnimals(CheapTerrAnimals cheapTerrAnimals) {
	}

	@Override
	public void housesCheapAquAnimals(CheapAquAnimals cheapAquAnimals) {
		this.setInhabitant(cheapAquAnimals);
	}

	@Override
	public void housesExpensiveTerrAnimals(ExpensiveTerrAnimals expensiveTerrAnimals) {
	}

	@Override
	public void housesExpensiveAquAnimals(ExpensiveAquAnimals expensiveAquAnimals) {
	}

	@Override
	public void housesCheapTerrSecondChoice(CheapTerrAnimals cheapTerrAnimals) {
	}

	@Override
	public void housesCheapAquSecondChoice(CheapAquAnimals cheapAquAnimals) {
	}

	@Override
	public String toString() {
		String s = "[Type: cheap aquarium, Size: " + this.volume();
		if (this.getInhabitant() != null && !this.getShortString()) {
			this.getInhabitant().setShortString(true);
			s += ", Houses: " + this.getInhabitant().toString();
		}
		s += "]";
		this.setShortString(false);
		return s;
	}
}
