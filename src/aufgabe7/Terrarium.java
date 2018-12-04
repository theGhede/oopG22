package aufgabe7;

public abstract class Terrarium extends Vivarium {

	public Terrarium(int length, int width, int height) {
		super(length, width, height);
	}

	@Override
	public abstract void housesCheapTerrAnimals(CheapTerrAnimals cheapTerrAnimals);

	@Override
	public abstract void housesCheapAquAnimals(CheapAquAnimals cheapAquAnimals);

	@Override
	public abstract void housesExpensiveTerrAnimals(ExpensiveTerrAnimals expensiveTerrAnimals);

	@Override
	public abstract void housesExpensiveAquAnimals(ExpensiveAquAnimals expensiveAquAnimals);

	@Override
	public abstract void housesCheapTerrSecondChoice(CheapTerrAnimals cheapTerrAnimals);

	@Override
	public abstract void housesCheapAquSecondChoice(CheapAquAnimals cheapAquAnimals);

	@Override
	public abstract String toString();
}
