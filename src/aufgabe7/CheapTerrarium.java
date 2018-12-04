package aufgabe7;

public class CheapTerrarium extends Terrarium {

	public CheapTerrarium(int length, int width, int height) {
		super(length, width, height);
	}

	@Override
	public void housesCheapTerrAnimals(CheapTerrAnimals cheapTerrAnimals) {
		this.setInhabitant(cheapTerrAnimals);
	}

	@Override
	public void housesCheapAquAnimals(CheapAquAnimals cheapAquAnimals) {
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
		String s = "[Type: cheap terrarium, Size: " + this.volume();
		if (this.getInhabitant() != null && !this.getShortString()) {
			this.getInhabitant().setShortString(true);
			s += ", Houses: " + this.getInhabitant().toString();
		}
		s += "]";
		this.setShortString(false);
		return s;
	}
}
